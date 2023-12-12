package com.soa.vie.takaful.repositories.Impl;

import com.soa.vie.takaful.entitymodels.Reglement;
import com.soa.vie.takaful.repositories.IReglementRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

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

}