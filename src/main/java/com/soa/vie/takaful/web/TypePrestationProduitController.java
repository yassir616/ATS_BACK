package com.soa.vie.takaful.web;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateTypePrestationProduit;
import com.soa.vie.takaful.responsemodels.TypePrestationProduitModelResponse;
import com.soa.vie.takaful.services.ITypePrestationProduitService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/private")
public class TypePrestationProduitController {

	@Autowired
	private ITypePrestationProduitService produitPrestationService;


	@PostMapping("typeprestationproduit")
	public TypePrestationProduitModelResponse createTypePrestationProduit(@RequestBody CreateAndUpdateTypePrestationProduit model) throws InterruptedException, ExecutionException {
		return produitPrestationService.createTypePrestationProduit(model);
	}

	@PutMapping("typeprestationproduit/{id}")
	public TypePrestationProduitModelResponse updateTypePrestationProduit(@PathVariable String id,
			@RequestBody CreateAndUpdateTypePrestationProduit model) throws InterruptedException, ExecutionException {
		return produitPrestationService.updateTypePrestationProduit(id,model);
	}

	@GetMapping("typeprestationproduit")
	public Page<TypePrestationProduitModelResponse> getAllTypePrestationProduit(@RequestParam("page") int page,
			@RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit,
			@RequestParam("sort") String sort, @RequestParam("direction") String direction) throws InterruptedException, ExecutionException {
		return produitPrestationService.getTypePrestationProduits(page,limit,sort,direction);
	}

}
