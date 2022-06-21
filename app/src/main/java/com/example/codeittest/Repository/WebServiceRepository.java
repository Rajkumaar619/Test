package com.example.codeittest.Repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.codeittest.Models.PostDetailModel;
import com.example.codeittest.Models.ResultModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class WebServiceRepository {

    Application application;
    private Retrofit retrofit;

    public WebServiceRepository(Application application) {
        this.application = application;
    }

    private static OkHttpClient providesOkHttpClientBuilder() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();
    }

    List<ResultModel> webServiceResponseList = new ArrayList<>();
    ArrayList<PostDetailModel> postDetailList = new ArrayList<>();

    public LiveData<List<ResultModel>> providesWebService() {
        final MutableLiveData<List<ResultModel>> data = new MutableLiveData<>();

        String response = "";

        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiUrl.Base_url)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(providesOkHttpClientBuilder())
                    .build();

            ApiService service = retrofit.create(ApiService.class);
            service.getAllData().enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    webServiceResponseList = parseJson(response.body());
                    PostRoomDBRepository postRoomDBRepository = new PostRoomDBRepository(application);
                    postRoomDBRepository.insertPosts(webServiceResponseList);
                    data.setValue(webServiceResponseList);
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("Repository", "Failed");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    private List<ResultModel> parseJson(String response) {
        List<ResultModel> apiResults = new ArrayList<>();
        JSONObject jsonObject;
        JSONArray jsonArray;

        try {
            jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                ResultModel resultModel = new ResultModel();

                resultModel.setId(object.optInt("id"));
                resultModel.setTitle(object.optString("title"));
                resultModel.setBody(object.optString("body"));
                resultModel.setUserId(object.getInt("userId"));

                apiResults.add(resultModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return apiResults;
    }

    public LiveData<ArrayList<PostDetailModel>> getAuthor(int userId) {
        final MutableLiveData<ArrayList<PostDetailModel>> data = new MutableLiveData<>();
        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiUrl.Base_url)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(providesOkHttpClientBuilder())
                    .build();

            ApiService service = retrofit.create(ApiService.class);
            service.getAuthor(userId).enqueue(new Callback<ArrayList<PostDetailModel>>() {
                @Override
                public void onResponse(Call<ArrayList<PostDetailModel>> call, Response<ArrayList<PostDetailModel>> response) {
                    data.setValue(response.body());
                }

                @Override
                public void onFailure(Call<ArrayList<PostDetailModel>> call, Throwable t) {
                    Log.d("Repository", "Failed");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    private ArrayList<PostDetailModel> parseJsonForPost(String postDetail) {
        ArrayList<PostDetailModel> apiPost = new ArrayList<>();
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(postDetail);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                PostDetailModel postDetailModel = new PostDetailModel();

                postDetailModel.setId(object.optInt("id"));
                postDetailModel.setName(object.optString("name"));
                postDetailModel.setUsername(object.optString("username"));
                postDetailModel.setEmail(object.optString("email"));
                postDetailModel.setPhone(object.optString("phone"));
                postDetailModel.setWebsite(object.optString("website"));

                apiPost.add(postDetailModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return apiPost;
    }
}
