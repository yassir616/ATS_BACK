package com.soa.vie.takaful.servicesImplementation;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.entitymodels.ProduitMrb;
import com.soa.vie.takaful.entitymodels.TarificationMRB;
import com.soa.vie.takaful.repositories.IProduitMrbRepository;
import com.soa.vie.takaful.repositories.ITarificationMRB;
import com.soa.vie.takaful.responsemodels.TarificationMRBResponseModel;
import com.soa.vie.takaful.services.ITarificationMrbService;

import org.modelmapper.ModelMapper;
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
@Slf4j
public class TarificationServiceImpl implements ITarificationMrbService {
	@Autowired
	private ITarificationMRB tarificationMrbRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private IProduitMrbRepository mrbProduitRepository;

	@Override
	@Async("asyncExecutor")
	public Page<TarificationMRBResponseModel> getTarificationMrbById(ProduitMrb id, int page, int limit)
			throws InterruptedException, ExecutionException {
		Thread.sleep(1000L);
		log.info("lister les contrats décès acceptée");
		Pageable pageableRequest = PageRequest.of(page, limit);
		return tarificationMrbRepository.findByProduitMrb(id, pageableRequest)
				.map(o -> modelMapper.map(o, TarificationMRBResponseModel.class));

	}

	@Override
	public TarificationMRBResponseModel getTarrification(
			String produit) {
		Optional<ProduitMrb> decesProduit = this.mrbProduitRepository.findById(produit);
		if (decesProduit.isPresent()) {
			/* Optional<TarificationMRB> tarrificationMrbEntity = tarificationMrbRepository
					.findByValeurMaxGreaterThanEqualAndValeurMinLessThanEqualAndNatureBienAssureAndProduitMrb(valeurMax,
							valeurMin,
							nature, decesProduit.get()); */
			Optional<TarificationMRB> tarrificationMrbEntity = tarificationMrbRepository
					.findByProduitMrb(
							 decesProduit.get());
			if (tarrificationMrbEntity.isPresent()) {

				return modelMapper.map(tarrificationMrbEntity.get(), TarificationMRBResponseModel.class);

			}
		}

		throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "aucune tarification");

	}
}
