package com.belluzzifioravanti.bellapp.ui;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.belluzzifioravanti.bellapp.R;
import com.google.android.material.progressindicator.BaseProgressIndicator;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.transition.MaterialFadeThrough;

import java.util.Objects;

public class UtilitiesFragment extends Fragment {
    private MaterialFadeThrough materialFadeThrough = new MaterialFadeThrough();
    private WebView webView;
    private SwipeRefreshLayout refreshLayout;
    private LinearProgressIndicator progressIndicator;

    public UtilitiesFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        materialFadeThrough.setDuration(500);
        setEnterTransition(materialFadeThrough);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_utilities, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initWebView();
        refresh();
    }

    private void refresh() {
        refreshLayout = requireActivity().findViewById(R.id.swipeRefreshUtilities);
        refreshLayout.setColorSchemeColors(ResourcesCompat.getColor(getResources(), R.color.primaryColor, null), ResourcesCompat.getColor(getResources(), R.color.secondaryColor, null), ResourcesCompat.getColor(getResources(), R.color.primaryLightColor, null), ResourcesCompat.getColor(getResources(), R.color.secondaryLightColor, null));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.reload();
            }
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        progressIndicator = getActivity().findViewById(R.id.ProgressBar);
        webView = getActivity().findViewById(R.id.sito_web);
        WebSettings webSettings = webView.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            webSettings.setSafeBrowsingEnabled(true);
        }
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("https://www.belluzzifioravanti.it/");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressIndicator.setShowAnimationBehavior(BaseProgressIndicator.SHOW_INWARD);
                progressIndicator.setTrackCornerRadius(10);
                progressIndicator.show();
                progressIndicator.setProgressCompat(100, true);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressIndicator.setProgressCompat(100, true);
                progressIndicator.setHideAnimationBehavior(BaseProgressIndicator.HIDE_OUTWARD);
                progressIndicator.hide();
                refreshLayout.setRefreshing(false);
                super.onPageFinished(view, url);
            }
        });
    }

    public void clearWebCache() {
        webView.clearCache(true);
    }

    public boolean onKeyDown() {
        boolean prova = false;
        if (webView.canGoBack()) {
            webView.goBack();
            prova = true;
        }
        return prova;
    }
}