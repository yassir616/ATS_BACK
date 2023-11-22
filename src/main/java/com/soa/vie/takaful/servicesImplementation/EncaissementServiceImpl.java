package com.soa.vie.takaful.servicesImplementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.soa.vie.takaful.responsemodels.EncaissementResponseModel;
import com.soa.vie.takaful.services.IEmissionGlobale;
import com.soa.vie.takaful.services.IEncaissementService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.soa.vie.takaful.entitymodels.BordereauxEncaissement;
import com.soa.vie.takaful.entitymodels.CompteBancaire;
import com.soa.vie.takaful.entitymodels.Cotisation;
import com.soa.vie.takaful.entitymodels.DetailEncaissement;
import com.soa.vie.takaful.entitymodels.EmissionGlobale;
import com.soa.vie.takaful.entitymodels.Encaissement;
import com.soa.vie.takaful.entitymodels.autoIncrementhelpers.CostumIdGeneratedValueBordereauEntity;
import com.soa.vie.takaful.entitymodels.autoIncrementhelpers.CostumIdGeneratedValueEncaissementEntity;
import com.soa.vie.takaful.repositories.IBordereauRepository;
import com.soa.vie.takaful.repositories.ICompteBancaire;
import com.soa.vie.takaful.repositories.ICotisationRepository;
import com.soa.vie.takaful.repositories.IDetailEncaisseementRepository;
import com.soa.vie.takaful.repositories.IEncaissementRepository;
import com.soa.vie.takaful.repositories.IPointVenteRepository;
import com.soa.vie.takaful.repositories.autoincrementhelpers.CostumIdGeneratedValueBordereauRepository;
import com.soa.vie.takaful.repositories.autoincrementhelpers.CostumIdGeneratedValueEncaissementEntityRepository;
import com.soa.vie.takaful.requestmodels.BordereauEncaissementRequestModel;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateEncaissement;
import com.soa.vie.takaful.requestmodels.EncaissementGroupeRequestModel;
import com.soa.vie.takaful.util.EtatCotisation;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class EncaissementServiceImpl implements IEncaissementService {

	@Autowired
	private IEmissionGlobale emissionGlobaleRepo;
	@Autowired
	private ICotisationRepository cotisationRepository;
	@Autowired
	private IEncaissementRepository encaissementRepository;
	@Autowired
	private ICompteBancaire compteRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private CostumIdGeneratedValueEncaissementEntityRepository costumIdGeneratedValueEncaissementEntityRepository;
	@Autowired
	private IDetailEncaisseementRepository detailEncaisseementRepository;
	@Autowired
	private CostumIdGeneratedValueBordereauRepository bordereauIdGeneratorRepository;
	@Autowired
	private IBordereauRepository bordereauRepository;
	@Autowired
	private IPointVenteRepository pointVenteRepository;

	@Override
	@Async("asyncExecutor")
	public EncaissementResponseModel createEncaissement(CreateAndUpdateEncaissement model)
			throws InterruptedException, ExecutionException {
		log.info("creation encaissement ...");

		Thread.sleep(1000L);
		Encaissement encaissement = new Encaissement();
		// encaissement numero
		CostumIdGeneratedValueEncaissementEntity autoDataBaseGeneratedIdEncaissemnt = new CostumIdGeneratedValueEncaissementEntity();
		encaissement.setNumEncaissement(
				this.costumIdGeneratedValueEncaissementEntityRepository.save(autoDataBaseGeneratedIdEncaissemnt)
						.getId());
		Optional<Cotisation> type = this.cotisationRepository.findById(model.getCotisation());
		Optional<CompteBancaire> compte = compteRepository.findById(model.getCompteBancaire());

		if (type.isPresent() && compte.isPresent()) {

			Cotisation cot = type.get();

			cot.setSolde(cot.getSolde() - model.getMontantEncaissement());
			if (cot.getSolde() != 0) {
				cot.setEtatCotisation(EtatCotisation.PARTIELEMENT_ENCAISSEE);
			} else {
				cot.setEtatCotisation(EtatCotisation.ENCAISSEE);
			}
			this.cotisationRepository.save(cot);
			encaissement.setCompteBancaire(compte.get());
			encaissement.setCotisation(type.get());
			BeanUtils.copyProperties(model, encaissement);
			return modelMapper.map(encaissementRepository.save(encaissement), EncaissementResponseModel.class);

		} else {
			log.error("la creation d'encaissement est erronée");
			throw new NullPointerException("la creation d'encaissement est erronée");
		}
	}

	@Override
	@Async("asyncExecutor")
	public List<EncaissementResponseModel> encaissementByCotisation(String id)
			throws InterruptedException, ExecutionException {

		Thread.sleep(1000L);
		return encaissementRepository.findByCotisationId(id)
				.stream()
				.map(o -> modelMapper.map(o, EncaissementResponseModel.class))
				.collect(Collectors.toList());
	}

	@Async("asyncExecutor")
	@Override
	public void EncaissementLot(EncaissementGroupeRequestModel model) throws InterruptedException, ExecutionException {
		log.info("service impele in" + model.getNumeroLot());
		List<Encaissement> encaissements = new ArrayList<>();
		List<Cotisation> updatedCotisations = new ArrayList<>();
		List<Cotisation> cotisations = cotisationRepository.getCotisationByNumeroLot(model.getNumeroLot());
		if (!cotisations.isEmpty()) { // add compte and other conditions
			DetailEncaissement detailEncaissement = new DetailEncaissement();
			EmissionGlobale thisemissionGlobale = emissionGlobaleRepo.findByNumeroLot(model.getNumeroLot());
			int counterCotisation = 0;
			for (Cotisation cotisation : cotisations) {
				log.info("id : " + cotisation.getId());
				counterCotisation++;
				CostumIdGeneratedValueEncaissementEntity autoDataBaseGeneratedIdEncaissemnt = new CostumIdGeneratedValueEncaissementEntity();
				Encaissement encaissement = new Encaissement();
				encaissement.setNumEncaissement(
						this.costumIdGeneratedValueEncaissementEntityRepository.save(autoDataBaseGeneratedIdEncaissemnt)
								.getId());
				cotisation.setSolde(0);
				cotisation.setEtatCotisation(EtatCotisation.ENCAISSEE);
				// cotisationRepository.save(cotisation);
				updatedCotisations.add(cotisation);
				encaissement.setDateEncaissement(model.getDateEncaissement());
				encaissement.setModeEncaissement(model.getModeEncaissement());
				encaissement.setNumReference(model.getRefEncaissement());
				encaissement.setCotisation(cotisation);
				// ces montants et leurs formules sont a vérifier
				encaissement.setAccessoire(cotisation.getMontantAccessoire());
				encaissement.setMontantTaxe(cotisation.getMontantTaxe());
				encaissement.setMontantTaxeParafiscale(cotisation.getMontantTaxeParaFiscale());
				encaissement.setMontantEmission(cotisation.getMontantCotisation() - cotisation.getMontantAccessoire());
				encaissement.setMontantEncaissement(cotisation.getMontantTTC());
				// !!
				encaissement.setMontantQuittance(cotisation.getMontantTTC());
				thisemissionGlobale.setEtat(true);
				// encaissementRepository.save(encaissement);
				encaissements.add(encaissement);
				// Optional<CompteBancaire>
				// compteBancaire=compteRepository.findById(model.getNumeroCompte());
			}
			detailEncaissement.setCompteBancaire(model.getNumeroCompte()); // to fix
			detailEncaissement.setMontantEncaissement(thisemissionGlobale.getMontantTTC());
			detailEncaissement.setExercice("Date"); // to fix
			detailEncaissement.setCumEncaissement(counterCotisation);
			detailEncaissement.setNumeroLot(model.getNumeroLot());
			detailEncaisseementRepository.save(detailEncaissement);
			encaissementRepository.saveAll(encaissements);
			cotisationRepository.saveAll(updatedCotisations);
			log.info("succes");
		} else {
			log.error("la creation d'encaissement groupe est erronée");
			throw new NullPointerException("Encaissement erronée");
		}
	}

	@Override
	public List<Encaissement> genererBordereauEncaissement(String partenaireId, String dateEncaissement,
			String compteBancaireId) throws InterruptedException, ExecutionException {
		 List<Encaissement> encaissementBordereau= encaissementRepository.findForBordereau(partenaireId,dateEncaissement,compteBancaireId);
		 log.info(""+encaissementBordereau);
		 if(!encaissementBordereau.isEmpty() && encaissementBordereau!=null){
			BordereauxEncaissement bordereauuEncaissement=new BordereauxEncaissement();
			BordereauxEncaissement bordereauEncaissement=this.bordereauRepository.save(bordereauuEncaissement);
		    Double montantTotale=0.0;
		 //generation Réference Bordereau
		 	CostumIdGeneratedValueBordereauEntity autoDataBaseGeneratedIdLot = new CostumIdGeneratedValueBordereauEntity();
		 	CostumIdGeneratedValueBordereauEntity id = this.bordereauIdGeneratorRepository
						.save(autoDataBaseGeneratedIdLot);
			bordereauEncaissement.setRefBordereau("TTT"+id.getId());
			for (Encaissement encaissement:encaissementBordereau){
				log.info(encaissement.getId());
				montantTotale+=encaissement.getMontantEncaissement();
				encaissement.setBordereauEncaissement(bordereauEncaissement); 
				encaissement.setFlagBordereau(true);
			}
			String montantTotal = Double.toString(montantTotale);
			CompteBancaire compteBancaire=compteRepository.findById(compteBancaireId).get();
			String pointVente=compteBancaire.getPointVente().getId();
			bordereauEncaissement.setCompteBancaire(compteBancaire.getId());
			bordereauEncaissement.setMontantTotal(montantTotal);
			bordereauEncaissement.setPointVente(pointVente);
			//bordereauRepository.save(bordereauEncaissement);
			//return encaissementBordereau;
		 }
		 return encaissementBordereau;
		//  else{
		// 	throw new NullPointerException("Creation Bordereau Erroné"); 
		//  }
		}

	@Override
	public List<BordereauEncaissementRequestModel> getAllBordereaux() throws InterruptedException, ExecutionException {
		Iterable<BordereauxEncaissement> bordereauxEncaissements= bordereauRepository.findAll();
		List<BordereauEncaissementRequestModel> bordereauRequests = new ArrayList<>();
		String compteName;
		String pointVentename;
		for(BordereauxEncaissement bordereauxEncaissement:bordereauxEncaissements){
			compteName=compteRepository.findById(bordereauxEncaissement.getCompteBancaire()).get().getCode();
			pointVentename=pointVenteRepository.findById(bordereauxEncaissement.getPointVente()).get().getLibelle();
			BordereauEncaissementRequestModel requestModel=new BordereauEncaissementRequestModel();
			requestModel.setCompteBancaire(compteName);
			requestModel.setPointVente(pointVentename);
			requestModel.setCreationDate(bordereauxEncaissement.getCreationDate());
			requestModel.setMontantTotal(bordereauxEncaissement.getMontantTotal());
			requestModel.setRefBordereau(bordereauxEncaissement.getRefBordereau());
			requestModel.setId(bordereauxEncaissement.getId());
			bordereauRequests.add(requestModel);
		}
		return bordereauRequests;
	}

}
