package com.example.codeittest.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeittest.Activity.ProductDetailActivity;
import com.example.codeittest.ClickEvent.OnClickListener;
import com.example.codeittest.Models.ResultModel;
import com.example.codeittest.R;
import com.example.codeittest.Util.Constant;
import com.example.codeittest.viewmodel.PostsListViewModel;

import java.util.List;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder> {

    private final LayoutInflater inflater;
    private List<ResultModel> mUsers;
    private Context context;
    private ResultModel resultModel;
    private OnClickListener mClickListener;
    private PostsListViewModel mViewModel;


    public PostListAdapter(Context context, OnClickListener onClickListener) {
        this.context = context;
        mClickListener = onClickListener;
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

        holder.linPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("PostData", resultModel);
                context.startActivity(intent);
            }
        });
    }

    public void setPosts(List<ResultModel> users) {
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

    public void removePost(int pos) {
        int newPosition = pos;
        mUsers.remove(newPosition);
        notifyItemRemoved(newPosition);
        notifyItemRangeChanged(newPosition, mUsers.size());
    }

    public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtTitle, txtBody;
        LinearLayout linPost;
        ImageView imgDelete;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.mTitle);
            txtBody = itemView.findViewById(R.id.mBody);
            linPost = itemView.findViewById(R.id.linPost);
            imgDelete = itemView.findViewById(R.id.imgDelete);

            linPost.setOnClickListener(this);
            imgDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.linPost:
                    mClickListener.onClick(Constant.POST_DETAIL, resultModel, getAdapterPosition());
                    break;

                case R.id.imgDelete:
                    mClickListener.onClick(Constant.DELETE, resultModel, getAdapterPosition());
                    break;
            }
        }
    }
}
