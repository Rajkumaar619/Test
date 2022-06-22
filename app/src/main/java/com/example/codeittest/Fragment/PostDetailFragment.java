package com.example.codeittest.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.codeittest.Models.PostDetailModel;
import com.example.codeittest.Models.ResultModel;
import com.example.codeittest.R;
import com.example.codeittest.viewmodel.PostsListViewModel;

public class PostDetailFragment extends Fragment implements View.OnClickListener {
    private View mView;
    private PostsListViewModel mViewModel;
    private int userId;
    private TextView txtName, txtEmail, txtTitle, txtBody;
    private ResultModel resultModel;
    private ProgressDialog progressDialog;
    private LinearLayout linDelete;
    private Context mContext;


    public PostDetailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_post_detail, container, false);

        initViews();

        if (getArguments() != null) {
            resultModel = (ResultModel) getArguments().getSerializable("PostData");
            userId = resultModel.getUserId();
            Log.e("UserIdFragment", userId + "");
        }

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PostsListViewModel.class);
        getAuthorDetails(userId);
        mViewModel.deletePost(resultModel);
    }


    private void getAuthorDetails(int userId) {
        progressDialog = ProgressDialog.show(mContext, "Loading...", "Please wait...", true);

        mViewModel.getDetails(userId).observe(requireActivity(), postDetailModels -> {
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


    private void initViews() {
        mContext = getActivity();
        txtName = mView.findViewById(R.id.txtName);
        txtEmail = mView.findViewById(R.id.txtEmail);
        txtTitle = mView.findViewById(R.id.txtTitle);
        txtBody = mView.findViewById(R.id.txtBody);
        linDelete = mView.findViewById(R.id.linDelete);

        linDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.linDelete) {
            getDialog(resultModel);
        }
    }

    private void getDialog(ResultModel resultModel) {
        LinearLayout linCancle, linDelete;

        //will create a view of our custom dialog layout
        View alertCustomdialog = LayoutInflater.from(mContext).inflate(R.layout.custom_dialog, null);
        //initialize alert builder.
        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);

        //set our custom alert dialog to tha alertdialog builder
        alert.setView(alertCustomdialog);

        linCancle = alertCustomdialog.findViewById(R.id.linCancle);
        linDelete = alertCustomdialog.findViewById(R.id.linDelete);


        final AlertDialog dialog = alert.create();
        //this line removed app bar from dialog and make it transperent and you see the image is like floating outside dialog box.
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //finally show the dialog box in android all
        dialog.show();

        linCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        linDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (resultModel!=null){
                    mViewModel.deletePost(resultModel);
                }
                Toast.makeText(getActivity(), "Delete Dialog", Toast.LENGTH_SHORT).show();
            }
        });



    }
}
