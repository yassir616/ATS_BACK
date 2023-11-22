package com.soa.vie.takaful.util;


public enum ProfessionEnum {

    AVOCAT("Avocat"), INGENIEUR_SI("Ingenieur SI"), AUTRE("AUTRE"), MEDECIN ("MEDECIN "), OFFICIER("OFFICIER"), DENTISTE("DENTISTE")
    , Professeur("Professeur"), KINESITHERAPIE("KINESITHERAPIE"), ASSISTANT_SOCIAL("ASSISTANT SOCIAL"), MENUISIER("MENUISIER");

    private String action;

    ProfessionEnum(String action) {
        this.action = action;
    }
 
    public String getaction() {
        return action;
    }
}