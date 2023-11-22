package com.soa.vie.takaful.repositories;

import com.soa.vie.takaful.entitymodels.Contract;
import com.soa.vie.takaful.entitymodels.DecesContrat;
import com.soa.vie.takaful.util.ContratStatus;

import java.util.List;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@JaversSpringDataAuditable
public interface IDecesContratRepository extends PagingAndSortingRepository<DecesContrat, String> {

	public Page<DecesContrat> findAll(Pageable pageableRequest);

	public Page<DecesContrat> findByStatus(ContratStatus status, Pageable pageableRequest);

	@Query(value = "select max(numero_de_simulation) as numero_de_simulation from contract", nativeQuery = true)
	public Long getLastId();

	@Query(value = "select *,1 as clazz_ from contract c inner join deces_contrat d on c.id = d.contract_id left outer join point_vente pn on pn.id=c.point_vente_id  left outer join personne_physique p on c.assure_personne_id = p.personne_id where p.nom like ?% and pn.partenairepv_id = ?  \n-- #pageable\n", countQuery = "select count(*) from contract c inner join deces_contrat d on c.id = d.contract_id left outer join point_vente pn on pn.id=c.point_vente_id  left outer join personne_physique p on c.assure_personne_id = p.personne_id where p.nom like ?% and pn.partenairepv_id = ? ", nativeQuery = true)
	Page<DecesContrat> findByAssureNomAndPartenaire(Pageable pageable, String nom, String partenairId);

	@Query(value = "select *,1 as clazz_ from contract c inner join deces_contrat d on c.id = d.contract_id left outer join point_vente pn on pn.id=c.point_vente_id  left outer join personne_physique p on c.assure_personne_id = p.personne_id where p.cin like ?% and pn.partenairepv_id = ?  \n-- #pageable\n", countQuery = "SELECT count(*) from contract c inner join deces_contrat d on c.id = d.contract_id left outer join point_vente pn on pn.id=c.point_vente_id left outer join personne_physique p on c.assure_personne_id = p.personne_id where p.cin like ?% and pn.partenairepv_id = ?", nativeQuery = true)
	Page<DecesContrat> findByAssurecinAndPartenaire(Pageable pageable, String cin, String partenairId);

	@Query(value = "select *,1 as clazz_ from contract c inner join deces_contrat d on c.id = d.contract_id left outer join point_vente pn on pn.id=c.point_vente_id  left outer join personne_physique p on c.assure_personne_id = p.personne_id where c.numero_contrat like ?% and pn.partenairepv_id = ?  \n-- #pageable\n", countQuery = "SELECT count(*) from contract c inner join deces_contrat d on c.id = d.contract_id left outer join point_vente pn on pn.id=c.point_vente_id left outer join personne_physique p on c.assure_personne_id = p.personne_id where c.numero_contrat like ?% and pn.partenairepv_id = ?", nativeQuery = true)
	Page<DecesContrat> findByNumeroContratAndPartenaire(Pageable pageable, String numeroContrat, String partenairId);

	@Modifying
	@Transactional
	@Query(value = "update contract set status ='ANNULER' where id =?", nativeQuery = true)
	public void updateContratDecesStatus(String contratId);

	@Query(" From Contract as contrat, DecesContrat decesContrat  where contrat.id=decesContrat.id and contrat.status='INSTANTANEE'")
	public List<Contract> selectNoAcceptContractDeces();

	@Modifying
	@Transactional
	@Query(value = "update contract set produit_id = ?, point_vente_id = ?,periodicite_id = ? where id =?", nativeQuery = true)
	public void updateContratProduit(String produitId, String pointVenteId, String periodiciteId, String contratId);

	@Query(value = "select *,1 as clazz_ from contract c inner join   deces_contrat dc on c.id=dc.contract_id left outer join point_vente p on p.id=c.point_vente_id where p.partenairepv_id=?  \n-- #pageable\n", countQuery = "select count(*) from contract c inner join   deces_contrat dc on c.id=dc.contract_id left outer join point_vente p on p.id=c.point_vente_id where p.partenairepv_id like ?% ", nativeQuery = true)
	public Page<DecesContrat> findByPartenaire(String partenairId, Pageable pageableRequest);

