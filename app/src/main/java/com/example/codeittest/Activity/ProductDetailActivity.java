package com.example.codeittest.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.codeittest.Models.PostDetailModel;
import com.example.codeittest.Models.ResultModel;
import com.example.codeittest.R;
import com.example.codeittest.viewmodel.PostsListViewModel;

import java.util.ArrayList;

public class ProductDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private PostsListViewModel mViewModel;
    private int userId;
    private TextView txtName, txtEmail, txtTitle, txtBody;
    private ResultModel resultModel;
    private ProgressDialog progressDialog;
    private LinearLayout linDelete;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        mViewModel = new ViewModelProvider(this).get(PostsListViewModel.class);
        if (getIntent() != null) {
            resultModel = (ResultModel) getIntent().getSerializableExtra("PostData");
            userId = resultModel.getUserId();
            Log.e("UserId", userId + "");
        }
        initView();
        getAuthorDetails(userId);
    }

    private void getAuthorDetails(int userId) {
        progressDialog = ProgressDialog.show(this, "Loading...", "Please wait...", true);
        mViewModel.getDetails(userId).observe(this, postDetailModels -> {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (postDetailModels != null) {
                Log.e("UserEmail", postDetailModels.toString());
                for (PostDetailModel detailModel : postDetailModels) {
                    if (userId == detailModel.getId()) {
                        setData(detailModel);
                    }
                }
            }
        });
    }

    private void setData(PostDetailModel detailModel) {
        txtName.setText(detailModel.getName());
        txtEmail.setText(detailModel.getEmail());
        txtTitle.setText(resultModel.getTitle());
        txtBody.setText(resultModel.getBody());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.linDelete) {
            Toast.makeText(this, "Deleted Clicked", Toast.LENGTH_SHORT).show();
        }
    }


    private void initView() {
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtTitle = findViewById(R.id.txtTitle);
        txtBody = findViewById(R.id.txtBody);
        linDelete = findViewById(R.id.linDelete);

        linDelete.setOnClickListener(this);
    }
}