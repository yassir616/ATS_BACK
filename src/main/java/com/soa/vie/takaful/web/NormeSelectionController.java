package com.soa.vie.takaful.web;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateNormeSelection;
import com.soa.vie.takaful.requestmodels.UpdateNormeSelection;
import com.soa.vie.takaful.responsemodels.NormeSelectionResponseModel;
import com.soa.vie.takaful.services.INormeSelectionService;

import org.springframework.beans.factory.annotation.Autowired;
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
public class NormeSelectionController {
	@Autowired
	private INormeSelectionService normeService;

	@PostMapping("normeSelection/add")
	public NormeSelectionResponseModel createNormeSelection(@RequestBody CreateAndUpdateNormeSelection norme) throws InterruptedException, ExecutionException {
		return this.normeService.createNormeSelection(norme);
	}

	@GetMapping("normeSelection")
	public List<NormeSelectionResponseModel> getAuxiliairesByType(@RequestParam("id") String id) throws InterruptedException, ExecutionException {

		return normeService.getNormeById(id);

	}

	@PutMapping("normeSelection/{id}")
	public NormeSelectionResponseModel updateNorme(@PathVariable String id, @RequestBody UpdateNormeSelection model) throws InterruptedException, ExecutionException {
		return normeService.updateNormeSelection(id, model);
	}

	@GetMapping("normeSelection/select")
	public NormeSelectionResponseModel getNormes(@RequestParam("age") int age, @RequestParam("capitale") float capitale,
			@RequestParam String deceProduit) throws InterruptedException, ExecutionException {

		return normeService.getNorme(age, age, capitale, capitale, deceProduit);
	}
}