	@Query(value = "select *,1 as clazz_ from contract c inner join deces_contrat d on c.id = d.contract_id left outer join personne_physique p on c.assure_personne_id = p.personne_id where p.nom like ?%  \n-- #pageable\n", countQuery = "SELECT count(*) from contract c inner join deces_contrat d on c.id = d.contract_id left outer join personne_physique p on c.assure_personne_id = p.personne_id where p.nom like ?% ", nativeQuery = true)
	Page<DecesContrat> findByAssureNom(Pageable pageable, String nom);

	@Query(value = "select *,1 as clazz_ from contract c inner join deces_contrat d on c.id = d.contract_id left outer join personne_physique p on c.assure_personne_id = p.personne_id where p.cin like ?%  \n-- #pageable\n", countQuery = "SELECT count(*) from contract c inner join deces_contrat d on c.id = d.contract_id left outer join personne_physique p on c.assure_personne_id = p.personne_id where p.cin like ?% ", nativeQuery = true)
	Page<DecesContrat> findByAssurecin(Pageable pageable, String cin);

	@Query(value = "select *,1 as clazz_ from contract c inner join deces_contrat d on c.id = d.contract_id left outer join personne_physique p on c.assure_personne_id = p.personne_id left outer join acceptation a on a.contrat_contract_id=c.id  where a.code like ?%  \n-- #pageable\n", countQuery = "SELECT count(*) from contract c inner join deces_contrat d on c.id = d.contract_id left outer join personne_physique p on c.assure_personne_id = p.personne_id left outer join acceptation a on a.contrat_contract_id=c.id  where a.code like ?% ", nativeQuery = true)
	Page<DecesContrat> findByCodeAcceptation(Pageable pageable, String code);

	@Query(value = "select * from deces_contrat d inner join contract c on d.contract_id=c.id left outer join personne_physique p on c.assure_personne_id = p.personne_id where c.numero_contrat like ?%", nativeQuery = true)
	Page<DecesContrat> findByNumeroContrat(Pageable pageable, String numeroContrat);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "update deces_contrat set montant_surprime = ?1 where contract_id =?2", nativeQuery = true)
	public void updateSurprime(float montantSurprime, String id);

	@Query(value = " select CASE WHEN count(*) = 0 then 'false' ELSE 'true' END as exist,a.encours from contract c inner join deces_contrat d on c.id = d.contract_id inner join acceptation a on a.contrat_contract_id = c.id where c.status = 'ACCEPTED' and c.souscripteur_id like ? and DATEDIFF(month, c.creation_date,GETDATE()) < ? group by a.encours ", nativeQuery = true)
	Object findBySouscripteur(String idSouscripteur, int mois);

	// @Query(value = "select CASE WHEN count(*) = 0 then 'false' ELSE 'true' END
	// from contract c inner join deces_contrat d on c.id = d.contract_id inner join
	// acceptation a on a.contrat_contract_id = c.id where c.status = 'ACCEPTED' and
	// c.souscripteur_id like ? and DATEDIFF(month, c.creation_date,GETDATE()) < ?
	// and a.cumul < ? ", nativeQuery = true)
	// boolean findBySouscripteur(String idSouscripteur,int mois,float cumul);

	@Query(value = "select c.id from contract c inner join deces_contrat d on c.id = d.contract_id inner join acceptation a on a.contrat_contract_id = c.id where c.status = 'ACCEPTED' and c.souscripteur_id like ? and ((DATEDIFF(month, c.creation_date,GETDATE()) < 6 and ? < 2500000 ) Or (DATEDIFF(month, c.creation_date,GETDATE()) < 3 and ? >= 2500000)) group by c.id,c.creation_date order by c.creation_date DESC", nativeQuery = true)
	public List<String> findBySouscripteur(String idSouscripteur, float cumult1, float cumult2);

}
