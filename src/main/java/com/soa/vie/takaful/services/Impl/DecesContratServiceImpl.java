package com.soa.vie.takaful.services.Impl;


import java.security.SecureRandom;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import com.ibm.icu.text.SimpleDateFormat;
import com.soa.vie.takaful.entitymodels.Acceptation;
import com.soa.vie.takaful.entitymodels.Avenant;
import com.soa.vie.takaful.entitymodels.Cotisation;
import com.soa.vie.takaful.entitymodels.DecesContrat;
import com.soa.vie.takaful.entitymodels.DecesContratHistorique;
import com.soa.vie.takaful.entitymodels.DecesProduit;
import com.soa.vie.takaful.entitymodels.OptionAssurance;
import com.soa.vie.takaful.entitymodels.Personne;
import com.soa.vie.takaful.entitymodels.PersonnePhysiqueHistorique;
import com.soa.vie.takaful.entitymodels.PointVente;
import com.soa.vie.takaful.entitymodels.TypeAvenant;
import com.soa.vie.takaful.entitymodels.autoIncrementhelpers.CostumIdGeneratedValueAcceptationEntity;
import com.soa.vie.takaful.entitymodels.autoIncrementhelpers.CostumIdGeneratedValueContratEntity;
import com.soa.vie.takaful.repositories.IAcceptationRepository;
import com.soa.vie.takaful.repositories.IAvenantRepository;
import com.soa.vie.takaful.repositories.ICotisationRepository;
import com.soa.vie.takaful.repositories.IDecesContratHistoriqueRepository;
import com.soa.vie.takaful.repositories.IDecesContratRepository;
import com.soa.vie.takaful.repositories.IDecesProduitRepository;
import com.soa.vie.takaful.repositories.IOptionAssuranceRepository;
import com.soa.vie.takaful.repositories.IPersonneRepository;
import com.soa.vie.takaful.repositories.IPointVenteRepository;
import com.soa.vie.takaful.repositories.ITypeAvenantRepository;
import com.soa.vie.takaful.repositories.autoincrementhelpers.CostumIdGeneratedValueAcceptationEntityRepository;
import com.soa.vie.takaful.repositories.autoincrementhelpers.CostumIdGeneratedValueContratEntityRepository;
import com.soa.vie.takaful.requestmodels.CreateUpdateDecesContrat;
import com.soa.vie.takaful.requestmodels.UpdateDecesContrat;
import com.soa.vie.takaful.requestmodels.UpdateDecesContratStatusModel;
import com.soa.vie.takaful.requestmodels.updateDecesContratDateEffetModel;
import com.soa.vie.takaful.responsemodels.ContractResponseModel;
import com.soa.vie.takaful.responsemodels.DecesContratResponseModel;
import com.soa.vie.takaful.responsemodels.updateDecesContratDateEffetResponse;
import com.soa.vie.takaful.services.IDecesContratService;
import com.soa.vie.takaful.util.AcceptationStatus;
import com.soa.vie.takaful.util.Constants;
import com.soa.vie.takaful.util.ContratStatus;
import com.soa.vie.takaful.util.EtatCotisation;
import com.soa.vie.takaful.util.Pagination;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DecesContratServiceImpl implements IDecesContratService {

    private static final String INSTANTANEE = "INSTANTANEE";
    private static final String RESILIATION_EN_COURS = "EN_COURS_RESILIATION";

    @Autowired
    private IPersonneRepository personneRepository;

    @Autowired
    private IDecesProduitRepository decesProduitRepository;

    @Autowired
    private IPointVenteRepository pointVenteRepository;
    @Autowired
    private IOptionAssuranceRepository optionAssuranceRepository;

    @Autowired
    private IDecesContratRepository decesContratRepository;

    @Autowired
    private IDecesContratHistoriqueRepository decesContratHistoriqueRepository;

    @Autowired
    private IAvenantRepository avenantRepository;

    @Autowired
    private ITypeAvenantRepository typeAvenantRepository;

    @Autowired
    private AvenantServiceImpl avenantServiceImpl;

    @Autowired
    private DecesContratHistoriqueServiceImpl contratHistoriqueServiceImpl;

    @Autowired
    private IAcceptationRepository acceptationRepository;


    @Autowired
    private ICotisationRepository cotisationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CostumIdGeneratedValueAcceptationEntityRepository costumIdGeneratedValueAcceptationEntityRepository;

    @Autowired
    private CostumIdGeneratedValueContratEntityRepository costumIdGeneratedValueContratEntityRepository;

    @Override
    @Async("asyncExecutor")
    public DecesContratResponseModel createDecesContrat(CreateUpdateDecesContrat model)
            throws InterruptedException, ExecutionException {

        log.info("creation deces contrat ...");

        // // controle sur les contrats si le souscripteur est déjà inscrit (moins de 6
        // mois)
        // // debut
        // Object Souscripteur;
        // boolean exist = false;
        // float encours = 0;
        // float cumul = 0;

        // if(this.decesContratRepository.findBySouscripteur(model.getSouscripteur().getId(),6)
        // != null ){
        // Souscripteur =
        // this.decesContratRepository.findBySouscripteur(model.getSouscripteur().getId(),6);
        // exist = Boolean.valueOf(Array.get(Souscripteur, 0).toString());
        // encours = Float.valueOf(Array.get(Souscripteur, 1).toString());
        // cumul = encours + model.getCapitalAssure();
        // }
        // // fin

        Thread.sleep(1000L);
        DecesContrat decesCorntrat = new DecesContrat();
        Optional<Personne> souscripteur = this.personneRepository.findById(model.getSouscripteur().getId());
        Optional<Personne> assure = this.personneRepository.findById(model.getAssure().getId());
        Optional<DecesProduit> produit = this.decesProduitRepository.findById(model.getIdProd());
        Optional<PointVente> pointVente = this.pointVenteRepository.findById(model.getIdPointVente());
        String partenaireCode = pointVente.get().getPartenairepv().getCode();

        if (souscripteur.isPresent() && assure.isPresent() && produit.isPresent()) {

            SecureRandom random = new SecureRandom();

            BeanUtils.copyProperties(model, decesCorntrat);

            Long lastId = lastIdImplementer();

            int nombreAleatoire = 56 + (int) (random.nextFloat() * ((900 - 56) + 1));
            System.out.println("nombreeeeeee     " + nombreAleatoire);

            decesCorntrat.setNumeroDeSimulation(lastId + 1);
            String idProduit = model.getIdProd();
            String idContrat = decesCorntrat.getId();
            String idPointVente = model.getIdPointVente();
            String idPeriodicite = model.getIdPeriodicite();

            modelChecker(model, decesCorntrat);

            Acceptation acceptation = new Acceptation();

            acceptation.setEncours(model.getEncours());
            acceptation.setCumul(model.getCumul());
            acceptation.setContrat(decesCorntrat);

            // creating new autoDataBaseGeneratedId to save the generated Id as the
            // acceptation code
            CostumIdGeneratedValueAcceptationEntity autoDataBaseGeneratedId = new CostumIdGeneratedValueAcceptationEntity();

            acceptation.setCode(
                    this.costumIdGeneratedValueAcceptationEntityRepository.save(autoDataBaseGeneratedId).getId());

            // creating new autoDataBaseGeneratedId to save the generated Id as the
            // acceptation code
            CostumIdGeneratedValueContratEntity autoDataBaseGeneratedIdContrat = new CostumIdGeneratedValueContratEntity();
            if (Constants.CODE_PARTENAIRE_ALKHDAR.equals(partenaireCode)) {
                decesCorntrat.setNumeroContrat(Constants.PREFIX_NUM_CONTRACT_PARTENAIRE_AKHDAR
                        .concat(this.costumIdGeneratedValueContratEntityRepository
                                .save(autoDataBaseGeneratedIdContrat).getId()));
            } else if (Constants.CODE_PARTENAIRE_ALYOUSER.equals(partenaireCode)) {
                decesCorntrat.setNumeroContrat(Constants.PREFIX_NUM_CONTRACT_PARTENAIRE_ALYOUSR
                        .concat(this.costumIdGeneratedValueContratEntityRepository
                                .save(autoDataBaseGeneratedIdContrat).getId()));
            } else
                decesCorntrat.setNumeroContrat(this.costumIdGeneratedValueContratEntityRepository
                        .save(autoDataBaseGeneratedIdContrat).getId());

            DecesContrat contrat = null;

            Integer AgeVisite = produit.get().getAgeVisite();
            AgeVisite = AgeVisite == null ? 0 : AgeVisite;

            log.info("produit.get().getAgeVisite()     " + AgeVisite);

            if ((model.getSeuilConseil() >= model.getCumul()
                    && model.getSeuilExaminateur() >= model.getCumul()
                    && model.getSeuilReassurance() >= model.getCumul()
                    && !ContratStatus.SIMULATION.equals(decesCorntrat.getStatus()) && !model.isInvaliditeOuMaladie()
                    && !model.isPensionIncapacite() && !model.isSuspendreAtiviteDeuxDernierAnnee()
                    && !model.isMaladiesOuOperationChirurgicale() && model.getAge() < AgeVisite)) {

                System.out.println(produit.get().getAgeVisite());

                decesCorntrat.setStatus(ContratStatus.INSTANTANEE);
                acceptation.setStatus(AcceptationStatus.DONE);
                contrat = this.decesContratRepository.save(decesCorntrat);
                this.decesContratRepository.updateContratProduit(idProduit, idPointVente, idPeriodicite, idContrat);
                decesCorntrat.setAcceptation(this.acceptationRepository.save(acceptation));

            } else if (model.getSeuilConseil() < model.getCumul()
                    || model.getSeuilExaminateur() < model.getCumul()
                    || model.getSeuilReassurance() < model.getCumul() || model.getAge() >= AgeVisite
                    || model.isInvaliditeOuMaladie() || model.isPensionIncapacite()
                    || model.isSuspendreAtiviteDeuxDernierAnnee() || model.isMaladiesOuOperationChirurgicale()) {

                System.out.println(produit.get().getAgeVisite());
                if (!ContratStatus.SIMULATION.equals(decesCorntrat.getStatus())) {
                    decesCorntrat.setStatus(ContratStatus.WAITING_ACCEPTATION);
                    acceptation.setStatus(AcceptationStatus.IN_PROGRESS);
                    contrat = this.decesContratRepository.save(decesCorntrat);
                    this.decesContratRepository.updateContratProduit(idProduit, idPointVente, idPeriodicite, idContrat);
                    decesCorntrat.setAcceptation(this.acceptationRepository.save(acceptation));

                }
            }
            // String id_contrat_result =
            // this.decesContratRepository.findBySouscripteur(model.getSouscripteur().getId(),model.getCumul(),model.getCumul());
            // if (id_contrat_result == null) {
            // //id_contrat_result = 'test';
            // log.info("le résultat est nul");

            // } else {
            // log.info("le résultat est non nul");
            // log.info("l'id du contrat"+ id_contrat_result);
            // Optional<Acceptation> acceptationOptional =
            // this.acceptationRepository.findByContratId(id_contrat_result);
            // if (!acceptationOptional.isPresent()) {
            // throw new NoSuchElementException("l'acceptation n'existe pas ");
            // } else {
            // String code_acceptation =
            // this.acceptationRepository.findCodeByContratId(id_contrat_result);
            // System.out.println(code_acceptation);
            // List<String> accepted_examens=
            // this.acceptationRepository.findByCode(code_acceptation);
            // System.out.println("accepted examens"+accepted_examens);
            // //log.info("accepted examens"+accepted_examens.toString());
            // if(accepted_examens == null || accepted_examens.isEmpty()){
            // decesCorntrat.setStatus(ContratStatus.WAITING_ACCEPTATION);
            // acceptation.setStatus(AcceptationStatus.IN_PROGRESS);
            // contrat = this.decesContratRepository.save(decesCorntrat);
            // this.decesContratRepository.updateContratProduit(idProduit, idPointVente,
            // idPeriodicite, idContrat);
            // decesCorntrat.setAcceptation(this.acceptationRepository.save(acceptation));
            // }

            // else {
            // List<String> current_examens = model.getHonoraires();
            // log.info("current examens"+current_examens);
            // log.info("accepted examens"+accepted_examens.toString());
            // if(accepted_examens.containsAll(current_examens)){
            // decesCorntrat.setStatus(ContratStatus.INSTANTANEE);
            // acceptation.setStatus(AcceptationStatus.DONE);
            // contrat = this.decesContratRepository.save(decesCorntrat);
            // this.decesContratRepository.updateContratProduit(idProduit, idPointVente,
            // idPeriodicite, idContrat);
            // decesCorntrat.setAcceptation(this.acceptationRepository.save(acceptation));
            // }
            // else{
            // List<String> new_examens = new ArrayList<>(current_examens);
            // new_examens.removeAll(accepted_examens);
            // System.out.println("Nouveux examens: " + new_examens);
            // decesCorntrat.setStatus(ContratStatus.WAITING_ACCEPTATION);
            // decesCorntrat.setHonoraires(new_examens);
            // acceptation.setStatus(AcceptationStatus.IN_PROGRESS);
            // contrat = this.decesContratRepository.save(decesCorntrat);
            // this.decesContratRepository.updateContratProduit(idProduit, idPointVente,
            // idPeriodicite, idContrat);
            // decesCorntrat.setAcceptation(this.acceptationRepository.save(acceptation));
            // }
            // }

            // }

            // }
            // }

            if (ContratStatus.SIMULATION.equals(decesCorntrat.getStatus())) {

                acceptation.setStatus(AcceptationStatus.SIMULATION);
                contrat = this.decesContratRepository.save(decesCorntrat);
                this.decesContratRepository.updateContratProduit(idProduit, idPointVente, idPeriodicite, idContrat);
                decesCorntrat.setAcceptation(this.acceptationRepository.save(acceptation));
            }
            // float montantTaxeParafiscale =
            // Float.parseFloat(model.getMontantTaxeParafiscale());
            // System.out.println(montantTaxeParafiscale);
            Cotisation cotisation = new Cotisation();
            cotisation.setDatePrelevement(decesCorntrat.getDatePrelevement());
            cotisation.setMontantCotisation(decesCorntrat.getMontantCotisation());
            cotisation.setMontantTaxe(decesCorntrat.getMontantTaxe());
            cotisation.setMontantTTC(
                    decesCorntrat.getMontantCotisation() + decesCorntrat.getMontantTaxe()
                            + model.getMontantTaxeParafiscale());
            cotisation.setSolde(
                    decesCorntrat.getMontantCotisation() + decesCorntrat.getMontantTaxe()
                            + model.getMontantTaxeParafiscale());
            cotisation.setEtatCotisation(EtatCotisation.EMIS);
            cotisation.setNumQuittance(nombreAleatoire);
            cotisation.setContrat(contrat);
            cotisation.setContributionPure(decesCorntrat.getContributionPure());
            cotisation.setFraisAcquisitionTTC(decesCorntrat.getFraisAcquisitionTTC());
            cotisation.setFraisGestionTTC(decesCorntrat.getFraisGestionTTC());
            cotisation.setMontantTaxeParaFiscale(model.getMontantTaxeParafiscale());
            cotisation.setMontantAccessoire(model.getMontantAccessoire());
            cotisation.setCapitalAssure(model.getCapitalAssure());
            this.cotisationRepository.save(cotisation);
            DecesContratResponseModel Cmodel = modelMapper.map(contrat, DecesContratResponseModel.class);
            Cmodel.setNumQuittance(nombreAleatoire);
            return Cmodel;
        } else {
            throw new NoSuchElementException("worng data,please check the data you're sending");
        }
    }

    @Override
    @Async("asyncExecutor")
    public DecesContratResponseModel updateDecesContrat(String id, UpdateDecesContrat model)
            throws InterruptedException, ExecutionException {
        log.info("updating deces contrat : {}", id);

        Thread.sleep(1000L);
        Optional<DecesContrat> contratexists = decesContratRepository.findById(id);
        Optional<Acceptation> acceptationexists = this.acceptationRepository.findByContratId(id);
        Optional<TypeAvenant> typeExists = typeAvenantRepository.findById(model.getTypeAvenantId());

        if (contratexists.isPresent() && acceptationexists.isPresent()) {

            Integer num = avenantRepository.numeroAvenant(id);
            DecesContrat decesContrat = contratexists.get();
            Acceptation acceptation = acceptationexists.get();

            if (num == null) {
                if (typeExists.isPresent()) {

                    Integer numero = 0;
                    Avenant avenant0 = this.avenantServiceImpl.createAvenant(decesContrat, numero, typeExists.get());
                    this.contratHistoriqueServiceImpl.createDecesContratHistorique(decesContrat, model, avenant0);
                    // ajout avenant et contrat historique apres la modification du capital et duree
                    // ou adresse
                    Avenant avenant1 = this.avenantServiceImpl.createAvenant(decesContrat, numero + 1,
                            typeExists.get());
                    this.contratHistoriqueServiceImpl.createDecesContratHistorique(decesContrat, model, avenant1);
                }
            } else {
                if (typeExists.isPresent()) {
                    // ajout avenant et contrat historique avant la modification du capital et duree
                    // ou adresse
                    Avenant avenant = this.avenantServiceImpl.createAvenant(decesContrat, num + 1, typeExists.get());
                    this.contratHistoriqueServiceImpl.createDecesContratHistorique(decesContrat, model, avenant);
                }
            }

            // update Contrat avec durre et capital
            if (typeExists.get().getCode().equals(Constants.CODE_AVENANT_DURE_CAPITAL)) {
                decesContrat.setCapitalAssure(model.getCapital());
                decesContrat.setDureeContrat(model.getDuree());
                decesContrat.setDiffere(model.getDiffere());
                decesContrat.setMontantCotisation(model.getMontantCotisation());
                decesContrat.setMontantSurprime(model.getMontantSurprime());
                decesContrat.setMontantTaxe(model.getMontantTaxe());
                decesContrat.setTauxReduction(model.getTauxReduction());
                decesContrat.setTauxSurprime(model.getTauxSurprime());
                decesContrat.setProrata(model.getProrata());
            }
            if (typeExists.get().getCode().equals(Constants.CODE_AVENANT_CHANGEMENT_ADRESSE)) {
                decesContrat.setAssure(this.personneRepository.save(model.getAssure()));
            }
            if (typeExists.get().getCode().equals(Constants.CODE_AVENANT_STATUS)
                    && model.getStatus().equals("VALIDER")) {
                decesContrat.setDatePrelevement(model.getDatePrelevement());
                decesContrat.setCreationDate(model.getDateCreation());

                if (acceptation.getStatus().equals(AcceptationStatus.DONE)
                // && model.getSeuilConseil() >= acceptation.getCumul()
                // && model.getSeuilExaminateur() >= acceptation.getCumul()
                // && model.getSeuilReassurance() >= acceptation.getCumul()
                // && !model.isInvaliditeOuMaladie()
                // && !model.isPensionIncapacite() &&
                // !model.isSuspendreAtiviteDeuxDernierAnnee()
                // && !model.isMaladiesOuOperationChirurgicale()
                ) {

                    decesContrat.setStatus(ContratStatus.INSTANTANEE);

                } else if (model.getSeuilConseil() < acceptation.getCumul()
                        || acceptation.getStatus().equals(AcceptationStatus.IN_PROGRESS)
                        || model.getSeuilExaminateur() < acceptation.getCumul()
                        || model.getSeuilReassurance() < acceptation.getCumul()
                        || model.isInvaliditeOuMaladie() || model.isPensionIncapacite()
                        || model.isSuspendreAtiviteDeuxDernierAnnee() || model.isMaladiesOuOperationChirurgicale()) {

                    decesContrat.setStatus(ContratStatus.WAITING_ACCEPTATION);
                    // acceptation.setStatus(AcceptationStatus.IN_PROGRESS);

                }

            }

            if (typeExists.get().getCode().equals(Constants.CODE_AVENANT_STATUS)
                    && model.getStatus().equals("ANNULER")) {

                decesContrat.setStatus(ContratStatus.ANNULER);
                decesContrat.setMotifAnnulation(model.getMotifAnnulation());

            }
            if (typeExists.get().getCode().equals(Constants.CODE_AVENANT_DTEFFET)) {

                Date now = new Date();
                SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd");
                String stringDate = DateFor.format(now);
                String stringDt = DateFor.format(model.getDateEffet());
                if (stringDate.equals(stringDt)) {
                    decesContrat.setDateEffet(model.getDateEffet());
                    decesContrat.setFlag(2);

                }
            }

            decesContrat.setAcceptation(this.acceptationRepository.save(acceptation));
            return modelMapper.map(this.decesContratRepository.save(decesContrat), DecesContratResponseModel.class);
        } else {
            log.error("la modification1 de produit est erroné avec l'ID({}): ", id);
            throw new NoSuchElementException("la modification1 de produit est erroné");

        }
    }

    @Override
    @Async("asyncExecutor")
    public Page<DecesContratResponseModel> getDecesContrats(int page, int limit, String sort, String direction)
            throws InterruptedException, ExecutionException {
        log.info("lister les contrats deces");
        Thread.sleep(1000L);

        return decesContratRepository.findAll(Pagination.pageableRequest(page, limit, sort, direction))
                .map(o -> modelMapper.map(o, DecesContratResponseModel.class));
    }

    @Override
    @Async("asyncExecutor")
    public DecesContratResponseModel getDecesContratById(String id) throws InterruptedException, ExecutionException {
        Thread.sleep(1000L);
        return null;
    }

    @Override
    @Async("asyncExecutor")
    public Page<DecesContratResponseModel> getDecesContartsByStatus(ContratStatus status, int page, int limit,
            String sort, String direction) throws InterruptedException, ExecutionException {
        log.info("lister les contrats décès acceptée");

        Thread.sleep(1000L);
        return decesContratRepository.findByStatus(status, Pagination.pageableRequest(page, limit, sort, direction))
                .map(o -> modelMapper.map(o, DecesContratResponseModel.class));

    }

    @Override
    @Async("asyncExecutor")
    public Page<ContractResponseModel> searchContratParPartenaire(int page, int limit, String searchBy,
            String searchFor, String partnerId) throws InterruptedException, ExecutionException {

        Thread.sleep(1000L);
        Pageable pageableRequest = PageRequest.of(page, limit);
        if (Constants.NOM_ASSURE.equals(searchBy)) {
            return decesContratRepository.findByAssureNomAndPartenaire(pageableRequest, searchFor, partnerId)
                    .map(o -> modelMapper.map(o, DecesContratResponseModel.class));

        } else if (Constants.CIN_ASSURE.equals(searchBy)) {
            return decesContratRepository.findByAssurecinAndPartenaire(pageableRequest, searchFor, partnerId)
                    .map(o -> modelMapper.map(o, DecesContratResponseModel.class));
        } else if (Constants.NUM_CONTRAT.equals(searchBy)) {
            return decesContratRepository.findByNumeroContratAndPartenaire(pageableRequest, searchFor, partnerId)
                    .map(o -> modelMapper.map(o, DecesContratResponseModel.class));
        } else if (Constants.CODE_ACCEPTATION.equals(searchBy)) {
            return decesContratRepository.findByCodeAcceptation(pageableRequest, searchFor)
                    .map(o -> modelMapper.map(o, DecesContratResponseModel.class));
        }

        throw new IllegalArgumentException("la recherche doit etre efactuer par nom;prenom ou code");
    }

    @Override
    @Async("asyncExecutor")
    public Page<ContractResponseModel> searchContrat(int page, int limit, String searchBy, String searchFor)
            throws InterruptedException, ExecutionException {

        Thread.sleep(1000L);
        Pageable pageableRequest = PageRequest.of(page, limit);
        if (Constants.NOM_ASSURE.equals(searchBy)) {
            return decesContratRepository.findByAssureNom(pageableRequest, searchFor)
                    .map(o -> modelMapper.map(o, DecesContratResponseModel.class));

        } else if (Constants.CIN_ASSURE.equals(searchBy)) {
            return decesContratRepository.findByAssurecin(pageableRequest, searchFor)
                    .map(o -> modelMapper.map(o, DecesContratResponseModel.class));
        } else if (Constants.NUM_CONTRAT.equals(searchBy)) {
            return decesContratRepository.findByNumeroContrat(pageableRequest, searchFor)
                    .map(o -> modelMapper.map(o, DecesContratResponseModel.class));
        } else if (Constants.CODE_ACCEPTATION.equals(searchBy)) {
            return decesContratRepository.findByCodeAcceptation(pageableRequest, searchFor)
                    .map(o -> modelMapper.map(o, DecesContratResponseModel.class));
        }

        throw new IllegalArgumentException("la recherche doit etre efactuer par nom;prenom ou code");
    }

    @Override
    @Async("asyncExecutor")
    public DecesContratResponseModel updateDecesContratStatus(String id, UpdateDecesContratStatusModel model)
            throws InterruptedException, ExecutionException {
        log.info("updating deces contrat status : {}", id);

        Thread.sleep(1000L);
        Optional<DecesContrat> contratexists = decesContratRepository.findById(id);
        Optional<TypeAvenant> typeExists = typeAvenantRepository.findById(model.getTypeAvenantId());
        if (contratexists.isPresent()) {

            Integer num = avenantRepository.numeroAvenant(id);
            DecesContrat decesContrat = contratexists.get();
            BeanUtils.copyProperties(model, decesContrat);
            ajoutAvenant(num, typeExists, decesContrat, model);

            // update Contrat avec durre et capital

            if (INSTANTANEE.equals(model.getStatus()))
                decesContrat.setStatus(ContratStatus.INSTANTANEE);
            else if (RESILIATION_EN_COURS.equals(model.getStatus()))
                decesContrat.setStatus(ContratStatus.EN_COURS_RESILIATION);
            return modelMapper.map(this.decesContratRepository.save(decesContrat), DecesContratResponseModel.class);
        } else {
            log.error("la modification1 de produit est erroné avec l'ID({}): ", id);
            throw new NoSuchElementException("la modification1 de produit est erroné");
        }

    }

    @Override
    @Async("asyncExecutor")
    public updateDecesContratDateEffetResponse updateContratDateEffet(String id, updateDecesContratDateEffetModel model)
            throws InterruptedException, ExecutionException {
        Optional<DecesContrat> contratexists = decesContratRepository.findById(id);
        if (contratexists.isPresent() && INSTANTANEE.equals(model.getStatus()) && model.getFlag() == 0) {
            DecesContrat decesContrat = contratexists.get();
            decesContrat.setDateEffet(model.getDateEffet());
            decesContrat.setFlag(1);
            return modelMapper.map(this.decesContratRepository.save(decesContrat),
                    updateDecesContratDateEffetResponse.class);
        } else {
            log.error("la modification1 de produit est erroné avec l'ID({}): ", id);
            throw new NoSuchElementException("la modification1 de produit est erroné");
        }
    }

    protected Long lastIdImplementer() {
        if (this.decesContratRepository.getLastId() == null) {
            return Long.valueOf(0);
        } else {
            return this.decesContratRepository.getLastId();
        }
    }

    protected void modelChecker(CreateUpdateDecesContrat model, DecesContrat decesContrat) {
        if (model.getOptionAssurance() != null) {
            Optional<OptionAssurance> optionAssurance = this.optionAssuranceRepository
                    .findById(model.getOptionAssurance().getId());
            if (optionAssurance.isPresent())
                decesContrat.setOptionAssurance(model.getOptionAssurance());
        }
    }

    protected void ajoutAvenant(Integer num, Optional<TypeAvenant> typeExists, DecesContrat decesContrat,
            UpdateDecesContratStatusModel model) {
        Date now = new Date();
        if (num == null) {
            // ajout avenant et contrat historique avant la modification du capital et duree

            if (typeExists.isPresent()) {
                Integer numero = 0;
                Avenant avenantCreate = new Avenant();
                avenantCreate.setDateEffet(now);
                avenantCreate.setContrat(decesContrat);
                avenantCreate.setNumeroAvenant(numero);
                avenantCreate.setTypeAvenant(typeExists.get());
                this.avenantRepository.save(avenantCreate);

                DecesContratHistorique contratHistCreate = new DecesContratHistorique();
                contratHistCreate.setAvenantId(avenantCreate);
                contratHistCreate.setNumeroContrat(decesContrat.getNumeroContrat());
                contratHistCreate
                        .setAssure(modelMapper.map(decesContrat.getAssure(), PersonnePhysiqueHistorique.class));
                contratHistCreate.setSouscripteur(decesContrat.getSouscripteur());
                contratHistCreate.setProduit(decesContrat.getProduit());
                contratHistCreate.setPeriodicite(decesContrat.getPeriodicite());
                contratHistCreate.setPointVente(decesContrat.getPointVente());
                contratHistCreate.setDateEcheance(decesContrat.getDateEcheance());
                contratHistCreate.setDateEffet(decesContrat.getDateEffet());
                contratHistCreate.setOptionEmission(decesContrat.getOptionEmission());
                contratHistCreate.setStatus(decesContrat.getStatus());
                contratHistCreate.setDatePrelevement(decesContrat.getDatePrelevement());
                contratHistCreate.setDiffere(decesContrat.getDiffere());
                contratHistCreate.setMontantCotisation(decesContrat.getMontantCotisation());
                contratHistCreate.setMontantFrais(decesContrat.getMontantFrais());
                contratHistCreate.setMontantSurprime(decesContrat.getMontantSurprime());
                contratHistCreate.setMontantTaxe(decesContrat.getMontantTaxe());
                contratHistCreate.setProrata(decesContrat.getProrata());
                contratHistCreate.setTauxInteret(decesContrat.getTauxInteret());
                contratHistCreate.setTauxReduction(decesContrat.getTauxReduction());
                contratHistCreate.setTauxSurprime(decesContrat.getTauxSurprime());
                contratHistCreate.setOptionAssurance(decesContrat.getOptionAssurance());
                contratHistCreate.setCapitalAssure(decesContrat.getCapitalAssure());
                contratHistCreate.setDureeContrat(decesContrat.getDureeContrat());
                decesContratHistoriqueRepository.save(contratHistCreate);

                // ajout avenant et contrat historique apres la modification du capital et duree
                Avenant avenantsCreate = new Avenant();
                avenantsCreate.setDateEffet(now);
                avenantsCreate.setContrat(decesContrat);
                avenantsCreate.setNumeroAvenant(numero + 1);
                avenantsCreate.setTypeAvenant(typeExists.get());
                this.avenantRepository.save(avenantsCreate);

                DecesContratHistorique contratHistorCreate = new DecesContratHistorique();
                contratHistorCreate.setAvenantId(avenantsCreate);
                contratHistorCreate.setNumeroContrat(decesContrat.getNumeroContrat());
                contratHistorCreate
                        .setAssure(modelMapper.map(decesContrat.getAssure(), PersonnePhysiqueHistorique.class));
                contratHistorCreate.setSouscripteur(decesContrat.getSouscripteur());
                contratHistorCreate.setProduit(decesContrat.getProduit());
                contratHistorCreate.setPeriodicite(decesContrat.getPeriodicite());
                contratHistorCreate.setPointVente(decesContrat.getPointVente());
                contratHistorCreate.setDateEcheance(decesContrat.getDateEcheance());
                contratHistorCreate.setDateEffet(decesContrat.getDateEffet());
                contratHistorCreate.setOptionEmission(decesContrat.getOptionEmission());
                if (INSTANTANEE.equals(model.getStatus()))
                    contratHistorCreate.setStatus(ContratStatus.INSTANTANEE);
                else if (RESILIATION_EN_COURS.equals(model.getStatus()))
                    contratHistorCreate.setStatus(ContratStatus.EN_COURS_RESILIATION);
                contratHistorCreate.setDatePrelevement(decesContrat.getDatePrelevement());
                contratHistorCreate.setDiffere(decesContrat.getDiffere());
                contratHistorCreate.setMontantCotisation(decesContrat.getMontantCotisation());
                contratHistorCreate.setMontantFrais(decesContrat.getMontantFrais());
                contratHistorCreate.setMontantSurprime(decesContrat.getMontantSurprime());
                contratHistorCreate.setMontantTaxe(decesContrat.getMontantTaxe());
                contratHistorCreate.setProrata(decesContrat.getProrata());
                contratHistorCreate.setTauxInteret(decesContrat.getTauxInteret());
                contratHistorCreate.setTauxReduction(decesContrat.getTauxReduction());
                contratHistorCreate.setTauxSurprime(decesContrat.getTauxSurprime());
                contratHistorCreate.setOptionAssurance(decesContrat.getOptionAssurance());
                contratHistorCreate.setCapitalAssure(decesContrat.getCapitalAssure());
                contratHistorCreate.setDureeContrat(decesContrat.getDureeContrat());

                decesContratHistoriqueRepository.save(contratHistorCreate);
            }

        } else {
            if (typeExists.isPresent()) {
                // ajout avenant et contrat historique avant la modification du capital et duree
                Avenant avenantC = new Avenant();
                avenantC.setDateEffet(now);
                avenantC.setContrat(decesContrat);
                avenantC.setNumeroAvenant(num + 1);
                avenantC.setTypeAvenant(typeExists.get());
                this.avenantRepository.save(avenantC);

                DecesContratHistorique contratHistC = new DecesContratHistorique();
                contratHistC.setAvenantId(avenantC);
                contratHistC.setNumeroContrat(decesContrat.getNumeroContrat());
                contratHistC.setAssure(modelMapper.map(decesContrat.getAssure(), PersonnePhysiqueHistorique.class));
                contratHistC.setSouscripteur(decesContrat.getSouscripteur());
                contratHistC.setProduit(decesContrat.getProduit());
                contratHistC.setPeriodicite(decesContrat.getPeriodicite());
                contratHistC.setPointVente(decesContrat.getPointVente());
                contratHistC.setDateEcheance(decesContrat.getDateEcheance());
                contratHistC.setDateEffet(decesContrat.getDateEffet());
                contratHistC.setOptionEmission(decesContrat.getOptionEmission());
                if (INSTANTANEE.equals(model.getStatus()))
                    contratHistC.setStatus(ContratStatus.INSTANTANEE);
                else if (RESILIATION_EN_COURS.equals(model.getStatus()))
                    contratHistC.setStatus(ContratStatus.EN_COURS_RESILIATION);
                contratHistC.setDatePrelevement(decesContrat.getDatePrelevement());
                contratHistC.setDiffere(decesContrat.getDiffere());
                contratHistC.setMontantCotisation(decesContrat.getMontantCotisation());
                contratHistC.setMontantFrais(decesContrat.getMontantFrais());
                contratHistC.setMontantSurprime(decesContrat.getMontantSurprime());
                contratHistC.setMontantTaxe(decesContrat.getMontantTaxe());
                contratHistC.setProrata(decesContrat.getProrata());
                contratHistC.setTauxInteret(decesContrat.getTauxInteret());
                contratHistC.setTauxReduction(decesContrat.getTauxReduction());
                contratHistC.setTauxSurprime(decesContrat.getTauxSurprime());
                contratHistC.setOptionAssurance(decesContrat.getOptionAssurance());
                contratHistC.setCapitalAssure(decesContrat.getCapitalAssure());
                contratHistC.setDureeContrat(decesContrat.getDureeContrat());
                decesContratHistoriqueRepository.save(contratHistC);
            }
        }

    }

    @Override
    @Async("asyncExecutor")
    public DecesContratResponseModel findContratById(String id) throws InterruptedException, ExecutionException {

        Thread.sleep(1000L);
        Optional<DecesContrat> contrat = this.decesContratRepository.findById(id);
        if (contrat.isPresent()) {
            DecesContrat contratDeces = contrat.get();
            return modelMapper.map(contratDeces, DecesContratResponseModel.class);
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    @Async("asyncExecutor")
    public Page<DecesContratResponseModel> getContratByPartenaire(int page, int limit, String sort, String direction,
            String PartenaireId) throws InterruptedException, ExecutionException {
        Thread.sleep(1000L);
        if ("numeroContrat".equals(sort))
            sort = "numero_contrat";

        return decesContratRepository
                .findByPartenaire(PartenaireId, Pagination.pageableRequest(page, limit, sort, direction))
                .map(o -> modelMapper.map(o, DecesContratResponseModel.class));
    }

    @Override
    public DecesContratResponseModel updateDecesContratOrientation(String id, String orientation)
            throws InterruptedException, ExecutionException {
        log.info("updating deces contrat orientation : {}", id);

        Thread.sleep(1000L);
        Optional<DecesContrat> contratexists = decesContratRepository.findById(id);

        if (contratexists.isPresent()) {
            DecesContrat decesContrat = contratexists.get();
            decesContrat.setOrientation(orientation);

            return modelMapper.map(this.decesContratRepository.save(decesContrat), DecesContratResponseModel.class);
        }

        return null;
    }

    public DecesContrat findById(String id) {
        return decesContratRepository.findById(id).orElse(null);
    }

    public DecesContrat saveById(String id) {
        DecesContrat item = new DecesContrat();
        item.setId(id);
        return decesContratRepository.save(item);
    }

    @Override
    public DecesContrat findOrInsert(String id) {
        DecesContrat item = findById(id);
        if (item != null) {
            return item;
        }

        return saveById(id);
    }

}
