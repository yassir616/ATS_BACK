package com.soa.vie.takaful.web;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.soa.vie.takaful.responsemodels.PaysResponseModel;
import com.soa.vie.takaful.services.IPaysService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/private")
public class PaysController {

    @Autowired
    IPaysService paysService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("payss")
    public Page<PaysResponseModel> getPayss(@RequestParam("page") int page,
            @RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit,
            @RequestParam("sort") String sort, @RequestParam("direction") String direction)
            throws InterruptedException, ExecutionException {

        List<PaysResponseModel> list = paysService.getAllPayss(page, limit, sort, direction)
                .stream()
                .map(o -> modelMapper.map(o, PaysResponseModel.class))
                .collect(Collectors.toList());

        return new PageImpl<>(list);
    }
}
