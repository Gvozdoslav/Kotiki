package ru.itmo.kotiki.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.itmo.kotiki.data.constant.RabbitConstants;

@Configuration
@EnableRabbit
public class OwnerMessageConfig {

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
}
