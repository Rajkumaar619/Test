package com.example.codeittest.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.codeittest.Fragment.PostDetailFragment;
import com.example.codeittest.Fragment.PostListFragment;
import com.example.codeittest.R;

import java.util.Objects;

public class ProductDetailActivity extends AppCompatActivity {

    private Bundle bundle;
    private PostDetailFragment detailFragment;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        getActionButton();

        if (getIntent() != null) {
            bundle = new Bundle();
            bundle.putSerializable("PostData", getIntent().getSerializableExtra("PostData"));
        }

        callFragment(bundle);

    }

    private void getActionButton() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    private void callFragment(Bundle bundle) {
        detailFragment = new PostDetailFragment();
        detailFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, detailFragment)
                .commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}