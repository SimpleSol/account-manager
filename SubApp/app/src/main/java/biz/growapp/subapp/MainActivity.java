package biz.growapp.subapp;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final String AUTH_TOKEN_TYPE = "token_type";
    private static final String ACCOUNT_TYPE = "biz.growapp.account";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClickButton(View v) {
        final AccountManager accountManager = AccountManager.get(this);
        Account[] accounts = accountManager.getAccountsByType(ACCOUNT_TYPE);
        if (accounts.length > 0) {
            final Account account = accounts[0];
            accountManager.getAuthToken(account, AUTH_TOKEN_TYPE, Bundle.EMPTY, true, new AccountManagerCallback<Bundle>() {
                @Override
                public void run(AccountManagerFuture<Bundle> future) {
                    Bundle bundle;
                    try {
                        bundle = future.getResult();
                        Intent intent = (Intent) bundle.get(AccountManager.KEY_INTENT);
                        if (intent != null) {
                            // User input required
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, bundle.getString(AccountManager.KEY_AUTHTOKEN), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ignored) {}
                }
            }, null);


//            final AsyncTask<Void, Void, Bundle> execute = new AsyncTask<Void, Void, Bundle>() {
//                @Override
//                protected Bundle doInBackground(Void... params) {
//                    Bundle result = null;
//                    try {
//                        result = accountManager.getAuthToken(account, AUTH_TOKEN_TYPE, Bundle.EMPTY, false, null, null).getResult();
//                    } catch (OperationCanceledException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (AuthenticatorException e) {
//                        e.printStackTrace();
//                    }
//                    return result;
//                }
//
//                @Override
//                protected void onPostExecute(Bundle result) {
//                    if (result != null) {
//                        if (result.containsKey(AccountManager.KEY_INTENT)) {
//                            startActivity((Intent) result.get(AccountManager.KEY_INTENT));
//                        } else {
//                            Toast.makeText(MainActivity.this, result.getString(AccountManager.KEY_AUTHTOKEN), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//            };
//            execute.execute();

        } else {
            Toast.makeText(this, "No Accounts", Toast.LENGTH_SHORT).show();
        }
    }

}

