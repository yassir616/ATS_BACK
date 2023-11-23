/*package com.soa.vie.takaful.services.mapper;


    import com.soa.vie.takaful.entitymodels.Cotisation;
    import com.soa.vie.takaful.requestmodels.CotisationRequestDTO;
    import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

    @Configuration public class ModelMapperConfig {
        @Bean
        public ModelMapper cotisationMapper() {
            ModelMapper modelMapper = new ModelMapper();

            // Ajoutez une correspondance explicite pour Cotisation.contrat.numeroContrat -> CotisationDTO.numeroContrat
            modelMapper.addMappings(new PropertyMap<Cotisation, CotisationRequestDTO>() {
                @Override
                protected void configure() {
                    map().setNumeroContrat(source.getContrat().getNumeroContrat());
                }
            });

            return modelMapper;
        }
    }

}
*/