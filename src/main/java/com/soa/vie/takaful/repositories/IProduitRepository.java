package com.soa.vie.takaful.repositories;

import java.util.Optional;

import com.soa.vie.takaful.entitymodels.Produit;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProduitRepository extends PagingAndSortingRepository<Produit, String> {

	public Optional<Produit> findById(Produit produitId);

	public Optional<Produit> findByCode(String code);

}
