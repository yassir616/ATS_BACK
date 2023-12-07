package com.soa.vie.takaful.repositories;

import com.soa.vie.takaful.entitymodels.PrestationHonoraire;

import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;



@JaversSpringDataAuditable
public interface IPrestationHonoraireRepository extends PagingAndSortingRepository<PrestationHonoraire, String> {

    @Query(value = "select * from prestation p "+
                   "inner join prestation_honoraire ph on ph.prestation_id = p.id "+  
                   "inner join prestation_honoraire_detail_prestation_honoraire phdp on phdp.prestation_honoraire_prestation_id = p.id "+ 
                   "inner join detail_prestation_honoraire dph on dph.id = phdp.detail_prestation_honoraire_id "+
                   "inner join acceptation_test_medical at on at.id = dph.acceptation_test_medical_id "+
                   "inner join acceptation a on a.id = at.acceptation_id inner join contract c on c.id = a.contrat_contract_id  where "+
                   "type_prestation= ? and p.status = 'A_SIGNER' and mode_reglement=?",nativeQuery = true)
    List<PrestationHonoraire> findByStatusAndModeReglementHonoraire(String type,String ModeReglement);

    @Query(value = "select * from prestation p "+
                   "inner join prestation_honoraire ph on ph.prestation_id = p.id "+  
                   "inner join prestation_honoraire_detail_prestation_honoraire phdp on phdp.prestation_honoraire_prestation_id = p.id "+ 
                   "inner join detail_prestation_honoraire dph on dph.id = phdp.detail_prestation_honoraire_id "+
                   "inner join acceptation_test_medical at on at.id = dph.acceptation_test_medical_id "+
                   "inner join acceptation a on a.id = at.acceptation_id inner join contract c on c.id = a.contrat_contract_id  where "+
                   "type_prestation= ? and p.status = 'A_SIGNER' and mode_reglement=? and ph.auxiliaire_id=? ",nativeQuery = true)
    List<PrestationHonoraire> findByStatusAndModeReglementHonoraireAndAuxiliaire(String type,String ModeReglement,String auxiliaire);


    @Query(value = "declare @request nvarchar(max)																						     "+
                   "set @request='																										     "+
                   "select *	                                                                                                       	     "+
                   "from prestation p 																									     "+
                   "inner join prestation_honoraire ph on ph.prestation_id = p.id 														     "+
                   "inner join prestation_honoraire_detail_prestation_honoraire phdp on phdp.prestation_honoraire_prestation_id = p.id 	     "+
                   "inner join detail_prestation_honoraire dph on dph.id = phdp.detail_prestation_honoraire_id 						   	     "+
                   "inner join acceptation_test_medical at on at.id = dph.acceptation_test_medical_id 									     "+
                   "inner join auxiliaire au on au.id = ph.auxiliaire_id																     "+
                   "inner join acceptation a on a.id = at.acceptation_id 																     "+
                   "inner join contract c on c.id = a.contrat_contract_id 																     "+
                   "inner join personne_physique per on c.assure_personne_id = per.personne_id 										   	     "+
                   "'+Right(?,100)+ 				                                                                                         "+                                                                          
                   "' where p.status ='+QUOTENAME(?,'''')+' and ph.auxiliaire_id =' +QUOTENAME(?,'''')+' and at.paid = 1'  "+                   
                   "EXECUTE sp_executesql @request ", nativeQuery = true)
    public List<PrestationHonoraire> paidPrestationHonooraire(String TableJoint,String status ,String auxiliaire);

    @Query(value = "select *																											     "+                                                                                                 
                   "from prestation p 																									     "+
                   "inner join prestation_honoraire ph on ph.prestation_id = p.id 														     "+
                   "inner join prestation_honoraire_detail_prestation_honoraire phdp on phdp.prestation_honoraire_prestation_id = p.id       "+
                   "inner join detail_prestation_honoraire dph on dph.id = phdp.detail_prestation_honoraire_id 						         "+
                   "inner join acceptation_test_medical at on at.id = dph.acceptation_test_medical_id 									     "+
                   "inner join auxiliaire au on au.id = ph.auxiliaire_id																     "+
                   "inner join acceptation a on a.id = at.acceptation_id 																     "+
                   "inner join contract c on c.id = a.contrat_contract_id 																     "+
                   "inner join personne_physique per on c.assure_personne_id = per.personne_id 										         "+
                   "where p.status = ? and at.paid = 1", nativeQuery = true)
    public List<PrestationHonoraire> paidPrestationHonooraireByStatut(String status);


