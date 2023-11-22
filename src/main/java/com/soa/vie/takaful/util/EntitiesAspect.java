package com.soa.vie.takaful.util;

import java.sql.Timestamp;

import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.AuditRecord;
import com.soa.vie.takaful.repositories.IAuditRecordRepository;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class EntitiesAspect {

    @Autowired
    private IAuditRecordRepository entitiesAspectRepository;

    @AfterReturning(value = "execution(* com.soa.vie.takaful.services.*.create*(..)) ", returning = "result")
    public void afterCreation(JoinPoint joinPoint,Object result) {
        log.info("After method:" + joinPoint.getSignature());
        AbstractBaseEntity object = (AbstractBaseEntity)result;
        Object authentication = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Timestamp timestamp = new Timestamp(new java.util.Date().getTime());
 
            AuditRecord auditRecord = new AuditRecord();
            auditRecord.setObjectId(object.getId());
            auditRecord.setUsername(authentication.toString());
            auditRecord.setTableName(object.getClass().getSimpleName());
            auditRecord.setAction(ActionEnum.CREATE);
            auditRecord.setUpdateDate(timestamp);
            auditRecord.setCreationDate(object.getCreationDate());
            this.entitiesAspectRepository.save(auditRecord);
        
    }

    @AfterReturning(value = "execution(* com.soa.vie.takaful.services.*.update*(..)) ", returning = "result")
    public void afterUpdate(JoinPoint joinPoint,Object result) {

        AbstractBaseEntity object = (AbstractBaseEntity)result;
        Object authentication = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Timestamp timestamp = new Timestamp(new java.util.Date().getTime());
 
            AuditRecord auditRecord = new AuditRecord();
            auditRecord.setObjectId(object.getId());
            auditRecord.setUsername(authentication.toString());
            auditRecord.setTableName(object.getClass().getSimpleName());
            auditRecord.setAction(ActionEnum.UPDATE);
            auditRecord.setUpdateDate(timestamp);
            auditRecord.setCreationDate(object.getCreationDate());
            this.entitiesAspectRepository.save(auditRecord);
        
    }
}
