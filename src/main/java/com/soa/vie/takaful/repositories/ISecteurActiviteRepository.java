package com.soa.vie.takaful.repositories;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.soa.vie.takaful.entitymodels.SecteurActivite;

import java.util.Optional;

@Repository
@JaversSpringDataAuditable
public interface ISecteurActiviteRepository extends PagingAndSortingRepository<SecteurActivite, String> {

    public Optional<SecteurActivite> findByLibelle(String libelle);

}
