package com.soa.vie.takaful.responsemodels;

import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PoolInvestissmentResponseModel extends AbstractBaseEntity {
    private String libelle;
}
