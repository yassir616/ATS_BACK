package com.soa.vie.takaful.repositories;

import java.util.Optional;


import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.soa.vie.takaful.entitymodels.DecesProduit;

@Repository
@JaversSpringDataAuditable
public interface IDecesProduitRepository extends PagingAndSortingRepository<DecesProduit, String> {

	public Optional<DecesProduit> findById(DecesProduit idDeces);

	public Optional<DecesProduit> findByCode(String code);

}
