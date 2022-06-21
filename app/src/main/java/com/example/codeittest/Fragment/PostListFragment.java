package com.example.codeittest.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.codeittest.Adapter.PostListAdapter;
import com.example.codeittest.Models.ResultModel;
import com.example.codeittest.R;
import com.example.codeittest.viewmodel.PostsListViewModel;

import java.util.List;

public class PostListFragment extends Fragment {
    private View mView;
    PostsListViewModel mViewModel;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    PostListAdapter adapter = null;
    SwipeRefreshLayout mSwipeRefreshLayout;


    public PostListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_post_list, container, false);
        mViewModel = new ViewModelProvider(this).get(PostsListViewModel.class);
        initViews();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
                getPosts("Second");
            }
        });
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PostsListViewModel.class);
        getPosts("First");
        setAdapter();

    }


    private void initViews() {
        recyclerView = mView.findViewById(R.id.recyclerview);
        mSwipeRefreshLayout = mView.findViewById(R.id.refreshLayout);
    }

    private void setAdapter() {
        adapter = new PostListAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void getPosts(String type) {
        progressDialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        mViewModel.getAllPosts().observe(getActivity(), new Observer<List<ResultModel>>() {
            @Override
            public void onChanged(List<ResultModel> resultModels) {
                adapter.setWords(resultModels);
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), type, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
