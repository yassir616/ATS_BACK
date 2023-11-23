package com.soa.vie.takaful.services.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.transaction.Transactional;

import com.soa.vie.takaful.entitymodels.Categorie;
import com.soa.vie.takaful.entitymodels.CauseRestitution;
import com.soa.vie.takaful.entitymodels.Commission;
import com.soa.vie.takaful.entitymodels.DecesProduit;
import com.soa.vie.takaful.entitymodels.DecesProduitCauseRestitution;
import com.soa.vie.takaful.entitymodels.Exclusion;
import com.soa.vie.takaful.entitymodels.Honoraire;
import com.soa.vie.takaful.entitymodels.NormeSelection;
import com.soa.vie.takaful.entitymodels.OptionAssurance;
import com.soa.vie.takaful.entitymodels.Partenaire;
import com.soa.vie.takaful.entitymodels.Periodicite;
import com.soa.vie.takaful.entitymodels.PieceJointe;
import com.soa.vie.takaful.entitymodels.PointVente;
import com.soa.vie.takaful.entitymodels.Restitution;
import com.soa.vie.takaful.entitymodels.Risque;
import com.soa.vie.takaful.entitymodels.SousCategorie;
import com.soa.vie.takaful.entitymodels.Tarrification;
import com.soa.vie.takaful.entitymodels.TypePrestation;
import com.soa.vie.takaful.entitymodels.TypePrestationProduit;
import com.soa.vie.takaful.entitymodels.autoIncrementhelpers.CostumIdGeneratedValueDecesEntity;
import com.soa.vie.takaful.repositories.ICategorieRepository;
import com.soa.vie.takaful.repositories.ICauseRestitutionRepository;
import com.soa.vie.takaful.repositories.IDecesProduitRepository;
import com.soa.vie.takaful.repositories.IExclusionRepository;
import com.soa.vie.takaful.repositories.IHonoraireRepository;
import com.soa.vie.takaful.repositories.IPartenaireRepository;
import com.soa.vie.takaful.repositories.IPeriodiciteRepository;
import com.soa.vie.takaful.repositories.IPieceJointeRepository;
import com.soa.vie.takaful.repositories.IPointVenteRepository;
import com.soa.vie.takaful.repositories.IRestitutionRepository;
import com.soa.vie.takaful.repositories.IRisqueRepository;
import com.soa.vie.takaful.repositories.ISousCategorieRepository;
import com.soa.vie.takaful.repositories.ITypePrestationRepository;
import com.soa.vie.takaful.repositories.autoincrementhelpers.CostumIdGeneratedValueProduitEntityRepository;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateCommission;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateDecesProduit;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateDecesProduitCauseRestitution;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateNormeSelection;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateOptionAssurance;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateTarrification;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateTypePrestationProduit;
import com.soa.vie.takaful.requestmodels.UpdateDecesProduit;
import com.soa.vie.takaful.responsemodels.DecesProduitResponseModel;
import com.soa.vie.takaful.responsemodels.ProduitModelResponse;
import com.soa.vie.takaful.services.IDecesProduitService;
import com.soa.vie.takaful.util.Pagination;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class DecesProduitServiceImpl implements IDecesProduitService {

	@Autowired
	private ICategorieRepository categorieRepository;

	@Autowired
	private IPartenaireRepository partenaireRepository;

	@Autowired
	private IPeriodiciteRepository periodiciteRepository;

	@Autowired
	private IRisqueRepository risqueRepository;

	@Autowired
	private IDecesProduitRepository decesProduitRepository;

	@Autowired
	private IExclusionRepository exclusionRepository;

	@Autowired
	private IPieceJointeRepository pieceRepository;

	@Autowired
	private IPointVenteRepository pointventeRepository;

	@Autowired
	private IRestitutionRepository restitutionRepository;

	@Autowired
	private ISousCategorieRepository sousCategorieRepository;

	@Autowired
	private ITypePrestationRepository prestationRepository;

	@Autowired
	private ICauseRestitutionRepository causeRestitutionRepository;
	@Autowired
	private IHonoraireRepository honoraireRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CostumIdGeneratedValueProduitEntityRepository costumIdGeneratedValueProduitEntityRepository;

	@Override
	@Async("asyncExecutor")
	public Page<DecesProduitResponseModel> getDecesProduits(int page, int limit, String sort, String direction)
			throws InterruptedException, ExecutionException {
		log.info("lister les deces produits");

		Thread.sleep(1000L);
		return decesProduitRepository.findAll(Pagination.pageableRequest(page, limit, sort, direction))
				.map(o -> modelMapper.map(o, DecesProduitResponseModel.class));
	}

	@Override
	@Async("asyncExecutor")
	public DecesProduit getDecesProduitById(String id) throws InterruptedException, ExecutionException {

		Thread.sleep(1000L);
		Optional<DecesProduit> decesProduitOptional = decesProduitRepository.findById(id);
		if (decesProduitOptional.isPresent()) {
			return decesProduitOptional.get();
		} else {
			log.info("ce ID n'existe pas : " + id);
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "il y'a aucune deces produit avec ce id");
		}
	}

	@Override
	@Async("asyncExecutor")
	public ProduitModelResponse createDecesProduit(CreateAndUpdateDecesProduit model)
			throws InterruptedException, ExecutionException {

		log.info("creation nouveau produit");

		Thread.sleep(1000L);
		DecesProduit registredproduct = new DecesProduit();
		String code = model.getCode();

		Optional<DecesProduit> produitexists = decesProduitRepository.findByCode(code);
		BeanUtils.copyProperties(model, registredproduct);

		if (produitexists.isPresent()) {
			log.info("product already exist");
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "product already exists");
		}
		else {
			commissionsHandler(model, registredproduct);
			tarrificationHandler(model, registredproduct);
			optionsHandler(model, registredproduct);
			normesHandler(model, registredproduct);
			decesCauseRestitutionHandler(model, registredproduct);
			prestationsHandler(model, registredproduct);
			exclusionsHandler(model, registredproduct);
			periodicitesHandler(model, registredproduct);
			pointVentesHandler(model, registredproduct);

			Optional<Categorie> categorie = categorieRepository.findById(model.getCategorieId());
			Optional<Risque> risque = risqueRepository.findById(model.getRisqueId());
			Optional<Partenaire> partenaire = partenaireRepository.findById(model.getPartenaireId());
			Optional<SousCategorie> sousCategorie = sousCategorieRepository.findById(model.getSousCategorieId());

			if (categorie.isPresent() && risque.isPresent() && partenaire.isPresent() && sousCategorie.isPresent()) {

				registredproduct.setRisque(risque.get());
				registredproduct.setCategorie(categorie.get());
				registredproduct.setPartenaire(partenaire.get());
				registredproduct.setSousCategorie(sousCategorie.get());

			} else {

				log.error("la creation de produit est erroné");

			}

			// numuero de police
			CostumIdGeneratedValueDecesEntity autoDataBaseGeneratedNumeroPolice = new CostumIdGeneratedValueDecesEntity();
			registredproduct.setNumeroPolice(
					this.costumIdGeneratedValueProduitEntityRepository.save(autoDataBaseGeneratedNumeroPolice).getId());

			registredproduct = this.decesProduitRepository.save(registredproduct);
		}

		return modelMapper.map(registredproduct, ProduitModelResponse.class);

	}

	@Override
	@Async("asyncExecutor")
	public ProduitModelResponse updateDecesProduit(String id, UpdateDecesProduit model)
			throws InterruptedException, ExecutionException {
		log.info("updating deces product : {}", id);
		Thread.sleep(1000L);
		Optional<DecesProduit> productexists = decesProduitRepository.findById(id);
		Optional<Risque> risque = risqueRepository.findById(model.getRisqueId());
		Optional<Categorie> categorie = categorieRepository.findById(model.getCategorieId());

		if (productexists.isPresent() && risque.isPresent() && categorie.isPresent()) {
			Date now = new Date();
			DecesProduit decesproduit = productexists.get();
			decesproduit.setRisque(risque.get());
			decesproduit.setCategorie(categorie.get());
			decesproduit.setDateModification(now);
			exclusionsHandlerUpdate(model, decesproduit);
			periodicitesHandlerUpdate(model, decesproduit);

			BeanUtils.copyProperties(model, decesproduit);
			return modelMapper.map(decesProduitRepository.save(decesproduit), ProduitModelResponse.class);
		} else {
			log.error("la modification1 de produit est erroné avec l'ID({}): ",
					id);
			throw new NullPointerException("la modification1 de produit est erroné");
		}

	}

	protected void commissionsHandler(CreateAndUpdateDecesProduit model, DecesProduit registredproduct) {
		if (null != model.getCommissions()) {
			List<Commission> commissions = new ArrayList<>();

			for (CreateAndUpdateCommission request : model.getCommissions()) {
				Commission commission = new Commission();
				BeanUtils.copyProperties(request, commission);
				commission.setProduit(registredproduct);
				commissions.add(commission);
			}
			registredproduct.setCommissions(commissions);
		}
	}

	protected void tarrificationHandler(CreateAndUpdateDecesProduit model, DecesProduit registredproduct) {
		if (null != model.getTarrifications()) {
			List<Tarrification> tarrifications = new ArrayList<>();

			for (CreateAndUpdateTarrification request : model.getTarrifications()) {
				Tarrification tarrification = new Tarrification();
				BeanUtils.copyProperties(request, tarrification);
				tarrification.setDecesProduit(registredproduct);
				tarrifications.add(tarrification);
			}

			registredproduct.setTarrifications(tarrifications);
		}
	}

	protected void optionsHandler(CreateAndUpdateDecesProduit model, DecesProduit registredproduct) {
		if (null != model.getOptions()) {
			List<OptionAssurance> optionAssurances = new ArrayList<>();

			for (CreateAndUpdateOptionAssurance request : model.getOptions()) {
				OptionAssurance options = new OptionAssurance();
				BeanUtils.copyProperties(request, options);

				options.setDecesProduit(registredproduct);

				optionAssurances.add(options);
			}

			registredproduct.setOptions(optionAssurances);
		}
	}

	protected void normesHandler(CreateAndUpdateDecesProduit model, DecesProduit registredproduct) {
		if (null != model.getNormes()) {
			List<NormeSelection> normes = new ArrayList<>();

			for (CreateAndUpdateNormeSelection request : model.getNormes()) {
				List<Honoraire> honoraires = request.getHonoraires();
				NormeSelection norme = new NormeSelection();
				BeanUtils.copyProperties(request, norme);

				if (null != honoraires) {
					normesPropsCopy(honoraires, norme);
				}
				norme.setDecesProduit(registredproduct);
				normes.add(norme);
			}
			registredproduct.setNormes(normes);
		}
	}

	protected void normesPropsCopy(List<Honoraire> honoraires, NormeSelection norme) {
		List<Honoraire> listHonoraire = new ArrayList<>();
		for (Honoraire requests : honoraires) {
			Optional<Honoraire> honoraireOptional = honoraireRepository.findById(requests.getId());
			if (honoraireOptional.isPresent()) {
				Honoraire honoraire = honoraireOptional.get();
				listHonoraire.add(honoraire);
			} else {
				throw new NoSuchElementException();
			}

		}

		norme.setHonoraires(listHonoraire);

	}

	protected void decesCauseRestitutionHandler(CreateAndUpdateDecesProduit model, DecesProduit registredproduct) {
		if (null != model.getDecesCauseRestitution()) {
			List<DecesProduitCauseRestitution> deces = new ArrayList<>();

			for (CreateAndUpdateDecesProduitCauseRestitution request : model.getDecesCauseRestitution()) {
				List<PieceJointe> piece = request.getPieceJointes();

				DecesProduitCauseRestitution decesCauseRestitution = new DecesProduitCauseRestitution();
				BeanUtils.copyProperties(request, decesCauseRestitution);
				Optional<Restitution> restitution = restitutionRepository.findById(request.getIdRestitution());
				Optional<CauseRestitution> cause = causeRestitutionRepository.findById(request.getIdCauseRestitution());

				decesCauseRestitutionCheckerOne(restitution, decesCauseRestitution, cause, registredproduct);

				if (null != piece) {
					List<PieceJointe> pieces = new ArrayList<>();
					decesCauseRestitutionLoop(piece, pieces, decesCauseRestitution);
				}
				deces.add(decesCauseRestitution);

			}

			registredproduct.setDecesCauseRestitution(deces);

		}
	}

	protected void decesCauseRestitutionLoop(List<PieceJointe> piece, List<PieceJointe> pieces,
			DecesProduitCauseRestitution decesCauseRestitution) {
		for (PieceJointe requests : piece) {
			Optional<PieceJointe> pieceJointe = pieceRepository.findById(requests.getId());
			if (pieceJointe.isPresent()) {
				PieceJointe exclusion = pieceJointe.get();
				pieces.add(exclusion);
			} else {
				throw new NoSuchElementException();
			}

		}
		decesCauseRestitution.setPieceJointes(pieces);
	}

	protected void decesCauseRestitutionCheckerOne(Optional<Restitution> restitution,
			DecesProduitCauseRestitution decesCauseRestitution, Optional<CauseRestitution> cause,
			DecesProduit registredproduct) {
		if (restitution.isPresent() && cause.isPresent()) {
			decesCauseRestitution.setCauseRestitution(cause.get());
			decesCauseRestitution.setRestitution(restitution.get());
			decesCauseRestitution.setDeces(registredproduct);
		}
	}

	protected void prestationsHandler(CreateAndUpdateDecesProduit model, DecesProduit registredproduct) {
		if (null != model.getPrestations()) {
			List<TypePrestationProduit> prestations = new ArrayList<>();

			for (CreateAndUpdateTypePrestationProduit request : model.getPrestations()) {
				List<PieceJointe> piece = request.getPieceJointes();

				TypePrestationProduit typePrestation = new TypePrestationProduit();
				BeanUtils.copyProperties(request, typePrestation);
				Optional<TypePrestation> prestation = prestationRepository.findById(request.getTypePrestationId());

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

	protected void exclusionsHandler(CreateAndUpdateDecesProduit model, DecesProduit registredproduct) {
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

	protected void exclusionsHandlerUpdate(UpdateDecesProduit updateDecesProduit, DecesProduit registredproduct) {
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

	protected void periodicitesHandler(CreateAndUpdateDecesProduit model, DecesProduit registredproduct) {
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

	protected void periodicitesHandlerUpdate(UpdateDecesProduit updateDecesProduit, DecesProduit registredproduct) {
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

	protected void pointVentesHandler(CreateAndUpdateDecesProduit model, DecesProduit registredproduct) {
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

	@Override
	public Optional<DecesProduit> getDecesProduitByCode(String code) {
		// TODO Auto-generated method stub
		return this.decesProduitRepository.findByCode(code);
	}

}
