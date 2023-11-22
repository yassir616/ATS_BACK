package com.soa.vie.takaful.web;


import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdatePointVente;
import com.soa.vie.takaful.responsemodels.PointVenteResponseModel;
import com.soa.vie.takaful.services.IPointVenteService;

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
public class PoinVenteController {
	@Autowired
	private IPointVenteService pointVenteService;


	@PostMapping("pointvente")
	public PointVenteResponseModel createPointVente(@RequestBody CreateAndUpdatePointVente model) throws InterruptedException, ExecutionException {

		return this.pointVenteService.createPointVente(model);
	}

	@PutMapping(path = "pointvente/{id}")
	public PointVenteResponseModel updatePointVente(@PathVariable String id, @RequestBody CreateAndUpdatePointVente model) throws InterruptedException, ExecutionException {

		return pointVenteService.updatePointVente(model, id);

	}

	@GetMapping(path = "pointvente/{id}")
	public PointVenteResponseModel getPointVente(@PathVariable String id) throws InterruptedException, ExecutionException {
		return pointVenteService.getPointVenteById(id);

	}

	@GetMapping(path = "pointventebycode/{code}")
	public PointVenteResponseModel getPointVenteByCode(@PathVariable String code) throws InterruptedException, ExecutionException {
		return pointVenteService.getPointVenteByCodeInterne(code);

	}

	@GetMapping(path = "pointventes")
	public Page<PointVenteResponseModel> getAllPointVente(@RequestParam("page") int page,
			@RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit,
			@RequestParam("sort") String sort, @RequestParam("direction") String direction) throws InterruptedException, ExecutionException {


		return pointVenteService.getPointVentes(page, limit, sort, direction);
	}
	
	
}

