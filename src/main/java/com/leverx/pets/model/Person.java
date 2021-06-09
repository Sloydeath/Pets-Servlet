package com.leverx.pets.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leverx.pets.annotations.ValidName;
import com.leverx.pets.model.pet.Pet;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.LAZY;

@Data
@Valid
@NoArgsConstructor
@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ValidName
    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "person", cascade = {ALL, REMOVE}, fetch = LAZY)
    private List<Pet> pets;

}
