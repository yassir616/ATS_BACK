package com.soa.vie.takaful.repositories;


import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.soa.vie.takaful.entitymodels.EmissionGlobale;

@Repository
@JaversSpringDataAuditable
public interface IEmissionGlobaleRepository extends PagingAndSortingRepository<EmissionGlobale, String> {

	public EmissionGlobale findByNumeroLot(String numeroLot);

	@Query(value = "SELECT * FROM emission_globale", nativeQuery = true)
	public Page<EmissionGlobale> findAllEmissions(Pageable pageable);

	@Transactional
	@Modifying
	@Query(value = "delete dbo.emission_globale where numero_lot=:numeroLot ", nativeQuery = true)
	public void deleteByNumeroLot(@Param("numeroLot") String numeroLot);
}
