package com.soa.vie.takaful.repositories;

import com.soa.vie.takaful.entitymodels.BeneficiaireEnDeces;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBeneficiaireEnDecesRepository extends PagingAndSortingRepository<BeneficiaireEnDeces, String> {

}
