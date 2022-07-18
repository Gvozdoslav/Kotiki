package ru.itmo.kotiki.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Owners")
public class Owner implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @JsonIgnore
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private final List<Cat> cats = new ArrayList<>();

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Birth", nullable = false)
    private LocalDate birth;


    @JoinColumn(name = "user_id", nullable = false)
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private AppUser user;

    public Owner() {
    }

    public Owner(String name, LocalDate dateOfBirth, AppUser user) {
        this.name = name;
        this.birth = dateOfBirth;
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate dateOfBirth) {
        this.birth = dateOfBirth;
    }

    public List<Cat> getCats() {
        return cats;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
}
