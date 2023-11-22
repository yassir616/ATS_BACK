package com.soa.vie.takaful.util;

public enum NatureConditionDisciplinaire {
    FIXE("Montant fixe"), POURCENTAGE("Pourcentage"), ASAISIR("Montant à saisir");

    private String message;

    NatureConditionDisciplinaire(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
