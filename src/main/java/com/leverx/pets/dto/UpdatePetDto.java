package com.leverx.pets.dto;

import com.leverx.pets.annotations.ValidName;
import com.leverx.pets.model.Person;
import com.leverx.pets.model.pet.enums.PetType;
import lombok.Data;

import javax.validation.Valid;

@Valid
@Data
public class UpdatePetDto {
    private Long id;
    @ValidName
    private String name;
    private PetType petType;
    private Person person;
}
