package com.soa.vie.takaful.repositories;

import com.soa.vie.takaful.entitymodels.PersonnePhysiqueHistorique;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonnePhysiqueHistoriqueRepository extends CrudRepository<PersonnePhysiqueHistorique,String> {
    
}
