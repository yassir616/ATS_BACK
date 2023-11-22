package com.soa.vie.takaful.servicesImplementation;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.transaction.Transactional;

import com.soa.vie.takaful.entitymodels.PersonneMorale;
import com.soa.vie.takaful.entitymodels.SecteurActivite;
import com.soa.vie.takaful.entitymodels.TypePersonneMorale;
import com.soa.vie.takaful.repositories.IPersonneMoraleRepository;
import com.soa.vie.takaful.repositories.ISecteurActiviteRepository;
import com.soa.vie.takaful.repositories.ITypePersonneMoraleRepository;
import com.soa.vie.takaful.requestmodels.CreateAndUpdatePersonneMor;
import com.soa.vie.takaful.responsemodels.PersonneMoraleResponseModel;
import com.soa.vie.takaful.services.IPersonneMoraleService;
import com.soa.vie.takaful.util.Pagination;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class PersonneMoraleImpl implements IPersonneMoraleService {

    @Autowired
    private IPersonneMoraleRepository personneMoraleRepo;

    @Autowired
    private ITypePersonneMoraleRepository typePersonneMoraleRepo;

    @Autowired
    private ISecteurActiviteRepository secteurActiviteRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Async("asyncExecutor")
    public PersonneMoraleResponseModel createPersonneMorale(CreateAndUpdatePersonneMor p)
            throws InterruptedException, ExecutionException {
        log.info("creation PersonneMorale ...");
        PersonneMorale personneMorale = new PersonneMorale();   
 
        Optional<TypePersonneMorale> typePersMor;
        if(p.getTypePersonneMorale()==null){
            typePersMor = this.typePersonneMoraleRepo.findById("21");
        }
        else
        {
            typePersMor = this.typePersonneMoraleRepo.findById("21");
            // typePersMor = this.typePersonneMoraleRepo.findById(p.getTypePersonneMorale().getId());
        }
    
        Optional<SecteurActivite> secteurActivite = this.secteurActiviteRepository
                .findById(p.getSecteurActivite().getId());
        Optional<PersonneMorale> personneMoralepatente = personneMoraleRepo.findByPatente(p.getPatente());

        if (!personneMoralepatente.isPresent()) {
            if (typePersMor.isPresent() && secteurActivite.isPresent()) {

                BeanUtils.copyProperties(p, personneMorale);
                personneMorale.setTypePersonneMorale(typePersMor.get());
                personneMorale.setSecteurActivite(secteurActivite.get());
                personneMorale.setAdressPays(p.getAdressPays());
                personneMorale.setAdressVille(p.getAdressVille());
                personneMorale.setAdressVois(p.getAdressVois());
                personneMorale.setCode("code");

                return modelMapper.map(personneMoraleRepo.save(personneMorale), PersonneMoraleResponseModel.class);
                
            } else {
                log.error("la creation du personne morale est erronée");
                throw new NullPointerException("la creation du personne morale  est erronée");
            }
        } else {
            log.error("souscripteur already exists");
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "souscripteur already exists");
        }

    }

    @Override
    @Async("asyncExecutor")
    public PersonneMoraleResponseModel updatePersonneMorale(String id, CreateAndUpdatePersonneMor p)
            throws InterruptedException, ExecutionException {
        log.info("modifier PersonneMorale avec Id : {}", id);
        Thread.sleep(1000L);
        Optional<PersonneMorale> personneMorale = personneMoraleRepo.findById(id);
        Optional<TypePersonneMorale> typePersonneMorale = typePersonneMoraleRepo
                .findById(p.getTypePersonneMorale().getId());

        if (personneMorale.isPresent() && typePersonneMorale.isPresent()) {
            PersonneMorale perMorale = personneMorale.get();
            perMorale.setTypePersonneMorale(typePersonneMorale.get());
            BeanUtils.copyProperties(p, perMorale);

            return modelMapper.map(personneMoraleRepo.save(perMorale), PersonneMoraleResponseModel.class);

        } else {
            log.error("la modification du participant avec ce Id ({}): est erronée", id);
            throw new NullPointerException("la modification du participant avec ce Id est erronée");
        }

    }

    @Override
    @Async("asyncExecutor")
    public Page<PersonneMoraleResponseModel> getPersonneMorale(int page, int limit, String sort, String direction)
            throws InterruptedException, ExecutionException {
        log.info("lister des Personnes Morales");
        Thread.sleep(1000L);
        return personneMoraleRepo.findAll(Pagination.pageableRequest(page, limit, sort, direction))
                .map(o -> modelMapper.map(o, PersonneMoraleResponseModel.class));
    }

    @Override
    @Async("asyncExecutor")
    public PersonneMoraleResponseModel getPersonneMorale(String patente)
            throws InterruptedException, ExecutionException {
        Thread.sleep(1000L);
        Optional<PersonneMorale> personneMorale = this.personneMoraleRepo.findByPatente(patente);
        if (personneMorale.isPresent()) {

            return modelMapper.map(personneMorale.get(), PersonneMoraleResponseModel.class);
        }
        return null;
    }

    @Override
    @Async("asyncExecutor")
    public void deleteParticipant(String id) throws InterruptedException, ExecutionException {
        Thread.sleep(1000L);
        personneMoraleRepo.deleteById(id);
    }
}