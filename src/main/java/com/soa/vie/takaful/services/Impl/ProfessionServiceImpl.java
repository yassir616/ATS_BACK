package com.soa.vie.takaful.services.Impl;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.entitymodels.Profession;
import com.soa.vie.takaful.repositories.IProfessionRepository;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateProfession;
import com.soa.vie.takaful.responsemodels.ProfessionResponseModel;
import com.soa.vie.takaful.services.IProfessionService;
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
public class ProfessionServiceImpl implements IProfessionService {

    @Autowired
    private IProfessionRepository professionRepos;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Async("asyncExecutor")
    public Page<Profession> getAllProfessions(int page, int limit, String sort, String direction)
            throws InterruptedException, ExecutionException {
        Thread.sleep(1000L);
        return professionRepos.findAll(Pagination.pageableRequest(page, limit, sort, direction));
    }

    @Override
    @Async("asyncExecutor")
    public ProfessionResponseModel createProfession(CreateAndUpdateProfession profession)
            throws InterruptedException, ExecutionException {
        Thread.sleep(1000L);
        String libelle = profession.getLibelle();
        Profession professionToAdd = new Profession();
        Optional<Profession> professionExists = professionRepos.findByLibelle(libelle);
        if (professionExists.isPresent()) {

            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "cette profession existe déja");
        } else {
            BeanUtils.copyProperties(profession, professionToAdd);
            return modelMapper.map(this.professionRepos.save(professionToAdd), ProfessionResponseModel.class);

        }

    }

}
