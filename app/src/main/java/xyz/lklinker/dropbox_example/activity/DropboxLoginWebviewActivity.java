package xyz.lklinker.dropbox_example.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import xyz.lklinker.dropbox_example.R;
import xyz.lklinker.dropbox_example.util.DropboxUtil;
import xyz.lklinker.dropbox_example.widget.DropboxLoginWebView;

public class DropboxLoginWebviewActivity extends AppCompatActivity {

    private DropboxLoginWebView webView;
    private DropboxUtil utils;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setResult(RESULT_CANCELED);

        utils = new DropboxUtil(this);
        webView = (DropboxLoginWebView) findViewById(R.id.login_webview);

        webView.setAccessTokenCallback(new DropboxLoginWebView.AccessTokenCallback() {
            @Override
            public void onFindAccessToken(String token) {
                utils.saveAccessToken(token);
                setResult(RESULT_OK);
                finish();
            }
        });
        webView.loadUrl(DropboxUtil.AUTHORIZE_URL);
    }

    public DropboxLoginWebView getWebView() {
        return webView;
    }
}
