package ru.itmo.kotiki.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.itmo.kotiki.data.constant.RabbitConstants;

@Configuration
@EnableRabbit
public class CatMessageConfig {

    @Bean
    public Queue getCatsQueue() {

        return new Queue(RabbitConstants.CatConstants.GET_CATS);
    }

    @Bean
    public Queue getCatByIdQueue() {

        return new Queue(RabbitConstants.CatConstants.GET_CAT_BY_ID);
    }

    @Bean
    public Queue saveCatQueue() {

        return new Queue(RabbitConstants.CatConstants.SAVE_CAT);
    }

    @Bean
    public Queue updateCatQueue() {

        return new Queue(RabbitConstants.CatConstants.UPDATE_CAT);
    }

    @Bean
    public Queue deleteCatQueue() {

        return new Queue(RabbitConstants.CatConstants.DELETE_CAT);
    }

    @Bean
    public Queue getCatsByColorQueue() {

        return new Queue(RabbitConstants.CatConstants.GET_CATS_BY_COLOR);
    }

    @Bean
    public Queue getCatsByNameQueue() {

        return new Queue(RabbitConstants.CatConstants.GET_CATS_BY_NAME);
    }

    @Bean
    public Queue getCatsByBreedQueue() {

        return new Queue(RabbitConstants.CatConstants.GET_CATS_BY_BREED);
    }
}
