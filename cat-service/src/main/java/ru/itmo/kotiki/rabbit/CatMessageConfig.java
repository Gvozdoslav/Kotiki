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
public class CatMessageConfig {

    @Bean
    public DirectExchange catDirectExchange() {
        return new DirectExchange(RabbitConstants.CATS);
    }

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

    @Bean
    public Binding bindingGetCats(DirectExchange catDirectExchange, Queue getCatsQueue) {
        return BindingBuilder.bind(getCatsQueue).to(catDirectExchange)
                .withQueueName();
    }

    @Bean
    public Binding bindingGetCatById(DirectExchange catDirectExchange, Queue getCatByIdQueue) {
        return BindingBuilder.bind(getCatByIdQueue).to(catDirectExchange)
                .withQueueName();
    }

    @Bean
    public Binding bindingGetCatsByColor(DirectExchange catDirectExchange, Queue getCatsByColorQueue) {
        return BindingBuilder.bind(getCatsByColorQueue).to(catDirectExchange)
                .withQueueName();
    }

    @Bean
    public Binding bindingGetCatsByBreed(DirectExchange catDirectExchange, Queue getCatsByBreedQueue) {
        return BindingBuilder.bind(getCatsByBreedQueue).to(catDirectExchange)
                .withQueueName();
    }

    @Bean
    public Binding bindingGetCatsByName(DirectExchange catDirectExchange, Queue getCatsByNameQueue) {
        return BindingBuilder.bind(getCatsByNameQueue).to(catDirectExchange)
                .withQueueName();
    }

    @Bean
    public Binding bindingSaveCat(DirectExchange catDirectExchange, Queue saveCatQueue) {
        return BindingBuilder.bind(saveCatQueue).to(catDirectExchange)
                .withQueueName();
    }

    @Bean
    public Binding bindingUpdateCat(DirectExchange catDirectExchange, Queue updateCatQueue) {
        return BindingBuilder.bind(updateCatQueue).to(catDirectExchange)
                .withQueueName();
    }

    @Bean
    public Binding bindingDeleteCat(DirectExchange catDirectExchange, Queue deleteCatQueue) {
        return BindingBuilder.bind(deleteCatQueue).to(catDirectExchange)
                .withQueueName();
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
