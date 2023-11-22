package com.soa.vie.takaful.web;

import java.util.ArrayList;
import java.util.List;

import com.soa.vie.takaful.requestmodels.SimpleModelRequest;
import com.soa.vie.takaful.util.PrestationStatusEnum;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/private")
public class PrestationStatusController {
 

	@GetMapping("prestation-status")
	public List<SimpleModelRequest> getStatus() {
        List<SimpleModelRequest> statusList = new ArrayList<>();
        statusList.add(new SimpleModelRequest(PrestationStatusEnum.EN_COURS.getaction(),PrestationStatusEnum.EN_COURS.toString()));
        statusList.add(new SimpleModelRequest(PrestationStatusEnum.A_SIGNER.getaction(),PrestationStatusEnum.A_SIGNER.toString()));
        statusList.add(new SimpleModelRequest(PrestationStatusEnum.ENCOURS_SIGNATURE.getaction(),PrestationStatusEnum.ENCOURS_SIGNATURE.toString()));
        statusList.add(new SimpleModelRequest(PrestationStatusEnum.REGLE.getaction(),PrestationStatusEnum.REGLE.toString()));
        statusList.add(new SimpleModelRequest(PrestationStatusEnum.ANNULE.getaction(),PrestationStatusEnum.ANNULE.toString()));


		return statusList;
	}

}
