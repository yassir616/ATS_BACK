package com.soa.vie.takaful.servicesImplementation;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.soa.vie.takaful.entitymodels.ContratMrb;
import com.soa.vie.takaful.entitymodels.DomestiqueMrb;
import com.soa.vie.takaful.entitymodels.Personne;
import com.soa.vie.takaful.entitymodels.ProduitMrb;
import com.soa.vie.takaful.entitymodels.autoIncrementhelpers.CostumIdGeneratedValueContratEntity;
import com.soa.vie.takaful.repositories.IContratGarantieRepository;
import com.soa.vie.takaful.repositories.IContratMrb;
import com.soa.vie.takaful.repositories.IDomestiqueMrbRepository;
import com.soa.vie.takaful.repositories.IExclusionRepository;
import com.soa.vie.takaful.repositories.IGarantieMrbRepository;
import com.soa.vie.takaful.repositories.IPersonneRepository;
import com.soa.vie.takaful.repositories.IPointVenteRepository;
import com.soa.vie.takaful.repositories.IProduitMrbRepository;
import com.soa.vie.takaful.repositories.autoincrementhelpers.CostumIdGeneratedValueContratEntityRepository;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateContratMrb;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateDomestiqueSup;
import com.soa.vie.takaful.requestmodels.CreateContratMrb;
import com.soa.vie.takaful.responsemodels.ContratMrbModelResponse;
import com.soa.vie.takaful.services.IContratMrbService;
//import com.soa.vie.takaful.util.CategorieMrb;
import com.soa.vie.takaful.util.Pagination;
//import com.soa.vie.takaful.util.TypeProprieteMrb;
//import com.soa.vie.takaful.util.ValeurMrb;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ContratMrbServiceImpl implements IContratMrbService {

    @Autowired
    IProduitMrbRepository produitMrbRepository;

    @Autowired
    IPointVenteRepository pointVenteRepository;

    @Autowired
    IContratMrb contratMrbRepository;

    @Autowired
    IPersonneRepository personneRepository;

    @Autowired
    IContratGarantieRepository contratGarantie;

    @Autowired
    IExclusionRepository exclusionMrbRepository;

    @Autowired
    IGarantieMrbRepository garantieMrb;

    @Autowired
    IDomestiqueMrbRepository domestiqueMrb;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CostumIdGeneratedValueContratEntityRepository costumIdGeneratedValueContratEntityRepository;

    @Override
    @Async("asyncExecutor")
    public ContratMrbModelResponse createContratMrb(CreateAndUpdateContratMrb model)
            throws InterruptedException, ExecutionException {

        log.info("Creation du contrat MRB");
        Thread.sleep(4000L);
        ContratMrb contratMrb = new ContratMrb();

        Optional<Personne> souscripteur = this.personneRepository.findById(model.getSouscripteur().getId());
        Optional<ProduitMrb> produit = this.produitMrbRepository.findById(model.getProduitMrb().getId());
        Optional<Personne> assure = this.personneRepository.findById(model.getAssure().getId());

        if (produit.isPresent() && souscripteur.isPresent() && assure.isPresent()) {

            BeanUtils.copyProperties(model, contratMrb);

            if (null != model.getDomestique()) {
                List<DomestiqueMrb> domestiques = new ArrayList<>();

                for (CreateAndUpdateDomestiqueSup request : model.getDomestique()) {
                    DomestiqueMrb domestique = new DomestiqueMrb();
                    BeanUtils.copyProperties(request, domestique);
                    domestique.setContratDdomestiqueMrb(contratMrb);
                    domestiques.add(domestique);
                }

                contratMrb.setDomestique(domestiques);
            }
            CostumIdGeneratedValueContratEntity autoDataBaseGeneratedIdContrat = new CostumIdGeneratedValueContratEntity();
            // if (Constants.CODE_PARTENAIRE_ALKHDAR.equals(partenaireCode)) {
            // contratMrb.setNumeroContrat(Constants.PREFIX_NUM_CONTRACT_PARTENAIRE_AKHDAR
            // .concat(this.costumIdGeneratedValueContratEntityRepository
            // .save(autoDataBaseGeneratedIdContrat).getId()));
            // } else if (Constants.CODE_PARTENAIRE_ALYOUSER.equals(partenaireCode)) {
            // contratMrb.setNumeroContrat(Constants.PREFIX_NUM_CONTRACT_PARTENAIRE_ALYOUSR
            // .concat(this.costumIdGeneratedValueContratEntityRepository
            // .save(autoDataBaseGeneratedIdContrat).getId()));
            // }
            // else
            contratMrb.setNumeroContrat(this.costumIdGeneratedValueContratEntityRepository
                    .save(autoDataBaseGeneratedIdContrat).getId());
            contratMrb.setAssure(model.getAssure());
            contratMrb.setSouscripteur(model.getSouscripteur());
            contratMrb.setProduitMrb(model.getProduitMrb());
            contratMrb.setCategorie(model.getCategorie());
            contratMrb.setTypePropriete(model.getTypePropriete());
            contratMrb.setValeurBatiment(model.getValeurBatiment());
            contratMrb.setValeurContenu(model.getValeurContenu());

            return modelMapper.map(this.contratMrbRepository.save(contratMrb), ContratMrbModelResponse.class);

        } else {
            throw new NoSuchElementException("worng data,please check the data you're sending");
        }

    }

    @Override
    @Async("asyncExecutor")
    public ContratMrbModelResponse updateContratMrb(String id, CreateContratMrb model)
            throws InterruptedException, ExecutionException {
        Thread.sleep(1000L);
        return null;
    }

    @Override
    @Async("asyncExecutor")
    public Page<ContratMrbModelResponse> getContratMrb(int page, int limit, String sort, String direction)
            throws InterruptedException, ExecutionException {
        List<ContratMrbModelResponse> list = contratMrbRepository
                .findAll(Pagination.pageableRequest(page, limit, sort, direction)).stream()
                .map(o -> modelMapper.map(o, ContratMrbModelResponse.class)).collect(Collectors.toList());
        return new PageImpl<>(list);
    }

    @Override
    @Async("asyncExecutor")
    public Page<ContratMrbModelResponse> searchContratMrb(int page, int limit, String searchBy, String searchFor)
            throws InterruptedException, ExecutionException {

        Thread.sleep(1000L);
        Pageable pageableRequest = PageRequest.of(page, limit);
        if ("nom".equals(searchBy)) {
            return contratMrbRepository.findByAssureNom(pageableRequest, searchFor)
                    .map(o -> modelMapper.map(o, ContratMrbModelResponse.class));

        }

        else if ("numeroSimulation".equals(searchBy)) {
            return contratMrbRepository.findByNumeroSimulation(pageableRequest, searchFor)
                    .map(o -> modelMapper.map(o, ContratMrbModelResponse.class));
        }

        else if ("numeroContrat".equals(searchBy)) {
            return contratMrbRepository.findByNumeroContratStartingWith(pageableRequest, searchFor)
                    .map(o -> modelMapper.map(o, ContratMrbModelResponse.class));
        }

        throw new IllegalArgumentException(
                "la recherche doit etre efectuer par nom, numero de simulation ou numero de contrat");
    }

}