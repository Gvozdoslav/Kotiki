package ru.itmo.kotiki.rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.itmo.kotiki.data.constant.RabbitConstants;
import ru.itmo.kotiki.data.dto.*;
import ru.itmo.kotiki.service.CatService;

import java.util.List;
import java.util.UUID;

@Component
@EnableRabbit
public class CatListener {

    private final CatService catService;
    private final Logger logger = LoggerFactory.getLogger(CatListener.class);

    @Autowired
    public CatListener(CatService catService) {

        this.catService = catService;
    }

    @RabbitListener(queues = RabbitConstants.CatConstants.GET_CATS)
    public List<CatDto> getAll() {
        try {
            List<CatDto> cats = catService.findAll();
            logger.info("Cats found successfully");
            return cats;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RabbitListener(queues = RabbitConstants.CatConstants.GET_CAT_BY_ID)
    public CatDto getCatById(@Payload IdAndUsernameDto idAndUsernameDto) {
        try {
            CatDto cat = catService.findById(idAndUsernameDto.getId(), idAndUsernameDto.getUsername());
            logger.info("Cat with id {} found", idAndUsernameDto.getId());
            return cat;
        } catch (Exception e) {
            throw new RuntimeException("Cat with id " + idAndUsernameDto.getId() + " not found");
        }
    }

    @RabbitListener(queues = RabbitConstants.CatConstants.SAVE_CAT)
    public CatDto saveCat(@Payload CatDto catDto) {
        try {
            CatDto cat = catService.save(catDto.convertToCat());
            logger.info("Cat saved successfully");
            return cat;
        } catch (Exception e) {
            throw new RuntimeException("Exception during saving cat with name " + catDto.getName());
        }
    }

    @RabbitListener(queues = RabbitConstants.CatConstants.UPDATE_CAT)
    public CatDto updateCat(@Payload CatAndIdDto catAndIdDto) {
        try {
            CatDto cat = catService.update(catAndIdDto.getCatDto().convertToCat(), catAndIdDto.getId());
            logger.info("Cat updated successfully");
            return cat;
        } catch (Exception e) {
            throw new RuntimeException("Exception during updating cat with name " + catAndIdDto.getCatDto().getName());
        }
    }

    @RabbitListener(queues = RabbitConstants.CatConstants.DELETE_CAT)
    public void deleteCat(UUID id) {
        try {
            catService.deleteById(id);
            logger.info("Cat with id {} deleted successfully", id);
        } catch (Exception e) {
            throw new RuntimeException("Exception during deleting cat");
        }
    }

    @RabbitListener(queues = RabbitConstants.CatConstants.GET_CATS_BY_NAME)
    public List<CatDto> getCatsByName(@Payload StringAndUsernameDto stringAndUsernameDto) {
        try {
            List<CatDto> cats = catService
                    .findAllByName(stringAndUsernameDto.getString(), stringAndUsernameDto.getUsername());
            logger.info("Cats found successfully");
            return cats;
        } catch (Exception e) {
            throw new RuntimeException("Cats not found");
        }
    }

    @RabbitListener(queues = RabbitConstants.CatConstants.GET_CATS_BY_BREED)
    public List<CatDto> getCatsByBreed(@Payload StringAndUsernameDto stringAndUsernameDto) {
        try {
            List<CatDto> cats = catService
                    .findAllByBreed(stringAndUsernameDto.getString(), stringAndUsernameDto.getUsername());
            logger.info("Cats found successfully");
            return cats;
        } catch (Exception e) {
            throw new RuntimeException("Cats not found");
        }
    }

    @RabbitListener(queues = RabbitConstants.CatConstants.GET_CATS_BY_COLOR)
    public List<CatDto> getCatsByColor(@Payload CatColorAndUsernameDto catColorAndUsernameDto) {
        try {
            List<CatDto> cats = catService
                    .findAllByColor(catColorAndUsernameDto.getCatColor(), catColorAndUsernameDto.getUsername());
            logger.info("Cats found successfully");
            return cats;
        } catch (Exception e) {
            throw new RuntimeException("Cats not found");
        }
    }
}
