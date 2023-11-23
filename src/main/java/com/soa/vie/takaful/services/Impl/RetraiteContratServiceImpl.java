package com.soa.vie.takaful.services.Impl;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.soa.vie.takaful.entitymodels.Avenant;
import com.soa.vie.takaful.entitymodels.Cotisation;
import com.soa.vie.takaful.entitymodels.Periodicite;
import com.soa.vie.takaful.entitymodels.Personne;
import com.soa.vie.takaful.entitymodels.PersonnePhysiqueHistorique;
import com.soa.vie.takaful.entitymodels.PointVente;
import com.soa.vie.takaful.entitymodels.Produit;
import com.soa.vie.takaful.entitymodels.RetraiteContrat;
import com.soa.vie.takaful.entitymodels.RetraiteContratHistorique;
import com.soa.vie.takaful.entitymodels.TypeAvenant;
import com.soa.vie.takaful.entitymodels.autoIncrementhelpers.CostumIdGeneratedValueContratEntity;
import com.soa.vie.takaful.repositories.IAvenantRepository;
import com.soa.vie.takaful.repositories.ICotisationRepository;
import com.soa.vie.takaful.repositories.IPeriodiciteRepository;
import com.soa.vie.takaful.repositories.IPersonneRepository;
import com.soa.vie.takaful.repositories.IPointVenteRepository;
import com.soa.vie.takaful.repositories.IProduitRepository;
import com.soa.vie.takaful.repositories.IRetraiteContratHistoriqueRepository;
import com.soa.vie.takaful.repositories.IRetraiteContratRepository;
import com.soa.vie.takaful.repositories.ITypeAvenantRepository;
import com.soa.vie.takaful.repositories.autoincrementhelpers.CostumIdGeneratedValueContratEntityRepository;
import com.soa.vie.takaful.requestmodels.CreateUpdateRetraiteContrat;
import com.soa.vie.takaful.requestmodels.UpdateRetraiteContratStatusModel;
import com.soa.vie.takaful.responsemodels.RetraiteContratResponseModel;
import com.soa.vie.takaful.services.IRetraiteContratService;
import com.soa.vie.takaful.util.Constants;
import com.soa.vie.takaful.util.ContratStatus;
import com.soa.vie.takaful.util.EtatCotisation;
import com.soa.vie.takaful.util.Pagination;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RetraiteContratServiceImpl implements IRetraiteContratService {

    private static final String ACCEPTED = "ACCEPTED";

    private static final String RESILIATION_EN_COURS = "EN_COURS_RESILIATION";

    @Autowired
    private IRetraiteContratRepository retraiteContratRepository;

    @Autowired
    private IProduitRepository produitRepository;

    @Autowired
    private IPersonneRepository personneRepository;

    @Autowired
    private IPointVenteRepository pointVenteRepository;

    @Autowired
    private IPeriodiciteRepository periodiciteRepository;

    @Autowired
    private ITypeAvenantRepository typeAvenantRepository;

    @Autowired
    private IAvenantRepository avenantRepository;

    @Autowired
    private IRetraiteContratHistoriqueRepository retraiteContratHistoriqueRepository;

    @Autowired
    private ICotisationRepository cotisationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CostumIdGeneratedValueContratEntityRepository costumIdGeneratedValueContratEntityRepository;

    @Override
    @Async("asyncExecutor")
    public RetraiteContratResponseModel createRetraitContrat(CreateUpdateRetraiteContrat model)
            throws InterruptedException, ExecutionException {
        log.info("Creation d'un contrat de retraite...");
        Thread.sleep(1000L);
        RetraiteContrat retraiteContrat = new RetraiteContrat();
        Optional<Produit> produit = this.produitRepository.findById(model.getProduit().getId());
        Optional<Personne> souscripteur = this.personneRepository.findById(model.getSouscripteur().getId());
        Optional<Personne> assure = this.personneRepository.findById(model.getAssure().getId());
        Optional<PointVente> pointVente = this.pointVenteRepository.findById(model.getPointVente().getId());
        Optional<Periodicite> periodicite = this.periodiciteRepository.findById(model.getPeriodicite().getId());
        String partenaireCode = pointVente.get().getPartenairepv().getCode();
        if (produit.isPresent() && souscripteur.isPresent() && assure.isPresent() && pointVente.isPresent()
                && periodicite.isPresent()) {

            SecureRandom secureRandom = new SecureRandom();

            BeanUtils.copyProperties(model, retraiteContrat);

            Long lastId = lastIdImplementer();

            int nombreAleatoire = 56 + (int) (secureRandom.nextFloat() * ((900 - 56) + 1));

            // Cotisaion Periodique
            Cotisation cotisationPeriodique = new Cotisation();
            cotisationPeriodique.setDatePrelevement(retraiteContrat.getDatePrelevement());
            cotisationPeriodique.setMontantCotisation(retraiteContrat.getMontantContributionPeriodique());
            cotisationPeriodique.setCotisationType("Epargne periodique");
            cotisationPeriodique.setSolde(retraiteContrat.getMontantContributionPeriodique());
            cotisationPeriodique.setContributionPure(retraiteContrat.getMontantContributionPeriodique());
            cotisationPeriodique.setEtatCotisation(EtatCotisation.EMIS);
            cotisationPeriodique.setNumQuittance(nombreAleatoire);
            cotisationRepository.save(cotisationPeriodique);

            // Cotisation Initiale
            if (model.getMontantContributionInitiale() != 0) {
                Cotisation cotisationInitiale = new Cotisation();
                cotisationInitiale.setDatePrelevement(retraiteContrat.getDatePrelevement());
                cotisationInitiale.setMontantCotisation(retraiteContrat.getMontantContributionInitiale());
                cotisationInitiale.setCotisationType("Epargne initiale");
                cotisationInitiale.setSolde(retraiteContrat.getMontantContributionInitiale());
                cotisationInitiale.setContributionPure(retraiteContrat.getMontantContributionInitiale());
                cotisationInitiale.setEtatCotisation(EtatCotisation.EMIS);
                cotisationInitiale.setNumQuittance(nombreAleatoire + 1);
                cotisationRepository.save(cotisationInitiale);
            }

            retraiteContrat.setNumeroDeSimulation(lastId + 1);
            retraiteContrat.setAssure(model.getAssure());
            retraiteContrat.setSouscripteur(model.getSouscripteur());
            retraiteContrat.setProduit(model.getProduit());
            retraiteContrat.setPeriodicite(model.getPeriodicite());
            retraiteContrat.setPointVente(model.getPointVente());

            CostumIdGeneratedValueContratEntity autoDataBaseGeneratedIdContrat = new CostumIdGeneratedValueContratEntity();
            if (Constants.CODE_PARTENAIRE_ALKHDAR.equals(partenaireCode)) {
                retraiteContrat.setNumeroContrat(Constants.PREFIX_NUM_CONTRACT_PARTENAIRE_AKHDAR
                        .concat(this.costumIdGeneratedValueContratEntityRepository
                                .save(autoDataBaseGeneratedIdContrat).getId()));
            } else if (Constants.CODE_PARTENAIRE_ALYOUSER.equals(partenaireCode)) {
                retraiteContrat.setNumeroContrat(Constants.PREFIX_NUM_CONTRACT_PARTENAIRE_ALYOUSR
                        .concat(this.costumIdGeneratedValueContratEntityRepository
                                .save(autoDataBaseGeneratedIdContrat).getId()));
            } else
                retraiteContrat.setNumeroContrat(this.costumIdGeneratedValueContratEntityRepository
                        .save(autoDataBaseGeneratedIdContrat).getId());
            return modelMapper.map(this.retraiteContratRepository.save(retraiteContrat),
                    RetraiteContratResponseModel.class);

        } else {
            throw new NoSuchElementException("worng data,please check the data you're sending");
        }

    }

    @Override
    @Async("asyncExecutor")
    public RetraiteContratResponseModel updateRetraitContrat(String id, CreateUpdateRetraiteContrat model)
            throws InterruptedException, ExecutionException {
        log.info("updating retrait contrat status : {}", id);
        Thread.sleep(1000L);
        Optional<RetraiteContrat> contratexists = retraiteContratRepository.findById(id);
        Optional<TypeAvenant> typeExists = typeAvenantRepository.findById(model.getTypeAvenantId());
        if (contratexists.isPresent()) {
            Date date = new Date();
            Integer numAvenant = avenantRepository.numeroAvenant(id);
            RetraiteContrat retraiteContrat = contratexists.get();
            if (numAvenant == null && typeExists.isPresent()) {
                Integer numero = 0;
                Avenant avenantUpdate = new Avenant();
                avenantUpdate.setDateEffet(date);
                avenantUpdate.setContrat(retraiteContrat);
                avenantUpdate.setNumeroAvenant(numero);
                avenantUpdate.setTypeAvenant(typeExists.get());
                this.avenantRepository.save(avenantUpdate);

                RetraiteContratHistorique retraiteContratHistorique = new RetraiteContratHistorique();
                retraiteContratHistorique.setAvenantId(avenantUpdate);
                retraiteContratHistorique.setNumeroContrat(retraiteContrat.getNumeroContrat());
                // retraiteContratHistorique.setAssure(retraiteContrat.getAssure());
                retraiteContratHistorique
                        .setAssure(modelMapper.map(retraiteContrat.getAssure(), PersonnePhysiqueHistorique.class));
                retraiteContratHistorique.setSouscripteur(retraiteContrat.getSouscripteur());
                retraiteContratHistorique.setProduit(retraiteContrat.getProduit());
                retraiteContratHistorique.setPeriodicite(retraiteContrat.getPeriodicite());
                retraiteContratHistorique.setPointVente(retraiteContrat.getPointVente());
                retraiteContratHistorique.setDateEcheance(retraiteContrat.getDateEcheance());
                retraiteContratHistorique.setDateEffet(retraiteContrat.getDateEffet());
                retraiteContratHistorique.setOptionEmission(retraiteContrat.getOptionEmission());
                retraiteContratHistorique.setStatus(retraiteContrat.getStatus());
                retraiteContratHistorique.setDureeContrat(retraiteContrat.getDureeContrat());
                retraiteContratHistorique.setDeductibiliteFiscale(retraiteContrat.isDeductibiliteFiscale());
                retraiteContratHistorique.setSouscripteurIsAssure(retraiteContrat.isSouscripteurIsAssure());
                retraiteContratHistorique.setAttributionIrrevocable(retraiteContrat.isAttributionIrrevocable());
                retraiteContratHistorique.setBenificiareCasDeces(retraiteContrat.getBenificiareCasDeces());
                retraiteContratHistorique
                        .setMontantContributionInitiale(retraiteContrat.getMontantContributionInitiale());
                retraiteContratHistorique
                        .setMontantContributionPeriodique(retraiteContrat.getMontantContributionPeriodique());
                retraiteContratHistorique.setBeneficiaireEnDeces(retraiteContrat.getBeneficiaireEnDeces());
                retraiteContratHistorique.setNombrePeriodicite(retraiteContrat.getNombrePeriodicite());
                retraiteContratHistoriqueRepository.save(retraiteContratHistorique);

                Avenant avenants = new Avenant();
                avenants.setDateEffet(date);
                avenants.setContrat(retraiteContrat);
                avenants.setNumeroAvenant(numero + 1);
                avenants.setTypeAvenant(typeExists.get());
                this.avenantRepository.save(avenants);

                RetraiteContratHistorique retraiteContratHistoriqueUpdate = new RetraiteContratHistorique();
                retraiteContratHistoriqueUpdate.setAvenantId(avenantUpdate);
                retraiteContratHistoriqueUpdate.setNumeroContrat(model.getNumeroContrat());
                // retraiteContratHistoriqueUpdate.setAssure(model.getAssure());
                retraiteContratHistoriqueUpdate
                        .setAssure(modelMapper.map(model.getAssure(), PersonnePhysiqueHistorique.class));
                retraiteContratHistoriqueUpdate.setSouscripteur(model.getSouscripteur());
                retraiteContratHistoriqueUpdate.setProduit(model.getProduit());
                retraiteContratHistoriqueUpdate.setPeriodicite(model.getPeriodicite());
                retraiteContratHistoriqueUpdate.setPointVente(model.getPointVente());
                retraiteContratHistoriqueUpdate.setDateEcheance(model.getDateEcheance());
                retraiteContratHistoriqueUpdate.setDateEffet(model.getDateEffet());
                retraiteContratHistoriqueUpdate.setOptionEmission(model.getOptionEmission());
                retraiteContratHistoriqueUpdate.setStatus(model.getStatus());
                retraiteContratHistoriqueUpdate.setDureeContrat(model.getDureeContrat());
                retraiteContratHistoriqueUpdate.setDeductibiliteFiscale(model.isDeductibiliteFiscale());
                retraiteContratHistoriqueUpdate.setSouscripteurIsAssure(model.isSouscripteurIsAssure());
                retraiteContratHistoriqueUpdate.setAttributionIrrevocable(model.isAttributionIrrevocable());
                retraiteContratHistoriqueUpdate.setBenificiareCasDeces(model.getBenificiareCasDeces());
                retraiteContratHistoriqueUpdate.setMontantContributionInitiale(model.getMontantContributionInitiale());
                retraiteContratHistoriqueUpdate
                        .setMontantContributionPeriodique(model.getMontantContributionPeriodique());
                retraiteContratHistoriqueUpdate.setBeneficiaireEnDeces(model.getBeneficiaireEnDeces());
                retraiteContratHistoriqueUpdate.setNombrePeriodicite(model.getNombrePeriodicite());
                retraiteContratHistoriqueRepository.save(retraiteContratHistoriqueUpdate);
            } else if (numAvenant != null && typeExists.isPresent()) {
                Avenant avenant = new Avenant();
                avenant.setDateEffet(date);
                avenant.setContrat(retraiteContrat);
                avenant.setNumeroAvenant(numAvenant + 1);
                avenant.setTypeAvenant(typeExists.get());
                this.avenantRepository.save(avenant);

                RetraiteContratHistorique retraiteContratHistorique = new RetraiteContratHistorique();
                retraiteContratHistorique.setAvenantId(avenant);
                retraiteContratHistorique.setNumeroContrat(retraiteContrat.getNumeroContrat());
                // retraiteContratHistorique.setAssure(retraiteContrat.getAssure());
                retraiteContratHistorique
                        .setAssure(modelMapper.map(retraiteContrat.getAssure(), PersonnePhysiqueHistorique.class));
                retraiteContratHistorique.setSouscripteur(retraiteContrat.getSouscripteur());
                retraiteContratHistorique.setProduit(retraiteContrat.getProduit());
                retraiteContratHistorique.setPeriodicite(retraiteContrat.getPeriodicite());
                retraiteContratHistorique.setPointVente(retraiteContrat.getPointVente());
                retraiteContratHistorique.setDateEcheance(retraiteContrat.getDateEcheance());
                retraiteContratHistorique.setDateEffet(retraiteContrat.getDateEffet());
                retraiteContratHistorique.setOptionEmission(retraiteContrat.getOptionEmission());
                retraiteContratHistorique.setStatus(retraiteContrat.getStatus());
                retraiteContratHistorique.setDureeContrat(retraiteContrat.getDureeContrat());
                retraiteContratHistorique.setDeductibiliteFiscale(retraiteContrat.isDeductibiliteFiscale());
                retraiteContratHistorique.setSouscripteurIsAssure(retraiteContrat.isSouscripteurIsAssure());
                retraiteContratHistorique.setAttributionIrrevocable(retraiteContrat.isAttributionIrrevocable());
                retraiteContratHistorique.setBenificiareCasDeces(retraiteContrat.getBenificiareCasDeces());
                retraiteContratHistorique
                        .setMontantContributionInitiale(retraiteContrat.getMontantContributionInitiale());
                retraiteContratHistorique
                        .setMontantContributionPeriodique(retraiteContrat.getMontantContributionPeriodique());
                retraiteContratHistorique.setBeneficiaireEnDeces(retraiteContrat.getBeneficiaireEnDeces());
                retraiteContratHistorique.setNombrePeriodicite(retraiteContrat.getNombrePeriodicite());
                retraiteContratHistoriqueRepository.save(retraiteContratHistorique);

            }
            BeanUtils.copyProperties(model, retraiteContrat);
            return modelMapper.map(this.retraiteContratRepository.save(retraiteContrat),
                    RetraiteContratResponseModel.class);

        } else {
            log.error("la modification1 de contrat est erroné avec l'ID({}): ", id);
            throw new NoSuchElementException("la modificationde contrat est erroné");
        }
    }

    @Override
    @Async("asyncExecutor")
    public Page<RetraiteContratResponseModel> getRetraitContrats(int page, int limit, String sort, String direction)
            throws InterruptedException, ExecutionException {
        log.info("lister les contrats retraits");
        Thread.sleep(1000L);
        Page<RetraiteContratResponseModel> retraiteContratResponseModels = retraiteContratRepository
                .findAll(Pagination.pageableRequest(page, limit, sort, direction))
                .map(o -> modelMapper.map(o, RetraiteContratResponseModel.class));
        return retraiteContratResponseModels;

    }

    @Override
    @Async("asyncExecutor")
    public RetraiteContratResponseModel getRetraitContratById(String id)
            throws InterruptedException, ExecutionException {
        Optional<RetraiteContrat> retraiteContrat = this.retraiteContratRepository.findById(id);
        if (retraiteContrat.isPresent()) {
            return modelMapper.map(retraiteContrat, RetraiteContratResponseModel.class);
        } else {
            throw new NoSuchElementException("Aucune retrait contrat acec ce ID");
        }

    }

    @Override
    @Async("asyncExecutor")
    public RetraiteContratResponseModel getRetraitContratByNumeroContrat(String numeroContrat)
            throws InterruptedException, ExecutionException {
        Thread.sleep(1000L);
        RetraiteContrat retraiteContrat = retraiteContratRepository.findByNumeroContrat(numeroContrat);
        if (retraiteContrat != null) {
            return modelMapper.map(retraiteContrat, RetraiteContratResponseModel.class);
        } else {
            throw new NoSuchElementException("Aucune retrait contrat acec ce numero de contrat");
        }
    }

    @Override
    public Page<RetraiteContratResponseModel> getRetraitContratsByStatus(ContratStatus status, int page, int limit)
            throws InterruptedException, ExecutionException {
        log.info("lister les contrats retrait acceptée");
        Thread.sleep(1000L);
        Pageable pageableRequest = PageRequest.of(page, limit);
        List<RetraiteContratResponseModel> list = retraiteContratRepository.findByStatus(status, pageableRequest)
                .stream().map(o -> modelMapper.map(o, RetraiteContratResponseModel.class)).collect(Collectors.toList());
        return new PageImpl<>(list);
    }

    @Override
    @Async("asyncExecutor")
    public Page<RetraiteContratResponseModel> searchContrat(int page, int limit, String searchBy, String searchFor)
            throws InterruptedException, ExecutionException {
        Pageable pageableRequest = PageRequest.of(page, limit);
        if ("nom".equals(searchBy)) {

            return retraiteContratRepository.findByAssureNom(pageableRequest, searchFor)
                    .map(o -> modelMapper.map(o, RetraiteContratResponseModel.class));
        }

        else if ("cin".equals(searchBy)) {

            return retraiteContratRepository.findByAssurecin(pageableRequest, searchFor)
                    .map(o -> modelMapper.map(o, RetraiteContratResponseModel.class));

        } else if ("numeroContrat".equals(searchBy)) {

            return retraiteContratRepository.findByNumeroContratStartingWith(pageableRequest, searchFor)
                    .map(o -> modelMapper.map(o, RetraiteContratResponseModel.class));

        }

        throw new IllegalArgumentException("la recherche doit etre efactuer par nom;prenom ou code");
    }

    @Override
    @Async("asyncExecutor")
    public RetraiteContratResponseModel updateRetraitContratStatus(String id, UpdateRetraiteContratStatusModel model)
            throws InterruptedException, ExecutionException {
        log.info("updating retrait contrat status : {}", id);
        Thread.sleep(1000L);
        Optional<RetraiteContrat> contratexists = retraiteContratRepository.findById(id);
        Optional<TypeAvenant> typeExists = typeAvenantRepository.findById(model.getTypeAvenantId());
        if (contratexists.isPresent()) {
            Integer numAvenant = avenantRepository.numeroAvenant(id);
            RetraiteContrat retraiteContrat = contratexists.get();
            BeanUtils.copyProperties(model, retraiteContrat);
            ajoutAvenant(numAvenant, typeExists, retraiteContrat, model);

            if (ACCEPTED.equals(model.getStatus()))
                retraiteContrat.setStatus(ContratStatus.ACCEPTED);
            else if (RESILIATION_EN_COURS.equals(model.getStatus()))
                retraiteContrat.setStatus(ContratStatus.EN_COURS_RESILIATION);
            return modelMapper.map(this.retraiteContratRepository.save(retraiteContrat),
                    RetraiteContratResponseModel.class);
        } else {
            log.error("la modification1 de contrat est erroné avec l'ID({}): ", id);
            throw new NoSuchElementException("la modification de contrat est erroné");
        }
    }

    private Long lastIdImplementer() {
        if (this.retraiteContratRepository.getLastId() == null) {
            return Long.valueOf(0);
        } else {
            return this.retraiteContratRepository.getLastId();
        }
    }

    protected void ajoutAvenant(Integer num, Optional<TypeAvenant> typeExists, RetraiteContrat retraiteContrat,
            UpdateRetraiteContratStatusModel model) {
        Date now = new Date();
        if (num == null) {
            // ajout avenant et contrat historique avant la modification du capital et duree

            if (typeExists.isPresent()) {
                Integer numero = 0;
                Avenant avenantCreate = new Avenant();
                avenantCreate.setDateEffet(now);
                avenantCreate.setContrat(retraiteContrat);
                avenantCreate.setNumeroAvenant(numero);
                avenantCreate.setTypeAvenant(typeExists.get());
                this.avenantRepository.save(avenantCreate);

                RetraiteContratHistorique contratHistCreate = new RetraiteContratHistorique();
                contratHistCreate.setAvenantId(avenantCreate);
                contratHistCreate.setNumeroContrat(retraiteContrat.getNumeroContrat());
                // contratHistCreate.setAssure(retraiteContrat.getAssure());
                contratHistCreate
                        .setAssure(modelMapper.map(retraiteContrat.getAssure(), PersonnePhysiqueHistorique.class));
                contratHistCreate.setSouscripteur(retraiteContrat.getSouscripteur());
                contratHistCreate.setProduit(retraiteContrat.getProduit());
                contratHistCreate.setPeriodicite(retraiteContrat.getPeriodicite());
                contratHistCreate.setPointVente(retraiteContrat.getPointVente());
                contratHistCreate.setDateEcheance(retraiteContrat.getDateEcheance());
                contratHistCreate.setDateEffet(retraiteContrat.getDateEffet());
                contratHistCreate.setOptionEmission(retraiteContrat.getOptionEmission());
                contratHistCreate.setStatus(retraiteContrat.getStatus());
                contratHistCreate.setDureeContrat(retraiteContrat.getDureeContrat());
                contratHistCreate.setDeductibiliteFiscale(retraiteContrat.isDeductibiliteFiscale());
                contratHistCreate.setSouscripteurIsAssure(retraiteContrat.isSouscripteurIsAssure());
                contratHistCreate.setAttributionIrrevocable(retraiteContrat.isAttributionIrrevocable());
                contratHistCreate.setBenificiareCasDeces(retraiteContrat.getBenificiareCasDeces());
                contratHistCreate.setMontantContributionInitiale(retraiteContrat.getMontantContributionInitiale());
                contratHistCreate.setMontantContributionPeriodique(retraiteContrat.getMontantContributionPeriodique());
                contratHistCreate.setBeneficiaireEnDeces(retraiteContrat.getBeneficiaireEnDeces());
                contratHistCreate.setNombrePeriodicite(retraiteContrat.getNombrePeriodicite());
                retraiteContratHistoriqueRepository.save(contratHistCreate);

                // ajout avenant et contrat historique apres la modification du capital et duree
                Avenant avenantsCreate = new Avenant();
                avenantsCreate.setDateEffet(now);
                avenantsCreate.setContrat(retraiteContrat);
                avenantsCreate.setNumeroAvenant(numero + 1);
                avenantsCreate.setTypeAvenant(typeExists.get());
                this.avenantRepository.save(avenantsCreate);

                RetraiteContratHistorique contratHistorCreate = new RetraiteContratHistorique();
                contratHistorCreate.setAvenantId(avenantsCreate);
                contratHistorCreate.setNumeroContrat(retraiteContrat.getNumeroContrat());
                // contratHistorCreate.setAssure(retraiteContrat.getAssure());
                contratHistorCreate
                        .setAssure(modelMapper.map(retraiteContrat.getAssure(), PersonnePhysiqueHistorique.class));
                contratHistorCreate.setSouscripteur(retraiteContrat.getSouscripteur());
                contratHistorCreate.setProduit(retraiteContrat.getProduit());
                contratHistorCreate.setPeriodicite(retraiteContrat.getPeriodicite());
                contratHistorCreate.setPointVente(retraiteContrat.getPointVente());
                contratHistorCreate.setDateEcheance(retraiteContrat.getDateEcheance());
                contratHistorCreate.setDateEffet(retraiteContrat.getDateEffet());
                contratHistorCreate.setOptionEmission(retraiteContrat.getOptionEmission());
                if (ACCEPTED.equals(model.getStatus()))
                    contratHistorCreate.setStatus(ContratStatus.ACCEPTED);
                else if (RESILIATION_EN_COURS.equals(model.getStatus()))
                    contratHistorCreate.setStatus(ContratStatus.EN_COURS_RESILIATION);
                contratHistCreate.setDureeContrat(retraiteContrat.getDureeContrat());
                contratHistCreate.setDeductibiliteFiscale(retraiteContrat.isDeductibiliteFiscale());
                contratHistCreate.setSouscripteurIsAssure(retraiteContrat.isSouscripteurIsAssure());
                contratHistCreate.setAttributionIrrevocable(retraiteContrat.isAttributionIrrevocable());
                contratHistCreate.setBenificiareCasDeces(retraiteContrat.getBenificiareCasDeces());
                contratHistCreate.setMontantContributionInitiale(retraiteContrat.getMontantContributionInitiale());
                contratHistCreate.setMontantContributionPeriodique(retraiteContrat.getMontantContributionPeriodique());
                contratHistCreate.setBeneficiaireEnDeces(retraiteContrat.getBeneficiaireEnDeces());
                contratHistCreate.setNombrePeriodicite(retraiteContrat.getNombrePeriodicite());

                retraiteContratHistoriqueRepository.save(contratHistorCreate);
            }

        } else {
            if (typeExists.isPresent()) {
                // ajout avenant et contrat historique avant la modification du capital et duree
                Avenant avenantC = new Avenant();
                avenantC.setDateEffet(now);
                avenantC.setContrat(retraiteContrat);
                avenantC.setNumeroAvenant(num + 1);
                avenantC.setTypeAvenant(typeExists.get());
                this.avenantRepository.save(avenantC);

                RetraiteContratHistorique contratHistC = new RetraiteContratHistorique();
                contratHistC.setAvenantId(avenantC);
                contratHistC.setNumeroContrat(retraiteContrat.getNumeroContrat());
                // contratHistC.setAssure(retraiteContrat.getAssure());
                contratHistC.setAssure(modelMapper.map(retraiteContrat.getAssure(), PersonnePhysiqueHistorique.class));
                contratHistC.setSouscripteur(retraiteContrat.getSouscripteur());
                contratHistC.setProduit(retraiteContrat.getProduit());
                contratHistC.setPeriodicite(retraiteContrat.getPeriodicite());
                contratHistC.setPointVente(retraiteContrat.getPointVente());
                contratHistC.setDateEcheance(retraiteContrat.getDateEcheance());
                contratHistC.setDateEffet(retraiteContrat.getDateEffet());
                contratHistC.setOptionEmission(retraiteContrat.getOptionEmission());
                if (ACCEPTED.equals(model.getStatus()))
                    contratHistC.setStatus(ContratStatus.ACCEPTED);
                else if (RESILIATION_EN_COURS.equals(model.getStatus()))
                    contratHistC.setStatus(ContratStatus.EN_COURS_RESILIATION);
                contratHistC.setDureeContrat(retraiteContrat.getDureeContrat());
                contratHistC.setDeductibiliteFiscale(retraiteContrat.isDeductibiliteFiscale());
                contratHistC.setSouscripteurIsAssure(retraiteContrat.isSouscripteurIsAssure());
                contratHistC.setAttributionIrrevocable(retraiteContrat.isAttributionIrrevocable());
                contratHistC.setBenificiareCasDeces(retraiteContrat.getBenificiareCasDeces());
                contratHistC.setMontantContributionInitiale(retraiteContrat.getMontantContributionInitiale());
                contratHistC.setMontantContributionPeriodique(retraiteContrat.getMontantContributionPeriodique());
                contratHistC.setBeneficiaireEnDeces(retraiteContrat.getBeneficiaireEnDeces());
                contratHistC.setNombrePeriodicite(retraiteContrat.getNombrePeriodicite());
                retraiteContratHistoriqueRepository.save(contratHistC);
            }
        }

    }
}
