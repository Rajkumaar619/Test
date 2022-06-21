package com.example.codeittest.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.example.codeittest.Adapter.PostListAdapter;
import com.example.codeittest.Fragment.PostListFragment;
import com.example.codeittest.R;
import com.example.codeittest.viewmodel.PostsListViewModel;

public class MainActivity extends AppCompatActivity {
    PostsListViewModel mViewModel;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    PostListAdapter adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new PostListFragment())
                .addToBackStack("task_list")
                .commit();


        /*mViewModel = new ViewModelProvider(this).get(PostsListViewModel.class);
        initViews();
        setAdapter();

        progressDialog = ProgressDialog.show(MainActivity.this, "Loading...", "Please wait...", true);
        mViewModel.getAllPosts().observe(this, new Observer<List<ResultModel>>() {
            @Override
            public void onChanged(List<ResultModel> resultModels) {
                adapter.setWords(resultModels);
                if (progressDialog != null && progressDialog.isShowing())
                {
                    progressDialog.dismiss();
                }
            }
        });*/
    }

    private void initViews(){
        recyclerView = findViewById(R.id.recyclerview);
    }

    private void setAdapter(){
        adapter = new PostListAdapter(MainActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}