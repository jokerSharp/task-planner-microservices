package org.example.plannerutils.client;

import org.example.taskplannerentity.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class UserWebClientBuilder {

    private static final String baseUrl = "http://localhost:8765/";

    public boolean userExists(Long userId) {
        try {
            User user = WebClient.create(baseUrl)
                    .post()
                    .uri("planner-users/api/users/id")
                    .bodyValue(userId)
                    .retrieve()
                    .bodyToFlux(User.class)
                    .blockFirst();
            if (user != null) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Flux<Boolean> initUserData(Long id) {
        return WebClient.create(baseUrl)
                .post()
                .uri("planner-tasks/api/test-data/init")
                .bodyValue(id)
                .retrieve()
                .bodyToFlux(Boolean.class);
    }
}
