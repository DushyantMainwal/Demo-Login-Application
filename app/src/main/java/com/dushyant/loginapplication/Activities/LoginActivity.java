package com.dushyant.loginapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dushyant.loginapplication.CustomDottedProgress;
import com.dushyant.loginapplication.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText usernameLogin, passwordLogin;
    private CustomDottedProgress customDottedProgress;
    Button loginButton;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            customDottedProgress.stopAnimation();
            customDottedProgress.setVisibility(View.GONE);
//            loginButton.setText(getText(R.string.login));
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        usernameLogin = findViewById(R.id.username_edit_text);
        passwordLogin = findViewById(R.id.password_edit_text);
        loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(this);
        customDottedProgress = findViewById(R.id.customDottedProgress);
        customDottedProgress.setVisibility(View.GONE);
    }

    private boolean isEmptyFields() {
        String usernameLoginText = usernameLogin.getText().toString();
        String passwordLoginText = passwordLogin.getText().toString();

        return usernameLoginText.isEmpty() || passwordLoginText.isEmpty();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
//                if (!isEmptyFields())
                loginButton.setText("");
                customDottedProgress.setVisibility(View.VISIBLE);
                customDottedProgress.startAnimation();
                handler.postDelayed(runnable, 2500);
//                else
//                    Toast.makeText(this, "Empty Fields", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
