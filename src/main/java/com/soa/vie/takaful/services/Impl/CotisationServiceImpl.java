package com.soa.vie.takaful.services.Impl;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.soa.vie.takaful.entitymodels.Contract;
import com.soa.vie.takaful.entitymodels.Cotisation;
import com.soa.vie.takaful.entitymodels.EmissionGlobale;
import com.soa.vie.takaful.entitymodels.PersonnePhysique;
import com.soa.vie.takaful.entitymodels.Produit;
import com.soa.vie.takaful.entitymodels.autoIncrementhelpers.CostumIdGeneratedValueLotEntity;
import com.soa.vie.takaful.repositories.IContractRepository;
import com.soa.vie.takaful.repositories.ICotisationRepository;
import com.soa.vie.takaful.repositories.ICotisationRepositoryCustom;
import com.soa.vie.takaful.repositories.IEmissionGlobaleRepository;
import com.soa.vie.takaful.repositories.autoincrementhelpers.CostumIdGeneratedValueLotRepository;
import com.soa.vie.takaful.requestmodels.AnnulationCotisationRequest;
import com.soa.vie.takaful.requestmodels.CotisationRequestDTO;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateCotisation;
import com.soa.vie.takaful.requestmodels.CreateEmissionGlobale;
import com.soa.vie.takaful.responsemodels.CotisationModelResponse;
import com.soa.vie.takaful.services.ICotisationService;
import com.soa.vie.takaful.services.mapper.CotisationMapper;
import com.soa.vie.takaful.util.EtatCotisation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class CotisationServiceImpl implements ICotisationService {

	@Autowired
	private IEmissionGlobaleRepository emissionGlobaleRepository;

	@Autowired
	private CostumIdGeneratedValueLotRepository costumIdGeneratedValueLotRepository;

	@Autowired
	private ICotisationRepository cotisationRepository;

	@Autowired
	private IContractRepository contratRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CotisationMapper cotisationMapper;



	//cotisation.id,cotisation.datePrelevement,cotisation.montantCotisation,cotisation.etatCotisation,cotisation.numQuittance,cotisation.solde,cotisation.montantTaxe,cotisation.montantTaxeParaFiscale,cotisation.annulation,cotisation.montantAccessoire,cotisation.montantTTC,cotisation.cotisationType,cotisation.fraisAcquisitionTTC,cotisation.fraisGestionTTC,cotisation.contributionPure,cotisation.capitalAssure,cotisation.dateEtablissement,cotisation.exercice,cotisation.numeroLot,cotisation.flagBatch,cotisation.dateEmission



	@Override
	@Async("asyncExecutor")
	public Page<CotisationModelResponse> getCotisations(int page, int limit)
			throws InterruptedException, ExecutionException {

		Thread.sleep(1000L);
		Pageable pageableRequest = PageRequest.of(page, limit);
		return this.cotisationRepository.findAllCotisation(pageableRequest)
				.map(o -> modelMapper.map(o, CotisationModelResponse.class));

	}

	@Override
	@Async("asyncExecutor")
	public CotisationModelResponse createCotisation(CreateAndUpdateCotisation model)
			throws InterruptedException, ExecutionException {
		log.info("creation cotisation ...");

		Thread.sleep(1000L);
		Cotisation cotisation = new Cotisation();
		SecureRandom random = new SecureRandom();

		Optional<Contract> type = contratRepository.findById(model.getContrat());
		int nombreAleatoire = 56 + (int) (random.nextFloat() * ((900 - 56) + 1));
		if (type.isPresent()) {
			BeanUtils.copyProperties(model, cotisation);
			cotisation.setContrat(type.get());
			cotisation.setContributionPure(model.getContributionPure());
			cotisation.setFraisAcquisitionTTC(model.getFraisAcquisitionTTC());
			cotisation.setFraisGestionTTC(model.getFraisGestionTTC());
			cotisation.setSolde(model.getMontantTTC());
			cotisation.setEtatCotisation(EtatCotisation.EMIS);
			cotisation.setCotisationType(model.getCotisationType());
			cotisation.setNumQuittance(nombreAleatoire);
			cotisation.setMontantTaxeParaFiscale(model.getMontantTaxeParaFiscale());

			return modelMapper.map(cotisationRepository.save(cotisation), CotisationModelResponse.class);

		} else {
			log.error("la creation de cotisation est erronée");
			throw new NoSuchElementException("la creation de la cotisation est erronée");
		}
	}

	@Override
	@Async("asyncExecutor")
	public List<CotisationModelResponse> getCotisationByContrat(String id)
			throws InterruptedException, ExecutionException {

		Thread.sleep(1000L);
		return cotisationRepository.findByContratId(id).stream()
				.map(o -> modelMapper.map(o, CotisationModelResponse.class))
				.collect(Collectors.toList());

	}

	@Override
	@Async("asyncExecutor")
	public List<CotisationModelResponse> getCotisationByContrats(String type)
			throws InterruptedException, ExecutionException {

		Thread.sleep(1000L);
		return cotisationRepository.findAllCotisations(type).stream()
				.map(o -> modelMapper.map(o, CotisationModelResponse.class))
				.collect(Collectors.toList());
	}

	@Override
	@Async("asyncExecutor")
	public List<String> productByContrats() throws InterruptedException, ExecutionException {

		Thread.sleep(1000L);
		return cotisationRepository.findProductContisation().stream()
				.map(o -> modelMapper.map(o, String.class))
				.collect(Collectors.toList());
	}

	@Override
	public CotisationModelResponse AnnulationCotisation(String id, AnnulationCotisationRequest model)
			throws InterruptedException, ExecutionException {
		Cotisation cotisation = new Cotisation();

		BeanUtils.copyProperties(model, cotisation);

		cotisation.setEtatCotisation(EtatCotisation.ANNULER);
		cotisation.setAnnulation(id);

		return modelMapper.map(cotisationRepository.save(cotisation), CotisationModelResponse.class);

	}

	@Override
	public List<Cotisation> saveCotisation(List<Cotisation> cotisations)
			throws InterruptedException, ExecutionException {
		System.out.println("Content of cotisations list:");
		for (Cotisation cotisation : cotisations) {
			log.info(cotisation.toString());
		}
		return cotisations.stream().map(cotisation -> cotisationRepository.save(cotisation))
				.collect(Collectors.toList());
	}

	@Override
	public List<String> productByContrat() throws InterruptedException, ExecutionException {
		Thread.sleep(1000L);
		return cotisationRepository.findProductCotisation().stream()
				.map(o -> modelMapper.map(o, String.class))
				.collect(Collectors.toList());
	}

	

	// @Override
	// public List<CotisationRequestDTO> getEmissionGlobale(String partenaireId, String produitId, String startDate, String endDate)
	// 		throws InterruptedException, ExecutionException {
	// 	log.info("getting cotisations.........");
	// 	log.info("startDate : " + startDate);
	// 	log.info("endDate : " + endDate);
	// 	log.info("produitId : " + produitId);
	// 	log.info("partenaireId : " + partenaireId);
	// 	List<Cotisation> cotisations = new ArrayList<>();
	// 	if (!partenaireId.equals("empty")) {
	// 		if (!produitId.equals("empty")) {
	// 			log.info("searchByALL...");
	// 			cotisations=cotisationRepository.getEmissionGlobale(partenaireId, produitId, startDate, endDate);
	// 		} else {
	// 			log.info("searchByPartenaireAndDate...");
	// 			cotisations=cotisationRepository.getEmissionGlobaleByDateAndPartenaire(partenaireId, startDate, endDate);
	// 		}
	// 	} else {
	// 		log.info("searchByDateOnly...");
	// 		cotisations=cotisationRepository.getEmissionGlobaleByDateOnly(startDate, endDate);
			
	// 	}
	// 	log.info("END EXECUTION OF REQUEST BEGIN DTO ...");
	// 	//design pattern 
	// 	List<CotisationRequestDTO> cotisationsDTO= new ArrayList<>();
	// 	for (Cotisation coti : cotisations) {
	// 		CotisationRequestDTO cotiDTO =new CotisationRequestDTO();
	// 		//adding the data of cotisation pojo class
	// 		BeanUtils.copyProperties(coti, cotiDTO);
	// 		//adding the data of contracts
	// 		cotiDTO.setNumeroContrat(coti.getContrat().getNumeroContrat());
	// 		cotiDTO.setDateEffet(coti.getContrat().getDateEffet());
	// 		cotiDTO.setDateEcheance(coti.getContrat().getDateEcheance());
	// 		//adding the rest of the data
	// 		cotiDTO.setNomAssuree(coti.getContrat().getAssure().getNom());
	// 		cotiDTO.setPrenomAssuree(coti.getContrat().getAssure().getPrenom());
	// 		cotiDTO.setProduitLibelle(coti.getContrat().getProduit().getLibelle());
	// 		cotiDTO.setCreationDateCotisation(coti.getCreationDate());
	// 		cotiDTO.setEtatCotisation(coti.getEtatCotisation());
	// 		//cotiDTO.setNomSouscripteur(coti.getContrat().getSouscripteur().getId());
			
	// 		//adding to the list
	// 		cotisationsDTO.add(cotiDTO);
	// 	}

	// 	return cotisationsDTO;
	// }

	@Override
	public List<CotisationRequestDTO> getEmissionGlobale(String partenaireId, String produitId, String startDate, String endDate)
			throws InterruptedException, ExecutionException, ParseException {
		log.info("Début du service getEmissionGlobale avec partenaireId={}, produitId={}, startDate={}, endDate={}",
				partenaireId, produitId, startDate, endDate);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Cotisation> cotisations = cotisationRepository.recupererParIds(sdf.parse(startDate),sdf.parse(endDate),partenaireId,produitId);

		return cotisations.stream()
				.map(cotisation -> cotisationMapper.map(cotisation, CotisationRequestDTO.class))
				.collect(Collectors.toList());
	}


	@Override
	public CotisationModelResponse changeStatusCotisation(List<CotisationModelResponse> listCotisation,
			CreateEmissionGlobale EmissionGlobale, String reference)
			throws InterruptedException, ExecutionException {
		if (listCotisation.size() > 0) {
			CostumIdGeneratedValueLotEntity autoDataBaseGeneratedIdLot = new CostumIdGeneratedValueLotEntity();
			CostumIdGeneratedValueLotEntity id = this.costumIdGeneratedValueLotRepository
					.save(autoDataBaseGeneratedIdLot);
			log.info("the numeroLot added : " + id.getId());
			for (CotisationModelResponse requests : listCotisation) {
				Optional<Cotisation> cotisation = cotisationRepository.findById(requests.getId());
				log.info("cotisations id : " + cotisation.get().getId());
				cotisation.get().setEtatCotisation(EtatCotisation.VERIFIER);
				cotisation.get().setNumeroLot(id.getId());
			}
			CotisationModelResponse response = new CotisationModelResponse();
			response.setNumeroLot(id.getId());
			// traitement de l'emission globale
			log.info("Reference : " + reference);
			EmissionGlobale emissionGlobale = new EmissionGlobale();
			emissionGlobale.setFraisAcquisitionTTC(EmissionGlobale.getFraisAcquisitionTTC());
			emissionGlobale.setFraisGestionTTC(EmissionGlobale.getFraisGestionTTC());
			emissionGlobale.setMontantCotisation(EmissionGlobale.getMontantCotisation());
			emissionGlobale.setMontantTaxe(EmissionGlobale.getMontantTaxe());
			emissionGlobale.setMontantTaxeParaFiscale(EmissionGlobale.getMontantTaxeParaFiscale());
			emissionGlobale.setMontantTTC(EmissionGlobale.getMontantTTC());
			emissionGlobale.setSolde(EmissionGlobale.getSolde());
			emissionGlobale.setNumeroLot(id.getId());
			emissionGlobale.setReference(reference);
			emissionGlobaleRepository.save(emissionGlobale);
			return response;
		} else {
			log.error("Error in ListCotisation");
			throw new NullPointerException("Validation des cotisations erronés");
		}
	}

	@Override
	public List<Cotisation> getCotisationsBynumeroLot(String numeroLot)
			throws InterruptedException, ExecutionException {
		log.info("getting cotisations linked to  : " + numeroLot);
		return cotisationRepository.getCotisationByNumeroLot(numeroLot);
	}

	

}
