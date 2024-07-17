package com.example.elasticsearch.controller;

import com.example.elasticsearch.document.ChattingRoomEntity;
import com.example.elasticsearch.service.ChattingRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class ChattingRoomController {
    private final ChattingRoomService chattingRoomService;

    @GetMapping("/search/name")
    public void SearchChattingRoom(@RequestParam String name) {
        for(ChattingRoomEntity chattingRoom : chattingRoomService.searchChattingRoomFromName(name)) {
            log.info("search : " + name);
            log.info("chattingRoom = " + chattingRoom);
        }
    }

    @GetMapping("/search/likename")
    public void SearchLikeChattingRoom(@RequestParam String name) {
        for(ChattingRoomEntity chattingRoom : chattingRoomService.searchChattingRoomFromLikeName(name)) {
            log.info("search : " + name);
            log.info("chattingRoom = " + chattingRoom);
        }
    }

    @GetMapping("/search/test/name")
    public void SearchLikeChattingRoomTest(@RequestParam String name) {
        for(ChattingRoomEntity chattingRoom : chattingRoomService.findByFromName(name)) {
            log.info("search : " + name);
            log.info("chattingRoom = " + chattingRoom);
        }
    }


    @GetMapping("/search")
    public Iterable<ChattingRoomEntity> findAll() {
        return chattingRoomService.findAll();
    }
}
