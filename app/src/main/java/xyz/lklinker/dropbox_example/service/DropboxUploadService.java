package xyz.lklinker.dropbox_example.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.OpenableColumns;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.ProgressListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import xyz.lklinker.dropbox_example.util.DropboxUtil;

public class DropboxUploadService extends IntentService {

    private static final String ARG_FILE_PATH = "arg_file_path";

    public static void uploadFile(Context context, Uri contentUri) {
        Intent service = new Intent(context, DropboxUploadService.class);
        service.putExtra(ARG_FILE_PATH, contentUri.getPath());

        context.startService(service);
    }

    private DropboxUtil utils;
    private DropboxAPI dropboxAPI;

    private Uri fileUri;
    private File file;

    public DropboxUploadService() {
        super("Dropbox Upload");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        utils = new DropboxUtil(this);
        dropboxAPI = utils.getDropboxApi();

        fileUri = Uri.parse(intent.getStringExtra(ARG_FILE_PATH));
        file = new File(Environment.getExternalStorageDirectory() + "/" + getFilePath(fileUri));

        try {
            FileInputStream stream = new FileInputStream(file);
            dropboxAPI.putFile("/" + file.getName(), stream, file.length(), null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getFilePath(Uri uri) {
        // yeah what a hack. Ugh i have the content provider.
        return uri.toString().replace("/document/primary:", "");
    }
}
