package com.soa.vie.takaful.web;

import com.soa.vie.takaful.responsemodels.TarificationMRBResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.entitymodels.ProduitMrb;
import com.soa.vie.takaful.services.ITarificationMrbService;

@RestController
@RequestMapping("api/private")
public class TarificationMrbController {
	@Autowired
	private ITarificationMrbService tarificationMrbService;

	@GetMapping("tarificationsMrb/{id}")
	public Page<TarificationMRBResponseModel> getAllTarificationsMrb(@PathVariable ProduitMrb id,
			@RequestParam("page") int page,
			@RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit)
			throws InterruptedException, ExecutionException {
		return tarificationMrbService.getTarificationMrbById(id, page, limit);

	}

	@GetMapping("tarrificationMrb")
	public TarificationMRBResponseModel getTarrification(
			//@RequestParam("valeur") float valeur,
			//@RequestParam String nature, 
			@RequestParam String produit) {
		return tarificationMrbService.getTarrification(produit);

	}
}
