package xyz.lklinker.dropbox_example.activity;

import android.app.Activity;
import android.content.Intent;

import org.junit.Test;
import org.mockito.Mock;
import org.robolectric.Robolectric;

import xyz.lklinker.dropbox_example.DropboxRobolectricSuite;
import xyz.lklinker.dropbox_example.util.DropboxUtil;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class MainActivityTest extends DropboxRobolectricSuite {
    private MainActivity activity;

    @Override
    public void setup() {
        activity = spy(Robolectric.buildActivity(MainActivity.class)
                .create().start().resume()
                .get());
    }

    @Test
    public void test_viewsCreated() {
        assertNotNull(activity.getUploadButton());
        assertTrue(activity.getUploadButton().isClickable());
    }

    @Test
    public void test_loginComplete() {
        activity.setDropboxUtils(null);

        activity.onActivityResult(MainActivity.DROPBOX_LOGIN_REQUEST, Activity.RESULT_OK, null);
        assertNotNull(activity.getUtils());
    }

    @Test
    public void test_dontNeedLogin() {
        DropboxUtil dropboxUtil = mock(DropboxUtil.class);
        when(dropboxUtil.isLoggedIn()).thenReturn(true);

        activity.checkLogin(activity, dropboxUtil);
        verify(activity).checkLogin(activity, dropboxUtil);
        verifyNoMoreInteractions(activity);
    }

    @Test
    public void test_needLogin() {
        DropboxUtil dropboxUtil = mock(DropboxUtil.class);
        when(dropboxUtil.isLoggedIn()).thenReturn(false);

        activity.checkLogin(activity, dropboxUtil);
        verify(activity).startActivityForResult(any(Intent.class), anyInt());
    }
}