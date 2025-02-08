package org.example.plannerutils.client;

import org.example.taskplannerentity.entity.User;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class UserWebClientBuilder {

    private static final String baseUrl = "http://localhost:8765/planner-users/api/users/";

    public boolean userExists(Long userId) {
        try {
            User user = WebClient.create(baseUrl)
                    .post()
                    .uri("id")
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
}
