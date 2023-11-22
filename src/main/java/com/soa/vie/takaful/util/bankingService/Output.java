package com.soa.vie.takaful.util.bankingService;

import java.io.Serializable;

public class Output implements Serializable {

    private String accessToken;

    public Output() {
    }

    public Output(String accessToken) {

        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}