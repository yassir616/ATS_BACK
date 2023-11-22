package com.soa.vie.takaful.repositories;

import com.soa.vie.takaful.entitymodels.RetraiteContrat;
import com.soa.vie.takaful.util.ContratStatus;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface IRetraiteContratRepository extends PagingAndSortingRepository<RetraiteContrat, String> {

        @Query(value = "select max(numero_de_simulation) as numero_de_simulation from contract", nativeQuery = true)
        Long getLastId();

        Page<RetraiteContrat> findByStatus(ContratStatus status, Pageable pageableRequest);

        @Query(value = "select *,1 as clazz_ from contract c inner join retraite_contrat r on c.id = r.contract_id left outer join personne_physique p on c.assure_personne_id = p.personne_id where p.nom like ?% and c.status = 'ACCEPTED' \n-- #pageable\n", countQuery = "SELECT count(*) from contract c inner join retraite_contrat r on c.id = r.contract_id left outer join personne_physique p on c.assure_personne_id = p.personne_id where p.nom like ?% and c.status = 'ACCEPTED'", nativeQuery = true)
        Page<RetraiteContrat> findByAssureNom(Pageable pageable, String nom);

        @Query(value = "select *,1 as clazz_ from contract c inner join retraite_contrat r on c.id = r.contract_id left outer join personne_physique p on c.assure_personne_id = p.personne_id where p.cin like ?% and c.status = 'ACCEPTED' \n-- #pageable\n", countQuery = "SELECT count(*) from contract c inner join retraite_contrat r on c.id = r.contract_id left outer join personne_physique p on c.assure_personne_id = p.personne_id where p.cin like ?% and c.status = 'ACCEPTED'", nativeQuery = true)
        Page<RetraiteContrat> findByAssurecin(Pageable pageable, String cin);

        @Query(value = "select *,1 as clazz_ from contract c inner join retraite_contrat r on c.id = r.contract_id left outer join personne_physique p on c.assure_personne_id = p.personne_id where c.numero_contrat like ?% and c.status = 'ACCEPTED' \n-- #pageable\n", countQuery = "SELECT count(*) from contract c inner join retraite_contrat r on c.id = r.contract_id left outer join personne_physique p on c.assure_personne_id = p.personne_id where c.numero_contrat like ?% and c.status = 'ACCEPTED'", nativeQuery = true)
        Page<RetraiteContrat> findByNumeroContratStartingWith(Pageable pageable, String numeroContrat);

    RetraiteContrat findByNumeroContrat(String numeroContrat);


}
