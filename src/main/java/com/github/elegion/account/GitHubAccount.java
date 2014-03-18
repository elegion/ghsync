package com.github.elegion.account;

import android.accounts.Account;
import android.os.Parcel;

/**
 * @author Daniel Serdyukov
 */
public class GitHubAccount extends Account {

    public static final String TYPE = "com.github.elegion";

    public static final String TOKEN_FULL_ACCESS = "com.github.elegion.TOKEN_FULL_ACCESS";

    public static final String KEY_PASSWORD = "com.github.elegion.KEY_PASSWORD";

    public GitHubAccount(Parcel in) {
        super(in);
    }

    public GitHubAccount(String name) {
        super(name, TYPE);
    }

}
