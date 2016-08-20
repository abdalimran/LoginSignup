package com.github.abdalimran.loginsignup.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.github.abdalimran.loginsignup.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupActivity extends AppCompatActivity {

    private static final int REQUEST_SIGNUP = 1;
    private static final int REQUEST_LOGIN = 1;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @BindView(R.id.name) EditText name;
    @BindView(R.id.email) EditText email;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.mobileno) EditText mobileno;
    @BindView(R.id.country) EditText country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        setResult(RESULT_OK, getIntent());

        sharedPreferences = getSharedPreferences("LoginSignupData", MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    @OnClick(R.id.signup_btn)
    public void finishsignup(){
        signup();
    }

    @OnClick(R.id.already_reg)
    public void gotologin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, REQUEST_LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                this.finish();
            }
        }

        if (requestCode == REQUEST_LOGIN) {
            if (resultCode == RESULT_OK) {
                this.finish();
            }
        }
    }

    public void signup() {
        if (validate()) {
            final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Creating Account...");
            progressDialog.show();

            String getname = name.getText().toString();
            String getemail = email.getText().toString();
            String getpassword = password.getText().toString();
            String getmobileno = mobileno.getText().toString();
            String getcountry = country.getText().toString();


            editor.putString("name",getname);
            editor.putString("email",getemail);
            editor.putString("password",getpassword);
            editor.putString("mobileno",getmobileno);
            editor.putString("country",getcountry);
            editor.putBoolean("autologin",true);
            editor.commit();


            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            onSignupSuccess();
                            progressDialog.dismiss();
                        }
                    }, 2000);
        }
        else {
            onSignupFailed();
        }
    }

    public void onSignupSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, REQUEST_SIGNUP);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getApplicationContext(), "Enter valid information.", Toast.LENGTH_LONG).show();
    }

    public boolean validate() {
        boolean valid = true;

        String getname = name.getText().toString();
        String getemail = email.getText().toString();
        String getpassword = password.getText().toString();
        String getmobileno = mobileno.getText().toString();
        String getcountry = country.getText().toString();

        if (getname.isEmpty() || getname.length() < 3) {
            name.setError("At least 3 characters.");
            valid = false;
        } 
        else {
            name.setError(null);
        }

        if (getemail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(getemail).matches()) {
            email.setError("Enter a valid email address.");
            valid = false;
        } else {
            email.setError(null);
        }

        if (getpassword.isEmpty() || getpassword.length() < 4 || getpassword.length() > 10) {
            password.setError("Between 4 to 10 alphanumeric characters.");
            valid = false;
        } else {
            password.setError(null);
        }

        if (getmobileno.isEmpty() || getmobileno.length() < 11 || getmobileno.length() > 15) {
            mobileno.setError("Between 11 to 15 numbers.");
            valid = false;
        } else {
            mobileno.setError(null);
        }

        if (getcountry.isEmpty()) {
            country.setError("Enter valid country name.");
            valid = false;
        } else {
            country.setError(null);
        }

        return valid;
    }
}
