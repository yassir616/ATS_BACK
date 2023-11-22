package com.soa.vie.takaful.util;

public enum BenificiareCasDeces {
    HERITIERS("Heritiers"), PERSONNEDESIGNEE("Personne designée");

    private String message;

    BenificiareCasDeces(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
