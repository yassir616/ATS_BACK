package com.soa.vie.takaful.servicesImplementation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.entitymodels.Categorie;
import com.soa.vie.takaful.entitymodels.Commission;
import com.soa.vie.takaful.entitymodels.Exclusion;
import com.soa.vie.takaful.entitymodels.Partenaire;
import com.soa.vie.takaful.entitymodels.Periodicite;
import com.soa.vie.takaful.entitymodels.PieceJointe;
import com.soa.vie.takaful.entitymodels.PointVente;
import com.soa.vie.takaful.entitymodels.RetraiteProduit;
import com.soa.vie.takaful.entitymodels.Risque;
import com.soa.vie.takaful.entitymodels.TypePrestation;
import com.soa.vie.takaful.entitymodels.TypePrestationProduit;
import com.soa.vie.takaful.repositories.ICategorieRepository;
import com.soa.vie.takaful.repositories.IExclusionRepository;
import com.soa.vie.takaful.repositories.IPartenaireRepository;
import com.soa.vie.takaful.repositories.IPeriodiciteRepository;
import com.soa.vie.takaful.repositories.IPieceJointeRepository;
import com.soa.vie.takaful.repositories.IPointVenteRepository;
import com.soa.vie.takaful.repositories.IRetraiteProduitRepository;
import com.soa.vie.takaful.repositories.IRisqueRepository;
import com.soa.vie.takaful.repositories.ITypePrestationRepository;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateRetraiteProduit;
import com.soa.vie.takaful.responsemodels.RetraiteProduitResponseModel;
import com.soa.vie.takaful.services.IRetraiteProduitService;
import com.soa.vie.takaful.util.Pagination;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

@Service
@Transactional
public class RetraiteProduitServiceImpl implements IRetraiteProduitService {

    @Autowired
    private ICategorieRepository categorieRepository;

    @Autowired
    private IPartenaireRepository partenaireRepository;

    @Autowired
    private IPeriodiciteRepository periodiciteRepository;

    @Autowired
    private IRisqueRepository risqueRepository;

    @Autowired
    private IExclusionRepository exclusionRepository;

    @Autowired
    private IPieceJointeRepository pieceRepository;

    @Autowired
    private IPointVenteRepository pointventeRepository;

    @Autowired
    private ITypePrestationRepository prestationRepository;

    @Autowired
    private IRetraiteProduitRepository retraiteProduitRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Async("asyncExecutor")
    public RetraiteProduitResponseModel createRetraiteProduit(CreateAndUpdateRetraiteProduit model) throws InterruptedException,ExecutionException{
        
        Thread.sleep(1000L);
        RetraiteProduit registredproduct = new RetraiteProduit();
        String code = model.getCode();

        RetraiteProduit produitexists = retraiteProduitRepository.findByCode(code);
        BeanUtils.copyProperties(model, registredproduct);

        if (produitexists != null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "product already exists");
        }

