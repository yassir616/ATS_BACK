package com.soa.vie.takaful.batch;


import com.soa.vie.takaful.entitymodels.Contract;
import com.soa.vie.takaful.entitymodels.Cotisation;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateCotisation;
import com.soa.vie.takaful.services.IDecesContratService;
import com.soa.vie.takaful.util.EtatCotisation;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

public class CotisationItemProcessor implements ItemProcessor<CreateAndUpdateCotisation, Cotisation> {

    @Autowired
    private IDecesContratService contractService;

    @Override
    public Cotisation process(final CreateAndUpdateCotisation lineDTO) throws Exception {
        
        final Cotisation cotisation = new Cotisation();
        // Data transformation - Contract
        Contract contrat = contractService.findOrInsert(lineDTO.getContrat());
        cotisation.setContrat(contrat);

        // Simple type conversion
        cotisation.setEtatCotisation(EtatCotisation.EMIS);
        cotisation.setSolde(lineDTO.getSolde());
        cotisation.setFraisAcquisitionTTC(lineDTO.getFraisAcquisitionTTC());
        cotisation.setFraisGestionTTC(lineDTO.getFraisGestionTTC());
        cotisation.setMontantAccessoire(lineDTO.getMontantAccessoire());
        cotisation.setMontantCotisation(lineDTO.getMontantCotisation());
        cotisation.setMontantTaxe(lineDTO.getMontantTaxe());
        cotisation.setNumQuittance(Integer.parseInt(lineDTO.getNumQuittance()));
        cotisation.setCotisationType(lineDTO.getCotisationType());
        cotisation.setContributionPure(lineDTO.getContributionPure());
        cotisation.setExercice(lineDTO.getExercice());
        cotisation.setDatePrelevement(lineDTO.getDatePrelevement());
        cotisation.setDateEmission(lineDTO.getDatePrelevement());
        cotisation.setFlagBatch(1);
        
        return cotisation;
    }
}
