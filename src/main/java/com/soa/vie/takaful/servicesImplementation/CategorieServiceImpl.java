package com.soa.vie.takaful.servicesImplementation;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.entitymodels.Branche;
import com.soa.vie.takaful.entitymodels.Categorie;
import com.soa.vie.takaful.repositories.IBrancheRepository;
import com.soa.vie.takaful.repositories.ICategorieRepository;
import com.soa.vie.takaful.requestmodels.CreateUpdateCatAndSubCatRequestModel;
import com.soa.vie.takaful.responsemodels.CategorieModelResponse;
import com.soa.vie.takaful.services.ICategorieService;
import com.soa.vie.takaful.util.Pagination;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategorieServiceImpl implements ICategorieService {


    @Autowired
    private ICategorieRepository categorieRepository;
    
    @Autowired
    private IBrancheRepository branchrepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Async("asyncExecutor")
    public CategorieModelResponse createCategorie(CreateUpdateCatAndSubCatRequestModel requestModel)throws InterruptedException, ExecutionException {
          
        log.info("creating Categorie ...");
        Thread.sleep(1000L);
        Categorie categorie = new Categorie();

        if (!"".equals(requestModel.getCode()) && !"".equals(requestModel.getLibelle()) && !"".equals(requestModel.getBrancheId())) {

            Optional<Branche> branch = branchrepository.findById(requestModel.getBrancheId());

            if(branch.isPresent()){
                categorie.setBranche(branch.get());
                BeanUtils.copyProperties(requestModel, categorie);
                return modelMapper.map(categorieRepository.save(categorie),CategorieModelResponse.class);
            }else{
                log.error("Error creating categorie ,branch with id {} not found",requestModel.getBrancheId());
                throw new NoSuchElementException("Error creating categorie ,wrong branch id");
            }
        } else {
            log.error("Error creating categorie you need to insert all required values");
            throw new IllegalArgumentException("Error creating categorie you need to insert all required values");
        }
    }
    
    @Override
    @Async("asyncExecutor")
    public CategorieModelResponse updateCategorie(String id, CreateUpdateCatAndSubCatRequestModel requestModel)throws InterruptedException, ExecutionException {
       
        log.info("updating Categorie with Id : {}", id);
        Thread.sleep(1000L);
        if (!"".equals(requestModel.getCode()) && !"".equals(requestModel.getLibelle()) && !"".equals(requestModel.getBrancheId())) {

            Optional<Categorie> catOpt = categorieRepository.findById(id);
            Optional<Branche> branch = branchrepository.findById(requestModel.getBrancheId());

            if (catOpt.isPresent() && branch.isPresent()) {
                Categorie categorie = catOpt.get();
                categorie.setBranche(branch.get());
                BeanUtils.copyProperties(requestModel, categorie);
                return modelMapper.map(categorieRepository.save(categorie),CategorieModelResponse.class);

            } else {
                log.error("Error updating categorie with Id ({}): please check the given categorie and branch",
                id);
                throw new NoSuchElementException("Error updating categorie, please check the given categorie and branch");
            }
        } else {
            log.error("Error updating categorie with Id ({}): please insert required values", id);
            throw new NoSuchElementException("Error updating categorie, please insert required values");
        }
    }

    @Override
    @Async("asyncExecutor")
    public Page<CategorieModelResponse> getCategories(int page, int limit, String sort, String direction) throws InterruptedException, ExecutionException{
        log.info("Getting categorie");
        
        Thread.sleep(1000L);
        return categorieRepository.findAll(Pagination.pageableRequest(page, limit, sort, direction))
                .map(o -> modelMapper.map(o,CategorieModelResponse.class));

    }
}