package com.leverx.pets.dto;

import com.leverx.pets.model.Person;
import com.leverx.pets.model.pet.enums.PetType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class PetDto {
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private PetType petType;
    private Person person;
}
