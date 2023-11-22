package com.soa.vie.takaful.repositories;

import java.util.List;

import com.soa.vie.takaful.entitymodels.TypePrestation;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ITypePrestationRepository extends  PagingAndSortingRepository<TypePrestation, String> {
	 
	 @Query(
	            value = "select * from type_prestation where famille_id like ?%", 
	            countQuery = "SELECT count(*) from type_prestation where famille_id like ?%",
	            nativeQuery = true)
	  public  List<TypePrestation> findByFamilleId(String id);
}