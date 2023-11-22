
package com.soa.vie.takaful.repositories.autoincrementhelpers;

import com.soa.vie.takaful.entitymodels.autoIncrementhelpers.CostumIdGeneratedValueBordereauEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostumIdGeneratedValueBordereauRepository 
    extends PagingAndSortingRepository<CostumIdGeneratedValueBordereauEntity, String>{
    
}




