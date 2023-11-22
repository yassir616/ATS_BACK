package com.soa.vie.takaful.responsemodels;

import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.util.AccountStateEnum;
import com.soa.vie.takaful.util.VoisEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonneResponseModel extends AbstractBaseEntity {

    private String logo;

    private String adressPays;

    private String adressVille;

    private VoisEnum adressVois;

    private String adressComplement;

    private String adressCodePostal;

    private String adressNumero;

    private String adressComplete;

    private String numeroDeTelephone;

    private AccountStateEnum status = AccountStateEnum.ACTIVE;

    public void deleteParticipant() {
        this.status = AccountStateEnum.DELETED;
    }
}