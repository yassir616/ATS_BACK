package com.soa.vie.takaful.util;

public enum SituationFamilialeEnum {

    MARIE("Marié(e)"), CELIBATAIRE("Célibataire"), DIVORCE("Divorcé(e)"), VEUF("Veuf(ve)");

    private String action;

    SituationFamilialeEnum(String action) {
        this.action = action;
    }

    public String getaction() {
        return action;
    }
}