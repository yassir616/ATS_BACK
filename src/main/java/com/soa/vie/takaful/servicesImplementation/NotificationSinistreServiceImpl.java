package com.soa.vie.takaful.servicesImplementation;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.entitymodels.NotificationSinistre;
import com.soa.vie.takaful.entitymodels.TakafulUser;
import com.soa.vie.takaful.repositories.INotificationSinistreRepository;
import com.soa.vie.takaful.requestmodels.NotificationSinistreRequestModel;
import com.soa.vie.takaful.responsemodels.NotificationSinistreModelResponse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationSinistreServiceImpl {

    @Autowired
    private INotificationSinistreRepository notificationSinistreRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    @Async("asyncExecutor")
    public NotificationSinistre createNotificationObject(String message, TakafulUser user)
            throws InterruptedException, ExecutionException {
        Thread.sleep(1000L);
        return new NotificationSinistre(message, user);
    }

    @Async("asyncExecutor")
    public NotificationSinistre save(NotificationSinistre notificationSinistre) throws InterruptedException, ExecutionException {
        Thread.sleep(1000L);
        try {
            return this.notificationSinistreRepository.save(notificationSinistre);
        } catch (Exception e) {
            log.error("Exception occur while save Notification ", e);
            return null;
        }
    }

    @Async("asyncExecutor")
    public List<NotificationSinistre> getAll() throws InterruptedException, ExecutionException {
        Thread.sleep(1000L);
        String statut = "ACTIVE";
        try {
            return notificationSinistreRepository.findByStatut(statut);
        } catch (Exception e) {
            log.error("Exception occur while fetch Notification by User ", e);
            return null;
        }
    }

    @Async("asyncExecutor")
    public List<NotificationSinistre> findByUser(TakafulUser user) {
        try {
            return notificationSinistreRepository.findByUser(user);
        } catch (Exception e) {
            log.error("Exception occur while fetch Notification by User 12 ", e);
            return null;
        }
    }

    @Async("asyncExecutor")
    public NotificationSinistreModelResponse updateUserNotificationSinistre(String id, NotificationSinistreRequestModel notificationSinistreModel)
            throws InterruptedException {
        Thread.sleep(1000L);
        Optional<NotificationSinistre> notificationOpt = this.notificationSinistreRepository.findById(id);

        if (notificationOpt.isPresent()) {
            NotificationSinistre notificationSinistre = new NotificationSinistre();
            try {
                notificationSinistre.setId(id);
                notificationSinistre.setCreationDate(notificationOpt.get().getCreationDate());
                notificationSinistre.setFluxId(notificationOpt.get().getFluxId());
                notificationSinistre.setMessage(notificationSinistreModel.getMessage());
                notificationSinistre.setRead(notificationSinistreModel.isRead());
                notificationSinistre.setUser(notificationOpt.get().getUser());
                return modelMapper.map(save(notificationSinistre), NotificationSinistreModelResponse.class);
            } catch (Exception e) {
                log.error("NotificationSinistreNotUpdated", e);
            }
        }
        return null;
    }




    
}
