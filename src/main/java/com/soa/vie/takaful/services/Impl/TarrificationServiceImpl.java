package com.soa.vie.takaful.services.Impl;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.transaction.Transactional;

import com.soa.vie.takaful.entitymodels.DecesProduit;
import com.soa.vie.takaful.entitymodels.Tarrification;
import com.soa.vie.takaful.repositories.IDecesProduitRepository;
import com.soa.vie.takaful.repositories.ITarrificationRepository;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateTarrification;
import com.soa.vie.takaful.responsemodels.TarrificationResponseModel;
import com.soa.vie.takaful.services.ITarrificationService;
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
public class TarrificationServiceImpl implements ITarrificationService {

	@Autowired
	private ITarrificationRepository tarrificationRepository;

	@Autowired
	private IDecesProduitRepository decesProduitRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	@Async("asyncExecutor")
	public TarrificationResponseModel createTarrification(CreateAndUpdateTarrification model)
			throws InterruptedException, ExecutionException {
		log.info("creation de la tarrification ...");

		Thread.sleep(1000L);
		Tarrification tarrification = new Tarrification();
		BeanUtils.copyProperties(model, tarrification);
		return modelMapper.map(tarrificationRepository.save(tarrification), TarrificationResponseModel.class);

	}

	@Override
	@Async("asyncExecutor")
	public TarrificationResponseModel updateTarrification(CreateAndUpdateTarrification model, String tarrificationId)
			throws InterruptedException, ExecutionException {
		log.info("modifier la  Tarrification avec Id : {}", tarrificationId);

		Thread.sleep(1000L);
		Optional<Tarrification> tarrificationOpt = tarrificationRepository.findById(tarrificationId);
		if (tarrificationOpt.isPresent()) {
			Tarrification tarrification = tarrificationOpt.get();
			BeanUtils.copyProperties(model, tarrification);
			return modelMapper.map(tarrificationRepository.save(tarrification), TarrificationResponseModel.class);
		} else {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "No such tarrification");
		}
	}

	@Override
	@Async("asyncExecutor")
	public TarrificationResponseModel getTarrification(int duree, int dureem, int age, int agem, float capital,
			float capitalm, float differe, float differem, String typeProduit, String decesProduit)
			throws InterruptedException, ExecutionException {

		Thread.sleep(1000L);
		Optional<DecesProduit> decesProduit2 = this.decesProduitRepository.findById(decesProduit);

		if (typeProduit.equals("taux") && decesProduit2.isPresent()) {
			Optional<Tarrification> tarrificationEntity = tarrificationRepository
					.findByDureeMaxGreaterThanEqualAndDureeMinLessThanEqualAndAgeMaxGreaterThanEqualAndAndAgeMinLessThanEqualAndCapitalMaxGreaterThanEqualAndCapitalMinLessThanEqualAndDiffereMaxGreaterThanEqualAndDiffereMinLessThanEqualAndTauxIsNotNullAndDecesProduit(
							duree, dureem, age, agem, capital, capitalm, differe, differem, decesProduit2.get());
			if (tarrificationEntity.isPresent()) {
				return modelMapper.map(tarrificationEntity.get(), TarrificationResponseModel.class);
			}
		} else if (typeProduit.equals("forfait")) {

			Optional<Tarrification> tarrificationEntity = tarrificationRepository
					.findByDureeMaxGreaterThanEqualAndDureeMinLessThanEqualAndAgeMaxGreaterThanEqualAndAndAgeMinLessThanEqualAndCapitalMaxGreaterThanEqualAndCapitalMinLessThanEqualAndDiffereMaxGreaterThanEqualAndDiffereMinLessThanEqualAndForfaitIsNotNull(
							duree, dureem, age, agem, capital, capitalm, differe, differem);
			if (tarrificationEntity.isPresent()) {
				return modelMapper.map(tarrificationEntity.get(), TarrificationResponseModel.class);
			}
		}
		TarrificationResponseModel responseModel = new TarrificationResponseModel();
		responseModel.setFound(false);
		return responseModel;
	}

	@Override
	@Async("asyncExecutor")
	public Page<TarrificationResponseModel> getTarrifications(int page, int limit, String sort, String direction)
			throws InterruptedException, ExecutionException {
		log.info("Getting  tarrification causes");

		Thread.sleep(1000L);
		return tarrificationRepository.findAll(Pagination.pageableRequest(page, limit, sort, direction))
				.map(o -> modelMapper.map(o, TarrificationResponseModel.class));
	}

}