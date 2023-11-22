package com.soa.vie.takaful.web;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.responsemodels.FamilleProduitResponseModel;
import com.soa.vie.takaful.services.IFamilleProduitService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/private")
public class FamilleProduitController {
    
    @Autowired
	private IFamilleProduitService familleService;
  
    @GetMapping("familleProduit/{name}")
    public String getfamilles(@PathVariable String name) throws InterruptedException, ExecutionException {
        return familleService.getIdFamilleProduct(name);

    }
    @GetMapping(path = "familleproduit")
	public Page<FamilleProduitResponseModel> getAllfamille(@RequestParam("page") int page,
			@RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit,
			@RequestParam("sort") String sort, @RequestParam("direction") String direction) throws InterruptedException, ExecutionException{

		return familleService.getFamilleProduits(page, limit, sort, direction);

	}
}
