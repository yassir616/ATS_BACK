package com.soa.vie.takaful.repositories;

import java.util.List;
import java.util.Optional;

import com.soa.vie.takaful.entitymodels.DecesProduit;
import com.soa.vie.takaful.entitymodels.NormeSelection;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface INormeSelectionRepository extends PagingAndSortingRepository<NormeSelection, String> {

    public List<NormeSelection> findByDecesProduit(DecesProduit dProduct);

    Optional<NormeSelection> findByAgeMaxGreaterThanEqualAndAgeMinLessThanEqualAndCapitalMaxGreaterThanEqualAndCapitalMinLessThanEqualAndDecesProduit(
            int age, int agem, float capital, float capitalm, DecesProduit decesProduit);

}