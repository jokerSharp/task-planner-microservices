package org.example.plannerusers.mq;

import lombok.RequiredArgsConstructor;
import org.example.taskplannerentity.mq.MessageAction;
import org.example.taskplannerentity.mq.MessageWithAction;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@RequiredArgsConstructor
@Service
public class MessageFuncActions {

    private final MessageFunc messageFunc;

    public void sendUserMessage(Long userId, MessageAction action) {
        messageFunc.getInnerBus().emitNext(MessageBuilder.withPayload(new MessageWithAction(userId, action)).build(), Sinks.EmitFailureHandler.FAIL_FAST);
    }
}
