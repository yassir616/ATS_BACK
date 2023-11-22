package com.soa.vie.takaful.web;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateCommission;
import com.soa.vie.takaful.responsemodels.CommissionResponseModel;
import com.soa.vie.takaful.services.ICommissionService;

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
public class CommissionController {

	@Autowired
	private ICommissionService commissionService;

	@PostMapping("commission")
	public CommissionResponseModel createCommission(@RequestBody CreateAndUpdateCommission model) throws InterruptedException, ExecutionException {
		return commissionService.createCommission(model);
	}

	@PutMapping(path = "commission/{id}")
	public CommissionResponseModel updateCommission(@PathVariable String id, @RequestBody CreateAndUpdateCommission model) throws InterruptedException, ExecutionException {
		return commissionService.updateCommission(id, model);
	}

	@GetMapping(path = "commissions")
	public Page<CommissionResponseModel> getCommissions(@RequestParam("page") int page,
			@RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit,
			@RequestParam("sort") String sort, @RequestParam("direction") String direction) throws InterruptedException, ExecutionException {
		 return commissionService.getCommissions(page, limit, sort, direction);
	}
}
