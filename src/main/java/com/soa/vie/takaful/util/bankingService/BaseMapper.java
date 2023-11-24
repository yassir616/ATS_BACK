package com.soa.vie.takaful.util.bankingService;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

public abstract class BaseMapper<S, D> {
    public abstract ModelMapper createMapper();

    public D map(S source, Class<D> destinationType) {
        return createMapper().map(source, destinationType);
    }
}
