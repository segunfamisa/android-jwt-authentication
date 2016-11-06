package com.segunfamisa.sample.jwt.network;

import com.google.gson.annotations.SerializedName;

public class Token implements NetworkRequest.ApiResponse {
    @SerializedName("id_token")
    private String idToken;

    public String getIdToken() {
        return idToken;
    }

    @Override
    public String string() {
        return idToken;
    }
}
