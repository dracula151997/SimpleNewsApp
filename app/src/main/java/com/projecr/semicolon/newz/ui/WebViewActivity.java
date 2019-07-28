package com.projecr.semicolon.newz.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.projecr.semicolon.newz.R;
import com.projecr.semicolon.newz.databinding.WebViewDataBinding;

public class WebViewActivity extends AppCompatActivity {
    private WebViewDataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view);

        if (getIntent() != null) {
            String url = getIntent().getStringExtra("url");
            binding.articleWebView.loadUrl(url);

        }
    }
}
