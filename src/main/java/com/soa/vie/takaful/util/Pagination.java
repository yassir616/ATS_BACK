package com.soa.vie.takaful.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

public class Pagination {

    private Pagination(){
        throw new IllegalStateException("Utility class");
    }
    public static Pageable pageableRequest(int page, int limit, String sort, String direction) {
        Direction dir;
        if ("asc".equals(direction)) {
            dir = Direction.ASC;
        } else
            dir = Direction.DESC;

        return PageRequest.of(page, limit, dir, sort);
    }
}