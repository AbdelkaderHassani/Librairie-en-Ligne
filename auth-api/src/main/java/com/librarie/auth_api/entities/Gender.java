package com.librarie.auth_api.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    HOMME,
    FEMME;

    @JsonCreator
    public static Gender fromString(String value) {
        if (value != null) {
            String upperValue = value.toUpperCase();
            switch (upperValue) {
                case "HOMME":
                case "MALE":
                case "M":
                    return HOMME;
                case "FEMME":
                case "FEMALE":
                case "F":
                    return FEMME;
            }
        }
        throw new IllegalArgumentException("Unknown gender value: " + value + ". Accepted values are: HOMME, MALE, M, FEMME, FEMALE, F");
    }

    @JsonValue
    public String toJson() {
        return this.name();
    }
}
