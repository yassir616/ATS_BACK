package com.soa.vie.takaful.repositories;

import java.util.Optional;
import com.soa.vie.takaful.entitymodels.ProduitMrb;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.PagingAndSortingRepository;

@JaversSpringDataAuditable
public interface IProduitMrbRepository extends PagingAndSortingRepository<ProduitMrb , String>{
    public Optional<ProduitMrb> findByLibelle(String libelle);

    
}