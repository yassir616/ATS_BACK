package com.soa.vie.takaful.web;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdatePartenaire;
import com.soa.vie.takaful.responsemodels.PartenaireResponseModel;
import com.soa.vie.takaful.services.IPartenaireService;

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
public class PartenaireController {

    @Autowired
    private IPartenaireService partenaireService;


    @PostMapping("partenaire")
    public PartenaireResponseModel createPartenaire(@RequestBody CreateAndUpdatePartenaire partenaire) throws InterruptedException, ExecutionException {
        return this.partenaireService.createPartenaire(partenaire);
    }

    @GetMapping("partenaire/{id}")
    public PartenaireResponseModel getPartenaire(@PathVariable String id) throws InterruptedException, ExecutionException {
        return partenaireService.getPartenaireById(id);

    }

    @GetMapping("partenaire/code/{code}")
    public PartenaireResponseModel getPartenaireByCode(@PathVariable String code) throws InterruptedException, ExecutionException {
        return partenaireService.getPartenaireByCode(code);

    }

    @PutMapping("partenaire/{id}")
    public PartenaireResponseModel updatePartenaire(@PathVariable String id,
            @RequestBody CreateAndUpdatePartenaire partenaireModel) throws InterruptedException, ExecutionException {

        return partenaireService.updatePartenaire(id, partenaireModel);
    }

    @GetMapping("partenaires")
    public Page<PartenaireResponseModel> getAllPartenaire(@RequestParam("page") int page,
            @RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit,
            @RequestParam("sort") String sort, @RequestParam("direction") String direction) throws InterruptedException, ExecutionException {

        return partenaireService.getPartenaires(page, limit, sort, direction);

    }

}