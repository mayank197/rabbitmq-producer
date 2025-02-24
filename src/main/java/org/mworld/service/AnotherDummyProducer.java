package org.mworld.service;

import org.mworld.pojo.DummyMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnotherDummyProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(DummyMessage message) {
        rabbitTemplate.convertAndSend("x.another-dummy", "", message);
    }

}
