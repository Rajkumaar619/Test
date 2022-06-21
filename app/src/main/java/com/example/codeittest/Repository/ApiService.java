package com.example.codeittest.Repository;

import com.example.codeittest.Models.PostDetailModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("posts")
    Call<String> getAllData();

    @GET("users/")
    Call<ArrayList<PostDetailModel>> getAuthor(@Query("userId") int userId);


}
