package com.soa.vie.takaful.repositories.Impl;

import com.soa.vie.takaful.repositories.IPrestationSinistreRepositoryCustom;
import com.soa.vie.takaful.responsemodels.PrestationSinistreResponseDTO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IPrestationSinistreRepositoryImpl implements IPrestationSinistreRepositoryCustom {

    @PersistenceContext
    public EntityManager entityManager;

    @Override
    public List<PrestationSinistreResponseDTO> listerPrestationSinistresByReglementId(String reglementId) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder queryBuilder = new StringBuilder("SELECT NEW com.soa.vie.takaful.responsemodels.PrestationSinistreResponseDTO(")
                .append("  prestation.id ,prestationSinistre.numeroSinistre,  prestation.modeReglement, ")
                .append("prestation.montant, prestation.montantNet, prestation.montantIr, prestation.typePrestation, personnePhysique.nom,personnePhysique.prenom,partenaire.code,partenaire.numeroCompte ,partenaire.raisonSocial)")
                .append(" FROM PrestationSinistre prestationSinistre,  ")
                .append(" Prestation prestation, Contract contrat,PersonnePhysique personnePhysique,Produit produit,Partenaire partenaire  ")
                .append(" WHERE  prestation.id = prestationSinistre.id ")
                .append("AND prestation.contrat.id = contrat.id ")
                .append("AND contrat.assure.id = personnePhysique.id ")
                .append("AND contrat.produit.id = produit.id ")
                .append("AND produit.partenaire.id = partenaire.id ")
                .append("AND prestationSinistre.id IN (SELECT rh.id FROM Reglement reglement JOIN reglement.prestations rh WHERE reglement.id = :reglementId)");

        params.put("reglementId", reglementId);
        Query query = entityManager.createQuery(queryBuilder.toString());
        params.forEach(query::setParameter);

        return query.getResultList();
    }


}