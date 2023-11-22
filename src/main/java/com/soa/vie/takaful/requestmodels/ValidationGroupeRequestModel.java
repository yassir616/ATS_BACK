package com.soa.vie.takaful.requestmodels;

import java.util.List;

import com.soa.vie.takaful.responsemodels.CotisationModelResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationGroupeRequestModel {
	List<CotisationModelResponse> listCotisation;
	CreateEmissionGlobale createEmissionGlobale;
	private String reference;
}
