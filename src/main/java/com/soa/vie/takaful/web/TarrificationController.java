package com.soa.vie.takaful.web;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateTarrification;
import com.soa.vie.takaful.responsemodels.TarrificationResponseModel;
import com.soa.vie.takaful.services.ITarrificationService;
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
public class TarrificationController {
    @Autowired
    private ITarrificationService tarrificationService;

    @PostMapping("tarrification")
    public TarrificationResponseModel createTarrification(@RequestBody CreateAndUpdateTarrification model)
            throws InterruptedException, ExecutionException {
        return tarrificationService.createTarrification(model);
    }

    @PutMapping("tarrification/{id}")
    public TarrificationResponseModel updateTarrification(@PathVariable String id,
            @RequestBody CreateAndUpdateTarrification model) throws InterruptedException, ExecutionException {
        return tarrificationService.updateTarrification(model, id);
    }

    @GetMapping("tarrification")
    public TarrificationResponseModel getTarrification(@RequestParam("age") int age,
            @RequestParam(name = "duree") int duree,
            @RequestParam("differe") float differe,
            @RequestParam("capitale") float capitale,
            @RequestParam("type") String typeProduit,
            @RequestParam String deceProduit) throws InterruptedException, ExecutionException {
        return tarrificationService.getTarrification(duree, duree, age, age, capitale, capitale,
                differe, differe, typeProduit, deceProduit);
    }

    @GetMapping(path = "tarrifications")
    public Page<TarrificationResponseModel> getAlltarrification(@RequestParam("page") int page,
            @RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit,
            @RequestParam("sort") String sort, @RequestParam("direction") String direction)
            throws InterruptedException, ExecutionException {
        return tarrificationService.getTarrifications(page, limit, sort, direction);
    }

}
