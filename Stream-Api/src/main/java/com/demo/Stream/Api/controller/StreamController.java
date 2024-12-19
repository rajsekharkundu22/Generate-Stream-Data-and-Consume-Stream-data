package com.demo.Stream.Api.controller;

import com.demo.Stream.Api.service.StreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("api/v1/chat")
public class StreamController {

    private final StreamService streamService;

    @Autowired
    public StreamController(StreamService streamService) {
        this.streamService = streamService;
    }

    @GetMapping(value = "/completions", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatCompletion() {
        return streamService.getStreamFromExternalApi();
    }


}
