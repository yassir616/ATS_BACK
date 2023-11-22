package com.soa.vie.takaful.repositories;

import java.util.List;

import com.soa.vie.takaful.entitymodels.Ville;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVilleRepository extends CrudRepository<Ville,Integer> {
    
    public List<Ville> findAll();
}
