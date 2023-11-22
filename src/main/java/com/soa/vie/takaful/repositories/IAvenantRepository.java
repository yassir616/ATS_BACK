package com.soa.vie.takaful.repositories;

import java.util.List;

import com.soa.vie.takaful.entitymodels.Avenant;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@JaversSpringDataAuditable
public interface IAvenantRepository extends PagingAndSortingRepository<Avenant, String> {

    @Query(value = "select Max (numero_avenant) as num from avenant where contrat_id like ?% ", countQuery = "SELECT count(*) from avenant where contrat_id like ?% ", nativeQuery = true)
    public Integer numeroAvenant(String id);

    @Query(value = "select * from avenant where contrat_id = :id ", countQuery = "SELECT count(*) from avenant where contrat_id = :id  ", nativeQuery = true)
    public List<Avenant> findByContratId(String id);

    @Modifying
    @Transactional
    @Query(value = "update avenant set contrat_id = ?1 where id =?2", nativeQuery = true)
    public void updateContratAvenant(String contratId, String avenantId);

}