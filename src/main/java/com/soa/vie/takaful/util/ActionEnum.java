package com.soa.vie.takaful.util;


public enum ActionEnum {

    UPDATE("update"),
    CREATE("create"),
    DELETE("delete");

    private String action;
 
    ActionEnum(String action) {
        this.action = action;
    }
 
    public String getaction() {
        return action;
    }
}