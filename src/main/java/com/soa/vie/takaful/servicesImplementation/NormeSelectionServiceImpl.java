package com.soa.vie.takaful.servicesImplementation;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import com.soa.vie.takaful.entitymodels.DecesProduit;
import com.soa.vie.takaful.entitymodels.Honoraire;
import com.soa.vie.takaful.entitymodels.NormeSelection;
import com.soa.vie.takaful.repositories.IDecesProduitRepository;
import com.soa.vie.takaful.repositories.IHonoraireRepository;
import com.soa.vie.takaful.repositories.INormeSelectionRepository;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateNormeSelection;
import com.soa.vie.takaful.requestmodels.UpdateNormeSelection;

import com.soa.vie.takaful.responsemodels.NormeSelectionResponseModel;
import com.soa.vie.takaful.services.INormeSelectionService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.HttpClientErrorException;

@Service
@Transactional
@Slf4j
public class NormeSelectionServiceImpl implements INormeSelectionService {

    @Autowired
    private IHonoraireRepository honoraireRepository;

    @Autowired
    private INormeSelectionRepository normeSelectionRepository;

    @Autowired
    private IDecesProduitRepository decesProduitRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Async("asyncExecutor")
    public NormeSelectionResponseModel createNormeSelection(CreateAndUpdateNormeSelection model) throws InterruptedException, ExecutionException{

        log.info("creation nouveaux normes de séléction");

        Thread.sleep(1000L);
        
        NormeSelection registredNorme = new NormeSelection();
        BeanUtils.copyProperties(model, registredNorme);

        if (null != model.getHonoraires()) {
            List<Honoraire> list = new ArrayList<>();

            for (Honoraire request : model.getHonoraires()) {
                Optional<Honoraire> honoraireOptional = honoraireRepository.findById(request.getId());
                if (honoraireOptional.isPresent()){
                    Honoraire honoraire = honoraireOptional.get();
                    list.add(honoraire);
                }
                else {
                    throw new NoSuchElementException();
                }

            }
            registredNorme.setHonoraires(list);
        }
        registredNorme = this.normeSelectionRepository.save(registredNorme);
        return modelMapper.map(registredNorme, NormeSelectionResponseModel.class);
    }

    @Override
    @Async("asyncExecutor")
    public NormeSelectionResponseModel updateNormeSelection(String id, UpdateNormeSelection model) throws InterruptedException, ExecutionException{
        // search for role if already exist in database
        log.info("Searching for norme with id : {}", id);

        Thread.sleep(1000L);

        Optional<NormeSelection> normeOpt = normeSelectionRepository.findById(id);

        // update the norme if exist
        if (normeOpt.isPresent()) {
            NormeSelection norme = normeOpt.get();
            BeanUtils.copyProperties(model, norme);
            log.info("Updating norme ...");
            if (null != model.getHonoraires()) {
                List<Honoraire> list = new ArrayList<>();
                for (Honoraire request : model.getHonoraires()) {
                    Optional<Honoraire> honoraireOptional = honoraireRepository.findById(request.getId());
                    if (honoraireOptional.isPresent()){
                        Honoraire honoraire = honoraireOptional.get();
                        list.add(honoraire);
                    }
                    else {
                        throw new NoSuchElementException();
                    }
                }
                norme.setHonoraires(list);
            }
            return modelMapper.map(normeSelectionRepository.save(norme),NormeSelectionResponseModel.class);

        } else {

            log.error("No norme found with id : {}", id);
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND,"this norme doesn't exist !!");

        }

    }

    @Override
    @Async("asyncExecutor")
    public Page<NormeSelection> getNormeSelections(int page, int limit, String sort, String direction) throws InterruptedException, ExecutionException{
        Thread.sleep(1000L);
        return null;
    }

    @Override
    @Async("asyncExecutor")
    public List<NormeSelectionResponseModel> getNormeById(String productId) throws InterruptedException, ExecutionException{
        Thread.sleep(1000L);
        Optional<DecesProduit> type = decesProduitRepository.findById(productId);
        if (type.isPresent()) {
            return this.normeSelectionRepository.findByDecesProduit(type.get())
                    .stream()
                    .map(o -> modelMapper.map(o,NormeSelectionResponseModel.class))
                    .collect(Collectors.toList());
        } else {
            log.error("pas d'auxiliaire avec ce type d'auxiliaire");
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND,"pas d'auxiliaire avec ce type d'auxiliaire");
        }
    }

    @Override
    @Async("asyncExecutor")
    public NormeSelectionResponseModel getNorme(int age, int agem, float capital, float capitalm, String decesProduit)throws InterruptedException, ExecutionException {
        Thread.sleep(1000L);
        Optional<DecesProduit> decesProduit2 = this.decesProduitRepository.findById(decesProduit);
        if (decesProduit2.isPresent()){
            Optional<NormeSelection>  normeEntity = normeSelectionRepository.findByAgeMaxGreaterThanEqualAndAgeMinLessThanEqualAndCapitalMaxGreaterThanEqualAndCapitalMinLessThanEqualAndDecesProduit
            (age, agem, capital, capitalm, decesProduit2.get());
                    if(normeEntity.isPresent()){
                        return modelMapper.map(normeEntity.get(),NormeSelectionResponseModel.class);
                    }
        }
        throw new HttpClientErrorException(HttpStatus.NOT_FOUND,"aucune norme");

    }

}