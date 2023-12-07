package com.soa.vie.takaful.repositories.Impl;

import com.soa.vie.takaful.entitymodels.PrestationHonoraire;
import com.soa.vie.takaful.entitymodels.PrestationSinistre;
import com.soa.vie.takaful.entitymodels.Reglement;
import com.soa.vie.takaful.repositories.IReglementRepositoryCustom;
import com.soa.vie.takaful.responsemodels.DetailPrestationHonoraireResponseDTO;
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
        StringBuilder queryBuilder = new StringBuilder("SELECT NEW com.soa.vie.takaful.responsemodels.PrestationHonoraireResponseDTO(")
                .append("  prestation.id ,prestationHonoraire.reference, auxiliaire.adressPays, auxiliaire.adressVille, auxiliaire.adressComplement, auxiliaire.email, auxiliaire.nom, auxiliaire.patente, auxiliaire.prenom, auxiliaire.typePersonne, ")
                .append("auxiliaire.specialite, auxiliaire.cin, auxiliaire.rib, auxiliaire.typeFiscal, typeAuxiliaire.code, typeAuxiliaire.libelle, prestation.modeReglement, ")
                .append("prestation.montant, prestation.montantNet, prestation.montantIr, prestation.typePrestation, personnePhysique.nom,personnePhysique.prenom,partenaire.code,partenaire.numeroCompte ,partenaire.raisonSocial)")
                .append(" FROM PrestationHonoraire prestationHonoraire, Auxiliaire auxiliaire, TypeAuxiliaire typeAuxiliaire, ")
                .append(" Prestation prestation, Contract contrat,PersonnePhysique personnePhysique,Produit produit,Partenaire partenaire  ")
                .append(" WHERE prestationHonoraire.auxiliaire.id = auxiliaire.id ")
                .append("AND auxiliaire.typeAuxiliaire.id = typeAuxiliaire.id ")
                .append("AND prestation.id = prestationHonoraire.id ")
                .append("AND prestation.contrat.id = contrat.id ")
                .append("AND contrat.assure.id = personnePhysique.id ")
                .append("AND contrat.produit.id = produit.id ")
                .append("AND produit.partenaire.id = partenaire.id ")
                .append("AND prestationHonoraire.id IN (SELECT rh.id FROM Reglement reglement JOIN reglement.honoraires rh WHERE reglement.id = :reglementId)");

        params.put("reglementId", reglementId);
        Query query = entityManager.createQuery(queryBuilder.toString());
        params.forEach(query::setParameter);
        List<PrestationHonoraireResponseDTO> prestationHonoraires = query.getResultList();
        for(PrestationHonoraireResponseDTO dto:prestationHonoraires){
            dto.setDetailPrestationHonoraires(listerDetailPrestationHonorairesById(dto.getId()));
        }
        return prestationHonoraires;
    }


    private List<DetailPrestationHonoraireResponseDTO> listerDetailPrestationHonorairesById(String prestationHonoraireId) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder queryBuilder = new StringBuilder("SELECT NEW com.soa.vie.takaful.responsemodels.DetailPrestationHonoraireResponseDTO(")
                .append("detail.id, detail.montantHonoraire, detail.montantHonoraireInitial, acceptationTestMedical.paid, acceptationExamens.dateAnalyse, acceptationExaminateur.dateVisite, acceptationLaboratoire.dateAnalyse, acceptationSpecialiste.deteConsultation, acceptationConseil.dateExpertise) ")
                .append("FROM DetailPrestationHonoraire detail, AcceptationTestMedical acceptationTestMedical, AcceptationExamens acceptationExamens, AcceptationExaminateur acceptationExaminateur, ")
                .append("AcceptationLaboratoire acceptationLaboratoire, AcceptationSpecialiste acceptationSpecialiste, AcceptationConseil acceptationConseil ")
                .append("WHERE detail.acceptationTestMedical.id = acceptationTestMedical.id  and acceptationTestMedical.acceptationExamens.id = acceptationExamens.id ")
                .append("and acceptationTestMedical.acceptationExaminateur.id = acceptationExaminateur.id ")
                .append("AND detail.id IN (SELECT ph.id FROM PrestationHonoraire ph WHERE ph.id = :prestationHonoraireId)");

        params.put("prestationHonoraireId", prestationHonoraireId);
        Query query = entityManager.createQuery(queryBuilder.toString());
        params.forEach(query::setParameter);
        List<DetailPrestationHonoraireResponseDTO> detailPrestationHonoraires = query.getResultList();

        return detailPrestationHonoraires;
    }


}