package com.soa.vie.takaful.services.Impl;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.transaction.Transactional;

import com.soa.vie.takaful.entitymodels.PersonnePhysique;
import com.soa.vie.takaful.entitymodels.Profession;
import com.soa.vie.takaful.repositories.IPersonnePhysiqueRepository;
import com.soa.vie.takaful.repositories.IProfessionRepository;
import com.soa.vie.takaful.requestmodels.CreateAndUpdatePersonnePhy;
import com.soa.vie.takaful.responsemodels.PersonnePhysiqueResponseModel;
import com.soa.vie.takaful.services.IPersonnePhysiqueService;
import com.soa.vie.takaful.util.Pagination;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class PersonnePhysiqueImpl implements IPersonnePhysiqueService {

    @Autowired
    private IPersonnePhysiqueRepository personnePhysiqueRepo;

    @Autowired
    private IProfessionRepository professionRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    @Async("asyncExecutor")
    public PersonnePhysiqueResponseModel createPersonnePhysique(CreateAndUpdatePersonnePhy model)
            throws InterruptedException, ExecutionException {
        log.info("creation Personne Physique ...");
        Thread.sleep(1000L);
        PersonnePhysique personnePhysique = new PersonnePhysique();

        Optional<PersonnePhysique> personnePhy = personnePhysiqueRepo.findByCin(model.getCin());
        Optional<Profession> professionexist = professionRepo.findById(model.getProfession());

        if (!personnePhy.isPresent() && professionexist.isPresent()) {
            personnePhysique.setSituationFamiliale(model.getSituationFamiliale());
            personnePhysique.setProfession(professionexist.get());
            personnePhysique.setNationalite(model.getNationalite());
            personnePhysique.setAdressPays(model.getAdressPays());
            personnePhysique.setAdressVille(model.getAdressVille());
            personnePhysique.setAdressVois(model.getAdressVois());
            personnePhysique.setSexe(model.getSexe());
            BeanUtils.copyProperties(model, personnePhysique);

            return modelMapper.map(personnePhysiqueRepo.save(personnePhysique), PersonnePhysiqueResponseModel.class);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "souscripteur already exists");
        }
    }

    @Override
    @Async("asyncExecutor")
    public PersonnePhysiqueResponseModel updatePersonnePhysique(String id, CreateAndUpdatePersonnePhy model)
            throws InterruptedException, ExecutionException {
        log.info("modifier PersonnePhysique avec Id : {}", id);
        Thread.sleep(1000L);
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepo.findById(id);
        Optional<Profession> professionexist = professionRepo.findById(model.getProfession());

        if (!personnePhysique.isPresent() && professionexist.isPresent()) {

            PersonnePhysique perPhysique = personnePhysique.get();

            perPhysique.setSituationFamiliale(model.getSituationFamiliale());
            perPhysique.setProfession(professionexist.get());
            perPhysique.setNationalite(model.getNationalite());
            perPhysique.setAdressPays(model.getAdressPays());
            perPhysique.setAdressVille(model.getAdressVille());
            perPhysique.setAdressVois(model.getAdressVois());
            BeanUtils.copyProperties(model, perPhysique);

            return modelMapper.map(personnePhysiqueRepo.save(perPhysique), PersonnePhysiqueResponseModel.class);

        } else {
            log.error("la modification du participant physique avec ce Id ({}): est erronée", id);
            throw new NullPointerException("la modification du participant physique avec ce Id est erronée");
        }

    }

    @Override
    @Async("asyncExecutor")
    public Page<PersonnePhysiqueResponseModel> getPersonnePhysique(int page, int limit, String sort, String direction)
            throws InterruptedException, ExecutionException {
        log.info("lister des Personnes Physiques");
        Thread.sleep(1000L);
        return personnePhysiqueRepo.findAll(Pagination.pageableRequest(page, limit, sort, direction))
                .map(o -> modelMapper.map(o, PersonnePhysiqueResponseModel.class));

    }

    @Override
    @Async("asyncExecutor")
    public PersonnePhysiqueResponseModel getPersonnePhysique(String cin)
            throws InterruptedException, ExecutionException {
        Thread.sleep(1000L);
        Optional<PersonnePhysique> personPhysique = this.personnePhysiqueRepo.findByCin(cin);
        if (personPhysique.isPresent()) {
            return modelMapper.map(personPhysique.get(), PersonnePhysiqueResponseModel.class);
        }
        return null;
    }

    @Override
    @Async("asyncExecutor")
    public void deleteParticipant(String id) throws InterruptedException, ExecutionException {
        Thread.sleep(1000L);
        personnePhysiqueRepo.deleteById(id);
    }

    @Override
    @Async("asyncExecutor")
    public void personneExist(String cin) throws InterruptedException, ExecutionException {
        Thread.sleep(1000L);
        Optional<PersonnePhysique> personnePhy = personnePhysiqueRepo.findByCin(cin);

        if (personnePhy.isPresent()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Ce cin existe déja");

        }

    }

    @Override
    @Async("asyncExecutor")
    public void ribExist(String rib) throws InterruptedException, ExecutionException {
        Thread.sleep(1000L);
        Optional<PersonnePhysique> personnePhy = personnePhysiqueRepo.findByRib(rib);
        if (personnePhy.isPresent()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Ce rib existe déja");

        }

    }
}