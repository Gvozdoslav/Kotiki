package ru.itmo.kotiki.foreign.controller;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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
@EnableMethodSecurity(securedEnabled = true)
public class OwnerController {

    private final RabbitTemplate template;
    private final DirectExchange ownerDirectExchange;

    @Autowired
    public OwnerController(RabbitTemplate template, DirectExchange ownerDirectExchange) {

        this.template = template;
        this.ownerDirectExchange = ownerDirectExchange;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<OwnerDto>> getAll() {

        try {

            List<OwnerDto> owners = (List<OwnerDto>) template
                    .convertSendAndReceive(ownerDirectExchange.getName(),
                            RabbitConstants.OwnerConstants.GET_OWNERS, "");
            if (owners == null)
                return ResponseEntity.notFound().build();

            return ResponseEntity.ok().body(owners);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/getbyid/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<OwnerDto> getById(@PathVariable UUID id, Authentication authentication) {

        try {

            String username = authentication.getName();
            IdAndUsernameDto idAndUsernameDto = new IdAndUsernameDto(id, username);

            OwnerDto owner = (OwnerDto) template
                    .convertSendAndReceive(ownerDirectExchange.getName(),
                            RabbitConstants.OwnerConstants.GET_OWNER_BY_ID,
                            idAndUsernameDto);
            if (owner == null)
                return ResponseEntity.badRequest().body(null);

            return ResponseEntity.ok().body(owner);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<OwnerDto> saveOwner(@RequestBody OwnerDto ownerDto) {

        try {

            OwnerDto catEntityModel = (OwnerDto) template
                    .convertSendAndReceive(ownerDirectExchange.getName(),
                            RabbitConstants.OwnerConstants.SAVE_OWNER,
                            ownerDto);
            return ResponseEntity.ok(catEntityModel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<OwnerDto> updateOwner(@RequestBody OwnerDto ownerDto, @PathVariable UUID id) {

        try {

            OwnerAndIdDto ownerAndIdDto = new OwnerAndIdDto(ownerDto, id);
            OwnerDto catEntityModel = (OwnerDto) template
                    .convertSendAndReceive(ownerDirectExchange.getName(),
                            RabbitConstants.OwnerConstants.UPDATE_OWNER,
                            ownerAndIdDto);
            return ResponseEntity.ok(catEntityModel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<OwnerDto> deleteOwner(@PathVariable UUID id) {

        try {

            template.convertAndSend(ownerDirectExchange.getName(),
                    RabbitConstants.OwnerConstants.DELETE_OWNER, id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/getbyname/{name}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<OwnerDto>> getByName(@PathVariable String name, Authentication authentication) {

        try {

            String username = authentication.getName();
            StringAndUsernameDto stringAndUsernameDto = new StringAndUsernameDto(name, username);

            List<OwnerDto> owners = (List<OwnerDto>) template
                    .convertSendAndReceive(ownerDirectExchange.getName(),
                            RabbitConstants.OwnerConstants.GET_OWNERS_BY_NAME,
                            stringAndUsernameDto);
            if (owners == null)
                return ResponseEntity.notFound().build();

            return ResponseEntity.ok().body(owners);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
