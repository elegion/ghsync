package com.github.elegion.activity;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.os.Bundle;

import com.github.elegion.R;
import com.github.elegion.fragment.LoginForm;

/**
 * @author Daniel Serdyukov
 */
public class LoginActivity extends AccountAuthenticatorActivity {

    public static final String EXTRA_TOKEN_TYPE = "com.github.elegion.EXTRA_TOKEN_TYPE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_single_frame);
        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame1, new LoginForm())
                    .commit();
        }
    }

    public void onTokenReceived(Account account, String password, String token) {
        final AccountManager am = AccountManager.get(this);
        final Bundle result = new Bundle();
        if (am.addAccountExplicitly(account, password, new Bundle())) {
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, token);
            am.setAuthToken(account, account.type, token);
        } else {
            result.putString(AccountManager.KEY_ERROR_MESSAGE, getString(R.string.account_already_exists));
        }
        setAccountAuthenticatorResult(result);
        setResult(RESULT_OK);
        finish();
    }

}
