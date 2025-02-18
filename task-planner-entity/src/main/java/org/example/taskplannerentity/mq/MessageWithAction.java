package org.example.taskplannerentity.mq;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MessageWithAction {

    private long id;
    private MessageAction action;
}
