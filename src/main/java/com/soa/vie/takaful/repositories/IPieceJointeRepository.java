package com.soa.vie.takaful.repositories;

import com.soa.vie.takaful.entitymodels.PieceJointe;

import java.util.List;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface IPieceJointeRepository extends PagingAndSortingRepository<PieceJointe, String> {

    @Query(value = "select * from piece_jointe where code like ?% \n-- #pageable\n", countQuery = "SELECT * from piece_jointe where code like ?% ", nativeQuery = true)
    public Page<PieceJointe> findPieceJointsByCode(Pageable pageable, String code);

    @Query(value = "select p.* from piece_jointe p inner join typeprestation_piece_jointe tp on p.id=tp.piece_jointe_id inner join type_prestation_produit tpp on tp.type_prestation_id=tpp.id inner join produit pd on tpp.produit_id= pd.id inner join contract c on pd.id=c.produit_id inner join prestation ps on c.id=ps.contrat_id where ps.numero_sinistre=? ", nativeQuery = true)
    public List<PieceJointe> findPieceJointsByNumeroSinistre(String numeroSinistre);
}