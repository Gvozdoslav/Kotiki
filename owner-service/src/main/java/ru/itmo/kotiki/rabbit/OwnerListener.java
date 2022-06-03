package ru.itmo.kotiki.rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.itmo.kotiki.data.constant.RabbitConstants;
import ru.itmo.kotiki.data.dto.IdAndUsernameDto;
import ru.itmo.kotiki.data.dto.OwnerAndIdDto;
import ru.itmo.kotiki.data.dto.OwnerDto;
import ru.itmo.kotiki.data.dto.StringAndUsernameDto;
import ru.itmo.kotiki.service.OwnerService;

import java.util.List;
import java.util.UUID;

@Component
@EnableRabbit
public class OwnerListener {

    private final OwnerService ownerService;
    private final Logger logger = LoggerFactory.getLogger(OwnerListener.class);

    @Autowired
    public OwnerListener(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @RabbitListener(queues = RabbitConstants.OwnerConstants.GET_OWNERS)
    public List<OwnerDto> getOwners() {
        try {
            List<OwnerDto> owners = ownerService.findAll();
            logger.info("Owners found successfully");
            return owners;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RabbitListener(queues = RabbitConstants.OwnerConstants.GET_OWNER_BY_ID)
    public OwnerDto getOwnerById(@Payload IdAndUsernameDto idAndUsernameDto) {
        try {
            OwnerDto owner = ownerService.findById(idAndUsernameDto.getId(), idAndUsernameDto.getUsername());
            logger.info("Owner with id {} found successfully", idAndUsernameDto.getId());
            return owner;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RabbitListener(queues = RabbitConstants.OwnerConstants.SAVE_OWNER)
    public OwnerDto saveOwner(@Payload OwnerDto ownerDto) {
        try {
            OwnerDto owner = ownerService.save(ownerDto.convertToOwner());
            logger.info("Owner saved successfully");
            return owner;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RabbitListener(queues = RabbitConstants.OwnerConstants.UPDATE_OWNER)
    public OwnerDto updateOwner(@Payload OwnerAndIdDto ownerAndIdDto) {
        try {
            OwnerDto owner = ownerService
                    .update(ownerAndIdDto.getOwnerDto().convertToOwner(), ownerAndIdDto.getId());
            logger.info("Owner updated successfully");
            return owner;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RabbitListener(queues = RabbitConstants.OwnerConstants.DELETE_OWNER)
    public void deleteOwner(UUID id) {
        try {
            ownerService.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RabbitListener(queues = RabbitConstants.OwnerConstants.GET_OWNERS_BY_NAME)
    public List<OwnerDto> getOwnersByName(@Payload StringAndUsernameDto stringAndUsernameDto) {
        try {
            List<OwnerDto> owners = ownerService
                    .findAllByName(stringAndUsernameDto.getString(), stringAndUsernameDto.getUsername());
            logger.info("Owners found successfully");
            return owners;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
