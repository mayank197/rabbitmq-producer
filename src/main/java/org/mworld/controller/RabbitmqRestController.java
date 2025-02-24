package org.mworld.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class RabbitmqRestController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final Logger logger = LoggerFactory.getLogger(RabbitmqRestController.class);

    public static boolean isValidJson(String string) {
        try {
            new ObjectMapper().readTree(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Sample controller to test
    @PostMapping(path = {"/api/publish/{exchange}/{routingKey}", "/api/publish/{exchange}"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> publish(
            @PathVariable(name = "exchange") String exchange,
            @PathVariable(name = "routingKey", required = false) Optional<String> routingKey,
            @RequestBody String message) {
        if (!isValidJson(message)) {
            return ResponseEntity.badRequest().body(Boolean.FALSE.toString());
        }
        try {
            rabbitTemplate.convertAndSend(exchange, routingKey.orElse(""), message);
            return ResponseEntity.ok().body(Boolean.TRUE.toString());
        } catch (Exception e) {
            logger.error("Error when publishing : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
