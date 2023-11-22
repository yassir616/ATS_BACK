package com.soa.vie.takaful.repositories;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.soa.vie.takaful.entitymodels.Auxiliaire;
import com.soa.vie.takaful.entitymodels.TypeAuxiliaire;
@Repository
@JaversSpringDataAuditable
public interface IAuxiliaireRepository extends PagingAndSortingRepository<Auxiliaire, String> {
	
	 public Optional<Auxiliaire> findByCin(String cin);

	public List<Auxiliaire>  findByTypeAuxiliaire(TypeAuxiliaire typeAuxiliaire);

	//query to get un examinateur par defaut
	@Query(value = "set rowcount 1 select * from auxiliaire a inner join type_auxiliaire t on a.type_auxiliaire_id = t.id where a.adress_ville like ?% and t.libelle like ?% order by NEWID()",
	 countQuery = "SELECT count(*) from auxiliaire a inner join type_auxiliaire t on a.type_auxiliaire_id = t.id where a.adress_ville like ?% and t.libelle like ?% order by NEWID()", nativeQuery = true)
    public Auxiliaire findRandomAuxilaire(String ville ,String type);

	@Query(value =  "select distinct au.*											"+
					"from prestation p												"+
					"left join prestation_honoraire ph on ph.prestation_id = p.id	"+
					"left join auxiliaire au on ph.auxiliaire_id = au.id			"+
					"where p.status='A_SIGNER' and au.id is not null ",nativeQuery = true)
	List<Auxiliaire> findAuxiliaireByStatus();

}
