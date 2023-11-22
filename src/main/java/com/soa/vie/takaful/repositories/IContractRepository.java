package com.soa.vie.takaful.repositories;

import java.util.Optional;

import com.soa.vie.takaful.entitymodels.Contract;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface IContractRepository extends PagingAndSortingRepository<Contract, String> {

    @Query(value = "select *,1 as clazz_ from contract c inner join deces_contrat d on c.id = d.contract_id left outer join personne_physique p on c.assure_personne_id = p.personne_id where p.nom like ?% and c.status = 'ACCEPTED' \n-- #pageable\n", countQuery = "SELECT count(*) from contract c inner join deces_contrat d on c.id = d.contract_id left outer join personne_physique p on c.assure_personne_id = p.personne_id where p.nom like ?% and c.status = 'ACCEPTED'", nativeQuery = true)
    Page<Contract> findByAssureNom(Pageable pageable, String nom);

    @Query(value = "select *,1 as clazz_ from contract c inner join deces_contrat d on c.id = d.contract_id left outer join personne_physique p on c.assure_personne_id = p.personne_id where p.cin like ?% and c.status = 'ACCEPTED' \n-- #pageable\n", countQuery = "SELECT count(*) from contract c inner join deces_contrat d on c.id = d.contract_id left outer join personne_physique p on c.assure_personne_id = p.personne_id where p.cin like ?% and c.status = 'ACCEPTED'", nativeQuery = true)
    Page<Contract> findByAssurecin(Pageable pageable, String cin);

    @Query(value = "select *,1 as clazz_ from contract c inner join deces_contrat d on c.id = d.contract_id left outer join personne_physique p on c.assure_personne_id = p.personne_id where c.numero_contrat like ?% and c.status = 'ACCEPTED' \n-- #pageable\n", countQuery = "SELECT count(*) from contract c inner join deces_contrat d on c.id = d.contract_id left outer join personne_physique p on c.assure_personne_id = p.personne_id where c.numero_contrat like ?% and c.status = 'ACCEPTED'", nativeQuery = true)
    Page<Contract> findByNumeroContratStartingWith(Pageable pageable, String numeroContrat);

    Optional<Contract> findById(Long contrat);
}
