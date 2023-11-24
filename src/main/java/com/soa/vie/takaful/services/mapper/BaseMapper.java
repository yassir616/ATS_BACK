package com.soa.vie.takaful.services.mapper;

import lombok.Getter;
import org.modelmapper.ModelMapper;
@Getter
public abstract class BaseMapper<S, D> {
    private final ModelMapper modelMapper;

    public BaseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public D map(S source, Class<D> destinationType) {
        return modelMapper.map(source, destinationType);
    }

    public abstract void configureMappings();
}
