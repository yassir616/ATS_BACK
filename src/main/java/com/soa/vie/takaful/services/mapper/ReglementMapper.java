package com.soa.vie.takaful.services.mapper;

import com.soa.vie.takaful.entitymodels.Reglement;
import com.soa.vie.takaful.responsemodels.ReglementResponseDTO;
import com.soa.vie.takaful.services.mapper.BaseMapper;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ReglementMapper extends BaseMapper<Reglement, ReglementResponseDTO> {

    public ReglementMapper(ModelMapper modelMapper) {
        super(modelMapper);
        configureMappings();
    }

    @Override
    public void configureMappings() {
        getModelMapper().getConfiguration().setPropertyCondition(Conditions.isNotNull());
    }

    @Override
    public ReglementResponseDTO map(Reglement source, Class<ReglementResponseDTO> destinationType) {
        ReglementResponseDTO result = super.map(source, destinationType);

        return result;
    }
}
