package github.grace5921.TwrpBuilder.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import github.grace5921.TwrpBuilder.MainActivity;
import github.grace5921.TwrpBuilder.R;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset, btnLogin2,btnSignUp,btnSignIn,btnCreateAccount;
    private LinearLayout btn_login_singup_linear;
    private TextInputLayout TextInputLayoutPass;
    private ImageView TeamWinLoginLogo,XdaLoginLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        // set the view now
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
        btnLogin2=(Button)findViewById(R.id.btn_login_2);
        btn_login_singup_linear=(LinearLayout)findViewById(R.id.btn_login_singup_linear);
        TextInputLayoutPass=(TextInputLayout)findViewById(R.id.text_input_layout_password);
        btn_login_singup_linear.setVisibility(View.VISIBLE);
        XdaLoginLogo=(ImageView)findViewById(R.id.xda_login_logo);
        TeamWinLoginLogo=(ImageView)findViewById(R.id.teamwin_login_logo);
        XdaLoginLogo.setVisibility(View.VISIBLE);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnCreateAccount = (Button) findViewById(R.id.create_account_button);



        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSignUp.setVisibility(View.VISIBLE);
                btn_login_singup_linear.setVisibility(View.GONE);
                inputEmail.setVisibility(View.VISIBLE);
                TextInputLayoutPass.setVisibility(View.VISIBLE);
                inputPassword.setVisibility(View.VISIBLE);
                btnSignIn.setVisibility(View.VISIBLE);
                TeamWinLoginLogo.setVisibility(View.VISIBLE);
                XdaLoginLogo.setVisibility(View.GONE);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSignIn.setVisibility(View.GONE);
                btnSignUp.setVisibility(View.GONE);
                btnCreateAccount.setVisibility(View.VISIBLE);
                btnLogin2.setVisibility(View.VISIBLE);
                btnReset.setVisibility(View.VISIBLE);

            }
        });

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnCreateAccount.setVisibility(View.GONE);
                btnLogin2.setVisibility(View.GONE);
                btnSignUp.setVisibility(View.VISIBLE);
                btnSignIn.setVisibility(View.VISIBLE);
                btnReset.setVisibility(View.GONE);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user


            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_login_singup_linear.setVisibility(View.GONE);
                btnLogin2.setVisibility(View.VISIBLE);
                inputEmail.setVisibility(View.VISIBLE);
                TextInputLayoutPass.setVisibility(View.VISIBLE);
                inputPassword.setVisibility(View.VISIBLE);
                btnReset.setVisibility(View.VISIBLE);
                TeamWinLoginLogo.setVisibility(View.VISIBLE);
                XdaLoginLogo.setVisibility(View.GONE);
                btnCreateAccount.setVisibility(View.VISIBLE);

            }
        });
        btnLogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user

            }
        });
    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        XdaLoginLogo.setVisibility(View.VISIBLE);
        btn_login_singup_linear.setVisibility(View.VISIBLE);
        TeamWinLoginLogo.setVisibility(View.GONE);
        btnLogin2.setVisibility(View.GONE);
        inputEmail.setVisibility(View.GONE);
        TextInputLayoutPass.setVisibility(View.GONE);
        btnSignUp.setVisibility(View.GONE);
        btnReset.setVisibility(View.GONE);
        btnCreateAccount.setVisibility(View.GONE);
        btnSignIn.setVisibility(View.GONE);
        this.doubleBackToExitPressedOnce = true;

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }



}

