package org.example.plannerusers.mq;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@RequiredArgsConstructor
@Getter
@Service
public class MessageFuncActions {

    private final MessageFunc messageFunc;

    public void sendNewUserMessage(Long userId) {
        messageFunc.getInnerBus().emitNext(MessageBuilder.withPayload(userId).build(), Sinks.EmitFailureHandler.FAIL_FAST);
    }
}
