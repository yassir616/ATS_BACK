package com.soa.vie.takaful.servicesImplementation;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.soa.vie.takaful.entitymodels.Acceptation;
import com.soa.vie.takaful.entitymodels.AcceptationEtape;
import com.soa.vie.takaful.entitymodels.AcceptationReassurance;
import com.soa.vie.takaful.entitymodels.Notification;
import com.soa.vie.takaful.entitymodels.TakafulUser;
import com.soa.vie.takaful.repositories.IAcceptationEtapeRepository;
import com.soa.vie.takaful.repositories.IAcceptationReassuranceRepository;
import com.soa.vie.takaful.repositories.IAcceptationRepository;
import com.soa.vie.takaful.repositories.IDecesContratRepository;
import com.soa.vie.takaful.requestmodels.CreateReassuranceModel;
import com.soa.vie.takaful.requestmodels.CreateallAcceptationTestKindModelRequest;

import com.soa.vie.takaful.responsemodels.AcceptationReassuranceModelResponse;
import com.soa.vie.takaful.services.IAcceptationReassuranceService;
import com.soa.vie.takaful.util.AcceptationStatus;
import com.soa.vie.takaful.util.Constants;
import com.soa.vie.takaful.util.ContratStatus;
import com.soa.vie.takaful.util.NotificationMessages;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AcceptationReassuranceServiceImpl implements IAcceptationReassuranceService {

        @Autowired
        private IAcceptationReassuranceRepository acceptationReassuranceRepository;

        @Autowired
        private IAcceptationEtapeRepository acceptationEtapeRepository;

        @Autowired
        private IAcceptationRepository acceptationRepository;

        @Autowired
        private ModelMapper modelMapper;
        @Autowired
        private NotificationServiceImpl notificationServiceImpl;

        @Autowired
        private UserServiceImpl userServiceImpl;
        @Autowired
        private IDecesContratRepository contratRepository;

        @Override
        @Async("asyncExecutor")
        public AcceptationReassuranceModelResponse createAcceptationReassurance(
                        CreateallAcceptationTestKindModelRequest model)
                        throws InterruptedException, ExecutionException {
                Thread.sleep(1000L);
                AcceptationReassurance acceptationReassurance = new AcceptationReassurance();
                if (model.getVerdict().getStatus().equals(Constants.ACCEPTATION_AU_TARIF_NORMAL)) {

                        model.getAcceptation().setStatus(AcceptationStatus.DONE);
                        model.getAcceptation().getContrat().setStatus(ContratStatus.INSTANTANEE);
                        model.getAcceptation().getContrat().setSurprimeHT(model.getSurprimeHT());
                        model.getAcceptation().getContrat().setTauxSurprime(model.getTauxSurprime());
                        model.getAcceptation().getContrat().setSurprimeTaxe(model.getSurprimeTaxe());
                        model.getAcceptation().getContrat().setMontantSurprime(model.getMontantSurprime());
                        model.getAcceptation().getContrat().setSurprimeTTC(model.getSurprimeTTC());
                        model.getAcceptation().setVerdict(Constants.ACCEPTATION_AU_TARIF_NORMAL);

                        this.contratRepository.save(model.getAcceptation().getContrat());
                        this.acceptationRepository.save(model.getAcceptation());

                        // notification par point de vente de creation de contrat
                        List<TakafulUser> usersByP = this.userServiceImpl
                                        .getUsersByPointVente(
                                                        model.getAcceptation().getContrat().getPointVente().getId());

                        for (TakafulUser userP : usersByP) {
                                Notification notification = this.notificationServiceImpl.createNotificationObject(
                                                NotificationMessages
                                                                .ContratAccepteParMedecinConsielExaminateurMessageNormal(
                                                                                model.getAcceptation().getContrat()
                                                                                                .getNumeroContrat(),
                                                                                model.getAcceptation().getCode(),
                                                                                model.getAcceptation().getContrat()
                                                                                                .getAssure().getNom()),
                                                userP);
                                this.notificationServiceImpl.save(notification);
                        }
                } else if (model.getVerdict().getStatus().equals(Constants.ACCEPTATION_AVEC_SURPRIME)) {

                        model.getAcceptation().setStatus(AcceptationStatus.DONE);
                        model.getAcceptation().getContrat().setStatus(ContratStatus.INSTANTANEE);
                        model.getAcceptation().getContrat().setSurprimeHT(model.getSurprimeHT());
                        model.getAcceptation().getContrat().setTauxSurprime(model.getTauxSurprime());
                        model.getAcceptation().getContrat().setSurprimeTaxe(model.getSurprimeTaxe());
                        model.getAcceptation().getContrat().setMontantSurprime(model.getMontantSurprime());
                        model.getAcceptation().getContrat().setSurprimeTTC(model.getSurprimeTTC());
                        model.getAcceptation().setVerdict(Constants.ACCEPTATION_AVEC_SURPRIME);

                        this.contratRepository.save(model.getAcceptation().getContrat());
                        this.acceptationRepository.save(model.getAcceptation());

                        // notification par point de vente de creation de contrat
                        List<TakafulUser> usersByP = this.userServiceImpl
                                        .getUsersByPointVente(
                                                        model.getAcceptation().getContrat().getPointVente().getId());
                        for (TakafulUser userP : usersByP) {
                                Notification notification = this.notificationServiceImpl.createNotificationObject(
                                                NotificationMessages
                                                                .ContratAccepteParMedecinConsielExaminateurMessageAvecSupp(
                                                                                model.getAcceptation().getContrat()
                                                                                                .getNumeroContrat(),
                                                                                model.getAcceptation().getCode(),
                                                                                model.getAcceptation().getContrat()
                                                                                                .getAssure().getNom()),
                                                userP);
                                this.notificationServiceImpl.save(notification);
                        }
                } else if (model.getVerdict().getStatus().equals(Constants.ACCEPTATION_AVEC_RENONCIATION)) {

                        model.getAcceptation().setStatus(AcceptationStatus.DONE);
                        model.getAcceptation().getContrat().setStatus(ContratStatus.INSTANTANEE);
                        model.getAcceptation().getContrat().setSurprimeHT(model.getSurprimeHT());
                        model.getAcceptation().getContrat().setTauxSurprime(model.getTauxSurprime());
                        model.getAcceptation().getContrat().setSurprimeTaxe(model.getSurprimeTaxe());
                        model.getAcceptation().getContrat().setMontantSurprime(model.getMontantSurprime());
                        model.getAcceptation().getContrat().setSurprimeTTC(model.getSurprimeTTC());
                        model.getAcceptation().setVerdict(Constants.ACCEPTATION_AVEC_RENONCIATION);

                        this.contratRepository.save(model.getAcceptation().getContrat());
                        this.acceptationRepository.save(model.getAcceptation());

                        // notification par point de vente de creation de contrat
                        List<TakafulUser> usersByP = this.userServiceImpl
                                        .getUsersByPointVente(
                                                        model.getAcceptation().getContrat().getPointVente().getId());
                        for (TakafulUser userP : usersByP) {
                                Notification notification = this.notificationServiceImpl.createNotificationObject(
                                                NotificationMessages
                                                                .ContratAccepteParMedecinConsielExaminateurMessageAvecRenon(
                                                                                model.getAcceptation().getContrat()
                                                                                                .getNumeroContrat(),
                                                                                model.getAcceptation().getCode(),
                                                                                model.getAcceptation().getContrat()
                                                                                                .getAssure().getNom()),
                                                userP);
                                this.notificationServiceImpl.save(notification);
                        }
                } else if (model.getVerdict().getStatus().equals(Constants.REJET)) {

                        model.getAcceptation().setStatus(AcceptationStatus.DONE);
                        model.getAcceptation().getContrat().setStatus(ContratStatus.REJETE);
                        this.contratRepository.save(model.getAcceptation().getContrat());
                        this.acceptationRepository.save(model.getAcceptation());

                        // notification par point de vente de creation de contrat
                        List<TakafulUser> usersByP = this.userServiceImpl
                                        .getUsersByPointVente(
                                                        model.getAcceptation().getContrat().getPointVente().getId());
                        for (TakafulUser userP : usersByP) {
                                Notification notification = this.notificationServiceImpl.createNotificationObject(
                                                NotificationMessages
                                                                .ContratAccepteParMedecinConsielExaminateurMessageRejet(
                                                                                model.getAcceptation().getContrat()
                                                                                                .getNumeroContrat(),
                                                                                model.getAcceptation().getCode(),
                                                                                model.getAcceptation().getContrat()
                                                                                                .getAssure().getNom()),
                                                userP);
                                this.notificationServiceImpl.save(notification);
                        }
                }

                BeanUtils.copyProperties(model, acceptationReassurance);
                return modelMapper.map(acceptationReassuranceRepository.save(acceptationReassurance),
                                AcceptationReassuranceModelResponse.class);
        }

        @Override
        @Async("asyncExecutor")
        public List<AcceptationReassuranceModelResponse> acceptationReassuranceByAcceptation(String acceptationId)
                        throws InterruptedException, ExecutionException {
                Thread.sleep(1000L);
                Optional<Acceptation> acceptationOptional = acceptationRepository.findById(acceptationId);
                if (!acceptationOptional.isPresent()) {
                        throw new NoSuchElementException("ListAcceptationReassurance : acceptation n'existe pas ");
                } else {
                        Acceptation acceptation = acceptationOptional.get();
                        return acceptationReassuranceRepository.findByAcceptation(acceptation).stream()
                                        .map(o -> modelMapper.map(o, AcceptationReassuranceModelResponse.class))
                                        .collect(Collectors.toList());
                }
        }

        @Override
        @Async("asyncExecutor")
        public AcceptationReassuranceModelResponse updateAcceptationReassurance(CreateReassuranceModel model, String id)
                        throws InterruptedException, ExecutionException {
                Thread.sleep(1000L);
                Optional<AcceptationReassurance> acceptationReassurance = acceptationReassuranceRepository.findById(id);

                if (acceptationReassurance.isPresent()) {

                        AcceptationEtape acceptationEtape = acceptationEtapeRepository
                                        .findByEtapeAndAcceptationReassurance("reassurance",
                                                        acceptationReassurance.get());

                        if (!acceptationEtape.getVerdict().getId().equals(model.getVerdict().getId())) {
                                acceptationEtape.setDateVerdict(new Date());
                                acceptationEtape.setVerdict(model.getVerdict());
                                acceptationEtapeRepository.save(acceptationEtape);
                        }

                        AcceptationReassurance acceptationReassurance2 = acceptationReassurance.get();
                        BeanUtils.copyProperties(model, acceptationReassurance2);
                        return modelMapper.map(acceptationReassuranceRepository.save(acceptationReassurance2),
                                        AcceptationReassuranceModelResponse.class);
                }

                throw new NoSuchElementException("wrong data");
        }

}