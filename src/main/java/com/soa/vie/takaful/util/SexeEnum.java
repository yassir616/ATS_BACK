package com.soa.vie.takaful.util;


public enum SexeEnum {

    HOMME("Homme"), FEMME("Femme");

    private String action;

    SexeEnum(String action) {
        this.action = action;
    }
 
    public String getaction() {
        return action;
    }
}