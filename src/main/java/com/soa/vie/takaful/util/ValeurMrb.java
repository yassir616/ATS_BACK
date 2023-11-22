package com.soa.vie.takaful.util;

public enum ValeurMrb {
    
    OPTION1("option1"), OPTION2("option2");

    private String action;

    ValeurMrb(String action) {
        this.action = action;
    }
 
    public String getaction() {
        return action;
    }
}