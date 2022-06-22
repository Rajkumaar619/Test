package com.example.codeittest.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.codeittest.Models.ResultModel;

import java.util.List;

public class PostRoomDBRepository {

    private PostInfoDao postInfoDao;
    LiveData<List<ResultModel>> mAllPosts;

    public PostRoomDBRepository(Application application) {
        PostInfoRoomDatabase db = PostInfoRoomDatabase.getDatabase(application);
        postInfoDao = db.postInfoDao();
        mAllPosts = postInfoDao.getAllPosts();
    }

    public LiveData<List<ResultModel>> getAllPosts() {
        return mAllPosts;
    }

    public void insertPosts(List<ResultModel> resultModel) {
        new insertAsyncTask(postInfoDao).execute(resultModel);
    }

    public void deletePost(ResultModel resultModel) {
        new deletePostAsync(postInfoDao).execute(resultModel);
    }

    private static class insertAsyncTask extends AsyncTask<List<ResultModel>, Void, Void> {

        private PostInfoDao mAsyncTaskDao;

        public insertAsyncTask(PostInfoDao postInfoDao) {
            mAsyncTaskDao = postInfoDao;
        }

        @Override
        protected Void doInBackground(final List<ResultModel>... lists) {
            mAsyncTaskDao.insertPosts(lists[0]);
            return null;
        }
    }

    private static class deletePostAsync extends AsyncTask<ResultModel, Void, Void> {

        private PostInfoDao mAsyncTaskDao;

        deletePostAsync(PostInfoDao postInfoDao) {
            mAsyncTaskDao = postInfoDao;
        }

        @Override
        protected Void doInBackground(ResultModel... notes) {
            int i=mAsyncTaskDao.delete(notes[0]);
            return null;
        }
    }

}
