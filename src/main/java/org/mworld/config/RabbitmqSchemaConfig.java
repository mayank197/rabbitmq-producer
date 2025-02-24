package org.mworld.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqSchemaConfig {

    @Bean
    public FanoutExchange createFanoutExchange() {
        return new FanoutExchange("x.another-dummy", true, false, null);
    }

    @Bean
    public Queue createQueue() {
        return new Queue("q.another-dummy");
    }

    @Bean
    public Binding createBinding() {
        // way 1
        // return new Binding("q.another-dummy", Binding.DestinationType.QUEUE, "x.another-dummy", null, null);
        // way 2 - easier
        return BindingBuilder.bind(createQueue()).to(createFanoutExchange());
    }

    @Bean
    public Declarables createRabbitmqSchema() {
        return new Declarables(
                new FanoutExchange("x.another-dummy", true, false, null),
                new Queue("q.another-dummy"),
                new Binding("q.another-dummy", Binding.DestinationType.QUEUE, "x.another-dummy", "", null)
        );
    }

}
