package com.soa.vie.takaful.repositories;

import com.soa.vie.takaful.entitymodels.RetraiteContratHistorique;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRetraiteContratHistoriqueRepository extends PagingAndSortingRepository<RetraiteContratHistorique, String> {
}
