package com.soa.vie.takaful.services;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.entitymodels.AcceptationTestMedical;
import com.soa.vie.takaful.requestmodels.CreateAcceptationTestMedicalModelRequest;
import com.soa.vie.takaful.responsemodels.AcceptationTestMedicalModelResponse;

public interface IAcceptationTestMedicalService {

    public AcceptationTestMedicalModelResponse createAcceptationTestMedical(
            CreateAcceptationTestMedicalModelRequest model) throws InterruptedException, ExecutionException;

    public AcceptationTestMedicalModelResponse getAcceptationsTestAndLabo(String acceptation, String acceptationLabo) throws InterruptedException, ExecutionException;

    public AcceptationTestMedicalModelResponse getAcceptationsTestAndExaminateur(String acceptation,
            String accexaminateur) throws InterruptedException, ExecutionException;

    public AcceptationTestMedicalModelResponse getAcceptationsTestAndExamens(String acceptation,
            String acceptationExamens) throws InterruptedException, ExecutionException;

    public AcceptationTestMedicalModelResponse getAcceptationsTestAndConseil(String acceptation,
            String acceptationConseil) throws InterruptedException, ExecutionException;

    public AcceptationTestMedical updateAcceptationTestMedical(CreateAcceptationTestMedicalModelRequest model,
            String id) throws InterruptedException, ExecutionException;

    public List<AcceptationTestMedicalModelResponse> getAcceptationsTestByAuxiliare(String auxiliare,
            String auxiliareType, String partenaire) throws InterruptedException, ExecutionException;

}