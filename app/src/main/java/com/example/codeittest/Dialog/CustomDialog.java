package com.example.codeittest.Dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.codeittest.Models.ResultModel;
import com.example.codeittest.R;
import com.example.codeittest.Util.Constant;
import com.example.codeittest.viewmodel.PostsListViewModel;

public class CustomDialog {


    public void getDialog(Context mContext, PostsListViewModel mViewModel, ResultModel resultModel, String type) {
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
                if (type.equals(Constant.Second)) {
                    if (resultModel != null) {
                        mViewModel.deletePost(resultModel);
                        ((Activity) mContext).finish();
                        Toast.makeText(mContext, "Data delete successfully!!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mViewModel.deletePost(resultModel);
                    Toast.makeText(mContext, "Data delete successfully!!", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

}
