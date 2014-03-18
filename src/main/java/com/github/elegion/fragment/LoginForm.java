package com.github.elegion.fragment;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.github.elegion.R;
import com.github.elegion.account.GitHubAccount;
import com.github.elegion.activity.LoginActivity;
import com.github.elegion.loader.AuthTokenLoader;

/**
 * @author Daniel Serdyukov
 */
public class LoginForm extends Fragment implements LoaderManager.LoaderCallbacks<String>, View.OnClickListener {

    private EditText mLogin;

    private EditText mPassword;

    private Button mSignInButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fmt_login_form, container, false);
        mLogin = (EditText) view.findViewById(R.id.login);
        mPassword = (EditText) view.findViewById(R.id.password);
        mSignInButton = (Button) view.findViewById(R.id.btn_sign_in);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSignInButton.setOnClickListener(this);
    }

    @Override
    public void onPause() {
        mSignInButton.setOnClickListener(null);
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        if (v == mSignInButton) {
            if (TextUtils.isEmpty(mLogin.getText())) {
                mLogin.setError(getString(R.string.login));
            } else if (TextUtils.isEmpty(mPassword.getText())) {
                mPassword.setError(getString(R.string.password));
            } else {
                getLoaderManager().restartLoader(R.id.auth_token_loader, null, this);
            }
        }
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        if (id == R.id.auth_token_loader) {
            return new AuthTokenLoader(
                    getActivity().getApplicationContext(),
                    mLogin.getText().toString(),
                    mPassword.getText().toString()
            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String token) {
        if (loader.getId() == R.id.auth_token_loader && !TextUtils.isEmpty(token)) {
            ((LoginActivity) getActivity()).onTokenReceived(
                    new GitHubAccount(mLogin.getText().toString()),
                    mPassword.getText().toString(), token
            );
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

}
