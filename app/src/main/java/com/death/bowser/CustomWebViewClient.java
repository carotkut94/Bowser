package com.death.bowser;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by rajora_sd on 3/15/2017.
 */

public class CustomWebViewClient extends WebViewClient {

    @SuppressWarnings("deprecation")
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

}
