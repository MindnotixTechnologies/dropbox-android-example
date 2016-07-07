package xyz.lklinker.dropbox_example.service;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;

import xyz.lklinker.dropbox_example.DropboxJUnitSuite;
import xyz.lklinker.dropbox_example.DropboxRobolectricSuite;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class DropboxUploadServiceTest extends DropboxRobolectricSuite {

    private DropboxUploadService service;

    @Override
    public void setup() {
        service = Robolectric.buildService(DropboxUploadService.class).create().get();
    }

    @Test
    public void test_createService() {
        Context context = spy(RuntimeEnvironment.application);
        Uri uri = mock(Uri.class);
        DropboxUploadService.uploadFile(context, uri);

        verify(context).startService(any(Intent.class));
    }

    @Test
    public void test_getSameFilePath() {
        Uri uri = mock(Uri.class);
        when(uri.toString()).thenReturn("/file.test");
        assertEquals("/file.test", DropboxUploadService.getFilePath(uri));
    }

    @Test
    public void test_getCutFilePath() {
        Uri uri = mock(Uri.class);
        when(uri.toString()).thenReturn("/document/primary:/file.test");
        assertEquals("/file.test", DropboxUploadService.getFilePath(uri));
    }
}