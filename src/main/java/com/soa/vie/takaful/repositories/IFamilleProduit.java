package com.soa.vie.takaful.repositories;

import com.soa.vie.takaful.entitymodels.FamilleProduit;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface IFamilleProduit extends PagingAndSortingRepository<FamilleProduit, String> {

    @Query(
        value = "select id from famille_produit where name like ?% ", 
        countQuery = "SELECT count(*) from famille_produit where name like ?% ",
        nativeQuery = true)
    public String idFamilleProduit(String name);

}