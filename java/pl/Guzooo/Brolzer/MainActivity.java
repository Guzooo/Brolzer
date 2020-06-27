package pl.Guzooo.Brolzer;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.webkit.WebView;

public class MainActivity extends Activity {

    private static final String START_PAGE = "https://www.google.pl";

    private WebView webView;
    private MouseCursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();
        setWebView();
        setCursor();
    }

    @Override
    public void onBackPressed() {
        if(!cursor.hasFocus())
            setCursorFocus();
        else if(webView.canGoBack())
            webView.goBack();
        else
            super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(cursor.ClickDPad(keyCode))
            return true;
        return super.onKeyDown(keyCode, event);
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
        webView = findViewById(R.id.web_view);
        cursor = findViewById(R.id.mouse_cursor);
    }

    private void setWebView(){
        webView.getSettings().setJavaScriptEnabled(true);
        setStartPage();
    }

    private void setCursor(){
        setCursorFocus();
        cursor.setOnSelectListener(getOnSelectListener());
    }

    private void setCursorFocus(){
        cursor.requestFocus();
        webView.setFocusable(false);
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

    private MouseCursor.OnSelectListener getOnSelectListener(){
        return new MouseCursor.OnSelectListener() {
            @Override
            public void onSelect() {
                long downTime = SystemClock.uptimeMillis();
                long eventTime = SystemClock.uptimeMillis() + 100;
                float x = cursor.getX();
                float y = cursor.getY();
                int metaState = 0;
                MotionEvent motionEvent = MotionEvent.obtain(
                        downTime,
                        eventTime,
                        MotionEvent.ACTION_DOWN,
                        x,
                        y,
                        metaState
                );
                MotionEvent motionEvent2 = MotionEvent.obtain(
                        eventTime,
                        eventTime,
                        MotionEvent.ACTION_UP,
                        x,
                        y,
                        metaState
                );
                webView.setFocusable(true);
                webView.dispatchTouchEvent(motionEvent);
                webView.dispatchTouchEvent(motionEvent2);
            }
        };
    }
}
