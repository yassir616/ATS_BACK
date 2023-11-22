package com.soa.vie.takaful.repositories;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import com.soa.vie.takaful.entitymodels.CompteBancaire;
@Repository
@JaversSpringDataAuditable
public interface ICompteBancaire extends PagingAndSortingRepository<CompteBancaire, String> {
    Optional<CompteBancaire> findByPointVenteId(String pointVente);

}
