package com.soa.vie.takaful.repositories;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.soa.vie.takaful.entitymodels.ProduitMrb;
import com.soa.vie.takaful.entitymodels.TarificationMRB;

@Repository
@JaversSpringDataAuditable
public interface ITarificationMRB extends PagingAndSortingRepository<TarificationMRB, String> {
	public Page<TarificationMRB> findByProduitMrb(ProduitMrb id, Pageable pageableRequest);
	Optional<TarificationMRB> findByProduitMrb( ProduitMrb produit);
	/* Optional<TarificationMRB> findByValeurMaxGreaterThanEqualAndValeurMinLessThanEqualAndNatureBienAssureAndProduitMrb(
			float valeurMax, float valeurMin, String natureBienAssure, ProduitMrb produit); */
			Optional<TarificationMRB> findByNatureBienAssureAndProduitMrb(String natureBienAssure, ProduitMrb produit);

}
