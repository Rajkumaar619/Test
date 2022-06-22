package com.example.codeittest.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.codeittest.Activity.ProductDetailActivity;
import com.example.codeittest.Adapter.PostListAdapter;
import com.example.codeittest.ClickEvent.OnClickListener;
import com.example.codeittest.Models.ResultModel;
import com.example.codeittest.R;
import com.example.codeittest.Util.Constant;
import com.example.codeittest.viewmodel.PostsListViewModel;

import java.util.List;
import java.util.Objects;

public class PostListFragment extends Fragment implements View.OnClickListener {
    private View mView;
    private PostsListViewModel mViewModel;
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private PostListAdapter adapter = null;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayout linRefresh;


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
        getPosts("First");
        setAdapter();

    }


    private void initViews() {
        linRefresh = mView.findViewById(R.id.linRefresh);
        recyclerView = mView.findViewById(R.id.recyclerview);
        mSwipeRefreshLayout = mView.findViewById(R.id.refreshLayout);


        linRefresh.setOnClickListener(this);
    }

    private void setAdapter() {
        adapter = new PostListAdapter(getActivity(), new OnClickListener() {
            @Override
            public void onClick(String type, ResultModel resultModel, int pos) {

                if (type.equalsIgnoreCase(Constant.POST_DETAIL)) {
                    Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                    intent.putExtra("PostData", resultModel);
                    requireActivity().startActivity(intent);
                    Toast.makeText(getActivity(), type, Toast.LENGTH_SHORT).show();
                } else if (type.equalsIgnoreCase(Constant.DELETE)) {
                    mViewModel.deletePost(resultModel);
                }
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void getPosts(String type) {
        progressDialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        mViewModel.getAllPosts().observe(getActivity(), new Observer<List<ResultModel>>() {
            @Override
            public void onChanged(List<ResultModel> resultModels) {
                adapter.setPosts(resultModels);
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.linRefresh) {
            Toast.makeText(getActivity(), "Refresh Clicked", Toast.LENGTH_SHORT).show();
        }
    }
}
