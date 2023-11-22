package com.soa.vie.takaful.web;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.responsemodels.AcceptationModelResponse;
import com.soa.vie.takaful.services.IAcceptationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/private")
public class AcceptationController {

	@Autowired
	private IAcceptationService acceptationService;

	@GetMapping("acceptation")
	public Page<AcceptationModelResponse> getAllAcceptations(@RequestParam("page") final int page,
			@RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") final int limit,
			@RequestParam("sort") final String sort, @RequestParam("direction") final String direction)
			throws InterruptedException, ExecutionException {
		return acceptationService.getAcceptations(page, limit, sort, direction);
	}

	@GetMapping("acceptation/search")
	public Page<AcceptationModelResponse> getAcceptationsSearch(@RequestParam("page") final int page,
			@RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") final int limit,
			@RequestParam("sort") final String sort, @RequestParam("direction") final String direction,
			@RequestParam("by") String by, @RequestParam("for") String sfor)
			throws InterruptedException, ExecutionException {
		return acceptationService.searchAcceptation(page, limit, sort, direction, by, sfor);
	}

	@GetMapping("acceptation/{contratId}")
	public String getCodeAcceptationByContratId(@PathVariable String contratId) {

		return acceptationService.getCodeByContratId(contratId);

	}

	@GetMapping("acceptationContrat/{contratId}")
	public AcceptationModelResponse getAcceptationByContratId(@PathVariable String contratId) {

		return acceptationService.getAcceptationByContratId(contratId);

	}
}