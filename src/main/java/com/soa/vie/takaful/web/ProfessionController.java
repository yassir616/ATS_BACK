package com.soa.vie.takaful.web;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateProfession;
import com.soa.vie.takaful.responsemodels.ProfessionResponseModel;
import com.soa.vie.takaful.services.IProfessionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/private")
public class ProfessionController {

	@Autowired
	IProfessionService professionService;
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping("/profession")
	public Page<ProfessionResponseModel> getProfessions(@RequestParam("page") int page,
			@RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit,
			@RequestParam("sort") String sort, @RequestParam("direction") String direction)
			throws InterruptedException, ExecutionException {

		List<ProfessionResponseModel> list = professionService.getAllProfessions(page, limit, sort, direction)
				.stream()
				.map(o -> modelMapper.map(o, ProfessionResponseModel.class))
				.collect(Collectors.toList());

		return new PageImpl<>(list);
	}

	@PostMapping("/addProfession")
	public ProfessionResponseModel createProduct(@RequestBody CreateAndUpdateProfession requestModel)
			throws InterruptedException, ExecutionException {
		return professionService.createProfession(requestModel);

	}
}
