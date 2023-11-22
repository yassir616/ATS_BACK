package com.soa.vie.takaful.util;



public class Constants {

        public final static String CODE_ACCEPTATION = "code";
        public final static String PRENOM_ASSURE = "prenom";
        public final static String NOM_ASSURE = "nom";

        public final static String CIN_ASSURE = "cin";
        public final static String NUM_CONTRAT = "numeroContrat";

        public final static String MEDECIN_EXAMINATEUR = "MEDECIN EXAMINATEUR";
        public final static String MEDECIN_SPECIALISTE = "MEDECIN SPECIALISTE";
        public final static String LABORATOIRE = "LABORATOIRE";
        public final static String MEDECIN_CONSEIL = "MEDECIN CONSEIL";

        public final static String ACCEPTATION_AU_TARIF_NORMAL = "Acceptation au tarif normal";
        public final static String ACCEPTATION_AVEC_SURPRIME = "Acceptation avec surprime";
        public final static String ACCEPTATION_AVEC_RENONCIATION = "Acceptation avec rénonciation";
        public final static String REJET = "Rejet";

        public final static String TYPE_CONTRAT_DECES = "deces";
        public final static String TYPE_CONTRAT_RETRAIT = "retrait";
        public final static String TYPE_CONTRAT_MRB = "mrb";

        public final static String CODE_PARTENAIRE_ALYOUSER = "BAY";
        public final static String CODE_PARTENAIRE_ALKHDAR = "AAB";
        public final static String PREFIX_NUM_CONTRACT_PARTENAIRE_AKHDAR = "AAB";
        public final static String PREFIX_NUM_CONTRACT_PARTENAIRE_ALYOUSR = "BAY";
        public final static String SUCCESS = "Success";

        public final static String CODE_AVENANT_CHANGEMENT_ADRESSE = "AVN06";
        public final static String CODE_AVENANT_DURE_CAPITAL = "AVN01";
        public final static String CODE_AVENANT_STATUS = "AVN07";
        public final static String CODE_AVENANT_DTEFFET = "AVN08";

        public final static String PROPERTY_CSV_EXPORT_FILE_HEADER = "solde,montantCotisation,montantTaxe,montantAccessoire,montantTTC,fraisAcquisitionTTC,fraisGestionTTC,contrat_id,numQuittance,datePrelevement,dateEmission,etatCotisation,cotisationType,contributionPure,exercice,flagBatch";
        public final static String MENSUEL_COTISATION_FILE = "output/mensuelCotisation.csv";
        public final static String ANNUEL_COTISATION_FILE = "output/annuelCotisation.csv";
        public final static String[] GETFIELDSS = new String[] { "solde", "montantCotisation",
                        "montantTaxe",
                        "montantAccessoire",
                        "montantTTC", "fraisAcquisitionTTC", "fraisGestionTTC", "contrat_id", "numQuittance","datePrelevement","dateEmission","etatCotisation",
                        "cotisationType", "contributionPure", "exercice","flagBatch"

        };
        public final static String[] GETFIELDS = new String[] { "solde", "montantCotisation",
                        "montantTaxe",
                        "montantAccessoire",
                        "montantTTC", "fraisAcquisitionTTC", "fraisGestionTTC", "contrat.id", "numQuittance","datePrelevement","dateEmission","etatCotisation",
                        "cotisationType", "contributionPure", "exercice","flagBatch"

        };

        public final static String SQLREQUESTANNUELCOTISATION = "SELECT *  FROM cotisation cot ,contract cont, periodicite periodicite, personne_physique assure, personne souscripteur, produit prod  WHERE cont.id=cot.contrat_id and cont.produit_id=prod.id and periodicite.id=periodicite_id and cont.status='ACCEPTED' and cont.assure_personne_id=assure.personne_id and cont.souscripteur_id=souscripteur.id and cont.date_echeance<=CAST(CURRENT_TIMESTAMP AS DATE) and periodicite.libelle='Annuelle'or periodicite.abb='A'";
        public final static String SQLREQUESTMENSEULCOTISATION = "SELECT * FROM cotisation cot ,contract cont, periodicite periodicite, personne_physique assure, personne souscripteur, produit prod  ,partenaire p, deces_contrat dcont\r\n" + //
                        "WHERE cont.id=cot.contrat_id \r\n" + //
                        "and cont.produit_id=prod.id\r\n" + //
                        "and prod.partenaire_id=p.id\r\n" + //
                        "and periodicite.id=periodicite_id \r\n" + //
                        "and cont.id=dcont.contract_id\r\n" + //
                        "and cont.status='ACCEPTED' \r\n" + //
                        "and cont.assure_personne_id=assure.personne_id \r\n" + //
                        "and cont.souscripteur_id=souscripteur.id\r\n" + //
                        "and convert(date, cot.creation_date)<'2022-12-01'\r\n" + //
                        "and cont.date_echeance >='2022-12-01'\r\n" + //
                        "and convert(date, cont.creation_date)<'2022-12-01'\r\n" + //
                        "and (periodicite.libelle='Mensuelle' or periodicite.abb='M')  \r\n" + //
                        "and (SELECT TOP 1 convert(date,ftu.creation_date) FROM file_to_upload ftu WHERE ftu.contrat_id = cont.id ORDER BY ftu.creation_date DESC) <= '2022-12-01'\r\n" + //
                        "and cot.flag_batch=0" ;

        public final static String ALYOUS_URL_PROD = "https://10.92.0.209:8443/TAKAFUL/services";
        public final static String ALYOUS_URL_TEST = "https://10.92.0.210:8443/TAKAFUL/services";

        public final static String ALAKHDAR_URL = "https://172.25.17.14/takaful/api/v1/customers/";
        public final static String ALAKHDAR_URL_AUTHENTIFICATION = "https://172.25.17.14/takaful/auth/token";

}
