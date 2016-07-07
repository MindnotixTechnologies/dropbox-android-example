package xyz.lklinker.dropbox_example.activity;

import org.junit.Test;
import org.robolectric.Robolectric;

import xyz.lklinker.dropbox_example.DropboxRobolectricSuite;

import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;

public class DropboxLoginActivityTest extends DropboxRobolectricSuite {
    private DropboxLoginActivity activity;

    @Override
    public void setup() {
        activity = spy(Robolectric.buildActivity(DropboxLoginActivity.class)
                .create().start().resume()
                .get());
    }

    @Test
    public void test_viewsCreated() {
        assertNotNull(activity.getWebView());
    }
}