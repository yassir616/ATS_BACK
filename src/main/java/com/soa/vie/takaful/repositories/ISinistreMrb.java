package com.soa.vie.takaful.repositories;

import java.util.List;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.soa.vie.takaful.entitymodels.PrestationSinistreMrb;

@Repository
@JaversSpringDataAuditable
public interface ISinistreMrb extends PagingAndSortingRepository<PrestationSinistreMrb, String> {

   @Query(value = "select * from prestation_sinistre_mrb ps inner join prestation_mrb pm on ps.prestation_mrb_id = pm.id inner join contrat_mrb cm on cm.contract_id = pm.contrat_mrb_contract_id where pm.contrat_mrb_contract_id like  ?% \n-- #pageable\n", countQuery = "SELECT count(*) from prestation_sinistre_mrb ps inner join prestation_mrb pm on ps.prestation_mrb_id = pm.id inner join contrat_mrb cm on cm.contract_id = pm.contrat_mrb_contract_id where pm.contrat_mrb_contract_id like  ?%", nativeQuery = true)
   public List<PrestationSinistreMrb> findByContratMrbId(String contratId);

}
