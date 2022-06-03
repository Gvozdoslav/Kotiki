package ru.itmo.kotiki.service;

import ru.itmo.kotiki.data.dto.OwnerDto;
import ru.itmo.kotiki.data.model.Owner;

import java.util.List;
import java.util.UUID;

public interface OwnerService {
    OwnerDto save(Owner owner);

    OwnerDto update(Owner owner, UUID id);

    OwnerDto findById(UUID id, String username);

    List<OwnerDto> findAll();

    void deleteById(UUID id);

    List<OwnerDto> findAllByName(String name, String username);
}
