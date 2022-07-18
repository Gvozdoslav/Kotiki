package ru.itmo.kotiki.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import ru.itmo.kotiki.data.enums.AppUserRole;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "Roles")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Role implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;


    @Enumerated(value = EnumType.STRING)
    private AppUserRole roleName;

//    @Id
//    @Column(name = "id", nullable = false)
//    private UUID id;

    public Role() {
    }

    public Role(AppUserRole roleName) {
        this.roleName = roleName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public AppUserRole getRoleName() {
        return roleName;
    }

    public void setRoleName(AppUserRole roleName) {
        this.roleName = roleName;
    }
}
