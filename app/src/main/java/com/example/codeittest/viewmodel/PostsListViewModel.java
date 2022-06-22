package com.example.codeittest.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.codeittest.Models.PostDetailModel;
import com.example.codeittest.Models.ResultModel;
import com.example.codeittest.Repository.PostRoomDBRepository;
import com.example.codeittest.Repository.WebServiceRepository;

import java.util.ArrayList;
import java.util.List;

public class PostsListViewModel extends AndroidViewModel {

    private PostRoomDBRepository postRoomDBRepository;
    private LiveData<List<ResultModel>> mAllPosts;
    private LiveData<ArrayList<PostDetailModel>> postLiveData;

    WebServiceRepository webServiceRepository;


    private final LiveData<List<ResultModel>> retroObservable;


    public PostsListViewModel(@NonNull Application application) {
        super(application);

        postRoomDBRepository = new PostRoomDBRepository(application);
        webServiceRepository = new WebServiceRepository(application);

        retroObservable  = webServiceRepository.providesWebService();
        mAllPosts = postRoomDBRepository.getAllPosts();

    }

    public LiveData<List<ResultModel>> getAllPosts()
    {
        return  mAllPosts;
    }

    public LiveData<ArrayList<PostDetailModel>> getDetails(int userId) {
        postLiveData = webServiceRepository.getAuthor(userId);
        return postLiveData;
    }

    public void deletePost(ResultModel resultModel) {
        postRoomDBRepository.deletePost(resultModel);
    }
}
