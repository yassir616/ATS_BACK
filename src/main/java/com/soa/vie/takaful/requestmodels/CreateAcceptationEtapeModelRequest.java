package com.soa.vie.takaful.requestmodels;

import java.util.Date;

import com.soa.vie.takaful.entitymodels.AcceptationReassurance;
import com.soa.vie.takaful.entitymodels.AcceptationTestMedical;
import com.soa.vie.takaful.entitymodels.Verdict;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAcceptationEtapeModelRequest {
    
    private String etape;

    private Date dateVerdict;

    private Verdict verdict;

    private AcceptationTestMedical acceptationTestMedical;

    private AcceptationReassurance acceptationReassurance;

}