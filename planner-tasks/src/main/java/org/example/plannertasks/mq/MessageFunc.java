package org.example.plannertasks.mq;

import org.example.plannertasks.service.TestDataService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Configuration
public class MessageFunc {

    private TestDataService testDataService;

    @Bean
    public Consumer<Message<Long>> newUserActionConsume() {
        return message -> testDataService.initTestData(message.getPayload());
    }
}
