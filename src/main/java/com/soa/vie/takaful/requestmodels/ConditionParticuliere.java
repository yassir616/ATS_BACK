package com.soa.vie.takaful.requestmodels;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConditionParticuliere {

    String id;
    String numeroContrat;
    String dateEffet;
    String dureeContrat;
    String dateEcheance;
    String typePersonne;
    String nomPrenomAssure;
    String dateNaissance;
    String nationalite;
    String cinAssure;
    String profession;
    String adresseAssure;
    String telephone;
    String emailAssure;
    String ribAssure;
    String nomPrenomSouscripteur;
    String dateNaissanceSouscripteur;
    String nationaliteSouscripteur;
    String cinSouscripteur;
    String professionSouscripteur;
    String adresseSouscripteur;
    String telephoneSouscripteur;
    String emailSouscripteur;
    String ribSouscripteur;
    String partenaire;
    String dossierFinancement;
    String capitalAssure;
    String dateEffetFinancement;
    String dateDebutEcheanceFinancement;
    String dateFinEcheanceFinancement;
    String montantTTC;
    String montantHT;
    String montantTaxe;
    String periodicite;
    String datePrelevement;
    String villeAgence;
    String codePartenaire;
    String date;
    String codeQr;
}
