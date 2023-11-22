package com.soa.vie.takaful.util;

public enum RegimeFiscal {
    RETRAITE("Retraite complémentaire"),VIE("Vie capitalisation");

    private String message;

    RegimeFiscal(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
