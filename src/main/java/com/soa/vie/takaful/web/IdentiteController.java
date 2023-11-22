package com.soa.vie.takaful.web;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.soa.vie.takaful.responsemodels.IdentiteResponseModel;
import com.soa.vie.takaful.services.IIdentiteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/private")
public class IdentiteController {

	@Autowired
	IIdentiteService IdentiteService;
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping("/identite")
	public Page<IdentiteResponseModel> getIdentites(@RequestParam("page") int page,
			@RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit,
			@RequestParam("sort") String sort, @RequestParam("direction") String direction)
			throws InterruptedException, ExecutionException {

		List<IdentiteResponseModel> list = IdentiteService.getAllIdentites(page, limit, sort, direction)
				.stream()
				.map(o -> modelMapper.map(o, IdentiteResponseModel.class))
				.collect(Collectors.toList());

		return new PageImpl<>(list);
	}

	
 
}
