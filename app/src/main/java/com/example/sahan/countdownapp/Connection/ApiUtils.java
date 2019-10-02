package com.example.sahan.countdownapp.Connection;

import com.example.sahan.countdownapp.interfaces.RestCall;

public class ApiUtils {
    private ApiUtils() {}

    public static final String BASE_URL = "https://restapi-254719.appspot.com/";

    public static RestCall getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(RestCall.class);
    }
}
