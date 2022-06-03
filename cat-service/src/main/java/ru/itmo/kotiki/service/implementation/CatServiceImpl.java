package ru.itmo.kotiki.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.kotiki.data.dto.CatDto;
import ru.itmo.kotiki.data.enums.CatColor;
import ru.itmo.kotiki.data.model.Cat;
import ru.itmo.kotiki.data.tool.CatNotFoundException;
import ru.itmo.kotiki.repository.CatRepository;
import ru.itmo.kotiki.service.CatService;

import java.util.List;
import java.util.UUID;

@Service
public class CatServiceImpl implements CatService {

    private CatRepository catRepository;

    public CatServiceImpl() {
    }

    @Autowired
    public CatServiceImpl(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    @Override
    public CatDto findById(UUID id, String username) {
        Cat cat = catRepository.findCatByIdAndOwnerUserUsername(id, username)
                .orElseThrow(() -> new CatNotFoundException(id));

        return CatDto.convertToCatDto(cat);
    }

    @Override
    public CatDto save(Cat cat) {

        return CatDto.convertToCatDto(catRepository.save(cat));
    }

    @Override
    @Transactional
    public CatDto update(Cat cat, UUID id) {

        Cat updatedCat = catRepository.findById(id)
                .map(c -> {
                    if (cat.getId() != null) c.setId(cat.getId());
                    if (cat.getBreed() != null) c.setBreed(cat.getBreed());
                    if (cat.getColor() != null) c.setColor(cat.getColor());
                    if (cat.getName() != null) c.setName(cat.getName());
                    if (cat.getOwner() != null) c.setOwner(cat.getOwner());
                    if (cat.getBirth() != null) c.setBirth(cat.getBirth());
                    return catRepository.save(c);
                })
                .orElseGet(() -> {
                    cat.setId(id);
                    return catRepository.save(cat);
                });


        return CatDto.convertToCatDto(updatedCat);
    }

    @Override
    public List<CatDto> findAll() {
        List<CatDto> cats = catRepository.findAll()
                .stream().map(CatDto::convertToCatDto)
                .toList();

        return cats;
    }


    @Override
    public void deleteById(UUID id) {

        catRepository.deleteById(id);
    }

    @Override

    public List<CatDto> findAllByName(String name, String username) {
        List<CatDto> cats = catRepository
                .findCatsByNameAndOwnerUserUsername(name, username)
                .get()
                .stream().map(CatDto::convertToCatDto)
                .toList();

        return cats;
    }

    @Override
    public List<CatDto> findAllByBreed(String breed, String username) {
        List<CatDto> cats = catRepository
                .findCatsByBreedAndOwnerUserUsername(breed, username)
                .get()
                .stream().map(CatDto::convertToCatDto)
                .toList();

        return cats;
    }

    @Override
    public List<CatDto> findAllByColor(CatColor color, String username) {
        List<CatDto> cats = catRepository
                .findCatsByColorAndOwnerUserUsername(color, username)
                .get()
                .stream().map(CatDto::convertToCatDto)
                .toList();

        return cats;
    }
}
