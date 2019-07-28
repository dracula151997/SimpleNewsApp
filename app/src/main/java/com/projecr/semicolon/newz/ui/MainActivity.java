package com.projecr.semicolon.newz.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.projecr.semicolon.newz.AppController;
import com.projecr.semicolon.newz.R;
import com.projecr.semicolon.newz.adapter.NewsAdapter;
import com.projecr.semicolon.newz.databinding.MainActivityBinding;
import com.projecr.semicolon.newz.viewmodel.NewsViewModel;

public class MainActivity extends AppCompatActivity {
    private MainActivityBinding binding;
    private NewsViewModel newsViewModel;
    private NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        newsViewModel = new NewsViewModel(AppController.create(this));
        binding.newRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new NewsAdapter(this);




        newsViewModel.getArticlesItemLiveData().observe(this, articlesItems -> {
            if (articlesItems != null) {
                binding.loading.setVisibility(View.GONE);
                adapter.submitList(articlesItems);
            }

        });

        newsViewModel.getNetworkStateLiveData().observe(this, networkState -> adapter.setNetworkState(networkState));

        binding.newRecycler.setAdapter(adapter);


    }
}
