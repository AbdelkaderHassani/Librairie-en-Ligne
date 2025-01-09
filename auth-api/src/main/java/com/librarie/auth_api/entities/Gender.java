package com.librarie.auth_api.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    HOMME,
    FEMME;

    @JsonCreator
    public static Gender fromString(String value) {
        if (value != null) {
            switch (value.toUpperCase()) {
                case "HOMME":
                    return HOMME;
                case "FEMME":
                    return FEMME;
                case "Homme":
                    return HOMME;
                case "Femme":
                    return FEMME;
            }
        }
        throw new IllegalArgumentException("Unknown gender value: " + value);
    }

    @JsonValue
    public String toJson() {
        return this.name();
    }
}
