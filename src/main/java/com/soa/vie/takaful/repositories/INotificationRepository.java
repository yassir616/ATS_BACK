package com.soa.vie.takaful.repositories;

import com.soa.vie.takaful.entitymodels.Notification;
import com.soa.vie.takaful.entitymodels.TakafulUser;

import java.util.List;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface INotificationRepository extends PagingAndSortingRepository<Notification, String> {

    public List<Notification> findByUser(TakafulUser user);

    public Page<Notification> findByUser(Pageable pageable, TakafulUser user);

    @Query(value = "select * from notification n join takaful_user tu on tu.id = n.user_id where tu.status= ? and n.is_read='0'",nativeQuery = true)
    public List<Notification> findByStatut(String statut);

    public Notification findByUserAndId(TakafulUser user, String notificationId);
}