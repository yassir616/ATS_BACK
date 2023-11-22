package com.soa.vie.takaful.web;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdatePersonnePhy;
import com.soa.vie.takaful.responsemodels.PersonnePhysiqueResponseModel;
import com.soa.vie.takaful.services.IPersonnePhysiqueService;

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
public class PersonnePhysiqueController {

    @Autowired
    private IPersonnePhysiqueService personnePhysiqueService;

    @PostMapping("personne-physique")
    public PersonnePhysiqueResponseModel createPersonnePhysique(@RequestBody CreateAndUpdatePersonnePhy p)
            throws InterruptedException, ExecutionException {
        return personnePhysiqueService.createPersonnePhysique(p);

    }

    @PutMapping("personne-physique/{id}")
    public PersonnePhysiqueResponseModel updatePersonnePhysique(@PathVariable String id,
            @RequestBody CreateAndUpdatePersonnePhy p) throws InterruptedException, ExecutionException {
        return personnePhysiqueService.updatePersonnePhysique(id, p);

    }

    @GetMapping("personne-physique")
    public Page<PersonnePhysiqueResponseModel> getAllPersonnePhysique(@RequestParam("page") int page,
            @RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit,
            @RequestParam("sort") String sort, @RequestParam("direction") String direction)
            throws InterruptedException, ExecutionException {

        return personnePhysiqueService.getPersonnePhysique(page, limit, sort, direction);
    }

    @GetMapping("personne-physique/{cin}")
    public PersonnePhysiqueResponseModel getPersonnePhysique(@PathVariable String cin)
            throws InterruptedException, ExecutionException {
        return this.personnePhysiqueService.getPersonnePhysique(cin);
    }

    @DeleteMapping("personne-physique/delete/{id}")
    public void deleteUser(@PathVariable String id) throws InterruptedException, ExecutionException {
        personnePhysiqueService.deleteParticipant(id);
    }

    @GetMapping("personnePhysique/{cin}")
    public void PersonnePhysique(@PathVariable String cin) throws InterruptedException, ExecutionException {
        personnePhysiqueService.personneExist(cin);
    }

    @GetMapping("personnePhysique/rib/{rib}")
    public void PersonnePhysiqueByRib(@PathVariable String rib) throws InterruptedException, ExecutionException {
        personnePhysiqueService.ribExist(rib);

    }

}