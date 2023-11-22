package com.soa.vie.takaful.web;

import com.soa.vie.takaful.responsemodels.ExclusionResponseModel;
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

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateExclusion;
import com.soa.vie.takaful.services.IExclusionService;



@RestController
@RequestMapping("api/private")
public class ExclusionController {
	@Autowired
	private IExclusionService exclusionService;


	@PostMapping("exclusion/add")
	public ExclusionResponseModel createExclusion(@RequestBody CreateAndUpdateExclusion branche) throws InterruptedException, ExecutionException {
		return this.exclusionService.createExclusion(branche);
	}

	@PutMapping(path = "exclusion/{id}")
	public ExclusionResponseModel updateExclusion(@PathVariable String id, @RequestBody CreateAndUpdateExclusion brancheModel) throws InterruptedException, ExecutionException {
		return exclusionService.updateExclusion(id, brancheModel);
	}

	@GetMapping(path = "exclusions/{famille}")
	public Page<ExclusionResponseModel> getAllExclusion(@RequestParam("page") int page,
			@RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit,
			@RequestParam("sort") String sort, @RequestParam("direction") String direction, @PathVariable String famille) throws InterruptedException, ExecutionException{

		return exclusionService.getExclusions(page, limit, sort, direction,famille);

	}
	

}
