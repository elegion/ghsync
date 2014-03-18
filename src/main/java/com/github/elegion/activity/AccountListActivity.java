package com.github.elegion.activity;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.os.Bundle;

import com.github.elegion.R;
import com.github.elegion.account.GitHubAccount;
import com.github.elegion.fragment.AccountList;

import java.io.IOException;

/**
 * @author Daniel Serdyukov
 */
public class AccountListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_single_frame);
        final AccountManager am = AccountManager.get(this);
        if (am.getAccountsByType(GitHubAccount.TYPE).length == 0) {
            addNewAccount(am);
        }
        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.frame1, new AccountList())
                    .commit();
        }
    }

    private void addNewAccount(AccountManager am) {
        am.addAccount(GitHubAccount.TYPE, GitHubAccount.TOKEN_FULL_ACCESS, null, null, this,
                new AccountManagerCallback<Bundle>() {
                    @Override
                    public void run(AccountManagerFuture<Bundle> future) {
                        try {
                            future.getResult();
                        } catch (OperationCanceledException | IOException | AuthenticatorException e) {
                            AccountListActivity.this.finish();
                        }
                    }
                }, null
        );
    }

}
