package com.soa.vie.takaful.services.Impl;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.entitymodels.Avenant;
import com.soa.vie.takaful.entitymodels.DecesContrat;
import com.soa.vie.takaful.entitymodels.DecesContratHistorique;
import com.soa.vie.takaful.entitymodels.PersonnePhysiqueHistorique;
import com.soa.vie.takaful.repositories.IDecesContratHistoriqueRepository;
import com.soa.vie.takaful.repositories.IPersonneRepository;
import com.soa.vie.takaful.requestmodels.UpdateDecesContrat;
import com.soa.vie.takaful.services.IDecesContartHistoriqueService;
import com.soa.vie.takaful.util.Constants;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class DecesContratHistoriqueServiceImpl implements IDecesContartHistoriqueService {

    @Autowired
    private IDecesContratHistoriqueRepository contratHistoriqueRepository;

    @Autowired
    private IPersonneRepository personneRepository;

    @Autowired
    private PersonnePhysiqueHistoriqueServiceImpl personnePhysiqueServiceImpl;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Async("asyncExecutor")
    public DecesContratHistorique createDecesContratHistorique(DecesContrat decesContrat, UpdateDecesContrat model,
            Avenant avenant) throws InterruptedException, ExecutionException {

        Thread.sleep(1000L);
        DecesContratHistorique contratHistorique = new DecesContratHistorique();
        contratHistorique.setAvenantId(avenant);
        contratHistorique.setNumeroContrat(decesContrat.getNumeroContrat());
        contratHistorique.setSouscripteur(decesContrat.getSouscripteur());
        contratHistorique.setProduit(decesContrat.getProduit());
        contratHistorique.setPeriodicite(decesContrat.getPeriodicite());
        contratHistorique.setPointVente(decesContrat.getPointVente());
        contratHistorique.setDateEcheance(decesContrat.getDateEcheance());
        contratHistorique.setDateEffet(decesContrat.getDateEffet());
        contratHistorique.setOptionEmission(decesContrat.getOptionEmission());
        contratHistorique.setStatus(decesContrat.getStatus());
        contratHistorique.setDatePrelevement(decesContrat.getDatePrelevement());
        contratHistorique.setMontantFrais(decesContrat.getMontantFrais());
        contratHistorique.setTauxInteret(decesContrat.getTauxInteret());
        contratHistorique.setTauxReduction(decesContrat.getTauxReduction());
        contratHistorique.setTauxSurprime(decesContrat.getTauxSurprime());
        contratHistorique.setOptionAssurance(decesContrat.getOptionAssurance());

        if (avenant.getNumeroAvenant() == 0
                && avenant.getTypeAvenant().getCode().equals(Constants.CODE_AVENANT_DURE_CAPITAL)) {
            contratHistorique.setDiffere(decesContrat.getDiffere());
            contratHistorique.setMontantCotisation(decesContrat.getMontantCotisation());
            contratHistorique.setMontantSurprime(decesContrat.getMontantSurprime());
            contratHistorique.setMontantTaxe(decesContrat.getMontantTaxe());
            contratHistorique.setProrata(decesContrat.getProrata());
            contratHistorique.setTauxReduction(decesContrat.getTauxReduction());
            contratHistorique.setTauxSurprime(decesContrat.getTauxSurprime());
            contratHistorique.setCapitalAssure(decesContrat.getCapitalAssure());
            contratHistorique.setDureeContrat(decesContrat.getDureeContrat());
        } else if (avenant.getNumeroAvenant() != 0
                && avenant.getTypeAvenant().getCode().equals(Constants.CODE_AVENANT_DURE_CAPITAL)) {
            contratHistorique.setDiffere(model.getDiffere());
            contratHistorique.setMontantCotisation(model.getMontantCotisation());
            contratHistorique.setMontantSurprime(model.getMontantSurprime());
            contratHistorique.setMontantTaxe(model.getMontantTaxe());
            contratHistorique.setProrata(model.getProrata());
            contratHistorique.setTauxReduction(model.getTauxReduction());
            contratHistorique.setTauxSurprime(model.getTauxSurprime());
            contratHistorique.setCapitalAssure(model.getCapital());
            contratHistorique.setDureeContrat(model.getDuree());

        } else if (avenant.getTypeAvenant().getCode().equals(Constants.CODE_AVENANT_CHANGEMENT_ADRESSE)) {
            if (avenant.getNumeroAvenant() == 0) {
                contratHistorique.setAssure(modelMapper.map(
                        this.personnePhysiqueServiceImpl.createPersonnePhysique(decesContrat.getAssure()),
                        PersonnePhysiqueHistorique.class));
            } else {
                if (decesContrat.getAssure().getAdressPays() != model.getAssure().getAdressVille()
                        || decesContrat.getAssure().getAdressVille() != model.getAssure().getAdressVille()
                        || decesContrat.getAssure().getAdressNumero() != model.getAssure().getAdressNumero()
                        || decesContrat.getAssure().getAdressComplete() != model.getAssure().getAdressComplete()) {
                    this.personneRepository.save(model.getAssure());
                    contratHistorique.setAssure(
                            modelMapper.map(this.personnePhysiqueServiceImpl.createPersonnePhysique(model.getAssure()),
                                    PersonnePhysiqueHistorique.class));
                }
            }
        }
        return this.contratHistoriqueRepository.save(contratHistorique);
    }

}
