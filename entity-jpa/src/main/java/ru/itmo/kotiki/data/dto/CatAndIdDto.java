package ru.itmo.kotiki.data.dto;

import java.io.Serializable;
import java.util.UUID;

public class CatAndIdDto implements Serializable {

    private final CatDto catDto;
    private final UUID id;

    public CatAndIdDto(CatDto catDto, UUID id) {

        this.id = id;
        this.catDto = catDto;
    }


    public CatDto getCatDto() {
        return catDto;
    }

    public UUID getId() {
        return id;
    }
}
