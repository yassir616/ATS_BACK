package com.soa.vie.takaful.repositories;
import java.util.List;
import com.soa.vie.takaful.entitymodels.Acceptation;
import com.soa.vie.takaful.entitymodels.AcceptationConseil;
import com.soa.vie.takaful.entitymodels.AcceptationExamens;
import com.soa.vie.takaful.entitymodels.AcceptationExaminateur;
import com.soa.vie.takaful.entitymodels.AcceptationLaboratoire;
import com.soa.vie.takaful.entitymodels.AcceptationTestMedical;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface IAcceptationTestMedicalRepository extends PagingAndSortingRepository<AcceptationTestMedical, String> {

    public AcceptationTestMedical findByAcceptationAndAcceptationLaboratoire(Acceptation acceptation,AcceptationLaboratoire acceptationLaboratoire);
    
    public AcceptationTestMedical findByAcceptationAndAcceptationExamens(Acceptation acceptation,AcceptationExamens acceptationExames);

    public AcceptationTestMedical findByAcceptationAndAcceptationExaminateur(Acceptation acceptation,AcceptationExaminateur acceptationExaminateur);

    @Query(value = "declare @request nvarchar(max)																									"+																				    
                   "set @request='																										    		"+
                   "select *	                                                                                                       	    		"+
                   "from acceptation_test_medical t '+Right(?,100)+QUOTENAME(?,'''')+'																				"+
                   "inner join acceptation a on a.id  = t.acceptation_id 																			"+
                   "inner join deces_contrat c on c.contract_id = a.contrat_contract_id 															"+
                   "inner join contract co on co.id = c.contract_id 																				"+
                   "inner join produit p on p.id = co.produit_id 																					"+
                   "where t.paid ='+QUOTENAME('false','''')+' and p.partenaire_id =' +QUOTENAME(?,'''')                                           "+            
                   "EXECUTE sp_executesql @request ", nativeQuery = true)
    public List<AcceptationTestMedical> find(String TableJoint,String auxiliaire,String partenaire);


    
    // @Query(
    //     value = "select * from acceptation_test_medical t inner join acceptation_laboratoire e on t.acceptation_laboratoire_id = e.id inner join acceptation a on a.id  = t.acceptation_id inner join deces_contrat c on c.contract_id = a.contrat_contract_id inner join contract co on co.id = c.contract_id inner join produit p on p.id = co.produit_id where t.paid = 'false'  and  e.laboratoire_id = ? and p.partenaire_id = ? and p.id = ?", 
    //     countQuery = "SELECT count(*) from acceptation_test_medical t inner join acceptation_laboratoire e on t.acceptation_laboratoire_id = e.id where t.paid = 'false'  and  e.laboratoire_id = ? and p.partenaire_id = ? and p.id = ?",
    //     nativeQuery = true)
       
    // public List<AcceptationTestMedical> findByLaboratoire(String laboratoire, String partenaire , String produit);
   
    //     @Query(
    //         value = "select * from acceptation_test_medical t inner join acceptation_examinateur e  on t.acceptation_examinateur_id = e.id inner join acceptation a on a.id  = t.acceptation_id inner join deces_contrat c on c.contract_id = a.contrat_contract_id inner join contract co on co.id = c.contract_id inner join produit p on p.id = co.produit_id inner join auxiliaire au on e.medecin_id=au.id where t.paid = 'false'  and  e.medecin_id = ? and p.partenaire_id = ? and p.id = ?", 
    //         countQuery = "SELECT count(*) from acceptation_test_medical t inner join acceptation_examinateur e  on t.acceptation_examinateur_id = e.id inner join acceptation a on a.id  = t.acceptation_id inner join deces_contrat c on c.contract_id = a.contrat_contract_id inner join contract co on co.id = c.contract_id inner join produit p on p.id = co.produit_id where t.paid = 'false'  and  e.medecin_id = ? and p.partenaire_id = ? and p.id = ?",
    //         nativeQuery = true)
    // public List<AcceptationTestMedical> findByExaminateur(String examinateur, String partenaire , String produit);

    // @Query(
    //             value = "select * from acceptation_test_medical t inner join acceptation_conseil e on t.acceptation_conseil_id = e.id inner join acceptation a on a.id  = t.acceptation_id inner join deces_contrat c on c.contract_id = a.contrat_contract_id inner join contract co on co.id = c.contract_id inner join produit p on p.id = co.produit_id where t.paid = 'false'  and  e.medecin_id = ?  and p.partenaire_id = ? and p.id = ?", 
    //             countQuery = "SELECT count(*) from acceptation_test_medical t inner join acceptation_conseil e on t.acceptation_conseil_id = e.id inner join acceptation a on a.id  = t.acceptation_id inner join deces_contrat c on c.contract_id = a.contrat_contract_id inner join contract co on co.id = c.contract_id inner join produit p on p.id = co.produit_id where t.paid = 'false'  and  e.medecin_id = ?  and p.partenaire_id = ? and p.id = ?",
    //             nativeQuery = true)
    // public List<AcceptationTestMedical> findByConseil(String conseil, String partenaire , String produit);

    // @Query(
    //             value = "select * from acceptation_test_medical t inner join acceptation_specialiste e on t.acceptation_specialiste_id = e.id inner join acceptation a on a.id  = t.acceptation_id inner join deces_contrat c on c.contract_id = a.contrat_contract_id inner join contract co on co.id = c.contract_id inner join produit p on p.id = co.produit_id where t.paid = 'false'  and  e.specialiste_id = ?  and p.partenaire_id = ? and p.id = ?", 
    //             countQuery = "SELECT count(*) from acceptation_test_medical t inner join acceptation_conseil e on t.acceptation_conseil_id = e.id inner join acceptation a on a.id  = t.acceptation_id inner join deces_contrat c on c.contract_id = a.contrat_contract_id inner join contract co on co.id = c.contract_id inner join produit p on p.id = co.produit_id where t.paid = 'false'  and  e.specialiste_id = ?  and p.partenaire_id = ? and p.id = ?",
    //             nativeQuery = true)
    // public List<AcceptationTestMedical> findBySpecialiste(String specialiste, String partenaire , String produit);

    // @Query(
    //     value = "select * from acceptation_test_medical t inner join acceptation_examens e on t.acceptation_examens_id = e.id inner join acceptation a on a.id  = t.acceptation_id inner join deces_contrat c on c.contract_id = a.contrat_contract_id inner join contract co on co.id = c.contract_id inner join produit p on p.id = co.produit_id where t.paid = 'false'  and  e.specialiste_id = ?  and p.partenaire_id = ? and p.id = ?", 
    //     countQuery = "SELECT count(*) from acceptation_test_medical t inner join acceptation_conseil e on t.acceptation_conseil_id = e.id inner join acceptation a on a.id  = t.acceptation_id inner join deces_contrat c on c.contract_id = a.contrat_contract_id inner join contract co on co.id = c.contract_id inner join produit p on p.id = co.produit_id where t.paid = 'false'  and  e.specialiste_id = ?  and p.partenaire_id = ? and p.id = ?",
    //     nativeQuery = true)
    // public List<AcceptationTestMedical> findBy(String specialiste, String partenaire , String produit);


    public AcceptationTestMedical findByAcceptationAndAcceptationConseil(Acceptation acceptation,AcceptationConseil acceptationConseil);

}