        else {
            commissionsHandler(model, registredproduct);
            prestationsHandler(model, registredproduct);
            exclusionsHandler(model, registredproduct);
            periodicitesHandler(model, registredproduct);
            pointVentesHandler(model, registredproduct);

            Optional<Categorie> categorie = categorieRepository.findById(model.getCategorie().getId());
            Optional<Risque> risque = risqueRepository.findById(model.getRisque().getId());
            Optional<Partenaire> partenaire = partenaireRepository.findById(model.getPartenaire().getId());

            if (categorie.isPresent() && risque.isPresent() && partenaire.isPresent()) {

                registredproduct.setRisque(risque.get());
                registredproduct.setCategorie(categorie.get());
                registredproduct.setPartenaire(partenaire.get());

            } else {

                throw new NullPointerException("Création du produit Erronée");

            }

            registredproduct = retraiteProduitRepository.save(registredproduct);
        }
        return modelMapper.map(registredproduct, RetraiteProduitResponseModel.class);
    }

    @Override
    @Async("asyncExecutor")
    public RetraiteProduitResponseModel updateRetraiteProduit(String id, CreateAndUpdateRetraiteProduit model) throws InterruptedException,ExecutionException{
        Thread.sleep(1000L);
        Optional<RetraiteProduit> productexists = this.retraiteProduitRepository.findById(id);
        Optional<Risque> risque = this.risqueRepository.findById(model.getRisque().getId());
        Optional<Categorie> categorie = this.categorieRepository.findById(model.getCategorie().getId());

        if (productexists.isPresent() && risque.isPresent() && categorie.isPresent()) {
            Date now = new Date();
            RetraiteProduit retraiteProduit = productexists.get();
            retraiteProduit.setRisque(risque.get());
            retraiteProduit.setCategorie(categorie.get());
            retraiteProduit.setDateModification(now);
            exclusionsHandlerUpdate(model, retraiteProduit);
            periodicitesHandlerUpdate(model, retraiteProduit);

            BeanUtils.copyProperties(model, retraiteProduit);
            return modelMapper.map(this.retraiteProduitRepository.save(retraiteProduit), RetraiteProduitResponseModel.class);

        } else {

            throw new NullPointerException("la modification de ce produit est erroné");
        }

    }

    @Override
    @Async("asyncExecutor")
    public Page<RetraiteProduitResponseModel> getRetraiteProduits(int page, int limit, String sort, String direction) throws InterruptedException,ExecutionException{
        Thread.sleep(1000L);
        return retraiteProduitRepository.findAll(Pagination.pageableRequest(page, limit, sort, direction))
                .map(o -> modelMapper.map(o, RetraiteProduitResponseModel.class));

    }

    @Override
    @Async("asyncExecutor")
    public RetraiteProduitResponseModel getRetraiteProduitById(String id) throws InterruptedException,ExecutionException{
        Thread.sleep(1000L);
        Optional<RetraiteProduit> retraiteProduit = retraiteProduitRepository.findById(id);
        if (retraiteProduit.isPresent()) {
            return modelMapper.map(retraiteProduit.get(), RetraiteProduitResponseModel.class);
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "il y'a aucune deces produit avec ce id");
        }
    }

    @Override
    @Async("asyncExecutor")
    public RetraiteProduitResponseModel getRetraiteProduitByCode(String code)throws InterruptedException,ExecutionException {
        Thread.sleep(1000L);
        RetraiteProduit retraiteProduit = retraiteProduitRepository.findByCode(code);
        if (retraiteProduit != null) {
            return modelMapper.map(retraiteProduit, RetraiteProduitResponseModel.class);
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "il y'a aucune retrait produit avec ce id");
        }
    }

    protected void periodicitesHandler(CreateAndUpdateRetraiteProduit model, RetraiteProduit registredproduct) {
        if (null != model.getPeriodicites()) {
            List<Periodicite> periodicites = new ArrayList<>();

            for (Periodicite request : model.getPeriodicites()) {
                Optional<Periodicite> periodiciteOptional = periodiciteRepository.findById(request.getId());
                if (periodiciteOptional.isPresent()) {
                    Periodicite periodicite = periodiciteOptional.get();
                    periodicites.add(periodicite);
                } else {
                    throw new NoSuchElementException();
                }

            }
            registredproduct.setPeriodicites(periodicites);
        }
    }

    protected void pointVentesHandler(CreateAndUpdateRetraiteProduit model, RetraiteProduit registredproduct) {
        if (null != model.getPointVentes()) {
            List<PointVente> list = new ArrayList<>();

            for (PointVente request : model.getPointVentes()) {
                Optional<PointVente> pointVenteOptional = pointventeRepository.findById(request.getId());
                if (pointVenteOptional.isPresent()) {
                    PointVente pointvente = pointVenteOptional.get();
                    list.add(pointvente);
                } else {
                    throw new NoSuchElementException();
                }

            }
            registredproduct.setPointVentes(list);
        }
    }

    protected void commissionsHandler(CreateAndUpdateRetraiteProduit model, RetraiteProduit registredproduct) {
        if (null != model.getCommissions()) {
            List<Commission> commissions = new ArrayList<>();
            for (Commission request : model.getCommissions()) {
                Commission commission = new Commission();
                BeanUtils.copyProperties(request, commission);
                commission.setProduit(registredproduct);
                commissions.add(commission);
            }
            registredproduct.setCommissions(commissions);
        }
    }

    protected void prestationsHandler(CreateAndUpdateRetraiteProduit model, RetraiteProduit registredproduct) {
        if (null != model.getPrestations()) {
            List<TypePrestationProduit> prestations = new ArrayList<>();

            for (TypePrestationProduit request : model.getPrestations()) {
                List<PieceJointe> piece = request.getPieceJointe();

                TypePrestationProduit typePrestation = new TypePrestationProduit();
                BeanUtils.copyProperties(request, typePrestation);
                Optional<TypePrestation> prestation = prestationRepository
                        .findById(request.getTypePrestation().getId());

                if (prestation.isPresent()) {

                    typePrestation.setTypePrestation(prestation.get());
                    typePrestation.setProduit(registredproduct);
                }
                if (null != piece) {
                    prestationChecker(piece, typePrestation);
                }
                prestations.add(typePrestation);

            }

            registredproduct.setPrestations(prestations);
        }
    }

    protected void exclusionsHandler(CreateAndUpdateRetraiteProduit model, RetraiteProduit registredproduct) {
        if (null != model.getExclusions()) {
            List<Exclusion> exclusions = new ArrayList<>();

            for (Exclusion request : model.getExclusions()) {
                Optional<Exclusion> exclusionOptional = exclusionRepository.findById(request.getId());
                if (exclusionOptional.isPresent()) {
                    Exclusion exclusion = exclusionOptional.get();
                    exclusions.add(exclusion);
                } else {
                    throw new NoSuchElementException();
                }

            }
            registredproduct.setExclusions(exclusions);
        }
    }

    protected void exclusionsHandlerUpdate(CreateAndUpdateRetraiteProduit updateDecesProduit,
            RetraiteProduit registredproduct) {
        if (null != updateDecesProduit.getExclusions()) {
            List<Exclusion> exclusions = new ArrayList<>();

            for (Exclusion request : updateDecesProduit.getExclusions()) {
                Optional<Exclusion> exclusionOptional = exclusionRepository.findById(request.getId());
                if (exclusionOptional.isPresent()) {
                    Exclusion exclusion = exclusionOptional.get();
                    exclusions.add(exclusion);
                } else {
                    throw new NoSuchElementException();
                }

            }
            registredproduct.setExclusions(exclusions);
        }
    }

    protected void periodicitesHandlerUpdate(CreateAndUpdateRetraiteProduit updateDecesProduit,
            RetraiteProduit registredproduct) {
        if (null != updateDecesProduit.getPeriodicites()) {
            List<Periodicite> periodicites = new ArrayList<>();

            for (Periodicite request : updateDecesProduit.getPeriodicites()) {
                Optional<Periodicite> periodiciteOptional = periodiciteRepository.findById(request.getId());
                if (periodiciteOptional.isPresent()) {
                    Periodicite periodicite = periodiciteOptional.get();
                    periodicites.add(periodicite);
                } else {
                    throw new NoSuchElementException();
                }

            }
            registredproduct.setPeriodicites(periodicites);
        }
    }

    protected void prestationChecker(List<PieceJointe> piece, TypePrestationProduit typePrestation) {
        List<PieceJointe> pieces = new ArrayList<>();

        for (PieceJointe requests : piece) {
            Optional<PieceJointe> pieceJointeOptional = pieceRepository.findById(requests.getId());
            if (pieceJointeOptional.isPresent()) {
                PieceJointe pieceJointes = pieceJointeOptional.get();
                pieces.add(pieceJointes);
            } else {
                throw new NoSuchElementException();
            }

        }

        typePrestation.setPieceJointe(pieces);
    }

}
