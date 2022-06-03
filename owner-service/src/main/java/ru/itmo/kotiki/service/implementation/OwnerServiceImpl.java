package ru.itmo.kotiki.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.kotiki.data.dto.OwnerDto;
import ru.itmo.kotiki.data.model.Owner;
import ru.itmo.kotiki.data.tool.OwnerNotFoundException;
import ru.itmo.kotiki.repository.OwnerRepository;
import ru.itmo.kotiki.service.OwnerService;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class OwnerServiceImpl implements OwnerService {

    private OwnerRepository ownerRepository;

    @Autowired
    public OwnerServiceImpl(OwnerRepository catRepository) {
        this.ownerRepository = catRepository;
    }

    public OwnerServiceImpl() {
    }

    @Override
    public OwnerDto save(Owner owner) {

        return OwnerDto.convertToOwnerDto(ownerRepository.save(owner));
    }


    @Override
    @Transactional
    public OwnerDto update(Owner owner, UUID id) {

        Owner updatedOwner = ownerRepository.findById(id)
                .map(o -> {
                    if (owner.getId() != null) o.setId(owner.getId());
                    if (owner.getBirth() != null) o.setBirth(owner.getBirth());
                    if (owner.getName() != null) o.setName(owner.getName());
                    return ownerRepository.save(o);
                })
                .orElseGet(() -> {
                    owner.setId(id);
                    return ownerRepository.save(owner);
                });

        return OwnerDto.convertToOwnerDto(updatedOwner);
    }

    @Override
    public OwnerDto findById(UUID id, String username) {
        Owner owner = ownerRepository.findOwnerByIdAndUserUsername(id, username)
                .orElseThrow(() -> new OwnerNotFoundException(id));

        return OwnerDto.convertToOwnerDto(owner);
    }

    @Override
    public List<OwnerDto> findAll() {

        return Objects.requireNonNull(ownerRepository
                .findAll()
                .stream().map(OwnerDto::convertToOwnerDto)
                .toList());
    }

    @Override
    public void deleteById(UUID id) {
        ownerRepository.deleteById(id);
    }

    @Override

    public List<OwnerDto> findAllByName(String name, String username) {

        return Objects.requireNonNull(ownerRepository
                        .findOwnerByNameAndUserUsername(name, username)
                        .orElse(null))
                .stream().map(OwnerDto::convertToOwnerDto)
                .toList();
    }
}
