package com.leverx.pets.model.pet;

import com.leverx.pets.annotations.ValidName;
import com.leverx.pets.model.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.EAGER;

@Data
@Valid
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "pet_type")
public abstract class Pet {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ValidName
    @Column(name = "name")
    private String name;

    @NotNull
    @ManyToOne(fetch = EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "owner_id")
    private Person person;

    @Transient
    public String getPetType() {
        return this.getClass().getAnnotation(DiscriminatorValue.class).value();
    }
}
