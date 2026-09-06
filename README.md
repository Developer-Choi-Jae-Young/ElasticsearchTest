# Elasticsearch & ELK Stack + MySQL Integration Project

본 프로젝트는 Docker Compose를 활용하여 **Elasticsearch, Logstash, Kibana (ELK Stack)**와 **MySQL** 데이터베이스 간의 실시간/주기적 데이터 동기화 환경 구축 및 **한글 초성/자모 분석기(Jaso Analyzer)** 플러그인이 적용된 Elasticsearch 검색 엔진을 제공합니다.

---

## 📌 주요 특징 (Key Features)

1. **Docker Compose 기반 Elastic Stack (v8.11.4)**
   - Elasticsearch, Logstash, Kibana 8.x 서비스 통합 구동
   - 초기 보안 계정 및 권한(Logstash Internal, Kibana System 등) 자동 생성을 위한 **Setup Service** 제공

2. **MySQL - Logstash - Elasticsearch 자동 동기화 (JDBC Pipeline)**
   - Logstash JDBC Input Plugin을 사용해 MySQL DB (`server` 데이터베이스) 데이터를 5초 간격으로 폴링.
   - Elasticsearch 인덱스 (`mysql-test`)로 자동 매핑 및 문서 동기화.

3. **한글 자모/초성 분석기 (Jaso Analyzer Plugin) 연동**
   - 한글 검색 품질 향상을 위한 초성/자모 분리 검색 분석 플러그인 (`elasticsearch-jaso-analyzer`) 포함.
   - Gradle 빌드를 통해 커스텀 `.zip` 플러그인을 작성 및 Elasticsearch Docker 이미지 빌드 시 자동 설치.

4. **다양한 Elastic Extensions 확장 지원**
   - Filebeat, Metricbeat, Heartbeat, Curator, Logspout, Fleet 등 모니터링 및 로그 수집 확장 모듈 포함.

---

## 📁 프로젝트 구조 (Project Structure)

```
.
├── ELK_MYSQL/                          # ELK Stack + MySQL Docker Compose 환경
│   ├── docker-compose.yml              # 전체 서비스 (Elasticsearch, Logstash, Kibana, MySQL, Setup) 정의
│   ├── elasticsearch/                  # Elasticsearch 설정 & Jaso Analyzer 플러그인 설치 Dockerfile
│   │   ├── Dockerfile
│   │   ├── config/elasticsearch.yml
│   │   └── plugin/                      # 빌드된 jaso-analyzer-plugin zip 파일 위치
│   ├── logstash/                       # Logstash 파이프라인 및 JDBC 커넥터 설정
│   │   ├── config/
│   │   ├── jdbc/                        # MySQL Connector JAR (mysql-connector-j-8.2.0.jar)
│   │   ├── pipeline/                    # mysql.conf 등 DB 동기화 파이프라인
│   │   └── template/                    # Elasticsearch 인덱스 템플릿
│   ├── kibana/                         # Kibana 설정
│   ├── setup/                          # 초기 보안 계정 및 비밀번호 세팅 스크립트
│   └── extensions/                     # Filebeat, Metricbeat, Curator 등 옵션 확장 기능
│
└── elasticsearch-jaso-analyzer-master/ # 한글 자모 분석기 Elasticsearch 플러그인 (Gradle Java)
    └── elasticsearch-jaso-analyzer-master/
        ├── build.gradle                # ES 8.11.4 호환 빌드 설정
        └── src/                        # 자모 분리/초성 추출 토크나이저 및 필터 소스코드
```

---

## 🚀 시작하기 (Getting Started)

### 사전 요구 사항 (Prerequisites)
- Docker & Docker Compose
- Java 17+ & Gradle (자모 분석기 플러그인 수정/재빌드 시)

---

### 1. 한글 자모 분석기 플러그인 빌드 (선택 사항)
이미 `ELK_MYSQL/elasticsearch/plugin/` 경로에 빌드된 `.zip` 플러그인이 준비되어 있으며, 플러그인을 수정하여 재빌드하려는 경우 아래 명령어를 실행합니다.

```bash
cd elasticsearch-jaso-analyzer-master/elasticsearch-jaso-analyzer-master
./gradlew buildPluginZip
```
*(빌드 후 생성된 `build/distributions/jaso-analyzer-plugin-8.11.4-plugin.zip`을 `ELK_MYSQL/elasticsearch/plugin/`으로 복사합니다.)*

---

### 2. ELK Stack & MySQL 컨테이너 구동

1. **초기 사용자/권한 생성 (Setup 서비스 실행)**
   ```bash
   cd ELK_MYSQL
   docker-compose up setup
   ```
   > `setup` 컨테이너는 `.env` 파일에 정의된 패스워드를 기반으로 `logstash_internal`, `kibana_system` 등 시스템 계정을 초기화합니다.

2. **전체 서비스 실행 (Elasticsearch, Logstash, Kibana, MySQL)**
   ```bash
   docker-compose up -d
   ```

3. **서비스 컨테이너 상태 확인**
   ```bash
   docker-compose ps
   ```

---

## 🌐 서비스 접속 및 포트 정보

| 서비스 | 호스트 포트 | 컨테이너 포트 | 설명 |
| :--- | :--- | :--- | :--- |
| **Elasticsearch** | `9200`, `9300` | `9200`, `9300` | REST API (`http://localhost:9200`) 및 노드 간 통신 |
| **Kibana** | `5601` | `5601` | 데이터 시각화 웹 UI (`http://localhost:5601`) |
| **Logstash** | `5044`, `50000`, `9600` | `5044`, `50000`, `9600` | Beats / TCP / UDP 수집 및 관리 포트 |
| **MySQL** | `5000` | `3306` | MySQL DB 접속 (Host: `localhost:5000`, DB: `server`, 계정: `root` / `root`) |

---

## 🔄 MySQL - Elasticsearch 데이터 동기화 동작 방식

Logstash 파이프라인 (`ELK_MYSQL/logstash/pipeline/mysql.conf`)을 통해 자동 연동됩니다:

1. Logstash가 5초 간격(`*/5 * * * * *`)으로 MySQL DB (`server` DB의 `test` 테이블) 변경 사항을 조회합니다.
2. 조회 결과는 `id` 필드를 문서 ID(`_id`)로 사용하여 Elasticsearch `mysql-test` 인덱스에 저장/갱신합니다.
3. Elasticsearch에는 `jaso-analyzer-plugin`이 장착되어 있어, 한글 초성 검색(예: 'ㅎㄱㄷ') 및 자모 분리 검색을 지원합니다.

---

## 🛠️ 환경 변수 설정 (.env)

`ELK_MYSQL/.env` 파일에서 버전 및 비밀번호를 관리합니다:
- `ELASTIC_VERSION`: `8.11.4`
- `ELASTIC_PASSWORD`: Elasticsearch 슈퍼유저 비밀번호
- `LOGSTASH_INTERNAL_PASSWORD`: Logstash 연동 비밀번호
- `KIBANA_SYSTEM_PASSWORD`: Kibana 시스템 연동 비밀번호
