package org.javaguru.travel.insurance.config;

import org.springframework.amqp.core.*;
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

    public static final String DLX = "proposal.file.exchange.dlx";
    public static final String DLQ = "proposal.file.generate.queue.dlq";
    public static final String DLQ_ROUTING_KEY = "proposal.file.generate.dlq";

    @Bean
    MessageConverter jacksonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    DirectExchange proposalFileExchange() {
        return new DirectExchange(EXCHANGE_NAME, true, false);
    }

    @Bean
    DirectExchange proposalFileDlx() {
        return new DirectExchange(DLX, true, false);
    }

    @Bean
    Queue proposalFileGenerateDlq() {
        return QueueBuilder
                .durable(DLQ)
                .build();
    }

    @Bean
    Queue proposalFileGenerateQueue() {
        return QueueBuilder
                .durable(QUEUE_NAME)
                .deadLetterExchange(DLX)
                .deadLetterRoutingKey(DLQ_ROUTING_KEY)
                .build();
    }

    @Bean
    Binding proposalFileGenerateBinding() {
        return BindingBuilder
                .bind(proposalFileGenerateQueue())
                .to(proposalFileExchange())
                .with(ROUTING_KEY);
    }

    @Bean
    Binding proposalFileGenerateBindingDlqBinding() {
        return BindingBuilder
                .bind(proposalFileGenerateDlq())
                .to(proposalFileDlx())
                .with(DLQ_ROUTING_KEY);
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }
}
