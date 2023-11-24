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
import com.soa.vie.takaful.requestmodels.EmissionGroupeRequestModel;
import com.soa.vie.takaful.util.Utilis;


public class ICotisationRepositoryImpl implements ICotisationRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;

	@Override
	public List<Cotisation> recupererEmissionGroupeParCriteres(EmissionGroupeRequestModel requestModel){
        java.util.Map<String, Object> params = new HashMap<>();
        //designé les champs à
        StringBuilder queryBuilder = new StringBuilder("SELECT cotisation.id, cotisation.datePrelevement,cotisation.montantCotisation,cotisation.fraisAcquisitionTTC,cotisation.fraisGestionTTC,cotisation.montantTaxe,")
                .append("cotisation.montantTTC,cotisation.solde,cotisation.numQuittance,cotisation.montantTaxeParaFiscale,cotisation.montantAccessoire,cotisation.capitalAssure,")
                .append("contrat.numeroContrat,contrat.dateEffet,contrat.dateEcheance, ")
                .append("produit.libelle, ")
                .append("assure.nom,assure.prenom ")
                .append( "FROM Cotisation cotisation,Contract contrat ,Produit produit ,PersonnePhysique assure ,Partenaire partenaire ")
                .append(" WHERE cotisation.contrat.id=contrat.id and contrat.produit.id=produit.id and contrat.assure.id=assure.id and produit.partenaire.id=partenaire.id ")
                .append(" AND contrat.status='ACCEPTED' and cotisation.etatCotisation='EMIS' ");
        if(requestModel.getStartDate()!=null &&  requestModel.getEndDate()!=null){
            queryBuilder.append(" and cotisation.datePrelevement >= :startDate and cotisation.datePrelevement <= :endDate ");
            params.put("startDate", requestModel.getStartDate());
            params.put("endDate", requestModel.getEndDate());
        }


        if (!Utilis.isNullOrEmpty(requestModel.getPartenaireId())) {
            queryBuilder.append(" and partenaire.id=:partenaireId ");
            params.put("partenaireId", requestModel.getPartenaireId());
        }
        if (!Utilis.isNullOrEmpty(requestModel.getProduitId())) {
            queryBuilder.append(" and produit.id=:produitID ")	;
            params.put("produitID", requestModel.getProduitId());
        }
        Query query = entityManager.createQuery(queryBuilder.toString());
        params.forEach(query::setParameter);

        List<Object[]> resultList = query.getResultList();
        List<Cotisation> cotisations = new ArrayList<>();

        for (Object[] result : resultList) {
            Cotisation cotisation = new Cotisation();
            cotisation.setId((String) result[0]); // Assuming id is of type String
            cotisation.setDatePrelevement((Date) result[1]); // Assuming datePrelevement is of type Date
            cotisation.setMontantCotisation(result[2]!=null?(float) result[2]:0f);
            cotisation.setFraisAcquisitionTTC(result[3]!=null?(float) result[3]:0f);
            cotisation.setFraisGestionTTC(result[4]!=null?(float) result[4]:0f);
            cotisation.setMontantTaxe(result[5]!=null?(float) result[5]:0f);
            cotisation.setMontantTTC(result[6]!=null?(float) result[6]:0f);
            cotisation.setSolde(result[7]!=null?(float) result[7]:0f);
            cotisation.setNumQuittance(result[8]!=null?(int) result[8]:0);
            cotisation.setMontantTaxeParaFiscale(result[9]!=null?(float) result[9]:0f);
            cotisation.setMontantAccessoire(result[10]!=null?(float) result[10]:0f);
            cotisation.setCapitalAssure(result[11]!=null?(float) result[11]:0f);
            Contract contract=new Contract();
            Produit produit=new Produit();
            PersonnePhysique assure=new PersonnePhysique();
            contract.setNumeroContrat(result[12]!=null?(String) result[12]:"");
            contract.setDateEffet((Date) result[13] );
            contract.setDateEcheance((Date) result[14] );
            produit.setLibelle(result[15]!=null?(String) result[15]:"");
            assure.setNom(result[16]!=null?(String) result[16]:"");
            assure.setPrenom(result[17]!=null?(String) result[17]:"");
            contract.setProduit(produit);
            contract.setAssure(assure);
            cotisation.setContrat(contract);
            cotisations.add(cotisation);
        }

        return cotisations;
    }

}