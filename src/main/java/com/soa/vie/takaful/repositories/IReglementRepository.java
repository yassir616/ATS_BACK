package com.soa.vie.takaful.repositories;

import com.soa.vie.takaful.entitymodels.Reglement;

import java.util.List;

import javax.transaction.Transactional;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

@JaversSpringDataAuditable
public interface IReglementRepository extends JpaRepository<Reglement, String> {//,IReglementRepositoryCostum{

	@Query(value = "select reglement_id from reglement_prestations where reglement_id  = ?", countQuery = "select count(*) from reglement_prestations where reglement_id  = ?", nativeQuery = true)
	List<String> findPrestationByIdReglement(String id);

	@Modifying
	@Transactional
	@Query(value = "update reglement set statut= ?1 where id= ?2", nativeQuery = true)
	public void updateStatutReglement(String satut, String id);

	@Query(value = "select * from reglement order by creation_date desc ", countQuery = "select count(*) from reglement", nativeQuery = true)
	Page<Reglement> findReglements(Pageable pageable);

}