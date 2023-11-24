package com.soa.vie.takaful.repositories.Impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.soa.vie.takaful.entitymodels.Reglement;
import com.soa.vie.takaful.repositories.IReglementRepositoryCostum;

public class IReglementRepositoryImpl implements IReglementRepositoryCostum{

    @PersistenceContext
    public EntityManager entityManager;

    @Override
    public Page<Reglement> findReglements(Pageable pageable) {
        StringBuilder queryBuilder = new StringBuilder("SELECT r from Reglement r order by r.creationDate");
        String countBuilder="SELECT count(r) from Reglement r";
        Query query=entityManager.createQuery(queryBuilder.toString());
        Long total=entityManager.createQuery(countBuilder,Long.class).getSingleResult();

        List<Reglement> resultList = query.getResultList();

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        return new PageImpl<>(resultList, pageable, total);

    }

    
}