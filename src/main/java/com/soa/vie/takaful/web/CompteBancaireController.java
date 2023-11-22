package com.soa.vie.takaful.web;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.responsemodels.CompteBancaireResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.soa.vie.takaful.services.ICompteBancaireService;



@RestController
@RequestMapping("api/private")
public class CompteBancaireController {

	@Autowired
    private ICompteBancaireService compteService;

	@GetMapping(path = "compteBancaires")
	public Page<CompteBancaireResponseModel> compteBancaire (@RequestParam("page") int page,
            @RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit, @RequestParam("sort") String sort,@RequestParam("direction") String direction) throws InterruptedException, ExecutionException{
		 return compteService.getCompteBancaire(page, limit, sort, direction);

	}
	@GetMapping("compteBancaireId/{idCpt}")
    public CompteBancaireResponseModel getcompteBancaireId(@PathVariable String idCpt) throws InterruptedException, ExecutionException {
        return compteService.getCompteBancaireById(idCpt);

    }
}
