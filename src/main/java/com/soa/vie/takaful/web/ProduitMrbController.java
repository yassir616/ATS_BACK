package com.soa.vie.takaful.web;

import com.soa.vie.takaful.responsemodels.ProduitMrbModelResponse;
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

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateProduitMRB;
import com.soa.vie.takaful.requestmodels.UpdateProductMRB;
import com.soa.vie.takaful.services.IProduitMrbService;



@RestController
@RequestMapping("api/private")
public class ProduitMrbController {
	  @Autowired
	    private IProduitMrbService produitMrbService;



	    @PostMapping("addProductMrb")
	    public ProduitMrbModelResponse createProduct(@RequestBody CreateAndUpdateProduitMRB requestModel) throws InterruptedException, ExecutionException {
			return produitMrbService.createProduitMrb(requestModel);

	    }
	    
	    @PutMapping("updateProductMrb/{id}")
	    public ProduitMrbModelResponse updateProduct(@PathVariable String id, @RequestBody UpdateProductMRB requestModel) throws InterruptedException, ExecutionException {
	        return produitMrbService.updateProduitMrb(id, requestModel);
	    }
	    
	    @GetMapping("getAllProductMrb")
	    public Page<ProduitMrbModelResponse> getProducts(@RequestParam("page") int page,
	            @RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit,
	            @RequestParam("sort") String sort, @RequestParam("direction") String direction) throws InterruptedException, ExecutionException {

			return produitMrbService.getAllProductMrb(page, limit, sort,direction);

	    }
	    

}
