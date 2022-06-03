package ru.itmo.kotiki.foreign.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.kotiki.data.enums.AppUserRole;
import ru.itmo.kotiki.data.model.Role;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByRoleName(AppUserRole roleName);
}
