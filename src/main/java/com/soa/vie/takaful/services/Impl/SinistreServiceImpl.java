package com.soa.vie.takaful.services.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.soa.vie.takaful.entitymodels.Contract;
import com.soa.vie.takaful.entitymodels.Exclusion;
import com.soa.vie.takaful.entitymodels.NotificationSinistre;
import com.soa.vie.takaful.entitymodels.PrestationSinistre;
import com.soa.vie.takaful.entitymodels.TakafulUser;
import com.soa.vie.takaful.entitymodels.autoIncrementhelpers.CostumIdGeneratedValueSinistreEntity;
import com.soa.vie.takaful.repositories.IContractRepository;
import com.soa.vie.takaful.repositories.IExclusionRepository;
import com.soa.vie.takaful.repositories.ISinistreRepository;
import com.soa.vie.takaful.repositories.autoincrementhelpers.CostumIdGeneratedValueSinistreEntityRepository;
import com.soa.vie.takaful.requestmodels.CreateSinistre;
import com.soa.vie.takaful.requestmodels.UpdateSinistre;
import com.soa.vie.takaful.responsemodels.PrestationSinistreModelResponse;
import com.soa.vie.takaful.services.ISinistreService;
import com.soa.vie.takaful.util.ContratStatus;
import com.soa.vie.takaful.util.NotificationMessages;
import com.soa.vie.takaful.util.PrestationStatusEnum;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class SinistreServiceImpl implements ISinistreService {

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Autowired
	private ISinistreRepository sinistreRepository;

	@Autowired
	private IContractRepository contractRepository;

	@Autowired
	private IExclusionRepository exclusionRepository;


	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CostumIdGeneratedValueSinistreEntityRepository costumIdGeneratedValueSinistreEntityRepository;

	@Autowired
	private NotificationSinistreServiceImpl notificationSinistreServiceImpl;

	@Override
	@Async("asyncExecutor")
	public PrestationSinistreModelResponse createSinistre(CreateSinistre requestModel)
			throws InterruptedException, ExecutionException {
		log.info("creation du sinistre ...");
		Thread.sleep(1000L);
		PrestationSinistre addSinistre = new PrestationSinistre();
		// creating new autoDataBaseGeneratedId to save the generated Id as the
		// numero sinistre
		Optional<Contract> contrat = contractRepository.findById(requestModel.getContratId());
		log.info("this is verification of contract existance :" + contrat.isPresent());
		if (contrat.isPresent()) {
			CostumIdGeneratedValueSinistreEntity autoDataBaseGeneratedIdSinistre = new CostumIdGeneratedValueSinistreEntity();
			addSinistre.setNumeroSinistre(
					this.costumIdGeneratedValueSinistreEntityRepository.save(autoDataBaseGeneratedIdSinistre).getId());
			// CostumIdGeneratedValueSinistreEntity id =
			// this.costumIdGeneratedValueSinistreEntityRepository.save(autoDataBaseGeneratedIdSinistre);
			log.info("############################numero sinistre generated : " + addSinistre.getNumeroSinistre());
			// BeanUtils.copyProperties(requestModel, addSinistre);
			addSinistre.setContrat(contrat.get());
			log.info("hello 1");
			addSinistre.setNatureSinistre(requestModel.getNatureSinistre());
			addSinistre.setDateSurvenance(requestModel.getDateSurvenance());
			addSinistre.setDateDeclarationSinistre(requestModel.getDateDeclarationSinistre());
			addSinistre.setTypePrestation(requestModel.getTypePrestation());
			log.info("hello 3");
			if (addSinistre.getDateSurvenance().after(contrat.get().getDateEcheance())) {
				log.info("executing the date of code...");
				addSinistre.setMotif("Contrat échu");// add to constants if motif doesnt gonna be an enum.
				addSinistre.setStatus(PrestationStatusEnum.REJ_CONTRAT_ECHU);
				contrat.get().setStatus(ContratStatus.ACCEPTED);// to do : change to the choosed status for this case

				// notification par point de vente de creation de contrat
				// List<TakafulUser> usersByP =
				// this.userServiceImpl.getUsersByPointVente(contrat.get().getPointVente().getId());
				// for ( TakafulUser userP : usersByP) {
				// Notification notification =
				// this.notificationServiceImpl.createNotificationObject(NotificationMessages.ContratSinistreResiliationRejte(contrat.get().getNumeroContrat(),addSinistre.getNumeroSinistre(),addSinistre.getMotif()),
				// userP);
				// this.notificationServiceImpl.save(notification);
				// }
			} else {
				addSinistre.setStatus(PrestationStatusEnum.EN_COURS);
				contrat.get().setStatus(ContratStatus.EN_COURS_RESILIATION);
				// notification par privilege 'gestion de sinistre'
				// List<TakafulUser> usersByP =
				// this.userServiceImpl.getUserHavingPriSvilege(SecurityCanstants.GESTION_SINISTRE);
				// for ( TakafulUser userP : usersByP) {
				// Notification notification =
				// this.notificationServiceImpl.createNotificationObject(NotificationMessages.ContratSinistreResiliationDemandeMessage(contrat.get().getNumeroContrat(),id.getId()),
				// userP);
				// this.notificationServiceImpl.save(notification);
				// }
			}
			log.info("hello 4");
			this.contractRepository.save(contrat.get());
			log.info("hello 5");
			// log.info("add sinsitre null : "+addSinistre.getNumeroSinistre());
			// this.sinistreRepository.save(addSinistre);
			return modelMapper.map(this.sinistreRepository.save(addSinistre), PrestationSinistreModelResponse.class);

		} else {
			log.error("l'insertion  du sinistre  est erroné  ");
			throw new NullPointerException("l'insertion  du sinistre est erroné");
		}
	}

	@Override
	@Async("asyncExecutor")
	public List<PrestationSinistreModelResponse> getSinistrebyContrat(String contratId)
			throws InterruptedException, ExecutionException {
		log.info("executing the service...");
		Thread.sleep(1000L);
		return sinistreRepository.findByContratId(contratId)
				.stream()
				.map(o -> modelMapper.map(o, PrestationSinistreModelResponse.class))
				.collect(Collectors.toList());

	}

	@Override
	@Async("asyncExecutor")
	public PrestationSinistreModelResponse updateSinistre(UpdateSinistre model, String id)
			throws InterruptedException, ExecutionException {

		log.info("modifier le sinistre avec Id : {}", id);

		// Thread.sleep(2000L);
		Optional<PrestationSinistre> sinistreOpt = sinistreRepository.findById(id);
		// Optional<PointVente> pointVente =
		// pointRepository.findById(model.getPointVente());
		// Optional<CompteBancaire>
		// compte=compteBancaireRepository.findById(model.getNumeroCompte());
		if (sinistreOpt.isPresent()) {
			PrestationSinistre sinistre = sinistreOpt.get();
			if (null != model.getExclusions()) {
				List<Exclusion> exclusions = new ArrayList<>();
				for (Exclusion request : model.getExclusions()) {
					Optional<Exclusion> exclusionOptional = exclusionRepository.findById(request.getId());
					if (!exclusionOptional.isPresent()) {
						throw new HttpClientErrorException(HttpStatus.NOT_FOUND,
								"UpdateExclusion : Exclusion n'existe pas ");
					}
					Exclusion exclusion = exclusionOptional.get();
					exclusions.add(exclusion);
				}
				sinistre.setExclusions(exclusions);
			}
			exclusionsHandler(model, sinistre);

			/*
			 * if (pointVente.isPresent()) {
			 * BeneficiaireAgence addBeneficiaireAgence = new BeneficiaireAgence();
			 * addBeneficiaireAgence.setPointVente(pointVente.get());
			 * addBeneficiaireAgence.setSinistre(sinistreOpt.get());
			 * beneficiaireRepository.save(addBeneficiaireAgence);
			 * 
			 * }
			 */

			BeanUtils.copyProperties(model, sinistre);
			return modelMapper.map(sinistreRepository.save(sinistre), PrestationSinistreModelResponse.class);

		} else {
			log.error("la modification de sinistre avec ce Id ({}): est erronée", id);
			throw new NullPointerException("la modification de sinistre avec ce Id est erronée");
		}

	}

	protected void exclusionsHandler(UpdateSinistre model, PrestationSinistre sinistre) {
		List<Exclusion> exclusions = new ArrayList<>();
		for (Exclusion request : model.getExclusions()) {
			Optional<Exclusion> exclusionOptional = exclusionRepository.findById(request.getId());
			if (!exclusionOptional.isPresent()) {
				throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "UpdateSinistre : Exclusion n'existe pas ");
			}
			Exclusion exclusion = exclusionOptional.get();
			exclusions.add(exclusion);
		}
		sinistre.setExclusions(exclusions);
	}

	@Override
	@Async("asyncExecutor")
	public void deleteSinistre(String id) throws InterruptedException, ExecutionException {
		Thread.sleep(1000L);
		sinistreRepository.deleteById(id);
	}

	@Override
	@Async("asyncExecutor")
	public Page<PrestationSinistreModelResponse> setStatut(String id, String statut, int page, int limit, String motif)
			throws InterruptedException, ExecutionException {

		Thread.sleep(1000L);
		Pageable pageableRequest = PageRequest.of(page, limit);
		Optional<PrestationSinistre> sinistreOpt = sinistreRepository.findById(id);
		if (!sinistreOpt.isPresent()) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Sinistre n'existe pas ");
		}
		PrestationSinistre sinistre = sinistreOpt.get();
		if ("EN_COURS".equals(statut)) {
			sinistre.setStatus(PrestationStatusEnum.EN_COURS);
			return sinistreRepository.findAll(pageableRequest)
					.map(o -> modelMapper.map(o, PrestationSinistreModelResponse.class));

		} else if ("A_signer".equals(statut)) {
			sinistre.setStatus(PrestationStatusEnum.A_SIGNER);
			return sinistreRepository.findAll(pageableRequest)
					.map(o -> modelMapper.map(o, PrestationSinistreModelResponse.class));

		} else if ("ANNULER".equals(statut)) {
			sinistre.setStatus(PrestationStatusEnum.ANNULE);
			sinistre.setRemplie(false);
			sinistre.setMotif(motif);
			return sinistreRepository.findAll(pageableRequest)
					.map(o -> modelMapper.map(o, PrestationSinistreModelResponse.class));
		}
		throw new NoSuchElementException();

	}

	@Override
	public List<PrestationSinistre> searchSinistre(String searchby, String searchfor) {
		log.info("search by " + searchby);
		log.info("search for " + searchfor);
		if (searchby.equals("numContrat")) {
			return sinistreRepository.findbyNumeroContract(searchfor);
		} else if (searchby.equals("numeroSinistre")) {
			log.info("test test test num");
			return sinistreRepository.findbyNumeroSinistre(searchfor);

		}
		log.info("passed out the service");
		return null;
	}

	@Override
	public void notifLettreRelance(String numContrat, String pointVente) {
		log.info("numéro du contrat " + numContrat);
		// notification par point de vente d'envoi de lettre de relance
		List<TakafulUser> usersByP = this.userServiceImpl
				.getUsersByPointVente(pointVente);
		for (TakafulUser userP : usersByP) {
			try {
				NotificationSinistre notification = this.notificationSinistreServiceImpl.createNotificationObject(
						NotificationMessages.GenerateLettreDeRelance(numContrat), userP);
				this.notificationSinistreServiceImpl.save(notification);
			} catch (Exception e) {
				log.error("Exception occur  ", e);
			}
		}
	}

}