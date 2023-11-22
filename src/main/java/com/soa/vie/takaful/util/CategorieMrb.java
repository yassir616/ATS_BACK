package com.soa.vie.takaful.util;

public enum CategorieMrb {

    CAT1("cat1"), CAT2("cat2");

    private String action;

    CategorieMrb(String action) {
        this.action = action;
    }
 
    public String getaction() {
        return action;
    }
}