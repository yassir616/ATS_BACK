package com.soa.vie.takaful.services.mapper;

import com.soa.vie.takaful.entitymodels.Cotisation;
import com.soa.vie.takaful.entitymodels.CotisationDTO;
import com.soa.vie.takaful.requestmodels.CotisationRequestDTO;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CotisationMapper extends BaseMapper<Cotisation, CotisationRequestDTO> {

    public CotisationMapper(ModelMapper modelMapper) {
        super(modelMapper);
        configureMappings();
    }

    @Override
    public void configureMappings() {
        getModelMapper().getConfiguration().setPropertyCondition(Conditions.isNotNull());
    }

    @Override
    public CotisationRequestDTO map(Cotisation source, Class<CotisationRequestDTO> destinationType) {
        CotisationRequestDTO result = super.map(source, destinationType);

        if (source.getContrat() != null ) {
            result.setNumeroContrat(source.getContrat().getNumeroContrat());
            result.setDateEffet(source.getContrat().getDateEffet());
            result.setDateEcheance(source.getContrat().getDateEcheance());
            if (source.getContrat().getProduit() != null ) {
                result.setProduitLibelle(source.getContrat().getProduit().getLibelle());
            }
            if(source.getContrat().getAssure()!=null){
                result.setNomAssuree(source.getContrat().getAssure().getNom());
                result.setPrenomAssuree(source.getContrat().getAssure().getPrenom());
            }
            
        }

        return result;
    }
}