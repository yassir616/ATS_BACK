package com.soa.vie.takaful.util;

public enum GarantieMrbType {
    BASE("Base"), OPTIONNELLE("Optionnelle");

    private String action;

    GarantieMrbType(String action) {
        this.action = action;
    }
 
    public String getaction() {
        return action;
    }
}