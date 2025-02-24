package org.mworld.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mworld.pojo.Picture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RetryPictureProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final static Logger logger = LoggerFactory.getLogger(PictureProducer.class);

    public void sendMessage(Picture picture) {
        try {
            String json = new ObjectMapper().writeValueAsString(picture);
            // directly broadcast message to the exchange
            rabbitTemplate.convertAndSend("x.guideline.work", "", json);
        } catch (JsonProcessingException e) {
            logger.error("Error while sending picture : {}", e.getMessage());
        }
    }

}
