package com.soa.vie.takaful.services.Impl;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.soa.vie.takaful.entitymodels.DetailPrestationHonoraire;
import com.soa.vie.takaful.entitymodels.PrestationHonoraire;
import com.soa.vie.takaful.repositories.IAcceptationTestMedicalRepository;
import com.soa.vie.takaful.repositories.IPrestationHonoraireDetailRepository;
import com.soa.vie.takaful.repositories.IPrestationHonoraireRepository;
import com.soa.vie.takaful.requestmodels.CreateAndUpdatePrestationHonoraireRequestModel;

import com.soa.vie.takaful.responsemodels.PrestationHonoraireResponseModel;
import com.soa.vie.takaful.services.IPrestationHonoraireService;
import com.soa.vie.takaful.util.PrestationStatusEnum;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class PrestationHonoraireServiceImpl implements IPrestationHonoraireService {

    @Autowired
    private IPrestationHonoraireRepository prestationHonoraireRepository;

    @Autowired
    private IPrestationHonoraireDetailRepository prestationHonoraireDetailRepository;

    @Autowired
    private IAcceptationTestMedicalRepository acceptationTestMedicalRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override

    @Async("asyncExecutor")
    public PrestationHonoraireResponseModel createPrestationHonoraire(
            CreateAndUpdatePrestationHonoraireRequestModel model) throws InterruptedException, ExecutionException {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        SecureRandom random = new SecureRandom();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

        model.setNumeroSinistre(generatedString);
        model.setTypePrestation("Honoraire");
        

        PrestationHonoraire prestationHonoraire = new PrestationHonoraire();

        for (DetailPrestationHonoraire element : model.getDetailPrestationHonoraire()) {

            DetailPrestationHonoraire detailPrestation = new DetailPrestationHonoraire();
            BeanUtils.copyProperties(element, detailPrestation);

            element.getAcceptationTestMedical().setPaid(true);
            this.acceptationTestMedicalRepository.save(element.getAcceptationTestMedical());

            this.prestationHonoraireDetailRepository.save(detailPrestation);
        }

        BeanUtils.copyProperties(model, prestationHonoraire);

        return modelMapper.map(prestationHonoraireRepository.save(prestationHonoraire),
                PrestationHonoraireResponseModel.class);
    }

    @Override
    @Async("asyncExecutor")
    public List<PrestationHonoraireResponseModel> getPrestationHonoraire(String status, String produit,
            String auxiliaire, String contrat, String participant, String typeAuxiliaire)
            throws InterruptedException, ExecutionException {

                switch (typeAuxiliaire) {
            case "LABORATOIRE":
                return this.prestationHonoraireRepository.paidPrestationHonooraire("inner join acceptation_laboratoire al on al.id = at.acceptation_laboratoire_id",status, auxiliaire)
                        .stream()
                        .map(o -> modelMapper.map(o, PrestationHonoraireResponseModel.class))
                        .collect(Collectors.toList());
            case "MEDECIN CONSEIL":
                return this.prestationHonoraireRepository.paidPrestationHonooraire("inner join acceptation_conseil al on al.id = at.acceptation_conseil_id",status, auxiliaire)
                        .stream()
                        .map(o -> modelMapper.map(o, PrestationHonoraireResponseModel.class))
                        .collect(Collectors.toList());
            case "MEDECIN EXAMINATEUR":
                return this.prestationHonoraireRepository.paidPrestationHonooraire("inner join  acceptation_examinateur al on al.id = at.acceptation_examinateur_id",status, auxiliaire)
                        .stream()
                        .map(o -> modelMapper.map(o, PrestationHonoraireResponseModel.class))
                        .collect(Collectors.toList());
            case "MEDECIN-ARBITRE":
                return this.prestationHonoraireRepository.paidPrestationHonooraire("inner join acceptation_examens al on al.id = at.acceptation_examens_id ",status, auxiliaire)

                        .stream()
                        .map(o -> modelMapper.map(o, PrestationHonoraireResponseModel.class))
                        .collect(Collectors.toList());
            case "MEDECIN SPECIALISTE":
                    return this.prestationHonoraireRepository.paidPrestationHonooraire("inner join acceptation_specialiste al on al.id = at.acceptation_specialiste_id",status, auxiliaire)
                        .stream()
                        .map(o -> modelMapper.map(o, PrestationHonoraireResponseModel.class))
                        .collect(Collectors.toList());

            case "undefined":
                    if(auxiliaire != "undefined"){
                        return this.prestationHonoraireRepository.paidPrestationHonooraireByStatutAuxiliaire(status,auxiliaire)
                        .stream()
                        .map(o -> modelMapper.map(o, PrestationHonoraireResponseModel.class))
                        .collect(Collectors.toList());
                    }else {
                        return this.prestationHonoraireRepository.paidPrestationHonooraireByStatut(status)
                        .stream()
                        .map(o -> modelMapper.map(o, PrestationHonoraireResponseModel.class))
                        .collect(Collectors.toList());
                    }
            default:
                return new ArrayList<>();
        }

    }
    public PrestationHonoraireResponseModel updateStatusPrestation(String id, String status) {
        PrestationHonoraire prestationHonoraire = this.prestationHonoraireRepository.findById(id).orElseThrow(RuntimeException::new);
        if (status.equals(PrestationStatusEnum.A_SIGNER.getaction())){
            prestationHonoraire.setStatus(PrestationStatusEnum.A_SIGNER);  
        }
        else if(status.equals(PrestationStatusEnum.ANNULE.getaction())){
            prestationHonoraire.setStatus(PrestationStatusEnum.ANNULE);  
        }
        BeanUtils.copyProperties(id, prestationHonoraire);
        return modelMapper.map(prestationHonoraireRepository.save(prestationHonoraire),
        PrestationHonoraireResponseModel.class);
       
    }

    public void updateMontantPrestation(String idDetail,String numeroSinistre,Float montantHonoraire,String typeFiscal) {
      //float montant = model.getmontantHonoraire();

      
       this.prestationHonoraireRepository.updateMontant(montantHonoraire,idDetail);

       if(typeFiscal.equals("IR"))
       {
        this.prestationHonoraireRepository.updateMontantPrestationIR(numeroSinistre);
       }
       else
       {this.prestationHonoraireRepository.updateMontantPrestationIS(numeroSinistre);}



    }


    

}