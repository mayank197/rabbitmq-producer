package org.mworld.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mworld.pojo.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HumanResourceProvider {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final Logger logger = LoggerFactory.getLogger(HumanResourceProvider.class);

    public void sendMessage(Employee employee) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(employee);
            rabbitTemplate.convertAndSend("x.hr", "", json);
        } catch (Exception e) {
            logger.error("Exception occurred", e);
        }
    }

}
