package pl.Guzooo.Brolzer;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;

public class MainActivity extends Activity {

    private static final String START_PAGE = "https://www.google.pl";

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();
        setWebView();
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack())
            webView.goBack();
        else
            super.onBackPressed();
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                onLongBackPressed();
                return true;
        }
        return super.onKeyLongPress(keyCode, event);
    }

    private void initialization(){
        webView = findViewById(R.id.webView);
    }

    private void setWebView(){
        webView.getSettings().setJavaScriptEnabled(true);
        setStartPage();
    }

    private void onLongBackPressed(){
        webView.clearHistory();
        webView.loadUrl(START_PAGE);
    }

    private void setStartPage(){
        String www = webView.getUrl();
        if(www == null || www.isEmpty())
            webView.loadUrl(START_PAGE);
    }
}
