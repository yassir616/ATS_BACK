package com.soa.vie.takaful.web;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.entitymodels.Notification;
import com.soa.vie.takaful.entitymodels.NotificationSinistre;
import com.soa.vie.takaful.entitymodels.Role;
import com.soa.vie.takaful.entitymodels.TakafulUser;
import com.soa.vie.takaful.requestmodels.NotificationRequestModel;
import com.soa.vie.takaful.responsemodels.NotificationModelResponse;
import com.soa.vie.takaful.requestmodels.NotificationSinistreRequestModel;
import com.soa.vie.takaful.responsemodels.NotificationSinistreModelResponse;
import com.soa.vie.takaful.security.SecurityCanstants;
import com.soa.vie.takaful.servicesImplementation.NotificationServiceImpl;
import com.soa.vie.takaful.servicesImplementation.NotificationSinistreServiceImpl;
import com.soa.vie.takaful.servicesImplementation.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/private")
@Slf4j
public class NotificationController {

    @Autowired
    private NotificationServiceImpl notificationService;

    @Autowired
    private NotificationSinistreServiceImpl notificationSinistreService;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/notifications")
    public List<Notification> getNotificationsByUser(@RequestParam("page") final int page,
            @RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") final int limit)
            throws InterruptedException, ExecutionException {

        log.debug("inside getNotificationsByUser api for fetch user notification ");

        Object authentication = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TakafulUser user = userService.getUser(authentication.toString());
        boolean isAdmin = false;
        for (Role role : user.getRoles()) {
            if (role.getName().equals(SecurityCanstants.ADMIN))
                isAdmin = true;
        }
        if (isAdmin) {
            return notificationService.getAll();
        } else
            return notificationService.findByUser(user);
    }

    @GetMapping("/notificationsSinistre")
    public List<NotificationSinistre> getNotificationsSinistreByUser(@RequestParam("page") final int page,
            @RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") final int limit)
            throws InterruptedException, ExecutionException {

        log.debug("inside getNotificationsSinistreByUser api for fetch user notification ");

        Object authentication = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TakafulUser user = userService.getUser(authentication.toString());
        boolean isAdmin = false;
        for (Role role : user.getRoles()) {
            if (role.getName().equals(SecurityCanstants.ADMIN))
                isAdmin = true;
        }
        if (isAdmin) {
            return notificationSinistreService.getAll();
        } else
            return notificationSinistreService.findByUser(user);
    }

    @PutMapping("/notification/{id}")
    public NotificationModelResponse updateUserNotification(@PathVariable String id,
            @RequestBody NotificationRequestModel notificationRequestModel)
            throws InterruptedException, ExecutionException {

        log.debug("inside updateUserNotification api ");

        return this.notificationService.updateUserNotification(id, notificationRequestModel);
    }

    @PutMapping("/notificationSinistre/{id}")
    public NotificationSinistreModelResponse updateUserNotificationSinistre(@PathVariable String id,
            @RequestBody NotificationSinistreRequestModel notificationSinistreRequestModel)
            throws InterruptedException, ExecutionException {

        log.debug("inside updateUserNotificationSinistre api ");

        return this.notificationSinistreService.updateUserNotificationSinistre(id, notificationSinistreRequestModel);
    }

}
