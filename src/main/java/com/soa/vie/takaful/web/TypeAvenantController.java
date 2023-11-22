package com.soa.vie.takaful.web;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateTypeAvenant;
import com.soa.vie.takaful.responsemodels.TypeAvenantResponseModel;
import com.soa.vie.takaful.services.ITypeAvenantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/private")
public class TypeAvenantController {
	@Autowired
	private ITypeAvenantService typeAvenantService;


	@PostMapping("TypeAvenant/add")
	public TypeAvenantResponseModel createTypeAvenant(@RequestBody CreateTypeAvenant model) throws InterruptedException, ExecutionException {

		return typeAvenantService.createTypeAvenant(model);
    }
    
    @GetMapping(path = "TypeAvenant")
	public Page<TypeAvenantResponseModel> getAllTypeAvenants(@RequestParam("page") int page,
			@RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit,
			@RequestParam("sort") String sort, @RequestParam("direction") String direction) throws InterruptedException, ExecutionException {
		return typeAvenantService.getTypeAvenants(page, limit, sort, direction);
	}

	@GetMapping(path = "TypeAvenant/{id}")
	public String getCodeById(@PathVariable String id) throws InterruptedException, ExecutionException {
		return typeAvenantService.getCodeById(id);
	}
}