package com.soa.vie.takaful.services.Impl;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.entitymodels.Notification;
import com.soa.vie.takaful.entitymodels.TakafulUser;
import com.soa.vie.takaful.repositories.INotificationRepository;
import com.soa.vie.takaful.requestmodels.NotificationRequestModel;
import com.soa.vie.takaful.responsemodels.NotificationModelResponse;
import com.soa.vie.takaful.util.Pagination;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationServiceImpl {

    @Autowired
    private INotificationRepository notificationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Async("asyncExecutor")
    public NotificationModelResponse updateUserNotification(String id, NotificationRequestModel notificationModel)
            throws InterruptedException {
        Thread.sleep(1000L);
        Optional<Notification> notificationOpt = this.notificationRepository.findById(id);

        if (notificationOpt.isPresent()) {
            Notification notification = new Notification();
            try {
                notification.setId(id);
                notification.setCreationDate(notificationOpt.get().getCreationDate());
                notification.setFluxId(notificationOpt.get().getFluxId());
                notification.setMessage(notificationModel.getMessage());
                notification.setRead(notificationModel.isRead());
                notification.setUser(notificationOpt.get().getUser());
                return modelMapper.map(save(notification), NotificationModelResponse.class);
            } catch (Exception e) {
                log.error("NotificationNotUpdated", e);
            }
        }
        return null;
    }

    @Async("asyncExecutor")
    public Notification save(Notification notification) throws InterruptedException, ExecutionException {
        Thread.sleep(1000L);
        try {
            return this.notificationRepository.save(notification);
        } catch (Exception e) {
            log.error("Exception occur while save Notification ", e);
            return null;
        }
    }

    @Async("asyncExecutor")
    public List<Notification> findByUser(TakafulUser user) {
        try {
            return notificationRepository.findByUser(user);
        } catch (Exception e) {
            log.error("Exception occur while fetch Notification by User 12 ", e);
            return null;
        }
    }

    @Async("asyncExecutor")
    public Page<NotificationModelResponse> findByUser(int page, int limit, String sort, String direction,
            TakafulUser user) throws InterruptedException, ExecutionException {
        Thread.sleep(1000L);
        try {
            return notificationRepository.findByUser(Pagination.pageableRequest(page, limit, sort, direction), user)
                    .map(o -> modelMapper.map(o, NotificationModelResponse.class));
        } catch (Exception e) {
            log.error("Exception occur while fetch Notification by User ", e);
            return null;
        }
    }

    @Async("asyncExecutor")
    public List<Notification> getAll() throws InterruptedException, ExecutionException {
        Thread.sleep(1000L);
        String statut = "ACTIVE";
        try {
            return notificationRepository.findByStatut(statut);
        } catch (Exception e) {
            log.error("Exception occur while fetch Notification by User ", e);
            return null;
        }
    }

    @Async("asyncExecutor")
    public Notification createNotificationObject(String message, TakafulUser user)
            throws InterruptedException, ExecutionException {
        Thread.sleep(1000L);
        return new Notification(message, user);
    }

    @Async("asyncExecutor")
    public Notification findByUserAndNotificationId(TakafulUser user, String notificationId)
            throws InterruptedException, ExecutionException {
        Thread.sleep(1000L);
        try {
            return notificationRepository.findByUserAndId(user, notificationId);
        } catch (Exception e) {
            log.error("Exception occur while fetch Notification by User and Notification Id ", e);
            return null;
        }
    }
}