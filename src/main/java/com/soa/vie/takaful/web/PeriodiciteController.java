package com.soa.vie.takaful.web;

import java.util.concurrent.ExecutionException;

import javax.ejb.EJBException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdatePeriodicite;
import com.soa.vie.takaful.responsemodels.PeriodiciteResponseModel;
import com.soa.vie.takaful.services.IPeriodiciteService;

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
public class PeriodiciteController {

    @Autowired
    private IPeriodiciteService periodiciteService;

    @PostMapping("periodicite")
    public PeriodiciteResponseModel createPeriodicite(@RequestBody CreateAndUpdatePeriodicite model)
            throws InterruptedException, ExecutionException {
        return periodiciteService.createPeriodicite(model);
    }

    @PutMapping("periodicite/{id}")
    public PeriodiciteResponseModel updatePeriodicite(@PathVariable String id,
            @RequestBody CreateAndUpdatePeriodicite model) throws InterruptedException, ExecutionException {
        return periodiciteService.updatePeriodicite(id, model);
    }

    @GetMapping("periodicite")
    public Page<PeriodiciteResponseModel> getAllPeriodicite(@RequestParam("page") int page,
            @RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit,
            @RequestParam("sort") String sort, @RequestParam("direction") String direction)
            throws InterruptedException, ExecutionException {
        return periodiciteService.getPeriodicites(page, limit, sort, direction);
    }

    @GetMapping("periodiciteByAbb")
    public PeriodiciteResponseModel getperiodiciteByAbb(@RequestParam("abreviation") String abreviation)
            throws InterruptedException, EJBException, ExecutionException {
        return periodiciteService.getPeriodiciteByAbb(abreviation);

    }

}
