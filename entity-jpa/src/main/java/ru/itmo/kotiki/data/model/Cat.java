package ru.itmo.kotiki.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import ru.itmo.kotiki.data.enums.CatColor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "Cats")
public class Cat implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Birth", nullable = false)
    private LocalDate birth;

    @Column(name = "Breed", nullable = false)
    private String breed;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "Color", nullable = false)
    private CatColor color;

    @ManyToOne(optional = false)
    @JoinColumn(name = "Owner_Id")
    private Owner owner;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "Friends",
            joinColumns = @JoinColumn(name = "Cat_Id"),
            inverseJoinColumns = @JoinColumn(name = "Friend_Id"))
    private List<Cat> cats = new ArrayList<>();

    public Cat() {
    }

    public Cat(String name, LocalDate dateOfBirth, String breed, CatColor catColor, Owner owner) {

        this.name = name;
        this.birth = dateOfBirth;
        this.breed = breed;
        this.color = catColor;
        this.owner = owner;
    }

    public List<Cat> getCats() {
        return cats;
    }

    public void setCats(List<Cat> cats) {
        this.cats = cats;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID value) {
        this.id = value;
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

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public CatColor getColor() {
        return color;
    }

    public void setColor(CatColor color) {
        this.color = color;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Cat cat = (Cat) o;
        return Objects.equals(id, cat.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
