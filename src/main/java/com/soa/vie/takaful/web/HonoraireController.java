package com.soa.vie.takaful.web;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateHonoraire;
import com.soa.vie.takaful.responsemodels.HonoraireModelResponse;
import com.soa.vie.takaful.services.IHonoraireService;

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
public class HonoraireController {
	@Autowired
	private IHonoraireService honoraireService;



	@PostMapping("honoraire/add")
	public HonoraireModelResponse createHonoraire(@RequestBody CreateAndUpdateHonoraire model) throws InterruptedException, ExecutionException {
		return honoraireService.createHonoraire(model);
	}

	@PutMapping("honoraire/{id}")
	public HonoraireModelResponse updateHonoraire(@PathVariable String id, @RequestBody CreateAndUpdateHonoraire model) throws InterruptedException, ExecutionException {
		return honoraireService.updateHonoraire(id,model);
	}

	@GetMapping("honoraires")
	public Page<HonoraireModelResponse> getAllHonoraires(@RequestParam("page") int page,
			@RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit,
			@RequestParam("sort") String sort, @RequestParam("direction") String direction) throws InterruptedException, ExecutionException {

		return honoraireService.getHonoraires(page, limit, sort,direction);

	}

}
