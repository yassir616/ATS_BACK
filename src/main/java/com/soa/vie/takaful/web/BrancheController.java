package com.soa.vie.takaful.web;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateBranche;
import com.soa.vie.takaful.responsemodels.BrancheModelResponse;
import com.soa.vie.takaful.services.IBrancheService;

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
public class BrancheController {

    @Autowired
    private IBrancheService brancheService;


    @PostMapping("branche/add")
    public BrancheModelResponse createBranche(@RequestBody CreateAndUpdateBranche branche) throws InterruptedException, ExecutionException {
        return brancheService.createBranche(branche);
    }

    @GetMapping(path = "branche/{id}")
    public BrancheModelResponse getBranche(@PathVariable String id) throws InterruptedException, ExecutionException {
        return brancheService.getBrancheById(id);

    }

    @PutMapping(path = "branche/{id}")
    public BrancheModelResponse updateBranche(@PathVariable String id, @RequestBody CreateAndUpdateBranche brancheModel) throws InterruptedException, ExecutionException {

        return brancheService.updateBranche(id,brancheModel);
    }

    @GetMapping(path = "branche")
    public Page<BrancheModelResponse> getAllBranche(@RequestParam("page") int page,
            @RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit,
            @RequestParam("sort") String sort, @RequestParam("direction") String direction) throws InterruptedException, ExecutionException {
        return brancheService.getBranches(page, limit, sort, direction);

    }

}