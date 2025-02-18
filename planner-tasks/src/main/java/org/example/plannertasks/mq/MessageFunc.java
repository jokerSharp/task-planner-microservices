package org.example.plannertasks.mq;

import lombok.RequiredArgsConstructor;
import org.example.plannertasks.service.TestDataService;
import org.example.plannertasks.service.UserRelatedService;
import org.example.taskplannerentity.mq.MessageWithAction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Configuration
public class MessageFunc {

    private final TestDataService testDataService;
    private final List<UserRelatedService> userRelatedServices;

    @Bean
    public Consumer<Message<MessageWithAction>> userActionsConsume() {
        return message -> {
            switch (message.getPayload().getAction()) {
                case CREATE -> testDataService.initTestData(message.getPayload().getId());
                case DELETE ->
                        userRelatedServices.forEach(service -> service.deleteByUserId(message.getPayload().getId()));
            }
        };
    }
}
