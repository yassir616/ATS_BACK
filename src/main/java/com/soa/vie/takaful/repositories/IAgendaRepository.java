package com.soa.vie.takaful.repositories;


import java.util.List;


import com.soa.vie.takaful.entitymodels.Agenda;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.PagingAndSortingRepository;

@JaversSpringDataAuditable
public interface IAgendaRepository extends PagingAndSortingRepository <Agenda, String> {

	public List<Agenda> findByUserId(String id);

}