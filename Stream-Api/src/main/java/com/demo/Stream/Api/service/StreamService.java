package com.demo.Stream.Api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Service
public class StreamService {
    private final WebClient webClient;


    @Autowired
    public StreamService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:7070").build();
    }

    public Flux<String> getStreamFromExternalApi() {
        // Make a request to the external API stream and transform the response

        return webClient.get()
                .uri("/stream/testdata")
                .retrieve()
                .bodyToFlux(String.class)  // Stream the response body as a String
                .map(this::transformToResult); // Transform each response
    }

    private String transformToResult(String response) {
        // Extract "content" from the original response and map it to the new structure
        // Assuming that the input response is a JSON string, we will parse it.

        // Parse the response JSON and extract "content"
        // Example: {"object": "chat.completion.chunk", "model": "openai/gpt-4-turbo", "choices": [{"index": 0, "delta": {"content": "Test Data: 1734627680339"}}]}
        try {
            // Use a JSON library like Jackson or Gson to parse the response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode node = objectMapper.readTree(response);

            // Extract the "content" value
            String content = node.path("choices").get(0).path("delta").path("content").asText();

            // Construct the new response in the desired format
            String messageId = "unique-message-id-" + UUID.randomUUID().toString(); // Generate a unique messageId for each response

            // Construct the new response format
            String transformedResponse = String.format(
                    "{\"status\":\"success\",\"data\":{\"messageId\":\"%s\",\"result\":\"%s\"}}",
                    messageId, content
            );

            return transformedResponse;
        } catch (Exception e) {
            e.printStackTrace(); // Handle error if necessary
            return "{\"status\":\"error\",\"message\":\"Error processing response\"}";
        }
    }

}
