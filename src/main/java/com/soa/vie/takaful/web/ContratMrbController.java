package com.soa.vie.takaful.web;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateContratMrb;
import com.soa.vie.takaful.requestmodels.CreateContratMrb;
import com.soa.vie.takaful.responsemodels.ContratMrbModelResponse;
import com.soa.vie.takaful.services.IContratMrbService;

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
public class ContratMrbController {
    
    @Autowired
    private IContratMrbService iContratMrbService;

    @PostMapping("contratMrb")
    public ContratMrbModelResponse createContratMrb(@RequestBody CreateAndUpdateContratMrb model) throws InterruptedException, ExecutionException{
    	return iContratMrbService.createContratMrb(model);
    }

    @PutMapping(path = "contratMrb/{id}")
    public ContratMrbModelResponse updateContratMrb(@PathVariable String id,@RequestBody CreateContratMrb model) throws InterruptedException, ExecutionException{
		return iContratMrbService.updateContratMrb(id,model);
    }

    
	@GetMapping(path = "contratMrbs")
	public Page<ContratMrbModelResponse> getContratMrb(@RequestParam("page") int page,
			@RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit,
			@RequestParam("sort") String sort, @RequestParam("direction") String direction) throws InterruptedException, ExecutionException {
		return iContratMrbService.getContratMrb(page, limit, sort, direction);

	}
	
	  @GetMapping("contratMrb/search")
	    public Page<ContratMrbModelResponse> getContratSearch(@RequestParam("page") final int page,
	            @RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") final int limit,
	           @RequestParam("by")  String by ,@RequestParam("for")  String sfor) throws InterruptedException, ExecutionException {
		  return iContratMrbService.searchContratMrb(page, limit, by, sfor);
	        }

}