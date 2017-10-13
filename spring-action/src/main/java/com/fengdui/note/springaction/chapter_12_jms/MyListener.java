package com.fengdui.note.springaction.chapter_12_jms;

import com.fengdui.chapter_12_jms.listener.SessionAwareBatchMessageListener;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.List;

/**
 * @author FD
 * @date 2017/3/24
 */

@Component
public class MyListener extends MessageListenerAdapter implements SessionAwareBatchMessageListener<TextMessage> {
    @Override
    public void onMessages(Session session, List<TextMessage> messages) throws JMSException {

    }
}
