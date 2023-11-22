package com.soa.vie.takaful.responsemodels;
import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.Contract;
import com.soa.vie.takaful.entitymodels.TypeAvenant;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class AvenantModelResponse extends AbstractBaseEntity {

    /**
     *
     */

    private Date dateEffet;

    private Integer numeroAvenant;

    
    private Contract contrat;

    
    private TypeAvenant typeAvenant;

}