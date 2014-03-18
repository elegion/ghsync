package com.github.elegion.account;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * @author Daniel Serdyukov
 */
public class GitHubAuthenticatorService extends Service {

    private GitHubAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        super.onCreate();
        mAuthenticator = new GitHubAuthenticator(getApplicationContext());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }

}
