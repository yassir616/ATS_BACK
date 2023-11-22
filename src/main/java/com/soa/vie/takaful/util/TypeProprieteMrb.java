package com.soa.vie.takaful.util;

public enum TypeProprieteMrb {
    TYPE1("type1"), TYPE2("type2");

    private String action;

    TypeProprieteMrb(String action) {
        this.action = action;
    }
 
    public String getaction() {
        return action;
    }
}