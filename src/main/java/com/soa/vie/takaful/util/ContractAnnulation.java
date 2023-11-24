package com.soa.vie.takaful.util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.soa.vie.takaful.entitymodels.Contract;
import com.soa.vie.takaful.repositories.IDecesContratRepository;

@Component
public class ContractAnnulation {
    @Autowired
    IDecesContratRepository contractDecesRepos;

    @Scheduled(cron = "0 0 12 * * ?")

    public void AnnulateContract() {
        Date now = new Date();
        Calendar calendarNow = Calendar.getInstance();
        Calendar calendarOfCreationDate = Calendar.getInstance();
        calendarNow.setTime(now);
        List<Contract> listContract = contractDecesRepos.selectNoAcceptContractDeces();
        for (Contract contract : listContract) {
            calendarOfCreationDate.setTime(contract.getCreationDate());

            if (calendarOfCreationDate.get(Calendar.MONTH) < calendarNow.get(Calendar.MONTH)
                    || calendarOfCreationDate.get(Calendar.YEAR) < calendarNow.get(Calendar.YEAR)) {

                //contractDecesRepos.updateContratDecesStatus(contract.getId());

            } else if (calendarOfCreationDate.get(Calendar.MONTH) == calendarNow.get(Calendar.MONTH)
                    && calendarOfCreationDate.get(Calendar.YEAR) == calendarNow.get(Calendar.YEAR)
                    && calendarNow.get(Calendar.DAY_OF_MONTH)
                            - calendarOfCreationDate.get(Calendar.DAY_OF_MONTH) >= 5) {

                //contractDecesRepos.updateContratDecesStatus(contract.getId());

            }

        }

    }
}
