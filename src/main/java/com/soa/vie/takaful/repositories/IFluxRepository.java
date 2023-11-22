package com.soa.vie.takaful.repositories;

import com.soa.vie.takaful.entitymodels.Flux;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IFluxRepository extends PagingAndSortingRepository<Flux, String> {

    List<Flux> findByProduitIdAndPartenaireIdAndDateTraitementAndTypeFluxIdAndEtat(
                                                                                 String produitId,
                                                                                 String partenaireId,
                                                                                 Date dateTraitement,
                                                                                 String typeFluxId,
                                                                                 String etat);
}
