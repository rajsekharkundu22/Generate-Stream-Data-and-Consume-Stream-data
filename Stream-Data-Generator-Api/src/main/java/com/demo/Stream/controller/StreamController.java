package com.demo.Stream.controller;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;

@RestController
public class StreamController {

    @GetMapping(value = "/stream/testdata", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<StreamResponse> streamTestData() {
        System.out.println("Entering stream data");

        return Flux
                .interval(Duration.ofMillis(500))  // Emit a new item every 500 milliseconds
                .take(50)  // Limit to 50 items for this example
                .map(tick -> {
                    // Create a new StreamResponse for each emitted item
                    StreamResponse response = new StreamResponse();
                    response.setObject("chat.completion.chunk");
                    response.setModel("openai/gpt-4-turbo");

                    // Create a choice object
                    StreamResponse.Choice choice = new StreamResponse.Choice();
                    choice.setIndex(0);

                    // Create a Delta object containing the content
                    StreamResponse.Choice.Delta delta = new StreamResponse.Choice.Delta();
                    delta.setContent("Test Data: " + System.currentTimeMillis());

                    // Set the Delta into the choice
                    choice.setDelta(delta);

                    // Set the choice into the response
                    response.setChoices(Arrays.asList(choice));

                    return response;  // Return the response
                });
    }
}
