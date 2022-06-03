package ru.itmo.kotiki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.kotiki.data.model.Owner;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, UUID> {

    Optional<Owner> findOwnerByIdAndUserUsername(UUID id, String username);

    Optional<List<Owner>> findOwnerByNameAndUserUsername(String name, String username);
}
