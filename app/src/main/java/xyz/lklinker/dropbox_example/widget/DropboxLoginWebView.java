package xyz.lklinker.dropbox_example.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import xyz.lklinker.dropbox_example.util.DropboxUtil;

public class DropboxLoginWebView extends WebView {

    public interface AccessTokenCallback {
        void onFindAccessToken(String token);
    }

    private DropboxUtil dropboxUtil;
    private AccessTokenCallback callback;

    public DropboxLoginWebView(Context context) {
        this(context, null);
    }

    public DropboxLoginWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        getSettings().setJavaScriptEnabled(true);
        getSettings().setUseWideViewPort(true);
        getSettings().setLoadWithOverviewMode(true);

        setWebViewClient(getDropboxClient());
        this.dropboxUtil = new DropboxUtil(getContext());
    }

    public void setAccessTokenCallback(AccessTokenCallback callback) {
        this.callback = callback;
    }

    @VisibleForTesting
    protected WebViewClient getDropboxClient() {
        return new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.startsWith(DropboxUtil.REDIRECT_URL)) {
                    if (url.contains("access_token=") && callback != null) {
                        callback.onFindAccessToken(dropboxUtil.extractAccessToken(url));
                        return true;
                    }
                }

                return super.shouldOverrideUrlLoading(view, url);
            }
        };
    }
}
