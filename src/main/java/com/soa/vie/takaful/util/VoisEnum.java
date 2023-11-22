package com.soa.vie.takaful.util;

public enum VoisEnum {
    HAY("Hay"), RUE("Rue"), QUARTIER("quartier");

    private String action;

    VoisEnum(String action) {
        this.action = action;
    }

    public String getaction() {
        return action;
    }
}
