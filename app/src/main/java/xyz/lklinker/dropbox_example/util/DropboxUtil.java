package xyz.lklinker.dropbox_example.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AppKeyPair;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import xyz.lklinker.dropbox_example.BuildConfig;

public class DropboxUtil {

    private static final String APP_KEY = BuildConfig.APP_KEY;
    private static final String APP_SECRET = BuildConfig.APP_SECRET;

    // this should match the redirect from the API Console
    // it can be whatever you choose
    public static final String REDIRECT_URL = "https://www.dropbox.com/1/oauth2/redirect_receiver";
    public static final String AUTHORIZE_URL =
            "https://www.dropbox.com/1/oauth2/authorize" +
                    "?client_id=" + APP_KEY +
                    "&response_type=token" +
                    "&redirect_uri=" + REDIRECT_URL;

    private Context context;
    private SharedPreferences sharedPreferences;

    public DropboxUtil(Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public DropboxAPI<AndroidAuthSession> getDropboxApi() {
        if (!isLoggedIn()) {
            throw new RuntimeException("You should log in to dropbox first!");
        }

        AppKeyPair keys = new AppKeyPair(APP_KEY, APP_SECRET);
        AndroidAuthSession session = new AndroidAuthSession(keys, getOauthAccessToken());

        return new DropboxAPI<>(session);
    }

    public boolean isLoggedIn() {
        return !getOauthAccessToken().isEmpty();
    }

    private String getOauthAccessToken() {
        return sharedPreferences.getString("dropbox_oauth_access", "");
    }

    public String extractAccessToken(String redirectUrl) {
        Pattern p = Pattern.compile("(?<=access_token=).*?(?=&|$)");
        Matcher m = p.matcher(redirectUrl);

        if (m.find()) {
            return m.group();
        } else {
            throw new IllegalArgumentException("No access token found in url: " + redirectUrl);
        }
    }

    public void saveAccessToken(String oauthAccessToken) {
        sharedPreferences
                .edit()
                .putString("dropbox_oauth_access", oauthAccessToken)
                .commit();
    }
}
