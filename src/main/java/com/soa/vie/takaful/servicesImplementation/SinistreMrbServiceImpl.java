package com.soa.vie.takaful.servicesImplementation;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.soa.vie.takaful.entitymodels.ContratMrb;
import com.soa.vie.takaful.entitymodels.PrestationSinistreMrb;
import com.soa.vie.takaful.entitymodels.SinistreMrb;
import com.soa.vie.takaful.entitymodels.autoIncrementhelpers.CostumIdGeneratedValueSinistreMrbEntity;
import com.soa.vie.takaful.repositories.IContratMrb;
import com.soa.vie.takaful.repositories.ISinistreMrb;
import com.soa.vie.takaful.repositories.autoincrementhelpers.CostumIdGeneratedValueSinistreMrbEntityRepository;
import com.soa.vie.takaful.requestmodels.CreateSinistre;
import com.soa.vie.takaful.requestmodels.UpdateSinistreMrb;
import com.soa.vie.takaful.responsemodels.PrestationSinistreMrbModelResponse;
import com.soa.vie.takaful.services.ISinistreMrbService;
import com.soa.vie.takaful.util.PrestationStatusEnum;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class SinistreMrbServiceImpl implements ISinistreMrbService {
	@Autowired
	private ISinistreMrb sinistreRepository;

	@Autowired
	private IContratMrb contratRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CostumIdGeneratedValueSinistreMrbEntityRepository costumIdGeneratedValueSinistreMrbEntityRepository;

	@Override
	@Async("asyncExecutor")
	public PrestationSinistreMrbModelResponse createSinistre(CreateSinistre requestModel)
			throws InterruptedException, ExecutionException {
		log.info("creation du sinistre ...");

		Thread.sleep(1000L);
		PrestationSinistreMrb addSinistre = new PrestationSinistreMrb();

		CostumIdGeneratedValueSinistreMrbEntity autoDataBaseGeneratedIdSinistre = new CostumIdGeneratedValueSinistreMrbEntity();

		Optional<ContratMrb> contrat = contratRepository.findById(requestModel.getContratId());

		if (contrat.isPresent()) {
			addSinistre.setContratMrb(contrat.get());
			addSinistre.setNumeroSinistre(this.costumIdGeneratedValueSinistreMrbEntityRepository
					.save(autoDataBaseGeneratedIdSinistre).getId());
			addSinistre.setStatus(PrestationStatusEnum.EN_COURS);

			BeanUtils.copyProperties(requestModel, addSinistre);

			return modelMapper.map(sinistreRepository.save(addSinistre), PrestationSinistreMrbModelResponse.class);

		} else {
			log.error("l'insertion  du sinistre  est erroné  ");
			throw new NullPointerException("l'insertion  du sinistre est erroné");
		}
	}

	@Override
	@Async("asyncExecutor")
	public List<PrestationSinistreMrbModelResponse> getSinistrebyContrat(String contratId)
			throws InterruptedException, ExecutionException {

		Thread.sleep(1000L);
		Optional<ContratMrb> contrat = contratRepository.findById(contratId);
		System.out.println(contrat);
		return sinistreRepository.findByContratMrbId(contratId).stream()
				.map(o -> modelMapper.map(o, PrestationSinistreMrbModelResponse.class)).collect(Collectors.toList());

	}

	@Override
	@Async("asyncExecutor")
	public PrestationSinistreMrbModelResponse updateSinistre(UpdateSinistreMrb model, String id)
			throws InterruptedException, ExecutionException {
		log.info("modifier le sinistre avec Id : {}", id);

		Thread.sleep(1000L);
		Optional<PrestationSinistreMrb> sinistreOpt = sinistreRepository.findById(id);

		if (sinistreOpt.isPresent()) {
			PrestationSinistreMrb sinistre = sinistreOpt.get();
			BeanUtils.copyProperties(model, sinistre);
			return modelMapper.map(sinistreRepository.save(sinistre), PrestationSinistreMrbModelResponse.class);

		} else {
			log.error("la modification du sinistre avec ce Id ({}): est erronée", id);
			throw new NullPointerException("la modification du sinistre avec ce Id est erronée");
		}
	}

	@Override
	@Async("asyncExecutor")
	public void deleteSinistre(String id) {
		// To implement
	}

	@Override
	@Async("asyncExecutor")
	public Page<PrestationSinistreMrb> setStatut(String id, String statut, int page, int limit, String motif)
			throws InterruptedException, ExecutionException {
		Thread.sleep(1000L);
		return null;
	}

	@Override
	public SinistreMrb getDataFromSinistre(SinistreMrb sinistre) {
		SinistreMrb sinistreMrb = new SinistreMrb();
		BeanUtils.copyProperties(sinistre, sinistreMrb);
		return modelMapper.map(sinistre,
				SinistreMrb.class);
	}

}
