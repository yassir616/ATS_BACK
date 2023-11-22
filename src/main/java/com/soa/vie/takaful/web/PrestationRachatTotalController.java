package com.soa.vie.takaful.web;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdatePrestationRachatTotal;
import com.soa.vie.takaful.responsemodels.PrestationRachatTotalModelResponse;
import com.soa.vie.takaful.services.IPrestationRachatTotalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/private")
public class PrestationRachatTotalController {

    @Autowired
    private IPrestationRachatTotalService prestationRachatTotalService;


    @PostMapping("addPrestationRachatTotal")
    public PrestationRachatTotalModelResponse createPrestationRachatTotal(@RequestBody CreateAndUpdatePrestationRachatTotal requestModel) throws InterruptedException, ExecutionException {
        return prestationRachatTotalService.createPrestationRachatTotal(requestModel);
    }

    @GetMapping("getPrestationsRachatTotal/{contratId}")
    public List<PrestationRachatTotalModelResponse> getPrestationsRachatTotal(@PathVariable String contratId) throws InterruptedException, ExecutionException {

        return prestationRachatTotalService.getPrestationsRachatTotalbyContrat(contratId);
    }

    @PutMapping("updatePrestationRachatTotal/{id}")
    public PrestationRachatTotalModelResponse updatePrestationRachatTotal(@RequestBody CreateAndUpdatePrestationRachatTotal model, @PathVariable String id) throws InterruptedException, ExecutionException {
        return prestationRachatTotalService.updatePrestationRachatTotal(model, id);
    }

    @DeleteMapping("prestationRachatTotal/{id}")
    public void deleteSinistre(@PathVariable String id) throws InterruptedException, ExecutionException {
        prestationRachatTotalService.deletePrestationRachatTotal(id);
    }
}