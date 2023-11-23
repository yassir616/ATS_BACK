package com.soa.vie.takaful.repositories.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.soa.vie.takaful.entitymodels.Cotisation;
import com.soa.vie.takaful.repositories.ICotisationRepositoryCustom;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class CotisationRepositoryImpl implements ICotisationRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;

	//cotisation.id,cotisation.datePrelevement,cotisation.montantCotisation,cotisation.etatCotisation,cotisation.numQuittance,cotisation.solde,cotisation.montantTaxe,cotisation.montantTaxeParaFiscale,cotisation.annulation,cotisation.montantAccessoire,cotisation.montantTTC,cotisation.cotisationType,cotisation.fraisAcquisitionTTC,cotisation.fraisGestionTTC,cotisation.contributionPure,cotisation.capitalAssure,cotisation.dateEtablissement,cotisation.exercice,cotisation.numeroLot,cotisation.flagBatch,cotisation.dateEmission
	@Override
	public List<Cotisation> recupererParIds(List<String> cotisationIds) throws InterruptedException, ExecutionException {
    java.util.Map<String, Object> params = new HashMap<>();
    //designé les champs à selectionnées
    StringBuilder queryBuilder = new StringBuilder("SELECT cotisation ")
	.append( "FROM Cotisation cotisation,Contract contrat ");//,PersonnePhysique assure,Personne souscripteur,Produit produit");
    queryBuilder.append(" WHERE   cotisation.contrat.id=contrat.id and cotisation.id IN :cotisationIds ");
    params.put("cotisationIds", cotisationIds);
    Query query = entityManager.createQuery(queryBuilder.toString());
    params.forEach(query::setParameter);
   /* List<Cotisation> resultList = query.getResultList();
    List<Cotisation> cotisations = new ArrayList<>();
	
    for (Object[] result : resultList) {
        Cotisation cotisation = new Cotisation();
        cotisation.setId((String) result[0]); // Assuming id is of type String
        cotisation.setDatePrelevement((Date) result[1]); // Assuming datePrelevement is of type Date
		cotisation.setMontantCotisation((float) result[2]);
		cotisation.setFraisAcquisitionTTC((float) result[3]);
		cotisation.setFraisGestionTTC((float) result[4]);
		cotisation.setMontantTaxe((float) result[5]);
		cotisation.setMontantTTC((float) result[6]);
		cotisation.setSolde((float) result[7]);
		cotisation.setNumQuittance((int) result[8]);
		cotisation.setMontantTaxeParaFiscale((float) result[9]);
		cotisation.setMontantAccessoire((float) result[10]);
		cotisation.setCapitalAssure((float) result[11]);

        cotisations.add(cotisation);
    }
*/
    return 		query.getResultList();

}

    
}