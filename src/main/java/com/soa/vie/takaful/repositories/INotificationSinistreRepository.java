package com.soa.vie.takaful.repositories;

import java.util.List;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.soa.vie.takaful.entitymodels.NotificationSinistre;
import com.soa.vie.takaful.entitymodels.TakafulUser;

@Repository
@JaversSpringDataAuditable
public interface INotificationSinistreRepository extends PagingAndSortingRepository<NotificationSinistre, String> {
    public List<NotificationSinistre> findByUser(TakafulUser user);

    @Query(value = "select * from notification_sinistre n join takaful_user tu on tu.id = n.user_id where tu.status= ? and n.is_read='0'",nativeQuery = true)
    public List<NotificationSinistre> findByStatut(String statut);
}
