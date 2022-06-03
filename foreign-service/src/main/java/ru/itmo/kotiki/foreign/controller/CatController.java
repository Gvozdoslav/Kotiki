package ru.itmo.kotiki.foreign.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.itmo.kotiki.data.constant.RabbitConstants;
import ru.itmo.kotiki.data.dto.*;
import ru.itmo.kotiki.data.enums.CatColor;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cats")
public class CatController {

    private final RabbitTemplate template;

    @Autowired
    public CatController(RabbitTemplate template) {

        this.template = template;
    }

    @GetMapping
    public ResponseEntity<?> getAll(Authentication authentication) {

        try {

            List<CatDto> cats = (List<CatDto>) template
                    .convertSendAndReceive(RabbitConstants.CatConstants.GET_CATS, RabbitConstants.CatConstants.GET_CATS);
            if (cats == null)
                return ResponseEntity.badRequest().body(null);

            return ResponseEntity.ok(cats);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<CatDto> getById(@PathVariable UUID id, Authentication authentication) {

        try {

            String username = authentication.getName();
            IdAndUsernameDto idAndUsernameDto = new IdAndUsernameDto(id, username);

            CatDto cat = (CatDto) template
                    .convertSendAndReceive(RabbitConstants.CatConstants.GET_CAT_BY_ID, idAndUsernameDto);

            if (cat == null)
                return ResponseEntity.badRequest().body(null);

            return ResponseEntity.ok(cat);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<CatDto> saveCat(@RequestBody CatDto catDto) {

        try {

            CatDto cat = (CatDto) template
                    .convertSendAndReceive(RabbitConstants.CatConstants.SAVE_CAT, catDto);
            return ResponseEntity.ok(cat);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CatDto> updateCat(@RequestBody CatDto catDto, @PathVariable UUID id) {

        try {
            CatAndIdDto catAndIdDto = new CatAndIdDto(catDto, id);
            CatDto updatedCatDto = (CatDto) template
                    .convertSendAndReceive(RabbitConstants.CatConstants.UPDATE_CAT, catAndIdDto);
            return ResponseEntity.ok(updatedCatDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCat(@PathVariable UUID id) {

        try {
            template.convertAndSend(RabbitConstants.CatConstants.DELETE_CAT, id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getbyname/{name}")
    public ResponseEntity<?> getByName(@PathVariable String name, Authentication authentication) {

        try {

            String username = authentication.getName();
            StringAndUsernameDto stringAndUsernameDto = new StringAndUsernameDto(name, username);

            List<CatDto> cats = (List<CatDto>) template
                    .convertSendAndReceive(RabbitConstants.CatConstants.GET_CATS_BY_NAME, stringAndUsernameDto);
            if (cats == null) {
                return ResponseEntity.badRequest().body(null);
            }

            return ResponseEntity.ok(cats);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getbycolor/{color}")
    public ResponseEntity<?> getByColor(@PathVariable CatColor color, Authentication authentication) {

        try {

            String username = authentication.getName();
            CatColorAndUsernameDto catColorAndUsernameDto = new CatColorAndUsernameDto(color, username);

            List<CatDto> cats = (List<CatDto>) template
                    .convertSendAndReceive(RabbitConstants.CatConstants.GET_CATS_BY_COLOR, catColorAndUsernameDto);
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
                    .convertSendAndReceive(RabbitConstants.CatConstants.GET_CATS_BY_BREED, breedAndUsernameDto);
            if (cats == null)
                return ResponseEntity.badRequest().body(null);

            return ResponseEntity.ok(cats);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