    @Query(value = "select *																											     "+                                                                                                 
                   "from prestation p 																									     "+
                   "inner join prestation_honoraire ph on ph.prestation_id = p.id 														     "+
                   "inner join prestation_honoraire_detail_prestation_honoraire phdp on phdp.prestation_honoraire_prestation_id = p.id       "+
                   "inner join detail_prestation_honoraire dph on dph.id = phdp.detail_prestation_honoraire_id 						         "+
                   "inner join acceptation_test_medical at on at.id = dph.acceptation_test_medical_id 									     "+
                   "inner join auxiliaire au on au.id = ph.auxiliaire_id																     "+
                   "inner join acceptation a on a.id = at.acceptation_id 																     "+
                   "inner join contract c on c.id = a.contrat_contract_id 																     "+
                   "inner join personne_physique per on c.assure_personne_id = per.personne_id 										         "+
                   "where p.status = ? and ph.auxiliaire_id = ? and at.paid = 1", nativeQuery = true)
    public List<PrestationHonoraire> paidPrestationHonooraireByStatutAuxiliaire(String status,String Auxiliaire);

            @Modifying
            @Transactional
            @Query(value="update detail_prestation_honoraire set montant_honoraire= ? where id=? ",
            nativeQuery = true)
            public void updateMontant(Float montantHonoraire,String id);

            @Modifying
            @Transactional            
            @Query(value="declare @montant float,@montantIr float, @montantNet float,@nu varchar(50)=? "+
            "set @montant =(select sum(montant_honoraire) from detail_prestation_honoraire where id in "+
            "(select  detail_prestation_honoraire_id from prestation_honoraire_detail_prestation_honoraire  where prestation_honoraire_prestation_id "+
            "in( select id from prestation where numero_sinistre=@nu))) "+
            "UPDATE prestation set montant=@montant,montant_ir=0,montant_net=@montant where numero_sinistre=@nu",
            nativeQuery=true)
            public void updateMontantPrestationIS(String numeroSinistre);

            @Modifying
            @Transactional
            @Query(value="declare @montant float,@montantIr float, @montantNet float,@nu varchar(250)=? "+
            "set @montant =(select sum(montant_honoraire) from detail_prestation_honoraire where id in "+
            "(select  detail_prestation_honoraire_id from prestation_honoraire_detail_prestation_honoraire  where prestation_honoraire_prestation_id "+ 
            "in( select id from prestation where numero_sinistre=@nu))) "+
            "set @montantIr= @montant * 0.1 "+
            "set @montantNet=@montant - @montantIr "+
            "UPDATE prestation set montant=@montant,montant_ir=@montantIr,montant_net=@montantNet where numero_sinistre=@nu",
            nativeQuery=true)
            public void updateMontantPrestationIR(String numeroSinistre);

         /*   @Query(value = "SELECT rh.honoraire_id,ph.reference,a.adress_pays,a.adress_Ville,a.adress_complement,a.email,a.nom,a.patente,a.prenom," +
                    "a.type_personne," +
                    "a.specialite,a.cin,a.rib,a.type_fiscal,ta.code,ta.libelle" +
                    "from  reglement_honoraires rh,prestation_honoraire ph,auxiliaire a,type_auxiliaire ta" +
                    "WHERE  rh.honoraire_id=ph.prestation_id" +
                    "and ph.auxiliaire_id=a.id and a.type_auxiliaire_id=ta.id");
            public List<PrestationHonoraire> findPrestationHonoraireByReglementId(UUID reglementId);

*/


}
