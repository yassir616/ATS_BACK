package com.soa.vie.takaful.entitymodels;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.soa.vie.takaful.util.ActionEnum;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AuditRecord extends AbstractBaseEntity{

	/**
	 *
	 */
	private static final long serialVersionUID = -1041960619313782191L;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String tableName;

    @Column(columnDefinition = "datetime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm")
    private Date updateDate;

    @Column(nullable = false)
    private String objectId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ActionEnum  action;
    
}