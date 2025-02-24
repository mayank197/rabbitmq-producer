package org.mworld.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mworld.pojo.Picture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class PictureProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final static Logger logger = LoggerFactory.getLogger(PictureProducer.class);

    public void sendMessage(Picture picture) {
        try {
            String json = new ObjectMapper().writeValueAsString(picture);
            rabbitTemplate.convertAndSend("x.picture", picture.getType(), json);
        } catch (JsonProcessingException e) {
            logger.error("Error while sending picture : {}", e.getMessage());
        }
    }

    // For Topic exchange example - Routing Key will be in "source.size.type" format
    public void sendMessage2(Picture picture) {
        String routingKey = Arrays.asList(
                picture.getSource(),
                picture.getSize() > 4000 ? "large" : "small",
                picture.getType()
        ).stream().map(String::valueOf).collect(Collectors.joining("."));
        logger.info("## Routing Key : {}", routingKey);
        try {
            String json = new ObjectMapper().writeValueAsString(picture);
                rabbitTemplate.convertAndSend("x.picture2", routingKey, json);
        } catch (JsonProcessingException e) {
            logger.error("Error while sending picture : {}", e.getMessage());
        }
    }

    // For DLX example
    public void sendMessage3(Picture picture) {
        try {
            String json = new ObjectMapper().writeValueAsString(picture);
            rabbitTemplate.convertAndSend("x.mypicture", "", json);
        } catch (JsonProcessingException e) {
            logger.error("Error while sending picture : {}", e.getMessage());
        }
    }

}
