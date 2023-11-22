package com.soa.vie.takaful.batch;

import java.lang.reflect.Type;
import java.util.List;

import com.soa.vie.takaful.entitymodels.Cotisation;
import com.soa.vie.takaful.services.ICotisationService;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CotisationWriterToDB implements ItemWriter<Cotisation> {
    @Autowired
    private ICotisationService cotisationService;

    @Override
    public void write(List<? extends Cotisation> items) throws Exception {
        Type cotisations = new TypeToken<List<Cotisation>>() {
        }.getType();
        List<Cotisation> listCotisations = new ModelMapper().map(items, cotisations);
        
        cotisationService.saveCotisation(listCotisations);
    }
}
