package com.soa.vie.takaful.web;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateOptionAssurance;
import com.soa.vie.takaful.responsemodels.OptionAssuranceModelResponse;
import com.soa.vie.takaful.services.IOptionAssuranceService;

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
public class OptionAssuranceController {
	@Autowired
	private IOptionAssuranceService optionAssuranceService;



	@PostMapping("optionassurance")
	public OptionAssuranceModelResponse createOptionAssurance(@RequestBody CreateAndUpdateOptionAssurance model) throws InterruptedException, ExecutionException {
		return optionAssuranceService.createOptionAssurance(model);
	}

	@PutMapping("optionassurance/{id}")
	public OptionAssuranceModelResponse updateOptionAssurance(@PathVariable String id,
			@RequestBody CreateAndUpdateOptionAssurance model) throws InterruptedException, ExecutionException {
		return optionAssuranceService.updateOptionAssurance(id,model);

	}

	@GetMapping("optionassurance/{id}")
	public OptionAssuranceModelResponse getOptionAssuranceById(@PathVariable String id) throws InterruptedException, ExecutionException {
		return optionAssuranceService.getOptionAssuranceById(id);
	}

	@GetMapping(path = "optionassurances")
	public Page<OptionAssuranceModelResponse> getAllOptionAssurance(@RequestParam("page") int page,
			@RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit,
			@RequestParam("sort") String sort, @RequestParam("direction") String direction) throws InterruptedException, ExecutionException {

		return optionAssuranceService.getOptionAssuarnces(page, limit, sort,direction);

	}

}
