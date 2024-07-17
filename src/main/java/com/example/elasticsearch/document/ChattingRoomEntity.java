package com.example.elasticsearch.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "mysql")
public class ChattingRoomEntity {
    @Id
    private String id;
    private String name;
}
