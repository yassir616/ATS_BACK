package com.soa.vie.takaful.repositories.Impl;

import com.soa.vie.takaful.entitymodels.PrestationHonoraire;
import com.soa.vie.takaful.entitymodels.PrestationSinistre;
import com.soa.vie.takaful.entitymodels.Reglement;
import com.soa.vie.takaful.repositories.IReglementRepositoryCustom;
import com.soa.vie.takaful.responsemodels.PrestationHonoraireResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IReglementRepositoryImpl implements IReglementRepositoryCustom {

    @PersistenceContext
    public EntityManager entityManager;

    @Override
    public Page<Reglement> listerReglements(Pageable pageable) {
        StringBuilder queryBuilder = new StringBuilder("SELECT r from Reglement r ");
        StringBuilder countBuilder = new StringBuilder("SELECT count(r) from Reglement r");

        Query query = entityManager.createQuery(queryBuilder.toString());

        Query countQuery = entityManager.createQuery(countBuilder.toString());
        Long total = (Long) countQuery.getSingleResult();
        query.setFirstResult(((int) pageable.getPageNumber()) * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        List<Reglement> resultList = query.getResultList();

        return new PageImpl<>(resultList, pageable, total);

    }

    @Override
    public List<PrestationSinistre> listerPrestationsByReglementId(String reglementId) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder queryBuilder = new StringBuilder("SELECT reglement.prestations FROM Reglement reglement ");
        queryBuilder.append(" Where reglement.id = :reglementId ");
        params.put("reglementId", reglementId);
        Query query = entityManager.createQuery(queryBuilder.toString());
        params.forEach(query::setParameter);
        List<PrestationSinistre> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public List<PrestationHonoraireResponseDTO> listerPrestationHonorairesByReglementId(String reglementId) {
        Map<String, Object> params = new HashMap<>();
        //  StringBuilder queryBuilder = new StringBuilder("SELECT reglement.honoraires FROM Reglement reglement ");
        StringBuilder queryBuilder = new StringBuilder("SELECT NEW com.soa.vie.takaful.responsemodels.PrestationHonoraireResponseDTO(")
                .append("honoraire.reference, auxiliaire.adressPays, auxiliaire.adressVille, auxiliaire.adressComplement, auxiliaire.email, auxiliaire.nom, auxiliaire.patente, auxiliaire.prenom, auxiliaire.typePersonne, ")
                .append("auxiliaire.specialite, auxiliaire.cin, auxiliaire.rib, auxiliaire.typeFiscal,typeAuxiliaire.code,typeAuxiliaire.libelle, '') ")
                .append("FROM PrestationHonoraire honoraire, Auxiliaire auxiliaire,TypeAuxiliaire typeAuxiliaire ")
                .append("WHERE honoraire.auxiliaire.id = auxiliaire.id ")
                .append("AND honoraire.id IN (SELECT rh.id FROM Reglement reglement JOIN reglement.honoraires rh WHERE reglement.id = :reglementId)")
                .append("AND auxiliaire.typeAuxiliaire.id = typeAuxiliaire.id")
                .append("");
        params.put("reglementId", reglementId);

        Query query = entityManager.createQuery(queryBuilder.toString());
        params.forEach(query::setParameter);
        List<PrestationHonoraireResponseDTO> prestationHonoraires = query.getResultList();

        return prestationHonoraires;
    }

    private PrestationHonoraire mapToPrestationHonoraire(Object[] result) {
        PrestationHonoraire prestationHonoraire = new PrestationHonoraire();
        prestationHonoraire.setReference((String) result[0]);
        return prestationHonoraire;
    }

}