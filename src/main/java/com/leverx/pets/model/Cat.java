package com.leverx.pets.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue(value = "CAT")
public class Cat extends Pet{
}
