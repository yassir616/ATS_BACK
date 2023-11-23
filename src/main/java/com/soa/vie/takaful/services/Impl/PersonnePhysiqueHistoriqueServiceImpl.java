package com.soa.vie.takaful.services.Impl;

import com.soa.vie.takaful.entitymodels.PersonnePhysique;
import com.soa.vie.takaful.entitymodels.PersonnePhysiqueHistorique;
import com.soa.vie.takaful.repositories.IPersonnePhysiqueHistoriqueRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonnePhysiqueHistoriqueServiceImpl {

    @Autowired
    private IPersonnePhysiqueHistoriqueRepository personnePhysiqueHistoriqueRepo;

    public PersonnePhysiqueHistorique createPersonnePhysique(PersonnePhysique model) {

        PersonnePhysiqueHistorique personnePhysique = new PersonnePhysiqueHistorique();
        String id = personnePhysique.getId();
        BeanUtils.copyProperties(model, personnePhysique);
        personnePhysique.setSituationFamiliale(model.getSituationFamiliale());
        personnePhysique.setProfession(model.getProfession());
        personnePhysique.setNationalite(model.getNationalite());
        personnePhysique.setAdressPays(model.getAdressPays());
        personnePhysique.setAdressVille(model.getAdressVille());
        personnePhysique.setAdressVois(model.getAdressVois());
        personnePhysique.setSexe(model.getSexe());
        personnePhysique.setId(id);

        return personnePhysiqueHistoriqueRepo.save(personnePhysique);
    }
}
