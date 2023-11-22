package com.soa.vie.takaful.web;



import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.soa.vie.takaful.entitymodels.EmissionGlobale;
import com.soa.vie.takaful.services.IEmissionGlobale;



@RestController
@RequestMapping("api/private")
public class EmissionGlobaleController {
	
	
	 @Autowired
	 private IEmissionGlobale emissionGlobale;


	 @GetMapping("/EmissionsGlobales")
	 public Page<EmissionGlobale> createDecesProduit(@RequestParam int pageNumber,@RequestParam int pageSize,@RequestParam String sortCol,@RequestParam Boolean direction) throws InterruptedException, ExecutionException {
	     return emissionGlobale.getAllEmissionGlobale(pageNumber,pageSize,sortCol,direction);
	  }
	 
	 @GetMapping("/EmissionGlobale/{numeroLot}")
	 public EmissionGlobale findByNumeroLot(@PathVariable String numeroLot) throws InterruptedException, ExecutionException{
		 return emissionGlobale.findByNumeroLot(numeroLot);
	 }
	 

}






   
