package xyz.lklinker.dropbox_example.widget;

import android.webkit.WebViewClient;

import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

import xyz.lklinker.dropbox_example.DropboxJUnitSuite;
import xyz.lklinker.dropbox_example.DropboxRobolectricSuite;
import xyz.lklinker.dropbox_example.util.DropboxUtil;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class DropboxLoginWebViewTest extends DropboxRobolectricSuite {

    private DropboxLoginWebView webView;
    private DropboxLoginWebView.AccessTokenCallback callback;
    private WebViewClient client;

    @Override
    public void setup() {
        callback = mock(DropboxLoginWebView.AccessTokenCallback.class);

        webView = new DropboxLoginWebView(RuntimeEnvironment.application);
        webView.setAccessTokenCallback(callback);
        client = webView.getDropboxClient();
    }

    @Test
    public void test_webviewSetup() {
        assertTrue(webView.getSettings().getJavaScriptEnabled());
        assertTrue(webView.getSettings().getUseWideViewPort());
        assertTrue(webView.getSettings().getLoadWithOverviewMode());
    }

    @Test
    public void test_loadNormalWebpage() {
        assertFalse(client.shouldOverrideUrlLoading(webView, "google.com"));
    }

    @Test
    public void test_loadCallbackUrl() {
        assertTrue(client.shouldOverrideUrlLoading(webView, DropboxUtil.REDIRECT_URL + "&access_token=abcd"));
        verify(callback).onFindAccessToken(eq("abcd"));
    }

    @Test
    public void test_loadCallbackUrl_noInterfaceInteraction() {
        assertFalse(client.shouldOverrideUrlLoading(webView, DropboxUtil.REDIRECT_URL));
        verifyNoMoreInteractions(callback);
    }
}