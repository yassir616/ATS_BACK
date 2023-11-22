package com.soa.vie.takaful.repositories;

import java.util.Optional;

import com.soa.vie.takaful.entitymodels.PersonneMorale;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface IPersonneMoraleRepository extends PagingAndSortingRepository<PersonneMorale, String>{
    public Optional<PersonneMorale> findByPatente(String patente);
    public Optional<PersonneMorale> findByAbb(String abb);

}