package com.github.elegion.fragment;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OnAccountsUpdateListener;
import android.accounts.OperationCanceledException;
import android.app.ListFragment;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.elegion.account.GitHubAccount;

import java.io.IOException;

/**
 * @author Daniel Serdyukov
 */
public class AccountList extends ListFragment implements OnAccountsUpdateListener {

    private final Handler mHandler = new Handler();

    private AccountManager mAccountManager;

    private ArrayAdapter<Account> mListAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAccountManager = AccountManager.get(getActivity());
        mListAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1);
        setListAdapter(mListAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mAccountManager.addOnAccountsUpdatedListener(this, mHandler, true);
    }

    @Override
    public void onPause() {
        mAccountManager.removeOnAccountsUpdatedListener(this);
        super.onPause();
    }

    @Override
    public void onAccountsUpdated(Account[] accounts) {
        mListAdapter.setNotifyOnChange(false);
        mListAdapter.clear();
        for (final Account account : accounts) {
            if (TextUtils.equals(account.type, GitHubAccount.TYPE)) {
                mListAdapter.add(account);
            }
        }
        mListAdapter.setNotifyOnChange(true);
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        final Account account = mListAdapter.getItem(position);
        mAccountManager.getAuthToken(account, GitHubAccount.TOKEN_FULL_ACCESS, new Bundle(), true,
                new AccountManagerCallback<Bundle>() {
                    @Override
                    public void run(AccountManagerFuture<Bundle> future) {
                        try {
                            final Bundle result = future.getResult();
                            Log.d(AccountList.class.getSimpleName(), result.getString(AccountManager.KEY_AUTHTOKEN));
                        } catch (OperationCanceledException | IOException | AuthenticatorException e) {
                            Log.e(AccountList.class.getSimpleName(), e.getMessage(), e);
                        }
                    }
                }, null
        );
    }

}
