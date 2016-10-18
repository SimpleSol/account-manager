package biz.growapp.mainapp;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static android.R.id.message;

public class LoginActivity extends AppCompatActivity {

    private String accountType;
    private AccountManager accountManager;
    private EditText etLogin;
    private EditText etPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        accountType = getString(R.string.account_type);
        accountManager = AccountManager.get(this);
        etLogin = (EditText) findViewById(R.id.etLogin);
        etPassword = (EditText) findViewById(R.id.etPassword);
        findViewById(R.id.btnSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        findViewById(R.id.btnAddAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountManager.addAccount(accountType, AuthenticatorActivity.AUTH_TOKEN_TYPE, null,
                        Bundle.EMPTY, LoginActivity.this, null, null);
            }
        });
    }

    private void signIn() {
        String message;
        Account[] accounts = accountManager.getAccountsByType(accountType);
        if (accounts.length > 0) {
            Account account = accounts[0];
            String login = etLogin.getText().toString();
            String password = etPassword.getText().toString();
            String validToken = accountManager.peekAuthToken(account, AuthenticatorActivity.AUTH_TOKEN_TYPE);
            String userToken = String.valueOf(login.hashCode() + password.hashCode());

            if (TextUtils.equals(userToken, validToken)) {
                message = "Success Login";
            } else {
                message = "Wrong credentials";
            }
        } else {
            message = "No accounts";
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
