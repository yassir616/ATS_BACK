package com.soa.vie.takaful.util;

public enum ModeGestion {
    MOUDARABA("Moudaraba"),WAKALABILISTITMAR("WakalabillIstithmar");

    private String message;

    ModeGestion(String moudaraba) {
        this.message = moudaraba;
    }

    public String getMoudaraba() {
        return message;
    }
}
