package com.soa.vie.takaful.services.Impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


import com.soa.vie.takaful.entitymodels.Acceptation;
import com.soa.vie.takaful.entitymodels.AcceptationExaminateur;
import com.soa.vie.takaful.entitymodels.Notification;
import com.soa.vie.takaful.entitymodels.TakafulUser;
import com.soa.vie.takaful.repositories.IAcceptationExaminateurRepository;
import com.soa.vie.takaful.repositories.IAcceptationRepository;
import com.soa.vie.takaful.repositories.IDecesContratRepository;
import com.soa.vie.takaful.requestmodels.CreateallAcceptationTestKindModelRequest;

import com.soa.vie.takaful.responsemodels.AcceptationExaminateurModelResponse;
import com.soa.vie.takaful.services.IAcceptationExaminateurService;
import com.soa.vie.takaful.util.AcceptationStatus;
import com.soa.vie.takaful.util.Constants;
import com.soa.vie.takaful.util.ContratStatus;
import com.soa.vie.takaful.util.NotificationMessages;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AcceptationExaminateurServiceImpl implements IAcceptationExaminateurService {

    @Autowired
    private IAcceptationExaminateurRepository acceptationExaminateurRepository;

    @Autowired
    private IAcceptationRepository acceptationRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IDecesContratRepository contratRepository;
    @Autowired
    private NotificationServiceImpl notificationServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Override

    @Async("asyncExecutor")
    @Transactional
    public AcceptationExaminateurModelResponse createAcceptationExaminateur(
            CreateallAcceptationTestKindModelRequest model) throws InterruptedException, ExecutionException {
        //Thread.sleep(1000L);

        AcceptationExaminateur acceptationExaminateur = new AcceptationExaminateur();

        if (model.getVerdict().getStatus().equals(Constants.ACCEPTATION_AU_TARIF_NORMAL)) {

            model.getAcceptation().setStatus(AcceptationStatus.DONE);
            model.getAcceptation().getContrat().setStatus(ContratStatus.INSTANTANEE);
            model.getAcceptation().setVerdict(Constants.ACCEPTATION_AU_TARIF_NORMAL);

            this.contratRepository.save(model.getAcceptation().getContrat());
            this.acceptationRepository.save(model.getAcceptation());


             // notification par point de vente de creation de contrat
              List<TakafulUser> usersByP = this.userServiceImpl
              .getUsersByPointVente(model.getAcceptation().getContrat().getPointVente().getId());
              for (TakafulUser userP : usersByP) {
               Notification notification = this.notificationServiceImpl.createNotificationObject(NotificationMessages
                  .ContratAccepteParMedecinConsielExaminateurMessageNormal(model.getAcceptation().getContrat().getNumeroContrat(),model.getAcceptation().getCode(),model.getAcceptation().getContrat().getAssure().getNom()),
                  userP);
          this.notificationServiceImpl.save(notification);
                   }

        }else if(model.getVerdict().getStatus().equals(Constants.ACCEPTATION_AVEC_SURPRIME)){
            model.getAcceptation().setStatus(AcceptationStatus.DONE);
            model.getAcceptation().getContrat().setStatus(ContratStatus.INSTANTANEE);
            model.getAcceptation().setVerdict(Constants.ACCEPTATION_AVEC_SURPRIME);

            // notification par point de vente de creation de contrat
            List<TakafulUser> usersByP = this.userServiceImpl
            .getUsersByPointVente(model.getAcceptation().getContrat().getPointVente().getId());
            
            for (TakafulUser userP : usersByP) {
             Notification notification = this.notificationServiceImpl.createNotificationObject(NotificationMessages
                .ContratAccepteParMedecinConsielExaminateurMessageAvecSupp(model.getAcceptation().getContrat().getNumeroContrat(),model.getAcceptation().getCode(),model.getAcceptation().getContrat().getAssure().getNom()),
                userP);
        this.notificationServiceImpl.save(notification);
                 }

          this.contratRepository.save(model.getAcceptation().getContrat());
          this.acceptationRepository.save(model.getAcceptation());

        }else if(model.getVerdict().getStatus().equals(Constants.ACCEPTATION_AVEC_RENONCIATION)){
            model.getAcceptation().setStatus(AcceptationStatus.DONE);
            model.getAcceptation().getContrat().setStatus(ContratStatus.INSTANTANEE);
            model.getAcceptation().setVerdict(Constants.ACCEPTATION_AVEC_RENONCIATION);

             // notification par point de vente de creation de contrat
              List<TakafulUser> usersByP = this.userServiceImpl
              .getUsersByPointVente(model.getAcceptation().getContrat().getPointVente().getId());
              
              for (TakafulUser userP : usersByP) {
               Notification notification = this.notificationServiceImpl.createNotificationObject(NotificationMessages
                  .ContratAccepteParMedecinConsielExaminateurMessageAvecRenon(model.getAcceptation().getContrat().getNumeroContrat(),model.getAcceptation().getCode(),model.getAcceptation().getContrat().getAssure().getNom()),
                  userP);
          this.notificationServiceImpl.save(notification);
                   }

            this.contratRepository.save(model.getAcceptation().getContrat());
            this.acceptationRepository.save(model.getAcceptation());
            
        } else if (model.getVerdict().getStatus().equals(Constants.REJET)) {
            model.getAcceptation().setStatus(AcceptationStatus.DONE);
            model.getAcceptation().getContrat().setStatus(ContratStatus.REJETE);

            // notification par point de vente de creation de contrat
            List<TakafulUser> usersByP = this.userServiceImpl
            .getUsersByPointVente(model.getAcceptation().getContrat().getPointVente().getId());
            
            for (TakafulUser userP : usersByP) {
             Notification notification = this.notificationServiceImpl.createNotificationObject(NotificationMessages
                .ContratAccepteParMedecinConsielExaminateurMessageRejet(model.getAcceptation().getContrat().getNumeroContrat(),model.getAcceptation().getCode(),model.getAcceptation().getContrat().getAssure().getNom()),
                userP);
        this.notificationServiceImpl.save(notification);
                 }

            this.contratRepository.save(model.getAcceptation().getContrat());
            this.acceptationRepository.save(model.getAcceptation());
        }

        BeanUtils.copyProperties(model, acceptationExaminateur);
        return modelMapper.map(acceptationExaminateurRepository.save(acceptationExaminateur),
                AcceptationExaminateurModelResponse.class);
    }

    @Override
    @Async("asyncExecutor")
    public List<AcceptationExaminateurModelResponse> acceptationExaminateurByAcceptation(String acceptationId)
            throws InterruptedException, ExecutionException {
       // Thread.sleep(1000L);
        Optional<Acceptation> acceptationOptional = acceptationRepository.findById(acceptationId);
        if (!acceptationOptional.isPresent()) {
            throw new NoSuchElementException("ListAcceptationExaminateur : acceptation n'existe pas ");
        } else {
            Acceptation acceptation = acceptationOptional.get();

            return acceptationExaminateurRepository.findByAcceptation(acceptation)
                    .stream()
                    .map(
                            o -> modelMapper.map(o, AcceptationExaminateurModelResponse.class))
                    .collect(Collectors.toList());
        }
    }

}