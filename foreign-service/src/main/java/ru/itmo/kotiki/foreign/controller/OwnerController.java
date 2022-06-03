package ru.itmo.kotiki.foreign.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.itmo.kotiki.data.constant.RabbitConstants;
import ru.itmo.kotiki.data.dto.IdAndUsernameDto;
import ru.itmo.kotiki.data.dto.OwnerAndIdDto;
import ru.itmo.kotiki.data.dto.OwnerDto;
import ru.itmo.kotiki.data.dto.StringAndUsernameDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    private final RabbitTemplate template;

    @Autowired
    public OwnerController(RabbitTemplate template) {

        this.template = template;
    }

    @GetMapping
    public ResponseEntity<List<OwnerDto>> getAll() {

        try {

            List<OwnerDto> owners = (List<OwnerDto>) template
                    .convertSendAndReceive(RabbitConstants.OwnerConstants.GET_OWNERS, RabbitConstants.OwnerConstants.GET_OWNERS);
            if (owners == null)
                return ResponseEntity.notFound().build();

            return ResponseEntity.ok().body(owners);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<OwnerDto> getById(@PathVariable UUID id, Authentication authentication) {

        try {

            String username = authentication.getName();
            IdAndUsernameDto idAndUsernameDto = new IdAndUsernameDto(id, username);

            OwnerDto owner = (OwnerDto) template
                    .convertSendAndReceive(RabbitConstants.OwnerConstants.GET_OWNER_BY_ID, idAndUsernameDto);
            if (owner == null)
                return ResponseEntity.badRequest().body(null);

            return ResponseEntity.ok().body(owner);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<OwnerDto> saveOwner(@RequestBody OwnerDto ownerDto) {

        try {

            OwnerDto catEntityModel = (OwnerDto) template
                    .convertSendAndReceive(RabbitConstants.OwnerConstants.SAVE_OWNER, ownerDto);
            return ResponseEntity.ok(catEntityModel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OwnerDto> updateOwner(@RequestBody OwnerDto ownerDto, @PathVariable UUID id) {

        try {

            OwnerAndIdDto ownerAndIdDto = new OwnerAndIdDto(ownerDto, id);
            OwnerDto catEntityModel = (OwnerDto) template
                    .convertSendAndReceive(RabbitConstants.OwnerConstants.UPDATE_OWNER, ownerAndIdDto);
            return ResponseEntity.ok(catEntityModel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<OwnerDto> deleteOwner(@PathVariable UUID id) {

        try {

            template.convertAndSend(RabbitConstants.OwnerConstants.DELETE_OWNER, id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/getbyname/{name}")
    public ResponseEntity<List<OwnerDto>> getByName(@PathVariable String name, Authentication authentication) {

        try {

            String username = authentication.getName();
            StringAndUsernameDto stringAndUsernameDto = new StringAndUsernameDto(name, username);

            List<OwnerDto> owners = (List<OwnerDto>) template
                    .convertSendAndReceive(RabbitConstants.OwnerConstants.GET_OWNERS_BY_NAME, stringAndUsernameDto);
            if (owners == null)
                return ResponseEntity.notFound().build();

            return ResponseEntity.ok().body(owners);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
