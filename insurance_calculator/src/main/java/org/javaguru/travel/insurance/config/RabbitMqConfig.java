package org.javaguru.travel.insurance.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String EXCHANGE_NAME = "proposal.file.exchange";
    public static final String QUEUE_NAME = "proposal.file.generate.queue";
    public static final String ROUTING_KEY = "proposal.file.generate";

    @Bean
    MessageConverter jacksonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    DirectExchange proposalFileExchange() {
        return new DirectExchange(EXCHANGE_NAME, true, false);
    }

    @Bean
    Queue proposalFileGenerateQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    Binding proposalFileGenerateBinding() {
        return BindingBuilder
                .bind(proposalFileGenerateQueue())
                .to(proposalFileExchange())
                .with(ROUTING_KEY);
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }
}
