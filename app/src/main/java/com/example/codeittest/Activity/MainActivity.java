package com.example.codeittest.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.codeittest.Fragment.PostListFragment;
import com.example.codeittest.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new PostListFragment())
                .commit();
    }

}