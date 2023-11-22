package com.soa.vie.takaful.repositories;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.soa.vie.takaful.entitymodels.Cotisation;

public interface ICotisationRepository extends PagingAndSortingRepository<Cotisation, String>,ICotisationRepositoryCustom {

	@Query(value = "select * from cotisation  where solde != 0 \n-- #pageable\n", countQuery = "SELECT count(*) from cotisation  where solde != 0 ", nativeQuery = true)
	public Page<Cotisation> findAllCotisation(Pageable pageable);

	public List<Cotisation> findByContratId(String contratId);

	@Query(value = "select *,1 as clazz_ from cotisation c inner join contract cr on cr.id= c.contrat_id inner join produit p on p.id = cr.produit_id where c.solde > 0 and cr.status ='ACCEPTED' and p.code =?1\n-- #pageable\n", countQuery = "SELECT count(*) from cotisation where id NOT IN (select cotisation_id from encaissement) ", nativeQuery = true)
	List<Cotisation> findAllCotisations(String type);

	@Query(value = "select sum(ROUND(solde, 2)) from cotisation c inner join contract cr on cr.id= c.contrat_id inner join produit p on p.id = cr.produit_id inner join partenaire par on par.id= p.partenaire_id where c.solde > 0 and cr.status ='ACCEPTED' and p.code =?1\n-- #pageable\n", countQuery = "SELECT count(*) from cotisation where id NOT IN (select cotisation_id from encaissement) ", nativeQuery = true)
	public float sumSolde(String type);

	@Query(value = "select count(*) from cotisation c inner join contract cr on cr.id= c.contrat_id inner join produit p on p.id = cr.produit_id inner join partenaire par on par.id= p.partenaire_id  where c.solde > 0 and cr.status ='ACCEPTED' and p.code =?1\n-- #pageable\n", countQuery = "SELECT count(*) from cotisation where id NOT IN (select cotisation_id from encaissement) ", nativeQuery = true)
	public int countData(String type);

	@Query(value = "select distinct (r.libelle) from cotisation c inner join contract cr on cr.id= c.contrat_id inner join produit p on p.id = cr.produit_id inner join risque r on p.risque_id = r.id order by r.libelle", nativeQuery = true)
	List<String> findProductContisation();

	@Query(value = "select distinct (p.code) from cotisation c inner join contract cr on cr.id= c.contrat_id inner join produit p on p.id = cr.produit_id order by p.code", nativeQuery = true)
	List<String> findProductCotisation();

	@Modifying
	@Transactional
	@Query(value = "update contract set num_ordre_prelevement = ?1 where id =?2", nativeQuery = true)
	public void updateNumeroOrdre(int numOrdre, String contratId);

	// @Query(value = "SELECT cot.id FROM cotisation cot " +
	// 		"INNER JOIN contract c ON cot.contrat_id = c.id " +
	// 		"INNER JOIN produit p ON p.id = c.produit_id " +
	// 		"INNER JOIN partenaire pat ON pat.id = p.partenaire_id " +
	// 		"WHERE c.status = 'ACCEPTED' " +
	// 		"AND cot.etat_cotisation = 'EMIS' " +
	// 		"AND pat.id LIKE %:partenaireId% " +
	// 		"AND p.id LIKE %:produitId% " +
	// 		"AND cot.date_prelevement between :startDate and :endDate", nativeQuery = true)
	// public List<String> getEmissionGlobale(@Param("partenaireId") String partenaireId,
	// 		@Param("produitId") String produitId, @Param("startDate") String startDate,
	// 		@Param("endDate") String endDate);	
	@Query(value = "SELECT cot.id FROM cotisation cot WHERE cot.etat_cotisation = 'EMIS' "+
	  "AND cot.date_prelevement between :startDate and :endDate " +
	  "AND EXISTS (SELECT 1 FROM contract c JOIN produit p ON p.id = c.produit_id " +
	  "JOIN partenaire pat ON pat.id = :partenaireId" +
	  " WHERE c.status = 'ACCEPTED' AND p.id = :produitId  " +
		"AND cot.contrat_id = c.id);", nativeQuery = true)
	public List<String> getEmissionGlobale(@Param("partenaireId") String partenaireId,
			@Param("produitId") String produitId, @Param("startDate") Date startDate,
			@Param("endDate") Date endDate);


	// @Query(value = "SELECT cot.* FROM cotisation cot "
	// 		+ "INNER JOIN contract c ON cot.contrat_id = c.id "
	// 		// + "INNER JOIN produit p ON p.id = c.produit_id "
	// 		// + "INNER JOIN partenaire pat ON pat.id = p.partenaire_id "
	// 		+ "WHERE c.status = 'ACCEPTED' "
	// 		+ "AND cot.etat_cotisation = 'EMIS' "
	// 		+ "AND convert(date,cot.date_prelevement ) >= :startDate and convert(date,cot.date_prelevement ) <= :endDate", nativeQuery = true)
	@Query(value = "SELECT cot.* FROM cotisation cot where "
			//+ "INNER JOIN contract c ON cot.contrat_id = c.id  "
			// + "INNER JOIN produit p ON p.id = c.produit_id "
			// + "INNER JOIN partenaire pat ON pat.id = p.partenaire_id "
			//+ "WHERE c.status = 'ACCEPTED' "
			+ "cot.etat_cotisation = 'EMIS' "
			+ "AND convert(date,cot.date_prelevement ) >= :startDate and convert(date,cot.date_prelevement ) <= :endDate", nativeQuery = true)		
	public List<Cotisation> getEmissionGlobaleByDateOnly(@Param("startDate") String startDate,
			@Param("endDate") String endDate);

	@Query(value = "SELECT cot.* FROM cotisation cot "
			+ "INNER JOIN contract c ON cot.contrat_id = c.id "
			+ "INNER JOIN produit p ON p.id = c.produit_id "
			+ "INNER JOIN partenaire pat ON pat.id = p.partenaire_id "
			+ "WHERE c.status = 'ACCEPTED' "
			+ "AND cot.etat_cotisation = 'EMIS' "
			+ "AND pat.id LIKE %:partenaireId% "
			+ "AND cot.date_prelevement between  :startDate and :endDate", nativeQuery = true)
	public List<Cotisation> getEmissionGlobaleByDateAndPartenaire(@Param("partenaireId") String partenaireId,
			@Param("startDate") String startDate, @Param("endDate") String endDate);

	@Query(value = "SELECT * from cotisation cot inner join emission_globale eg on cot.numero_lot=eg.numero_lot where cot.numero_lot=:numeroLot", nativeQuery = true)
	public List<Cotisation> getCotisationByNumeroLot(@Param("numeroLot") String numeroLot);

}
