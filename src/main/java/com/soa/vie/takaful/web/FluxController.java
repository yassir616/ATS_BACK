package com.soa.vie.takaful.web;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateFlux;
import com.soa.vie.takaful.requestmodels.SearchFlux;
import com.soa.vie.takaful.responsemodels.FluxResponseModel;
import com.soa.vie.takaful.services.IFluxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("api/private")
public class FluxController {

    @Autowired
    IFluxService iFluxService;

    @PostMapping("flux")
    public List<FluxResponseModel> searchFlux(@RequestBody SearchFlux searchFlux) throws InterruptedException, ExecutionException{
        return iFluxService.searchFichiersRecus(searchFlux);
    }

    @PostMapping("addflux")
    public FluxResponseModel addFlux(@RequestBody CreateAndUpdateFlux createAndUpdateFlux) throws InterruptedException, ExecutionException{
        return iFluxService.addFlux(createAndUpdateFlux);
    }
    @GetMapping("downloadFlux/{title}")
    public ResponseEntity<Resource> download(@PathVariable String title) throws IOException{

        File file = new File("output/" + title);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
    @GetMapping("listFluxFiles")
    public List<String> listFluxFiles(){
        File f = new File("output");
        ArrayList<String> names = new ArrayList<String>(Arrays.asList(f.list()));
        return names;
    }

}
