package xyz.lklinker.dropbox_example.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

import xyz.lklinker.dropbox_example.service.DropboxUploadService;
import xyz.lklinker.dropbox_example.util.DropboxUtil;
import xyz.lklinker.dropbox_example.R;

public class MainActivity extends AppCompatActivity {

    private static final int DROPBOX_LOGIN_REQUEST = 1;
    private static final int PICK_FILE_REQUEST = 2;

    private DropboxUtil utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        utils = new DropboxUtil(this);

        // start the login, if the user has not already done this.
        if (!utils.isLoggedIn()) {
            startActivityForResult(
                    new Intent(this, DropboxLoginWebviewActivity.class),
                    DROPBOX_LOGIN_REQUEST
            );
        }

        Button upload = (Button) findViewById(R.id.upload_button);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                startActivityForResult(intent, PICK_FILE_REQUEST);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DROPBOX_LOGIN_REQUEST && resultCode == RESULT_OK) {
            utils = new DropboxUtil(this);
        } else if (requestCode == PICK_FILE_REQUEST) {
            DropboxUploadService.uploadFile(this, data.getData());
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
