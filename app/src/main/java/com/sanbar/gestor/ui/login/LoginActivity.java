package com.sanbar.gestor.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.sanbar.gestor.MenuActivity;
import com.sanbar.gestor.R;
import com.sanbar.gestor.Sesion;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private Sesion session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeLinearLayout();

                session = new Sesion(usernameEditText.getText().toString(),passwordEditText.getText().toString());

                Boolean auxToken = false;
                Boolean auxContratistas = false;

                if (session.attemptToken()) {
                    auxToken = true;
                }
                if (session.attemptContratistas()){
                    auxContratistas = true;
                }
                if (auxContratistas)
                    if (auxToken) {
                        Intent myIntent = new Intent(LoginActivity.this, MenuActivity.class);
                        myIntent.putExtra("SESSION", session); //Optional parameters
                        LoginActivity.this.startActivity(myIntent);
                    } else {
                        Toast.makeText(getApplicationContext(),"Credenciales inválidas",Toast.LENGTH_LONG).show();
                    }
            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        // TODO : initiate successful logged in experience

        Intent myIntent = new Intent(LoginActivity.this, MenuActivity.class);
        myIntent.putExtra("loginModelData1", model.getDisplayName()); //Optional parameters
        LoginActivity.this.startActivity(myIntent);
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    public void changeLinearLayout(){

        final LinearLayout ll_progressBar = (LinearLayout) findViewById(R.id.linearlayout_login_progressbar);
        final LinearLayout ll_activity = (LinearLayout) findViewById(R.id.linearlayout_login_activity);

        if (ll_progressBar.getVisibility()==View.GONE){
            ll_activity.setVisibility(View.GONE);
            ll_progressBar.setVisibility(View.VISIBLE);
        } else if (ll_progressBar.getVisibility()==View.VISIBLE){
            ll_progressBar.setVisibility(View.GONE);
            ll_activity.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onResume(){
        super.onResume();

        final LinearLayout ll_progressBar = (LinearLayout) findViewById(R.id.linearlayout_login_progressbar);
        final LinearLayout ll_activity = (LinearLayout) findViewById(R.id.linearlayout_login_activity);
        ll_progressBar.setVisibility(View.GONE);
        ll_activity.setVisibility(View.VISIBLE);
    }
}


