package com.example.elasticsearch.repository;

import com.example.elasticsearch.document.ChattingRoomEntity;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChattingRoomRepository extends ElasticsearchRepository<ChattingRoomEntity, String> {
    List<ChattingRoomEntity> findAllByName(String name);
    List<ChattingRoomEntity> findByName(String name);

    @Query ("{\"bool\": { \"must\": [ \n" +
            "    {\"wildcard\": {\"name\": \"*?0*\"}}]}}")
    List<ChattingRoomEntity> findByFromName(String name);
}
