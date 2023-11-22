package com.soa.vie.takaful.web;

import com.soa.vie.takaful.requestmodels.CreateUpdateOneStringRequestModel;
import com.soa.vie.takaful.responsemodels.PoolInvestissmentResponseModel;
import com.soa.vie.takaful.services.IPoolInvetissmentService;

import org.bouncycastle.openssl.EncryptionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/private")
public class PoolInvestissmsntController {
    @Autowired
    IPoolInvetissmentService poolInvetissmentService;

    @PostMapping("poolinvestissment")
    public PoolInvestissmentResponseModel createPoolInvestissmsent(@RequestBody CreateUpdateOneStringRequestModel model) throws EncryptionException, InterruptedException{
        return poolInvetissmentService.createPoolInvestissment(model);
    }

    @PutMapping("poolinvestissment/{id}")
    public PoolInvestissmentResponseModel updatePoolInvestissment(@PathVariable String id, @RequestBody CreateUpdateOneStringRequestModel model) throws EncryptionException, InterruptedException{
        return poolInvetissmentService.updatePoolInvestissment(model, id);
    }

    @GetMapping("poolinvestissment")
    public Page<PoolInvestissmentResponseModel> getPoolInvestissments(@RequestParam("page") int page,
                                                                      @RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit,
                                                                      @RequestParam("sort") String sort, @RequestParam("direction") String direction) throws EncryptionException, InterruptedException{
        return poolInvetissmentService.getPoolInvestissments(page,limit,sort,direction);
    }
}
