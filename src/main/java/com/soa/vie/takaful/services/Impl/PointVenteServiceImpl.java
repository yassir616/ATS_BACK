package com.soa.vie.takaful.services.Impl;


import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.transaction.Transactional;

import com.soa.vie.takaful.entitymodels.CompteBancaire;
import com.soa.vie.takaful.entitymodels.Partenaire;
import com.soa.vie.takaful.entitymodels.PointVente;
import com.soa.vie.takaful.entitymodels.SecteurActivite;
import com.soa.vie.takaful.entitymodels.TypePointVente;
import com.soa.vie.takaful.repositories.ICompteBancaire;
import com.soa.vie.takaful.repositories.IPartenaireRepository;
import com.soa.vie.takaful.repositories.IPointVenteRepository;
import com.soa.vie.takaful.repositories.ISecteurActiviteRepository;
import com.soa.vie.takaful.repositories.ITypePointVenteRepository;
import com.soa.vie.takaful.requestmodels.CreateAndUpdatePointVente;
import com.soa.vie.takaful.responsemodels.PointVenteResponseModel;
import com.soa.vie.takaful.services.IPointVenteService;
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
public class PointVenteServiceImpl implements IPointVenteService {

    @Autowired
    private IPartenaireRepository partenairerepository;

    @Autowired
    private IPointVenteRepository pointVenteRepository;

    @Autowired
    private ITypePointVenteRepository typePointVenterepository;

    @Autowired
    private ISecteurActiviteRepository secteurActiviterepository;

    @Autowired
    private ICompteBancaire compteBancaireRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Async("asyncExecutor")
    public PointVenteResponseModel createPointVente(CreateAndUpdatePointVente model)
            throws InterruptedException, ExecutionException {
        log.info("creation de point vente ...");
        Thread.sleep(1000L);
        PointVente pointVente = new PointVente();
        PointVente PointV = null;

        if (!"".equals(model.getPartenairepvId()) && !"".equals(model.getSecteurActiviteId())
                && !"".equals(model.getTypePointVenteId())) {

            Optional<Partenaire> partenaire = partenairerepository.findById(model.getPartenairepvId());
            Optional<TypePointVente> typePointVente = typePointVenterepository.findById(model.getTypePointVenteId());
            Optional<SecteurActivite> secteur = secteurActiviterepository.findById(model.getSecteurActiviteId());
            if (partenaire.isPresent() && typePointVente.isPresent() && secteur.isPresent()) {

                pointVente.setPartenairepv(partenaire.get());
                pointVente.setSecteurActivite(secteur.get());
                pointVente.setTypePointVente(typePointVente.get());
                BeanUtils.copyProperties(model, pointVente);
                PointV = this.pointVenteRepository.save(pointVente);

                CompteBancaire compteBancaire = new CompteBancaire();
                compteBancaire.setCode(model.getCode());
                compteBancaire.setRib(model.getRib());
                compteBancaire.setPointVente(PointV);
                this.compteBancaireRepository.save(compteBancaire);

                PointVenteResponseModel Cmodel = modelMapper.map(PointV, PointVenteResponseModel.class);
                return Cmodel;



                //return modelMapper.map(pointVenteRepository.save(pointVente), PointVenteResponseModel.class);

            } else {
                log.error("l'insertion  de point de vente est erroné  ");
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "l'insertion  de point de vente est erroné");
            }
        } else {
            log.error("l'insertion de point de vente est erroné avec l'ID({}): Merci d'insérer les champs obligatoire");
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND,
                    "la creation de point de vente est erroné, Merci d'insérer les champs obligatoire");
        }

    }

    @Override
    @Async("asyncExecutor")
    public PointVenteResponseModel updatePointVente(CreateAndUpdatePointVente model, String pointVenteId)
            throws InterruptedException, ExecutionException {
        log.info("modifier le point de vente avec l'Id : {}", pointVenteId);
        Thread.sleep(1000L);
        if (!"".equals(model.getPartenairepvId()) && !"".equals(model.getSecteurActiviteId())
                && !"".equals(model.getTypePointVenteId())) {

            Optional<PointVente> pointVenteOpt = pointVenteRepository.findById(pointVenteId);
            Optional<CompteBancaire> compteBancaireOpt = compteBancaireRepository.findByPointVenteId(pointVenteId);
            Optional<Partenaire> partenaire = partenairerepository.findById(model.getPartenairepvId());
            Optional<TypePointVente> typePointVente = typePointVenterepository.findById(model.getTypePointVenteId());
            Optional<SecteurActivite> secteur = secteurActiviterepository.findById(model.getSecteurActiviteId());

            if (pointVenteOpt.isPresent() && partenaire.isPresent() && typePointVente.isPresent()
                    && secteur.isPresent()) {
                PointVente pointVente = pointVenteOpt.get();
                pointVente.setPartenairepv(partenaire.get());
                pointVente.setSecteurActivite(secteur.get());
                pointVente.setTypePointVente(typePointVente.get());
                BeanUtils.copyProperties(model, pointVente);

                CompteBancaire compteBancaire = compteBancaireOpt.get();

                compteBancaire.setCode(model.getCode());
                compteBancaire.setRib(model.getRib());
                this.compteBancaireRepository.save(compteBancaire);

                PointVenteResponseModel Cmodel = modelMapper.map(pointVente, PointVenteResponseModel.class);
                return Cmodel;

                //return modelMapper.map(pointVenteRepository.save(pointVente), PointVenteResponseModel.class);

            } else {
                log.error("la modification1 de point de vente est erroné avec l'ID({}): ", pointVenteId);
                throw new NullPointerException("la modification1 de point de vente est erroné");
            }
        } else {
            log.error(
                    "la modification 3 de point de vente est erroné avec l'ID({}): Merci d'insérer les champs obligatoire",
                    pointVenteId);
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND,
                    "la creation de point de vente est erroné, Merci d'insérer les champs obligatoire");
        }
    }

    @Override
    @Async("asyncExecutor")
    public PointVenteResponseModel getPointVenteById(String pointVenteId)
            throws InterruptedException, ExecutionException {
        Optional<PointVente> pointVenteOptional = pointVenteRepository.findById(pointVenteId);
        Thread.sleep(1000L);
        if (pointVenteOptional.isPresent()) {

            return modelMapper.map(pointVenteOptional.get(), PointVenteResponseModel.class);
        } else {
            log.info("ce ID n'existe pas : " + pointVenteId);
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "il y'a aucune point vente avec ce id");
        }
    }

    @Override
    @Async("asyncExecutor")
    public Page<PointVenteResponseModel> getPointVentes(int page, int limit, String sort, String direction)
            throws InterruptedException, ExecutionException {
        log.info("lister les points de vente");
        Thread.sleep(1000L);
        return pointVenteRepository.findAll(Pagination.pageableRequest(page, limit, sort, direction))
                .map(o -> modelMapper.map(o, PointVenteResponseModel.class));

    }

    @Override
    @Async("asyncExecutor")
    public PointVenteResponseModel getPointVenteByCodeInterne(String code)
            throws InterruptedException, ExecutionException {
        Optional<PointVente> pointVenteOptional = pointVenteRepository.findByCodeInterne(code);
        Thread.sleep(1000L);
        if (pointVenteOptional.isPresent()) {

            return modelMapper.map(pointVenteOptional.get(), PointVenteResponseModel.class);
        } else {
            log.info("ce ID n'existe pas : " + code);
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "il y'a aucune point vente avec ce code");
        }
    }

}
