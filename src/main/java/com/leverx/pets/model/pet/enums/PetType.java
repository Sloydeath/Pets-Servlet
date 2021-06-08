package com.leverx.pets.model.pet.enums;

public enum PetType {
    CAT("CAT"),
    DOG("DOG");
    private final String type;

    PetType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
