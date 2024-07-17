package com.example.elasticsearch.service;

import com.example.elasticsearch.document.ChattingRoomEntity;
import com.example.elasticsearch.repository.ChattingRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChattingRoomService {
    private final ChattingRoomRepository chattingRoomRepository;

    public List<ChattingRoomEntity> searchChattingRoomFromName(String name) {
        return chattingRoomRepository.findAllByName(name);
    }

    public List<ChattingRoomEntity> searchChattingRoomFromLikeName(String name) {
        return chattingRoomRepository.findByName(name);
    }

    public Iterable<ChattingRoomEntity> findAll() {
        return chattingRoomRepository.findAll();
    }

    public List<ChattingRoomEntity> findByFromName(String name) {
        return chattingRoomRepository.findByFromName(name);
    }
}
