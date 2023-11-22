package com.soa.vie.takaful.util;

public class NotificationMessages {

    public final static String ContratSinistreResiliationRejte(String numeroContrat, String numeroSinistre,
            String motif) {
        return "La demande de résiliation pour le contrat numéro :" + numeroContrat + "  sinistre numéro : "
                + numeroSinistre + "est rejté avec motif : " + motif;
    }

    public final static String ContratAccepteParMedecinConsielExaminateurMessageNormal(String numeroContrat,
            String Code, String Nom) {

        return "Acceptation au Tarif Normal --> Contrat n°: " + numeroContrat + " , Code acceptation : " + Code
                + " ,  Nom : " + Nom + ".";
    }

    public final static String ContratAccepteParMedecinConsielExaminateurMessageAvecSupp(String numeroContrat,
            String Code, String Nom) {

        return "Acceptation avec Supprime --> Contrat n°: " + numeroContrat + ", Code acceptation : " + Code
                + " , Nom : " + Nom + ".";
    }

    public static String ContratAccepteParMedecinConsielExaminateurMessageRejet(String numeroContrat, String code,
            String nom) {
        return "Acceptation avec rejet --> Contrat n°: " + numeroContrat + " , Code acceptation : " + code + " , Nom : "
                + nom + ".";
    }

    public final static String ContratSinistreResiliationDemandeMessage(String numeroContrat, String numeroSinistre) {
        return "Il y a une demande de résiliation pour le contrat numéro :" + numeroContrat
                + "\n numéro de sinistre est : " + numeroSinistre;
    }

    public static String ContratAccepteParMedecinConsielExaminateurMessageAvecRenon(String numeroContrat, String code,
            String nom) {
        return "Acceptation avec rénonciation --> Contrat n°: " + numeroContrat + ", Code acceptation : " + code
                + " , Nom : " + nom + ".";
    }

    public final static String GenerateLettreDeRelance(String numeroContrat) {
        return "Une lettre de relance est générée, Vérifiez votre boîte mail --> Contrat n°: " + numeroContrat;
    }

}
