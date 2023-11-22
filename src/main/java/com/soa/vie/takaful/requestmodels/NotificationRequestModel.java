package com.soa.vie.takaful.requestmodels;

import com.soa.vie.takaful.entitymodels.TakafulUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequestModel{

    private String message;

    private boolean isRead;

    private TakafulUser user;

}
