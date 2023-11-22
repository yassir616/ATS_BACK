package com.soa.vie.takaful.repositories;

import com.soa.vie.takaful.entitymodels.ContratMrb;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IContratMrb extends PagingAndSortingRepository<ContratMrb, String> {
	//@Query(value = "select *,1 as clazz_ from contrat_mrb m left outer join personne_physique p on m.assure_personne_id = p.personne_id where p.nom like ?% \n-- #pageable\n", countQuery = "SELECT count(*) from contrat_mrb m left outer join personne_physique p on m.assure_personne_id = p.personne_id where p.nom like ?% ", nativeQuery = true)
	@Query(value = "select * from contrat_mrb m inner join contract c on m.contract_id=c.id left outer join personne_physique p on c.assure_personne_id = p.personne_id where p.nom like ?% \n-- #pageable\n", countQuery = "SELECT count(*) from contrat_mrb m inner join contract c on m.contract_id=c.id left outer join personne_physique p on c.assure_personne_id = p.personne_id where p.nom like ?% ", nativeQuery = true)

	Page<ContratMrb> findByAssureNom(Pageable pageable, String nom);

	@Query(value = "select * from contrat_mrb m inner join contract c on m.contract_id=c.id left outer join personne_physique p on c.assure_personne_id = p.personne_id where c.numero_simulation like ?%\n-- #pageable\n", countQuery = "SELECT count(*) from contrat_mrb m inner join contract c on m.contract_id=c.id left outer join personne_physique p on c.assure_personne_id = p.personne_id where c.numero_simulation like ?%", nativeQuery = true)
	Page<ContratMrb> findByNumeroSimulation(Pageable pageable, String numeroSimulation);

	@Query(value = "select * from contrat_mrb m inner join contract c on m.contract_id=c.id left outer join personne_physique p on c.assure_personne_id = p.personne_id where c.numero_contrat like ?%\n-- #pageable\n", countQuery = "SELECT count(*) from contrat_mrb m inner join contract c on m.contract_id=c.id left outer join personne_physique p on c.assure_personne_id = p.personne_id where c.numero_contrat like ?%", nativeQuery = true)
	Page<ContratMrb> findByNumeroContratStartingWith(Pageable pageable, String numeroContrat);
}