package com.example.sahan.countdownapp.interfaces;

import com.example.sahan.countdownapp.Model.Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

public interface RestCall {
    @GET("api/times")
    Call<List<Model>> getTimes();

}
