package com.soa.vie.takaful.util;

public enum ModeCalculCapitalConstitue {
    METHOD1("Méthode 1 – Début de période + cotisations du mois (non investies"), METHOD2("Méthode 2 – Fin de période");

    private String message;

    ModeCalculCapitalConstitue(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
