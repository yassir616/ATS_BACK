package com.soa.vie.takaful.web;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdatePersonneMor;
import com.soa.vie.takaful.responsemodels.PersonneMoraleResponseModel;
import com.soa.vie.takaful.services.IPersonneMoraleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class PersonneMoraleController {

    @Autowired
    private IPersonneMoraleService personneMoraleService;


    @PostMapping("personne-morale")
    public PersonneMoraleResponseModel createPersonneMorale(@RequestBody CreateAndUpdatePersonneMor p) throws InterruptedException, ExecutionException {
        return personneMoraleService.createPersonneMorale(p);
    }

    @PutMapping("personne-morale/{id}")
    public PersonneMoraleResponseModel updatePersonneMorale(@PathVariable String id, @RequestBody CreateAndUpdatePersonneMor p) throws InterruptedException, ExecutionException {
        return personneMoraleService.updatePersonneMorale(id, p);

    }

    @GetMapping("personne-morale")
    public Page<PersonneMoraleResponseModel> getAllPersonneMorale(@RequestParam("page") int page,
            @RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit,
            @RequestParam("sort") String sort, @RequestParam("direction") String direction) throws InterruptedException, ExecutionException {


        return personneMoraleService.getPersonneMorale(page, limit, sort, direction);
    }

    @GetMapping("personne-morale/{patente}")
    public PersonneMoraleResponseModel getPersonneMorale(@PathVariable String patente) throws InterruptedException, ExecutionException {
        return personneMoraleService.getPersonneMorale(patente);

    }

    @DeleteMapping("personne-morale/delete/{id}")
    public void deleteUser(@PathVariable String id) throws InterruptedException, ExecutionException {
        personneMoraleService.deleteParticipant(id);
    }

}