package com.leverx.pets.dto;

import com.leverx.pets.annotations.ValidName;
import lombok.Data;

import javax.validation.Valid;

@Valid
@Data
public class PersonDto {

    private Long id;

    @ValidName
    private String name;
}
