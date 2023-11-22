package com.soa.vie.takaful.servicesImplementation;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.transaction.Transactional;

import com.soa.vie.takaful.entitymodels.SecteurActivite;
import com.soa.vie.takaful.repositories.ISecteurActiviteRepository;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateSecteurActivite;
import com.soa.vie.takaful.responsemodels.SecteurActiviteModelResponse;
import com.soa.vie.takaful.services.ISecteurActiviteService;
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
public class SecteurActiviteServiceImpl implements ISecteurActiviteService {

    @Autowired
    private ISecteurActiviteRepository secteurRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Async("asyncExecutor")
    public SecteurActiviteModelResponse createSecteurActivite(CreateAndUpdateSecteurActivite secteur)
            throws InterruptedException, ExecutionException {

        Thread.sleep(1000L);
        SecteurActivite addsecteur = new SecteurActivite();
        String libelle = secteur.getLibelle();
        Optional<SecteurActivite> secteurexists = secteurRepository.findByLibelle(libelle);

        if (secteurexists.isPresent()) {
            log.info("partenaire already exist");
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "ce partenaire existe déja");
        } else {
            BeanUtils.copyProperties(secteur, addsecteur);
            addsecteur = this.secteurRepository.save(addsecteur);
        }
        return modelMapper.map(addsecteur, SecteurActiviteModelResponse.class);
    }

    @Override
    @Async("asyncExecutor")
    public Page<SecteurActiviteModelResponse> getsecteurActivites(int page, int limit, String sort, String direction)
            throws InterruptedException, ExecutionException {

        Thread.sleep(1000L);
        return secteurRepository.findAll(Pagination.pageableRequest(page, limit, sort, direction))
                .map(o -> modelMapper.map(o, SecteurActiviteModelResponse.class));

    }

    @Override
    @Async("asyncExecutor")
    public SecteurActiviteModelResponse getsecteurActiviteById(String id)
            throws InterruptedException, ExecutionException {

        Thread.sleep(1000L);
        java.util.Optional<SecteurActivite> secteurEntity = secteurRepository.findById(id);
        if (secteurEntity.isPresent()) {
            return modelMapper.map(secteurEntity.get(), SecteurActiviteModelResponse.class);
        } else {
            log.info("ce ID n'existe pas : " + id);
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "il y'a aucun service avec ce id");
        }
    }

    @Override
    @Async("asyncExecutor")
    public SecteurActiviteModelResponse getsecteurActiviteByLibelle(String libelle)
            throws InterruptedException, ExecutionException {

        Thread.sleep(1000L);
        java.util.Optional<SecteurActivite> secteurEntity = secteurRepository.findByLibelle(libelle);
        if (secteurEntity.isPresent()) {
            return modelMapper.map(secteurEntity.get(), SecteurActiviteModelResponse.class);
        } else {

            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "il y'a aucun service avec cette libelle");
        }
    }

    @Override
    @Async("asyncExecutor")
    public SecteurActiviteModelResponse updateSecteurActivite(String secteurId,
            CreateAndUpdateSecteurActivite secteurModel) throws InterruptedException, ExecutionException {

        Thread.sleep(1000L);
        java.util.Optional<SecteurActivite> secteurEntity = secteurRepository.findById(secteurId);
        if (secteurEntity.isPresent()) {
            SecteurActivite secteur = secteurEntity.get();
            secteur.setLibelle(secteurModel.getLibelle());
            secteur.setDescription(secteurModel.getDescription());
            return modelMapper.map(secteurRepository.save(secteur), SecteurActiviteModelResponse.class);
        } else {
            log.info("no secteur with the id : " + secteurId);
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "ce ID n'existe pas");
        }
    }

}
