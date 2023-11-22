package com.soa.vie.takaful.repositories;

import java.util.List;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.soa.vie.takaful.entitymodels.Encaissement;

@Repository
@JaversSpringDataAuditable
public interface IEncaissementRepository extends PagingAndSortingRepository<Encaissement, String> {
	
	public List<Encaissement> findByCotisationId(String cotisationId); 

	@Query(value = "SELECT * " +
        "FROM encaissement e " +
        "INNER JOIN cotisation cot ON e.cotisation_id = cot.id " +
        "INNER JOIN contract c ON c.id = cot.contrat_id " +
        "INNER JOIN produit p ON c.produit_id = p.id " +
        "WHERE p.partenaire_id = :partenaireId " +
        "AND e.date_encaissement LIKE CONCAT(:dateEncaissement, '%') " +
        "AND e.compte_bancaire_id = :compteBancaireId "+
        "AND e.flag_bordereau=0", nativeQuery = true)
	public List<Encaissement> findForBordereau(String partenaireId, String dateEncaissement, String compteBancaireId);

}
