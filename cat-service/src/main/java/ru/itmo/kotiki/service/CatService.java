package ru.itmo.kotiki.service;

import ru.itmo.kotiki.data.dto.CatDto;
import ru.itmo.kotiki.data.enums.CatColor;
import ru.itmo.kotiki.data.model.Cat;

import java.util.List;
import java.util.UUID;

public interface CatService {
    CatDto save(Cat cat);

    CatDto update(Cat cat, UUID id);

    CatDto findById(UUID id, String username);

    List<CatDto> findAll();

    void deleteById(UUID id);

    List<CatDto> findAllByName(String name, String username);

    List<CatDto> findAllByBreed(String breed, String username);

    List<CatDto> findAllByColor(CatColor color, String username);
}
