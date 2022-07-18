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
import ru.itmo.kotiki.data.dto.*;
import ru.itmo.kotiki.data.enums.CatColor;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cats")
@EnableMethodSecurity(securedEnabled = true)
public class CatController {

    private final RabbitTemplate template;
    private final DirectExchange catDirectExchange;

    @Autowired
    public CatController(RabbitTemplate template, DirectExchange catDirectExchange) {

        this.template = template;
        this.catDirectExchange = catDirectExchange;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAll() {

        try {

            List<CatDto> cats = (List<CatDto>) template
                    .convertSendAndReceive(catDirectExchange.getName(), RabbitConstants.CatConstants.GET_CATS, "");
            if (cats == null)
                return ResponseEntity.badRequest().body(null);

            return ResponseEntity.ok(cats);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getbyid/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<CatDto> getById(@PathVariable UUID id, Authentication authentication) {

        try {

            String username = authentication.getName();
            IdAndUsernameDto idAndUsernameDto = new IdAndUsernameDto(id, username);

            CatDto cat = (CatDto) template
                    .convertSendAndReceive(catDirectExchange.getName(), RabbitConstants.CatConstants.GET_CAT_BY_ID, idAndUsernameDto);

            if (cat == null)
                return ResponseEntity.badRequest().body(null);

            return ResponseEntity.ok(cat);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<CatDto> saveCat(@RequestBody CatDto catDto) {

        try {

            CatDto cat = (CatDto) template
                    .convertSendAndReceive(catDirectExchange.getName(), RabbitConstants.CatConstants.SAVE_CAT, catDto);
            return ResponseEntity.ok(cat);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<CatDto> updateCat(@RequestBody CatDto catDto, @PathVariable UUID id) {

        try {
            CatAndIdDto catAndIdDto = new CatAndIdDto(catDto, id);
            CatDto updatedCatDto = (CatDto) template
                    .convertSendAndReceive(catDirectExchange.getName(), RabbitConstants.CatConstants.UPDATE_CAT, catAndIdDto);
            return ResponseEntity.ok(updatedCatDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteCat(@PathVariable UUID id) {

        try {
            template.convertAndSend(catDirectExchange.getName(), RabbitConstants.CatConstants.DELETE_CAT, id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getbyname/{name}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getByName(@PathVariable String name, Authentication authentication) {

        try {

            String username = authentication.getName();
            StringAndUsernameDto stringAndUsernameDto = new StringAndUsernameDto(name, username);

            List<CatDto> cats = (List<CatDto>) template
                    .convertSendAndReceive(catDirectExchange.getName(),
                            RabbitConstants.CatConstants.GET_CATS_BY_NAME,
                            stringAndUsernameDto);
            if (cats == null) {
                return ResponseEntity.badRequest().body(null);
            }

            return ResponseEntity.ok(cats);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getbycolor/{color}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getByColor(@PathVariable CatColor color, Authentication authentication) {

        try {

            String username = authentication.getName();
            CatColorAndUsernameDto catColorAndUsernameDto = new CatColorAndUsernameDto(color, username);

            List<CatDto> cats = (List<CatDto>) template
                    .convertSendAndReceive(catDirectExchange.getName(),
                            RabbitConstants.CatConstants.GET_CATS_BY_COLOR,
                            catColorAndUsernameDto);
            if (cats == null)
                return ResponseEntity.badRequest().body(null);

            return ResponseEntity.ok(cats);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getbybreed/{breed}")
    public ResponseEntity<?> getByBreed(@PathVariable String breed, Authentication authentication) {

        try {

            String username = authentication.getName();
            StringAndUsernameDto breedAndUsernameDto = new StringAndUsernameDto(breed, username);

            List<CatDto> cats = (List<CatDto>) template
                    .convertSendAndReceive(catDirectExchange.getName(),
                            RabbitConstants.CatConstants.GET_CATS_BY_BREED,
                            breedAndUsernameDto);
            if (cats == null)
                return ResponseEntity.badRequest().body(null);

            return ResponseEntity.ok(cats);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
