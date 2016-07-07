package xyz.lklinker.dropbox_example.util;

import android.content.Context;
import android.content.SharedPreferences;


import com.dropbox.client2.android.AndroidAuthSession;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import xyz.lklinker.dropbox_example.DropboxJUnitSuite;
import xyz.lklinker.dropbox_example.DropboxRobolectricSuite;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class DropboxUtilTest extends DropboxRobolectricSuite {

    private DropboxUtil dropboxUtil;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    public void setup() {
        Context context = mock(Context.class);
        sharedPreferences = mock(SharedPreferences.class);
        editor = mock(SharedPreferences.Editor.class);

        doReturn(editor).when(editor).putString(anyString(), anyString());
        doReturn(editor).when(sharedPreferences).edit();
        doReturn(sharedPreferences).when(context).getSharedPreferences(anyString(), anyInt());
        doReturn("test_token").when(sharedPreferences).getString(DropboxUtil.PREF_KEY, "");

        dropboxUtil = new DropboxUtil(context);
    }

    @Test
    public void test_getAccessToken() {
        assertEquals("test_token", dropboxUtil.getOauthAccessToken());
    }

    @Test
    public void test_isLoggedIn() {
        assertTrue(dropboxUtil.isLoggedIn());
    }

    @Test
    public void test_notLoggedIn() {
        doReturn("").when(sharedPreferences).getString(DropboxUtil.PREF_KEY, "");
        assertFalse(dropboxUtil.isLoggedIn());
    }

    @Test(expected = RuntimeException.class)
    public void test_exceptionWhenNotLoggedIn() {
        doReturn("").when(sharedPreferences).getString(DropboxUtil.PREF_KEY, "");
        dropboxUtil.getDropboxApi();
    }

    @Test
    public void test_getApi() {
        assertThat(dropboxUtil.getDropboxApi().getSession(),
                CoreMatchers.instanceOf(AndroidAuthSession.class));
    }

    @Test
    public void test_saveToken() {
        dropboxUtil.saveAccessToken("test_token");
        verify(editor).putString(eq(DropboxUtil.PREF_KEY), eq("test_token"));
        verify(editor).commit();
    }

    @Test
    public void test_extractToken_valid() {
        String url = "http://dropbox.com?access_token=abcd";
        assertEquals("abcd", dropboxUtil.extractAccessToken(url));

        url = "http://dropbox.com?test=t&access_token=abcd";
        assertEquals("abcd", dropboxUtil.extractAccessToken(url));

        url = "http://dropbox.com?test=t&access_token=abcd&test=t";
        assertEquals("abcd", dropboxUtil.extractAccessToken(url));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_extractToken_invalid() {
        String url = "http://dropbox.com?test=t&test=t";
        dropboxUtil.extractAccessToken(url);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_extractToken_empty() {
        String url = "";
        dropboxUtil.extractAccessToken(url);
    }

    @Test(expected = NullPointerException.class)
    public void test_extractToken_null() {
        String url = null;
        dropboxUtil.extractAccessToken(url);
    }
}