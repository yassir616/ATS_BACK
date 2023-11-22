package com.soa.vie.takaful.responsemodels;
import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.util.ActionEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AuditRecordModelResponse extends AbstractBaseEntity {

	/**
	 *
	 */

    private String username;

    private String tableName;

    private Date updateDate;

    private String objectId;

    private ActionEnum  action;
    
}