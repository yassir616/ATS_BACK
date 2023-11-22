package com.soa.vie.takaful.servicesImplementation;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.soa.vie.takaful.entitymodels.Acceptation;
import com.soa.vie.takaful.entitymodels.AcceptationConseil;
import com.soa.vie.takaful.entitymodels.AcceptationEtape;
import com.soa.vie.takaful.entitymodels.AcceptationExamens;
import com.soa.vie.takaful.entitymodels.AcceptationExaminateur;
import com.soa.vie.takaful.entitymodels.AcceptationLaboratoire;
import com.soa.vie.takaful.entitymodels.AcceptationTestMedical;
import com.soa.vie.takaful.repositories.IAcceptationConseilRepository;
import com.soa.vie.takaful.repositories.IAcceptationEtapeRepository;
import com.soa.vie.takaful.repositories.IAcceptationExamensRepository;
import com.soa.vie.takaful.repositories.IAcceptationExaminateurRepository;
import com.soa.vie.takaful.repositories.IAcceptationLaboratoireRepository;
import com.soa.vie.takaful.repositories.IAcceptationRepository;
import com.soa.vie.takaful.repositories.IAcceptationTestMedicalRepository;
import com.soa.vie.takaful.repositories.IDecesContratRepository;
import com.soa.vie.takaful.requestmodels.CreateAcceptationTestMedicalModelRequest;
import com.soa.vie.takaful.responsemodels.AcceptationTestMedicalModelResponse;
import com.soa.vie.takaful.services.IAcceptationTestMedicalService;
import com.soa.vie.takaful.util.AcceptationStatus;
import com.soa.vie.takaful.util.Constants;
import com.soa.vie.takaful.util.ContratStatus;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AcceptationTestMedicalServiceImpl implements IAcceptationTestMedicalService {

  @Autowired
  private IAcceptationTestMedicalRepository acceptationTestMedicalRepository;

  @Autowired
  private IAcceptationRepository acceptationRepository;

  @Autowired
  private IAcceptationLaboratoireRepository acceptationLaboratoireRepository;

  @Autowired
  private IAcceptationExaminateurRepository acceptationExaminateurRepository;

  @Autowired
  private IAcceptationExamensRepository acceptationExamensRepository;

  @Autowired
  private IAcceptationConseilRepository acceptationConseilRepository;

  @Autowired
  private IAcceptationEtapeRepository acceptationEtapeRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private IDecesContratRepository contratRepository;

  @Override
  @Async("asyncExecutor")
  public AcceptationTestMedicalModelResponse createAcceptationTestMedical(
      CreateAcceptationTestMedicalModelRequest model) throws InterruptedException, ExecutionException {

    Thread.sleep(1000L);
    AcceptationTestMedical acceptationTestMedical = new AcceptationTestMedical();

    BeanUtils.copyProperties(model, acceptationTestMedical);
    return modelMapper.map(acceptationTestMedicalRepository.save(acceptationTestMedical),
        AcceptationTestMedicalModelResponse.class);
  }

  @Override
  @Async("asyncExecutor")
  public AcceptationTestMedicalModelResponse getAcceptationsTestAndLabo(String acceptation, String acceptationLabo)
      throws InterruptedException, ExecutionException {

    Optional<Acceptation> acceptationOpt = this.acceptationRepository.findById(acceptation);
    Optional<AcceptationLaboratoire> acceptationLaboOpt = this.acceptationLaboratoireRepository
        .findById(acceptationLabo);

    if (acceptationLaboOpt.isPresent() && acceptationOpt.isPresent()) {
      return modelMapper.map(this.acceptationTestMedicalRepository.findByAcceptationAndAcceptationLaboratoire(
          acceptationOpt.get(), acceptationLaboOpt.get()), AcceptationTestMedicalModelResponse.class);
    }
    return null;
  }

  @Override
  @Async("asyncExecutor")
  public AcceptationTestMedicalModelResponse getAcceptationsTestAndExamens(String acceptation,
      String acceptationExamens) throws InterruptedException, ExecutionException {

    Optional<Acceptation> acceptationOpt = this.acceptationRepository.findById(acceptation);
    Optional<AcceptationExamens> acceptationExamensOpt = this.acceptationExamensRepository.findById(acceptationExamens);

    if (acceptationExamensOpt.isPresent() && acceptationOpt.isPresent()) {

      AcceptationTestMedical acceptationTestMedical = this.acceptationTestMedicalRepository
          .findByAcceptationAndAcceptationExamens(acceptationOpt.get(), acceptationExamensOpt.get());

      return modelMapper.map(acceptationTestMedical, AcceptationTestMedicalModelResponse.class);
    }

    return null;
  }

  @Override
  @Async("asyncExecutor")
  public AcceptationTestMedicalModelResponse getAcceptationsTestAndExaminateur(String acceptation,
      String accexaminateur) throws InterruptedException, ExecutionException {

    Thread.sleep(1000L);
    Optional<Acceptation> acceptationOpt = this.acceptationRepository.findById(acceptation);
    Optional<AcceptationExaminateur> acceptationExaminateurOpt = this.acceptationExaminateurRepository
        .findById(accexaminateur);

    if (acceptationExaminateurOpt.isPresent() && acceptationOpt.isPresent()) {
      return modelMapper.map(this.acceptationTestMedicalRepository.findByAcceptationAndAcceptationExaminateur(
          acceptationOpt.get(), acceptationExaminateurOpt.get()), AcceptationTestMedicalModelResponse.class);
    }

    return null;
  }

  @Override
  @Async("asyncExecutor")
  public AcceptationTestMedicalModelResponse getAcceptationsTestAndConseil(String acceptation,
      String acceptationConseil) throws InterruptedException, ExecutionException {

    Thread.sleep(1000L);
    Optional<Acceptation> acceptationOpt = this.acceptationRepository.findById(acceptation);
    Optional<AcceptationConseil> acceptationConseilOpt = this.acceptationConseilRepository.findById(acceptationConseil);

    if (acceptationConseilOpt.isPresent() && acceptationOpt.isPresent()) {
      return modelMapper.map(this.acceptationTestMedicalRepository.findByAcceptationAndAcceptationConseil(
          acceptationOpt.get(), acceptationConseilOpt.get()), AcceptationTestMedicalModelResponse.class);
    }

    return null;
  }

  @Override
  @Async("asyncExecutor")
  public AcceptationTestMedical updateAcceptationTestMedical(CreateAcceptationTestMedicalModelRequest model,
      String id) throws InterruptedException, ExecutionException {

    Thread.sleep(1000L);
    Optional<AcceptationTestMedical> acceptationTestMedical = acceptationTestMedicalRepository.findById(id);
    if (model.getAcceptationLaboratoire() != null) {
      return updateAcceptationLaboratory(model, acceptationTestMedical);
    } else if (model.getAcceptationExamens() != null) {
      return updateAcceptationExamens(model, acceptationTestMedical);
    } else if (model.getAcceptationExaminateur() != null) {
      return updateAcceptationExaminateur(model, acceptationTestMedical);
    } else if (model.getAcceptationConseil() != null) {
      return updateAcceptationConseil(model, acceptationTestMedical);
    }
    throw new NoSuchElementException("wrong data");

  }

  @Override
  @Async("asyncExecutor")
  public List<AcceptationTestMedicalModelResponse> getAcceptationsTestByAuxiliare(String auxiliare,
      String auxiliareType, String partenaire) throws InterruptedException, ExecutionException {

    Thread.sleep(1000L);


    List<String> tableJoint = Arrays.asList("inner join acceptation_laboratoire e on t.acceptation_laboratoire_id = e.id and e.laboratoire_id =",
                                              "inner join acceptation_examinateur e  on t.acceptation_examinateur_id = e.id and e.medecin_id =",
                                              "inner join acceptation_specialiste e on t.acceptation_specialiste_id = e.id and e.specialiste_id =",
                                              "inner join acceptation_conseil e on t.acceptation_conseil_id = e.id and e.medecin_id =");

    List<AcceptationTestMedical> acceptationTestMedical = Collections.emptyList();

    

    // System.out.println("test aux");
    switch (auxiliareType) {
        case ("LABORATOIRE"):
            return this.acceptationTestMedicalRepository.find(tableJoint.get(0),auxiliare, partenaire)
                .stream()
                .map(o -> modelMapper
                .map(o, AcceptationTestMedicalModelResponse.class))
                .collect(Collectors.toList());
        case ("MEDECIN EXAMINATEUR"):
            return this.acceptationTestMedicalRepository.find(tableJoint.get(1),auxiliare, partenaire)
                  .stream()
                  .map(o -> modelMapper
                  .map(o, AcceptationTestMedicalModelResponse.class))
                  .collect(Collectors.toList());
        case ("MEDECIN SPECIALISTE"):
            return this.acceptationTestMedicalRepository.find(tableJoint.get(2),auxiliare, partenaire)
                  .stream()
                  .map(o -> modelMapper
                  .map(o, AcceptationTestMedicalModelResponse.class))
                  .collect(Collectors.toList());
        case ("MEDECIN CONSEIL"):
            return this.acceptationTestMedicalRepository.find(tableJoint.get(3),auxiliare, partenaire)
                  .stream()
                  .map(o -> modelMapper
                  .map(o, AcceptationTestMedicalModelResponse.class))
                  .collect(Collectors.toList());
        case "undefined":
            for(int i=0 ; i<4 ; i++){
                acceptationTestMedical = this.acceptationTestMedicalRepository.find(tableJoint.get(i),auxiliare,partenaire);  
                if(acceptationTestMedical.size() != 0){
                  break;
                }
            }
            return acceptationTestMedical
                   .stream()
                   .map(o -> modelMapper
                   .map(o, AcceptationTestMedicalModelResponse.class))
                   .collect(Collectors.toList());
            
      // case ("MEDECIN SPECIALISTE"):
      //   List<AcceptationTestMedical> specialistes = this.acceptationTestMedicalRepository.findBySpecialiste(auxiliare,
      //       partenaire, produit);
      //   List<AcceptationTestMedical> examinateur = this.acceptationTestMedicalRepository.findByExaminateur(auxiliare,
      //       partenaire, produit);
      //   List<AcceptationTestMedical> medecinConseil = this.acceptationTestMedicalRepository.findByConseil(auxiliare,
      //       partenaire, produit);
      //   List<AcceptationTestMedical> results = new ArrayList<>();
      //   results.addAll(specialistes);
      //   results.addAll(examinateur);
      //   results.addAll(medecinConseil);
      //   return results.stream().map(o -> modelMapper.map(o, AcceptationTestMedicalModelResponse.class))
      //       .collect(Collectors.toList());

      // case ("MEDECIN-ARBITRE"):
      //   List<AcceptationTestMedical> specialiste = this.acceptationTestMedicalRepository.findBySpecialiste(auxiliare,
      //       partenaire, produit);
      //   List<AcceptationTestMedical> examinateurs = this.acceptationTestMedicalRepository.findByExaminateur(auxiliare,
      //       partenaire, produit);
      //   List<AcceptationTestMedical> medecinConseils = this.acceptationTestMedicalRepository.findByConseil(auxiliare,
      //       partenaire, produit);
      //   List<AcceptationTestMedical> laboratoires = this.acceptationTestMedicalRepository.findByLaboratoire(auxiliare,
      //       partenaire, produit);
      //   List<AcceptationTestMedical> result = new ArrayList<>();
      //   result.addAll(specialiste);
      //   result.addAll(examinateurs);
      //   result.addAll(laboratoires);
      //   result.addAll(medecinConseils);
      //   return result.stream().map(o -> modelMapper.map(o, AcceptationTestMedicalModelResponse.class))
      //       .collect(Collectors.toList());

      default:
        return Collections.emptyList();

    }

  }

  private AcceptationTestMedical updateAcceptationLaboratory(CreateAcceptationTestMedicalModelRequest model,
      Optional<AcceptationTestMedical> acceptationTestMedical) {
    Optional<AcceptationLaboratoire> acceptationLaboratoire = acceptationLaboratoireRepository
        .findById(model.getAcceptationLaboratoire().getId());
    if (acceptationTestMedical.isPresent() && acceptationLaboratoire.isPresent()) {

      acceptationLaboratoireRepository.save(model.getAcceptationLaboratoire());
      AcceptationTestMedical acceptationTestMedical2 = acceptationTestMedical.get();
      BeanUtils.copyProperties(model, acceptationTestMedical2);
      return acceptationTestMedicalRepository.save(acceptationTestMedical.get());

    }

    return null;
  }

  private AcceptationTestMedical updateAcceptationExamens(CreateAcceptationTestMedicalModelRequest model,
      Optional<AcceptationTestMedical> acceptationTestMedical) {
    Optional<AcceptationExamens> acceptationExamens = acceptationExamensRepository
        .findById(model.getAcceptationExamens().getId());

    if (acceptationTestMedical.isPresent() && acceptationExamens.isPresent()) {

      acceptationExamensRepository.save(model.getAcceptationExamens());
      AcceptationTestMedical acceptationTestMedical2 = acceptationTestMedical.get();
      BeanUtils.copyProperties(model, acceptationTestMedical2);
      return acceptationTestMedicalRepository.save(acceptationTestMedical.get());

    }

    return null;
  }

  private AcceptationTestMedical updateAcceptationExaminateur(CreateAcceptationTestMedicalModelRequest model,
      Optional<AcceptationTestMedical> acceptationTestMedical) {
    Optional<AcceptationExaminateur> acceptationExaminateur = acceptationExaminateurRepository
        .findById(model.getAcceptationExaminateur().getId());

    if (acceptationTestMedical.isPresent() && acceptationExaminateur.isPresent()) {

      AcceptationEtape acceptationEtape = acceptationEtapeRepository.findByEtapeAndAcceptationTestMedical("examinateur",
          acceptationTestMedical.get());

      if (!acceptationEtape.getVerdict().getId().equals(acceptationExaminateur.get().getVerdict().getId())) {

        acceptationEtape.setDateVerdict(new Date());
        acceptationEtape.setVerdict(model.getAcceptationExaminateur().getVerdict());
        acceptationEtapeRepository.save(acceptationEtape);
      }

      AcceptationExaminateur acceptationExaminateur2 = new AcceptationExaminateur();
      BeanUtils.copyProperties(model.getAcceptationExaminateur(), acceptationExaminateur2);
      acceptationExaminateur2.setId(acceptationExaminateur.get().getId());
      acceptationExaminateurRepository.save(acceptationExaminateur2);

      AcceptationTestMedical acceptationTestMedical2 = acceptationTestMedical.get();
      BeanUtils.copyProperties(model, acceptationTestMedical2);
      acceptationTestMedical2.setAcceptationExaminateur(acceptationExaminateur2);
      return acceptationTestMedicalRepository.save(acceptationTestMedical2);

    }

    return null;
  }

  private AcceptationTestMedical updateAcceptationConseil(CreateAcceptationTestMedicalModelRequest model,
      Optional<AcceptationTestMedical> acceptationTestMedical) {

    Optional<AcceptationConseil> acceptationConseil = acceptationConseilRepository
        .findById(model.getAcceptationConseil().getId());

    if (acceptationTestMedical.isPresent() && acceptationConseil.isPresent()) {

      if (model.getAcceptationConseil().getVerdict().getStatus().equals(Constants.ACCEPTATION_AU_TARIF_NORMAL)
          || model.getAcceptationConseil().getVerdict().getStatus().equals(Constants.ACCEPTATION_AVEC_SURPRIME)) {

        model.getAcceptation().setStatus(AcceptationStatus.DONE);
        model.getAcceptation().getContrat().setStatus(ContratStatus.INSTANTANEE);

        this.contratRepository.save(model.getAcceptation().getContrat());
        this.acceptationRepository.save(model.getAcceptation());

      } else if (model.getAcceptationConseil().getVerdict().getStatus().equals(Constants.REJET)) {
        model.getAcceptation().setStatus(AcceptationStatus.DONE);
        model.getAcceptation().getContrat().setStatus(ContratStatus.REJETE);

        this.contratRepository.save(model.getAcceptation().getContrat());
        this.acceptationRepository.save(model.getAcceptation());
      }

      AcceptationEtape acceptationEtape = acceptationEtapeRepository
          .findByEtapeAndAcceptationTestMedical("medecinConseil", acceptationTestMedical.get());

      if (!acceptationEtape.getVerdict().getId().equals(model.getAcceptationConseil().getVerdict().getId())) {

        acceptationEtape.setDateVerdict(new Date());
        acceptationEtape.setVerdict(model.getAcceptationConseil().getVerdict());
        acceptationEtapeRepository.save(acceptationEtape);
      }

      AcceptationConseil acceptationConseil2 = acceptationConseilRepository.save(model.getAcceptationConseil());
      AcceptationTestMedical acceptationTestMedical2 = acceptationTestMedical.get();

      BeanUtils.copyProperties(model, acceptationTestMedical2);
      acceptationTestMedical2.setAcceptationConseil(acceptationConseil2);
      return acceptationTestMedicalRepository.save(acceptationTestMedical2);

    }

    return null;
  }

}