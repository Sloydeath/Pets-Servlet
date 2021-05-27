package com.leverx.pets.dto;

import com.leverx.pets.model.PetType;
import lombok.Data;

@Data
public class PetDto {
    private String name;
    private PetType petType;
}
