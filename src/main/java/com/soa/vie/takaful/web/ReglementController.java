package com.soa.vie.takaful.web;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.responsemodels.ReglementResponseDTO;
import com.soa.vie.takaful.responsemodels.ReglementResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.soa.vie.takaful.requestmodels.CreateReglementRequest;
import com.soa.vie.takaful.services.IReglementService;

@RestController
@RequestMapping("api/private")
public class ReglementController {
	@Autowired
	private IReglementService reglementService;

	@PostMapping("sinistre/{id}/{type}")
	public ReglementResponseModel getPrestationSinistre(@RequestBody CreateReglementRequest model,
			@PathVariable String id, @PathVariable String type) throws InterruptedException, ExecutionException {
		return reglementService.reglement(id, type, model);

	}

	@GetMapping("reglements")
	public Page<ReglementResponseDTO> getAllReglements(@RequestParam("page") int page,
													   @RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit,
													   @RequestParam("sort") String sort, @RequestParam("direction") String direction)
			throws InterruptedException, ExecutionException {
		return reglementService.getReglements(page, limit, sort, direction);
	}

	@GetMapping("reglement/{id}/{statut}")
	public List<ReglementResponseModel> getAllPrestations(@PathVariable String id, @PathVariable String statut)
			throws InterruptedException, ExecutionException {

		return reglementService.modifierReglementPrestationByIdAndStatut(id, statut);

	}

	@PostMapping("reglementHonoraire/{ModeReglement}/{Auxiliaire}/{statut}")
	public ReglementResponseModel getAllPrestationsHonoraire(@PathVariable String ModeReglement,
			@PathVariable String Auxiliaire,
			@PathVariable String statut,
			@RequestBody CreateReglementRequest model) throws InterruptedException, ExecutionException {
		return reglementService.reglementHonoraire(ModeReglement, Auxiliaire, statut, model);

	}

	// @DeleteMapping("reglement/delete/{id}")
	// public void deleteReglement(@PathVariable String id) {
	// reglementService.deleteReglement(id);
	// }
}
