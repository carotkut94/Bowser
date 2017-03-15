package com.death.bowser;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;

public class MainActivity extends AppCompatActivity {


    FloatingActionButton floatingActionButton;
    EditText editText;
    ProgressBar progressBar;
    WebView webView;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    webView.loadUrl("https://google.com/");
                    return true;
                case R.id.navigation_back:
                    if(webView.canGoBack())
                        webView.goBack();
                    return true;
                case R.id.navigation_refresh:
                    webView.reload();
                    return true;
                case R.id.navigation_forward:
                    if(webView.canGoForward())
                        webView.goForward();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.webView);
        editText = (EditText) findViewById(R.id.query);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.go);

        webView.setWebViewClient(new CustomWebViewClient());
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
                if(newProgress == 100){
                    progressBar.setVisibility(View.GONE);
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
        WebSettings websettings = webView.getSettings();
        websettings.setJavaScriptEnabled(true);
        webView.loadUrl("https://google.com/");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = editText.getText().toString();
                doSearch(link);
            }
        });
    }

    public void doSearch(String query){

        if(!query.startsWith("http://"))
            query = "http://" + query;

        String url = query;
        webView.loadUrl(url);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(),0);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(webView.canGoBack())
            webView.goBack();
    }
}
