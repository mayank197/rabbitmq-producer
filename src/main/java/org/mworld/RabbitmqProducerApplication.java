package org.mworld;

import org.mworld.pojo.DummyMessage;
import org.mworld.pojo.Picture;
import org.mworld.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@EnableScheduling
@SpringBootApplication
//public class RabbitmqProducerApplication {
public class RabbitmqProducerApplication implements CommandLineRunner {

    @Autowired
    private HelloRabbitProducer helloRabbitProducer;

    @Autowired
    private EmployeeProducer employeeProducer;

    @Autowired
    private HumanResourceProvider humanResourceProvider;

    @Autowired
    private PictureProducer pictureProducer;

    @Autowired
    private RetryPictureProducer retryPictureProducer;

    private static final Logger logger = LoggerFactory.getLogger(RabbitmqProducerApplication.class);
    @Autowired
    private AnotherDummyProducer anotherDummyProducer;

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqProducerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//      helloRabbitProducer.sendHello("Mayank " +Math.random());   // basic example

        // For employee use case
        /*
        IntStream.rangeClosed(1, 30).forEach(val -> {
            Employee employee = new Employee(String.valueOf(val), "Emp"+val, new Timestamp(new Date().getTime()));
            employeeProducer.sendMessage(employee);      // pushing an object (json) to queue example
            humanResourceProvider.sendMessage(employee); // fanout example
        });
        */

        List<String> types = Arrays.asList("jpg", "png", "svg");
        List<String> sources = Arrays.asList("mobile", "web");

        // for picture use case
        IntStream.rangeClosed(1, 10).forEach(val -> {
            Picture picture = new Picture();
            picture.setName("Picture "+val);
            picture.setSize(new Random().nextInt(10000));
            picture.setSource(sources.get(val % sources.size()));
            picture.setType(types.get(val % types.size()));
//            logger.info("Sending picture {}", picture);
            // pictureProducer.sendMessage(picture);    // for direct exchange example
            // pictureProducer.sendMessage2(picture);   // for topic exchange example

            // to produce, we're setting this size to be more than 9000.. and we'll throw error based on this
            picture.setSize(ThreadLocalRandom.current().nextLong(9001, 10001));

            // pictureProducer.sendMessage3(picture);     // for DLX example

            // retryPictureProducer.sendMessage(picture);
        });

        // example for queue/exchange creation by Java code
        DummyMessage message = new DummyMessage("sending to exchange which doesn't exist", 1);
        anotherDummyProducer.sendMessage(message);

    }
}
