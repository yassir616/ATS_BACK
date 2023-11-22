package com.soa.vie.takaful.web;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdatePrestationHonoraireRequestModel;
import com.soa.vie.takaful.responsemodels.PrestationHonoraireResponseModel;
import com.soa.vie.takaful.services.IPrestationHonoraireService;

import org.springframework.beans.factory.annotation.Autowired;
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
public class PrestationHonoraireController {

    @Autowired
    private IPrestationHonoraireService prestationHonoraireService;

    @PostMapping("prestation-honoraire")
    public PrestationHonoraireResponseModel createPrestationHonoraire(
            @RequestBody CreateAndUpdatePrestationHonoraireRequestModel model)
            throws InterruptedException, ExecutionException {

        return this.prestationHonoraireService.createPrestationHonoraire(model);

    }

    @PutMapping("updatestatus/{id}")
    public PrestationHonoraireResponseModel updatestatus(@PathVariable String id,
            @RequestBody String p) throws InterruptedException, ExecutionException {
        return this.prestationHonoraireService.updateStatusPrestation(id, p);
}

@PutMapping("updateMontant")
public void updatemontant(
    @RequestParam String idDetail, @RequestParam String numeroSinistre,
    @RequestParam Float montantHonoraire, @RequestParam String typeFiscal) throws InterruptedException, ExecutionException {
     this.prestationHonoraireService.updateMontantPrestation(idDetail,numeroSinistre,montantHonoraire,typeFiscal);
}
    @GetMapping("prestation-honoraire-auxiliare")
    public List<PrestationHonoraireResponseModel> getPaidPrestationHonoraire(
            @RequestParam(name = "type-auxiliaire") String typeAuxiliaire, @RequestParam String status,
            @RequestParam String produit, @RequestParam String auxiliaire, @RequestParam String contrat,
            @RequestParam String participant) throws InterruptedException, ExecutionException {
        return this.prestationHonoraireService.getPrestationHonoraire(status, produit, auxiliaire, contrat, participant,
                typeAuxiliaire);
    }

}