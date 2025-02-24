package org.mworld.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mworld.pojo.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeProducer.class);

    public void sendMessage(Employee employee) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(employee);
            rabbitTemplate.convertAndSend("course.employee", json);
            Thread.sleep(2000);
        } catch (JsonProcessingException | InterruptedException e) {
            logger.error("Exception while writing employee to queue : ", e);
        }
    }

}
