package com.soa.vie.takaful.repositories;

import java.util.List;

import com.soa.vie.takaful.entitymodels.PrestationSinistre;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface ISinistreRepository extends PagingAndSortingRepository<PrestationSinistre, String> {

	@Query(value = "select *,1 as clazz_ from prestation_sinistre s join prestation p on s.prestation_id=p.id inner join contract c on  c.id=p.contrat_id where c.id=:contratId", countQuery = "select count(*) from prestation_sinistre s join prestation p on s.prestation_id=p.id inner join contract c on  c.id=p.contrat_id where c.id=:contratId", nativeQuery = true)
	public List<PrestationSinistre> findByContratId(String contratId);

	@Query(value = "select *,1 as clazz_  from prestation c inner join prestation_sinistre d on c.id = d.prestation_id left outer join contract p on c.contrat_id = p.id left outer join produit pr on p.produit_id = pr.id where c.status = 'A_SIGNER' and c.mode_reglement = 'Virement' and pr.id like ?% and c.type_prestation like ?%", countQuery = "select count(*) from prestation c inner join prestation_sinistre d on c.id = d.prestation_id left outer join contract p on c.contrat_id = p.id left outer join produit pr on p.produit_id = pr.id where c.status = 'A_SIGNER' and c.mode_reglement = 'Virement' and pr.id like ?% and c.type_prestation like ?%", nativeQuery = true)
	List<PrestationSinistre> findByProductIdAndStatusAndModeReglement(String id, String type);

	@Query(value = "select *,1 as clazz_ from prestation_sinistre ps inner join prestation p on ps.prestation_id=p.id inner join contract c on c.id=p.contrat_id where numero_contrat like ?%",
			// value="select *,1 as clazz_ from prestation_sinistre ps inner join prestation
			// p on p.contrat_id=c.id inner join prestation_sinistre ps on
			// ps.prestation_id=p.id where numero_contrat like ?%",
			// countQuery = "select count(*) from prestation_sinistre ps inner join
			// prestation p on p.contrat_id=c.id inner join prestation_sinistre ps on
			// ps.prestation_id=p.id where numero_contrat like ?%",
			nativeQuery = true)
	public List<PrestationSinistre> findbyNumeroContract(String numeroContract);

	@Query(value = "select *,1 as clazz_ from prestation_sinistre ps inner join prestation p on ps.prestation_id=p.id inner join contract c on c.id=p.contrat_id where p.numero_sinistre like ?%", nativeQuery = true)

	public List<PrestationSinistre> findbyNumeroSinistre(String numeroSinistre);

	@Query(value = "select *,1 as clazz_ from prestation_sinistre where prestation_id=?", nativeQuery = true)
	public List<PrestationSinistre> findByPrestationId(String prestationId);

}