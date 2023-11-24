package com.soa.vie.takaful.repositories.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.soa.vie.takaful.entitymodels.Contract;
import com.soa.vie.takaful.entitymodels.PersonnePhysique;
import com.soa.vie.takaful.entitymodels.Produit;

import com.soa.vie.takaful.entitymodels.Cotisation;
import com.soa.vie.takaful.repositories.ICotisationRepositoryCustom;


public class ICotisationRepositoryImpl implements ICotisationRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;

	//cotisation.id,cotisation.datePrelevement,cotisation.montantCotisation,cotisation.etatCotisation,cotisation.numQuittance,cotisation.solde,cotisation.montantTaxe,cotisation.montantTaxeParaFiscale,cotisation.annulation,cotisation.montantAccessoire,cotisation.montantTTC,cotisation.cotisationType,cotisation.fraisAcquisitionTTC,cotisation.fraisGestionTTC,cotisation.contributionPure,cotisation.capitalAssure,cotisation.dateEtablissement,cotisation.exercice,cotisation.numeroLot,cotisation.flagBatch,cotisation.dateEmission
	@Override
	public List<Cotisation> recupererParIds(Date startDate,Date endDate,String partenaireID,String produitID){
        java.util.Map<String, Object> params = new HashMap<>();
        //designé les champs à selectionnées
        StringBuilder queryBuilder = new StringBuilder("SELECT cotisation.id, cotisation.datePrelevement,cotisation.montantCotisation,cotisation.fraisAcquisitionTTC,cotisation.fraisGestionTTC,cotisation.montantTaxe,")
                .append("cotisation.montantTTC,cotisation.solde,cotisation.numQuittance,cotisation.montantTaxeParaFiscale,cotisation.montantAccessoire,cotisation.capitalAssure,")
                .append("contrat.numeroContrat,contrat.dateEffet,contrat.dateEcheance, ")
                .append("produit.libelle, ")
                .append("assure.nom,assure.prenom ")
                .append( "FROM Cotisation cotisation,Contract contrat ,Produit produit ,PersonnePhysique assure ,Partenaire partenaire ")
                .append(" WHERE cotisation.contrat.id=contrat.id and contrat.produit.id=produit.id and contrat.assure.id=assure.id and produit.partenaire.id=partenaire.id ")
                .append(" AND contrat.status='ACCEPTED' and cotisation.etatCotisation='EMIS' and cotisation.datePrelevement >= :startDate and cotisation.datePrelevement <= :endDate ");

        params.put("startDate", startDate);
        params.put("endDate", endDate);
        if (!partenaireID.equals("empty")) {
            queryBuilder.append(" and partenaire.id=:partenaireId ");
            params.put("partenaireId", partenaireID);
        }
        if (!produitID.equals("empty")) {
            queryBuilder.append(" and produit.id=:produitID ")	;
            params.put("produitID", produitID);
        }
        Query query = entityManager.createQuery(queryBuilder.toString());
        params.forEach(query::setParameter);

        List<Object[]> resultList = query.getResultList();
        List<Cotisation> cotisations = new ArrayList<>();

        for (Object[] result : resultList) {
            Cotisation cotisation = new Cotisation();
            cotisation.setId((String) result[0]); // Assuming id is of type String
            cotisation.setDatePrelevement((Date) result[1]); // Assuming datePrelevement is of type Date
            cotisation.setMontantCotisation((float) result[2]);
            cotisation.setFraisAcquisitionTTC((float) result[3]);
            cotisation.setFraisGestionTTC((float) result[4]);
            cotisation.setMontantTaxe((float) result[5]);
            cotisation.setMontantTTC((float) result[6]);
            cotisation.setSolde((float) result[7]);
            cotisation.setNumQuittance((int) result[8]);
            cotisation.setMontantTaxeParaFiscale((float) result[9]);
            cotisation.setMontantAccessoire((float) result[10]);
            cotisation.setCapitalAssure((float) result[11]);
            Contract contract=new Contract();
            Produit produit=new Produit();
            PersonnePhysique assure=new PersonnePhysique();
            assure.setNom((String) result[16]);
            assure.setPrenom((String) result[17]);
            produit.setLibelle((String) result[15]);
            contract.setProduit(produit);
            contract.setAssure(assure);
            contract.setNumeroContrat((String)result[12]);
            contract.setDateEffet((Date) result[13] );
            contract.setDateEcheance((Date) result[14] );
            cotisation.setContrat(contract);
            cotisations.add(cotisation);
        }

        return cotisations;
    }

}