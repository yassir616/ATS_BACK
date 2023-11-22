package com.soa.vie.takaful.servicesImplementation;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.entitymodels.Pays;
import com.soa.vie.takaful.repositories.IPaysRepository;
import com.soa.vie.takaful.requestmodels.CreateAndUpdatePays;
import com.soa.vie.takaful.responsemodels.PaysResponseModel;
import com.soa.vie.takaful.services.IPaysService;
import com.soa.vie.takaful.util.Pagination;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class PaysServiceImpl implements IPaysService {

    @Autowired
    private IPaysRepository paysRepos;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Async("asyncExecutor")
    public Page<Pays> getAllPayss(int page, int limit, String sort, String direction)
            throws InterruptedException, ExecutionException {
        Thread.sleep(1000L);
        return paysRepos.findAll(Pagination.pageableRequest(page, limit, sort, direction));
    }
  
    @Override
    @Async("asyncExecutor")
    public PaysResponseModel createPays(CreateAndUpdatePays pays)
            throws InterruptedException, ExecutionException {
        Thread.sleep(1000L);
        String libelle = pays.getLibelle();
        Pays paysToAdd = new Pays();
        Optional<Pays> paysExists = paysRepos.findByLibelle(libelle);
        if (paysExists.isPresent()) {

            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "cette pays existe déja");
        } else {
            BeanUtils.copyProperties(pays, paysToAdd);
            return modelMapper.map(this.paysRepos.save(paysToAdd), PaysResponseModel.class);
        }

    }
}
