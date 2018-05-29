package edu.jam.telephony.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.jam.telephony.R;
import edu.jam.telephony.model.AccountSaver;
import edu.jam.telephony.network.RetrofitService;
import edu.jam.telephony.network.api.AccountApi;
import io.reactivex.disposables.Disposable;
import okhttp3.Credentials;

public class LoginActivity extends AppCompatActivity implements OnClickListener{

    @BindView(R.id.email)                   EditText emailText;
    @BindView(R.id.password)                EditText passwordText;
    @BindView(R.id.email_sign_in_button)    Button signInButton;
    @BindView(R.id.login_progress)          ProgressBar progressBar;

    AccountSaver saver;
    Disposable loginDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        saver = new AccountSaver(this);

        signInButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (TextUtils.isEmpty(email)){
            emailText.setError("Email empty");
            return;
        }
        if (TextUtils.isEmpty(password)){
            passwordText.setError("Password empty");
            return;
        }

        processCredentials(email, password);

    }

    private void processCredentials(String email, String password) {
        RetrofitService.credentials = Credentials.basic(email, password);

        AccountApi api = RetrofitService.createApi(AccountApi.class);

        loginDisposable = api.getAccount().subscribe(
                subscriber -> {
                    saver.save(subscriber);
                    saver.saveCredentials(email, password);

                    startActivity(new Intent(this, MainActivity.class));
                    this.finish();
                    },
                throwable -> {
                    Toast.makeText(this, "Can't login", Toast.LENGTH_SHORT).show();
                    passwordText.setText("");
                });
    }

}

