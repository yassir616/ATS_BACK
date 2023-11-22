package com.soa.vie.takaful.util;

public enum ReglementStatut {
	EN_COURS("En cours"), VALIDER("Valider"),SUPPRIMER("Supprimer");

    private String action;

    ReglementStatut(String action) {
        this.action = action;
    }
 
    public String getaction() {
        return action;
    }
}
