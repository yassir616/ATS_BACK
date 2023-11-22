package com.soa.vie.takaful.util;

public enum PrestationStatusEnum {

    EN_COURS("En cours"), A_SIGNER("A signer"), ENCOURS_SIGNATURE("Encours de signature"),
    REGLE("Regle"), ANNULE("Annulé"), REJ_CONTRAT_ECHU("REJ CONTRAT ECHU");

    private String action;

    PrestationStatusEnum(String action) {
        this.action = action;
    }

    public String getaction() {
        return action;
    }
}