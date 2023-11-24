package com.soa.vie.takaful.services.mapper;

import com.soa.vie.takaful.entitymodels.Cotisation;
import com.soa.vie.takaful.requestmodels.CotisationRequestDTO;
import com.soa.vie.takaful.util.bankingService.BaseMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;
@Component
public class CotisationMapper extends BaseMapper<Cotisation, CotisationRequestDTO> {


    @Override
    public ModelMapper createMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(new PropertyMap<Cotisation, CotisationRequestDTO>() {
            @Override
            protected void configure() {
                // Here, let's add a null check for the contrat property
                if (source.getContrat() != null) {
                    map().setNumeroContrat(source.getContrat().getNumeroContrat());
                    map().setDateEffet(source.getContrat().getDateEffet());
                    map().setDateEcheance(source.getContrat().getDateEcheance());
                }
            }
        });

        return modelMapper;
    }
}