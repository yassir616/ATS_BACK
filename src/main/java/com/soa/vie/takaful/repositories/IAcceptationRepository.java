package com.soa.vie.takaful.repositories;

import java.util.List;
import java.util.Optional;

import com.soa.vie.takaful.entitymodels.Acceptation;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface IAcceptationRepository extends PagingAndSortingRepository<Acceptation, String> {
   
    @Query(value = "select * from acceptation a inner join contract d on a.contrat_contract_id = d.id inner join personne_physique p on d.assure_personne_id = p.personne_id where d.numero_contrat like ?% \n-- #pageable\n", countQuery = "SELECT count(*) from acceptation a inner join contract d on a.contrat_contract_id = d.id inner join personne_physique p on d.assure_personne_id = p.personne_id where d.numero_contrat like ?%", nativeQuery = true)
    public Page<Acceptation> findByNumeroContrat(Pageable pageable, String numeroContrat);
    
    @Query(value = "select * from acceptation a inner join contract d on a.contrat_contract_id = d.id inner join personne_physique p on d.assure_personne_id = p.personne_id where p.prenom like ?% \n-- #pageable\n", countQuery = "SELECT count(*) from acceptation a inner join contract d on a.contrat_contract_id = d.id inner join personne_physique p on d.assure_personne_id = p.personne_id where p.prenom like ?%", nativeQuery = true)
    public Page<Acceptation> findByAssurePrenom(Pageable pageable, String prenom);

    @Query(value = "select * from acceptation a inner join contract d on a.contrat_contract_id = d.id inner join personne_physique p on d.assure_personne_id = p.personne_id where p.nom like ?% \n-- #pageable\n", countQuery = "SELECT count(*) from acceptation a inner join contract d on a.contrat_contract_id = d.id inner join personne_physique p on d.assure_personne_id = p.personne_id where p.nom like ?%", nativeQuery = true)
    public Page<Acceptation> findByAssureNom(Pageable pageable, String nom);

    public Page<Acceptation> findByCodeStartingWith(Pageable pageable, String code);

    @Query(value = "select * from acceptation a inner join contract d on a.contrat_contract_id = d.id inner join personne_physique p on d.assure_personne_id = p.personne_id where a.contrat_contract_id like ?%\n-- #pageable\n", countQuery = "SELECT count(*) from acceptation a inner join contract d on a.contrat_contract_id = d.id inner join personne_physique p on d.assure_personne_id = p.personne_id where a.contrat_contract_id like ?%", nativeQuery = true)
    public Optional<Acceptation> findByContratId(String id);

    @Query(value = "select a.code from acceptation a inner join contract d on a.contrat_contract_id = d.id inner join personne_physique p on d.assure_personne_id = p.personne_id where a.contrat_contract_id like ?%\n-- #pageable\n", countQuery = "SELECT count(*) from acceptation a inner join contract d on a.contrat_contract_id = d.id inner join personne_physique p on d.assure_personne_id = p.personne_id where a.contrat_contract_id like ?%", nativeQuery = true)
    public String findCodeByContratId(String id);

    @Query(value="select h.code from honoraire h inner join acceptation_test_medical_honoraires atmh on h.id = atmh.honoraires_id inner join acceptation_test_medical atm on atmh.acceptation_test_medical_id = atm.id inner join acceptation a on atm.acceptation_id = a.id where a.code = ? group by h.code", nativeQuery = true)
    public List<String> findByCode(String code);

}