package ru.itmo.kotiki.rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.itmo.kotiki.data.constant.RabbitConstants;

@Configuration
@EnableRabbit
public class OwnerMessageConfig {

    @Bean
    public DirectExchange ownerDirectExchange() {
        return new DirectExchange(RabbitConstants.OWNERS);
    }

    @Bean
    public Queue getOwnersQueue() {
        return new Queue(RabbitConstants.OwnerConstants.GET_OWNERS);
    }

    @Bean
    public Queue getOwnerByIdQueue() {
        return new Queue(RabbitConstants.OwnerConstants.GET_OWNER_BY_ID);
    }

    @Bean
    public Queue getOwnersByNameQueue() {
        return new Queue(RabbitConstants.OwnerConstants.GET_OWNERS_BY_NAME);
    }

    @Bean
    public Queue saveOwnerQueue() {
        return new Queue(RabbitConstants.OwnerConstants.SAVE_OWNER);
    }

    @Bean
    public Queue updateOwnerQueue() {
        return new Queue(RabbitConstants.OwnerConstants.UPDATE_OWNER);
    }

    @Bean
    public Queue deleteOwnerQueue() {
        return new Queue(RabbitConstants.OwnerConstants.DELETE_OWNER);
    }

    @Bean
    public Binding bindingGetOwners(DirectExchange ownerDirectExchange, Queue getOwnersQueue) {
        return BindingBuilder.bind(getOwnersQueue).to(ownerDirectExchange)
                .with(RabbitConstants.OwnerConstants.GET_OWNERS);
    }

    @Bean
    public Binding bindingGetOwnerById(DirectExchange ownerDirectExchange, Queue getOwnerByIdQueue) {
        return BindingBuilder.bind(getOwnerByIdQueue).to(ownerDirectExchange)
                .with(RabbitConstants.OwnerConstants.GET_OWNER_BY_ID);
    }

    @Bean
    public Binding bindingGetOwnersByName(DirectExchange ownerDirectExchange, Queue getOwnersByNameQueue) {
        return BindingBuilder.bind(getOwnersByNameQueue).to(ownerDirectExchange)
                .with(RabbitConstants.OwnerConstants.GET_OWNERS_BY_NAME);
    }

    @Bean
    public Binding bindingSaveOwner(DirectExchange ownerDirectExchange, Queue saveOwnerQueue) {
        return BindingBuilder.bind(saveOwnerQueue).to(ownerDirectExchange)
                .with(RabbitConstants.OwnerConstants.SAVE_OWNER);
    }

    @Bean
    public Binding bindingUpdateOwner(DirectExchange ownerDirectExchange, Queue updateOwnerQueue) {
        return BindingBuilder.bind(updateOwnerQueue).to(ownerDirectExchange)
                .with(RabbitConstants.OwnerConstants.UPDATE_OWNER);
    }

    @Bean
    public Binding bindingDeleteOwner(DirectExchange ownerDirectExchange, Queue deleteOwnerQueue) {
        return BindingBuilder.bind(deleteOwnerQueue).to(ownerDirectExchange)
                .with(RabbitConstants.OwnerConstants.DELETE_OWNER);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter(objectMapper());
    }

    @Bean
    public ObjectMapper objectMapper() {
        var objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return objectMapper;
    }
}
