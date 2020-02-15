package com.supernova.selleradmin.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.supernova.selleradmin.R;
import com.supernova.selleradmin.dialog.CustomProgress;
import com.supernova.selleradmin.util.Constants;
import com.supernova.selleradmin.util.SharedPrefs;
import com.supernova.selleradmin.viewmodel.LoginViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_email)
    TextInputEditText loginEmail;
    @BindView(R.id.login_password)
    TextInputEditText loginPassword;

    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initialize();
    }

    private void initialize() {

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    @OnClick({R.id.login_button, R.id.facebook_button, R.id.google_button, R.id.forgot_password_text})
    public void onViewClicked(View view) {

        switch (view.getId()) {

            case R.id.login_button:

                authenticate();
                break;

            case R.id.forgot_password_text:

                break;

            case R.id.facebook_button:

                break;

            case R.id.google_button:
                break;
        }
    }

    private void authenticate() {

        if (loginEmail.getText() == null || TextUtils.isEmpty(loginEmail.getText().toString())) {

            loginEmail.setError("Required");

        } else if (loginPassword.getText() == null || TextUtils.isEmpty(loginPassword.getText().toString())) {

            loginPassword.setError("Required");

        } else {

            String email = loginEmail.getText().toString();
            String password = loginPassword.getText().toString();
            CustomProgress progress = new CustomProgress();

            progress.setDialogMessage("Authenticating");
            progress.show(getSupportFragmentManager(), "progress");
            progress.setCancelable(false);

            viewModel.signInUser(email, password).observe(this, response -> {

                if (response != null) {

                    if (response.getStatus() != null) {

                        progress.dismiss();

                        if (response.getStatus().equals(Constants.STATUS_SUCCESS)) {

                            SharedPrefs prefs = new SharedPrefs(this);

                            prefs.setIsFirstInstall(true);
                            prefs.setIsLoggedIn(true);
                            prefs.setUserEmail(email);
                            prefs.setUserToken(response.getUserToken());

                            changeActivity();

                        } else {

                            Toast.makeText(this, response.getFailReason(), Toast.LENGTH_SHORT).show();
                        }

                    } else {

                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    progress.dismiss();
                    Toast.makeText(this, "Error connecting to server", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void changeActivity() {

        finish();
        overridePendingTransition(R.anim.slide_right, R.anim.stay);
        startActivity(new Intent(this, DashboardActivity.class));
    }
}
