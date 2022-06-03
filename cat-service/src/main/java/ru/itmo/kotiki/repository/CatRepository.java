package ru.itmo.kotiki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.kotiki.data.enums.CatColor;
import ru.itmo.kotiki.data.model.Cat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CatRepository extends JpaRepository<Cat, UUID> {

    Optional<List<Cat>> findCatsByNameAndOwnerUserUsername(String name, String username);

    Optional<List<Cat>> findCatsByBreedAndOwnerUserUsername(String breed, String username);

    Optional<List<Cat>> findCatsByColorAndOwnerUserUsername(CatColor color, String username);

    Optional<Cat> findCatByIdAndOwnerUserUsername(UUID id, String username);
}
