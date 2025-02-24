package org.mworld.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FixedRateProducer {

    private static final Logger logger = LoggerFactory.getLogger(FixedRateProducer.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private int i = 0;
    private final long startTime = System.currentTimeMillis();

    // Send every 500ms
//    @Scheduled(fixedRate = 500)
    private void sendMessage() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        if (elapsedTime > 50000) { // Stop after 50 seconds
            logger.info("Message threshold reached");
            return;
        }
        i++;
        logger.info("Value of i : {}", i);
        rabbitTemplate.convertAndSend("course.fixedRate", "Fixed rate " +i);
    }

}
