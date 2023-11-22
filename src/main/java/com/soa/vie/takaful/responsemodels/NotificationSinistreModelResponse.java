package com.soa.vie.takaful.responsemodels;

import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationSinistreModelResponse extends AbstractBaseEntity{
    
    private String message;

    private boolean isRead;
}
