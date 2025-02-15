package org.example.plannertasks.mq;

import lombok.RequiredArgsConstructor;
import org.example.plannertasks.service.TestDataService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@RequiredArgsConstructor
@Configuration
public class MessageFunc {

    private final TestDataService testDataService;

    @Bean
    public Consumer<Message<Long>> newUserActionConsume() {
        return message -> testDataService.initTestData(message.getPayload());
    }
}
