package com.soa.vie.takaful.util;

public enum NatureFiscale {
    EPARGNE("Epargne"),RETRAITE("Retraite complémentaire");
    String message;

    NatureFiscale(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
