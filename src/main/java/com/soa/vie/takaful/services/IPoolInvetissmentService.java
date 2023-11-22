package com.soa.vie.takaful.services;

import com.soa.vie.takaful.requestmodels.CreateUpdateOneStringRequestModel;
import com.soa.vie.takaful.responsemodels.PoolInvestissmentResponseModel;

import org.bouncycastle.openssl.EncryptionException;
import org.springframework.data.domain.Page;

public interface IPoolInvetissmentService {
    PoolInvestissmentResponseModel createPoolInvestissment(CreateUpdateOneStringRequestModel model) throws InterruptedException, EncryptionException;
    PoolInvestissmentResponseModel updatePoolInvestissment(CreateUpdateOneStringRequestModel model, String id) throws InterruptedException, EncryptionException;
    Page<PoolInvestissmentResponseModel> getPoolInvestissments(int page, int limit, String sort, String direction) throws InterruptedException, EncryptionException;
}
