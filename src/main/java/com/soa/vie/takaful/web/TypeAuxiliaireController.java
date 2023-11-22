package com.soa.vie.takaful.web;

import java.util.concurrent.ExecutionException;

import javax.websocket.EncodeException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateTypeAuxiliaire;
import com.soa.vie.takaful.responsemodels.TypeAuxiliaireModelResponse;
import com.soa.vie.takaful.services.ITypeAuxiliaireService;
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
public class TypeAuxiliaireController {
	@Autowired
	private ITypeAuxiliaireService typeAuxiliaireService;


	@PostMapping("Typeauxiliaire/add")
	public TypeAuxiliaireModelResponse createTypeAuxiliaire(@RequestBody CreateAndUpdateTypeAuxiliaire model) throws InterruptedException, ExecutionException {

		return typeAuxiliaireService.createTypeAuxiliaire(model);
	}

	@PutMapping(path = "Typeauxiliaire/{id}")
	public TypeAuxiliaireModelResponse updateTypeAuxiliaire(@PathVariable String id,
			@RequestBody CreateAndUpdateTypeAuxiliaire model) throws InterruptedException, ExecutionException {

		return typeAuxiliaireService.updateTypeAuxiliaire(id, model);
	}

	@GetMapping(path = "Typeauxiliaire")
	public Page<TypeAuxiliaireModelResponse> getAllTypeAuxiliaires(@RequestParam("page") int page,
			@RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit,
			@RequestParam("sort") String sort, @RequestParam("direction") String direction) throws InterruptedException, EncodeException {
		return typeAuxiliaireService.getTypeAuxiliaires(page, limit, sort, direction);

	}

}
