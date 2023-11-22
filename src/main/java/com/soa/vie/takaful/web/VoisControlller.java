package com.soa.vie.takaful.web;

import java.util.ArrayList;
import java.util.List;

import com.soa.vie.takaful.requestmodels.SimpleModelRequest;
import com.soa.vie.takaful.util.VoisEnum;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/private")
public class VoisControlller {

    @GetMapping("vois")
    public List<SimpleModelRequest> getVois() {

        List<SimpleModelRequest> voisList = new ArrayList<>();
        voisList.add(new SimpleModelRequest(VoisEnum.HAY.getaction(), VoisEnum.HAY.toString()));
        voisList.add(new SimpleModelRequest(VoisEnum.QUARTIER.getaction(), VoisEnum.QUARTIER.toString()));
        voisList.add(new SimpleModelRequest(VoisEnum.RUE.getaction(), VoisEnum.RUE.toString()));

        return voisList;
    }

}