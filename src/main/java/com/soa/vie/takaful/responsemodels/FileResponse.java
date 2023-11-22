package com.soa.vie.takaful.responsemodels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileResponse {

    private String name;
    private String url;
    private String type;
    private long size;
}
