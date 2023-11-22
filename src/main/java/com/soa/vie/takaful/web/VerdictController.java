package com.soa.vie.takaful.web;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.responsemodels.VerdictModelResponse;
import com.soa.vie.takaful.services.IVerdictService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/private")
public class VerdictController {

	
	@Autowired
    private IVerdictService verdictService;

	@GetMapping("verdict")
	public List<VerdictModelResponse> getVerdictsByType(@RequestParam("type") String type) throws InterruptedException, ExecutionException {
		return   verdictService.getVerdicts(type);
	    }

}



