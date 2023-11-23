package com.soa.vie.takaful.services.Impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import javax.transaction.Transactional;

import com.soa.vie.takaful.entitymodels.Prestation;
import com.soa.vie.takaful.entitymodels.PrestationHonoraire;
import com.soa.vie.takaful.entitymodels.PrestationSinistre;
import com.soa.vie.takaful.entitymodels.Reglement;
import com.soa.vie.takaful.repositories.IPrestationHonoraireRepository;
import com.soa.vie.takaful.repositories.IReglementRepository;
import com.soa.vie.takaful.repositories.ISinistreRepository;
import com.soa.vie.takaful.requestmodels.CreateReglementRequest;
import com.soa.vie.takaful.responsemodels.ReglementResponseModel;
import com.soa.vie.takaful.services.IReglementService;
import com.soa.vie.takaful.util.Pagination;
import com.soa.vie.takaful.util.PrestationStatusEnum;
import com.soa.vie.takaful.util.ReglementStatut;

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
public class ReglementServiceImpl implements IReglementService {
	@Autowired
	private IReglementRepository reglementRepository;

	@Autowired
	private ISinistreRepository sinistreRepository;

	@Autowired
	private IPrestationHonoraireRepository honoraireRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	@Async("asyncExecutor")
	public ReglementResponseModel reglement(String idProduct, String type, CreateReglementRequest model)
			throws InterruptedException, ExecutionException {
		Thread.sleep(1000L);
		List<PrestationSinistre> sinistreOpt = sinistreRepository.findByProductIdAndStatusAndModeReglement(idProduct,
				type);
		if (sinistreOpt.isEmpty()) {

			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "y'a pas de prestation avec le statut A Signer");
		} else {
			log.info(sinistreOpt.toString());
			Reglement reglementadd = new Reglement();
			reglementadd.setStatut(ReglementStatut.EN_COURS);

			List<PrestationSinistre> prestations = new ArrayList<>();
			for (Prestation request : sinistreOpt) {
				request.setStatus(PrestationStatusEnum.ENCOURS_SIGNATURE);
				Optional<PrestationSinistre> prestationSinistreOptional = sinistreRepository.findById(request.getId());
				if (prestationSinistreOptional.isPresent()) {
					PrestationSinistre prestation = prestationSinistreOptional.get();
					prestations.add(prestation);
				} else {
					throw new NoSuchElementException("Aucune prestation trouve");
				}

			}
			reglementadd.setPrestations(prestations);
			BeanUtils.copyProperties(model, reglementadd);

			return modelMapper.map(reglementRepository.save(reglementadd), ReglementResponseModel.class);

		}
	}

	@Override
	@Async("asyncExecutor")
	public Page<ReglementResponseModel> getReglements(int page, int limit, String sort, String direction)
			throws InterruptedException, ExecutionException {
		Thread.sleep(1000L);
		return reglementRepository.findReglements(Pagination.pageableRequest(page, limit, sort, direction))
				.map(o -> modelMapper.map(o, ReglementResponseModel.class));
	}

	@Override
	@Async("asyncExecutor")
	public List<ReglementResponseModel> reglementPrestation(String id, String statut)
			throws InterruptedException, ExecutionException {
		Thread.sleep(1000L);
		System.out.println(id + "  " + statut);
		// List<String> reglementOpt =
		// reglementRepository.findPrestationByIdReglement(id);
		Optional<Reglement> reglementOptional = reglementRepository.findById(id);
		if (reglementOptional.isPresent()) {
			//Reglement reglement = reglementOptional.get();

			if ("EN_COURS".equals(statut)) {
				reglementRepository.updateStatutReglement("VALIDER", id);
				// reglementLoopMethod(reglementOpt);
			} else if ("Supprimer".equals(statut)) {
				// reglement.setStatut(ReglementStatut.SUPPRIMER);
				reglementRepository.updateStatutReglement("Supprimer", id);
				// reglementLoopMethod(reglementOpt);

			}
		} else {
			throw new NoSuchElementException();
		}
		return new ArrayList<>();
	}

	protected void reglementLoopMethod(List<String> reglementOpt) {
		for (String request : reglementOpt) {
			Optional<PrestationSinistre> prestationOptional = sinistreRepository.findById(request);
			if (prestationOptional.isPresent()) {
				Prestation prestation = prestationOptional.get();
				prestation.setStatus(PrestationStatusEnum.REGLE);
			} else {
				throw new NoSuchElementException();
			}
		}
	}

	@Override
	@Async("asyncExecutor")
	public ReglementResponseModel reglementHonoraire(String ModeReglement, String Auxiliaire, String type,
			CreateReglementRequest model) throws InterruptedException, ExecutionException {
		List<PrestationHonoraire> sinistreOpt;

		LocalDate currentDate = LocalDate.now();

		if (Auxiliaire.equals("null")) {
			sinistreOpt = honoraireRepository.findByStatusAndModeReglementHonoraire(type, ModeReglement);
		} else {
			sinistreOpt = honoraireRepository.findByStatusAndModeReglementHonoraireAndAuxiliaire(type, ModeReglement,
					Auxiliaire);
		}

		if (sinistreOpt.isEmpty()) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "y'a pas d'honoraire avec le statut A Signer");
		} else {
			log.info(sinistreOpt.toString());
			Reglement reglementadd = new Reglement();
			int currentYear = currentDate.getYear();
			int lastTwoDigits = currentYear % 100;
			Random random = new Random();
			int randomNumber = random.nextInt(90) + 10;

			String numero_lot = "A" + lastTwoDigits + String.format("%05d", randomNumber);
			reglementadd.setNum_lot(numero_lot);
			reglementadd.setStatut(ReglementStatut.EN_COURS);

			List<PrestationHonoraire> honoraires = new ArrayList<>();
			for (Prestation request : sinistreOpt) {
				request.setStatus(PrestationStatusEnum.ENCOURS_SIGNATURE);
				Optional<PrestationHonoraire> honoraireOptional = honoraireRepository.findById(request.getId());
				if (honoraireOptional.isPresent()) {
					PrestationHonoraire honoraire = honoraireOptional.get();
					honoraires.add(honoraire);
				} else {
					throw new NoSuchElementException();
				}

			}
			reglementadd.setHonoraires(honoraires);
			BeanUtils.copyProperties(model, reglementadd);

			return modelMapper.map(reglementRepository.save(reglementadd), ReglementResponseModel.class);

		}
	}

}
