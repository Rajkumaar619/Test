package com.example.codeittest.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeittest.Activity.ProductDetailActivity;
import com.example.codeittest.Models.ResultModel;
import com.example.codeittest.R;

import java.util.List;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder> {

    private final LayoutInflater inflater;
    private List<ResultModel> mUsers;
    private Context context;
    private ResultModel resultModel;

    public PostListAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.product_item_view, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        if (mUsers != null) {
            resultModel = mUsers.get(position);
            holder.txtTitle.setText(resultModel.getTitle());
            holder.txtBody.setText(resultModel.getBody());
        }

        holder.postCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, resultModel.getUserId() + "", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context, ProductDetailActivity.class);
                intent.putExtra("PostData",resultModel);
                context.startActivity(intent);
            }
        });
    }

    public void setWords(List<ResultModel> users) {
        mUsers = users;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mUsers != null) {
            return mUsers.size();
        } else {
            return 0;
        }
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle, txtBody;
        CardView postCard;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.mTitle);
            txtBody = itemView.findViewById(R.id.mBody);
            postCard = itemView.findViewById(R.id.postCard);
        }
    }
}
