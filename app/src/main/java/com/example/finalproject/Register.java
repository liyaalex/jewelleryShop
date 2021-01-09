package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private EditText username,password,confirmPassword,phoneNumber,email;
    private Button regBtn;
    DatabaseReference databaseUser;
    private Boolean isUsernameCorrect,isPasswordCorrect,isPhoneCorrect,isEmailCorrect,isConfirmPasswordCorrect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username=findViewById(R.id.usernameReg);
        password=findViewById(R.id.passwordReg);
        confirmPassword=findViewById(R.id.confirmPasswordReg);
        phoneNumber=findViewById(R.id.phoneNoReg);
        email=findViewById(R.id.emailReg);
        regBtn=findViewById(R.id.registerBtn);
        databaseUser= FirebaseDatabase.getInstance().getReference("users");

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String usernameStr=username.getText().toString().trim();
                String passwordStr=password.getText().toString();
                String confirmPasswordStr=confirmPassword.getText().toString();
                String phoneStr=phoneNumber.getText().toString();
                String emailStr=email.getText().toString();

                if(usernameStr.isEmpty())
                {
                    username.setError(getText(R.string.error_user_empty));
                    isUsernameCorrect=false;
                }
                else if(usernameStr.length()<5)
                {
                    username.setError(getText(R.string.error_user_less_length));
                    isUsernameCorrect=false;
                }
                else
                {
                    username.setError(null);
                    isUsernameCorrect=true;
                }

                if(passwordStr.isEmpty())
                {
                    password.setError(getResources().getString(R.string.error_pass_empty));
                    isPasswordCorrect=false;
                }
                else if(passwordStr.length()<5)
                {
                    password.setError(getResources().getString(R.string.error_pass_less_length));
                    isPasswordCorrect=false;
                }
                else
                {
                    password.setError(null);
                    isPasswordCorrect=true;
                }

                if(confirmPasswordStr.isEmpty())
                {
                    confirmPassword.setError(getResources().getString(R.string.error_pass_empty));
                    isConfirmPasswordCorrect=false;
                }
                else if(confirmPasswordStr.length()<5)
                {
                    confirmPassword.setError(getResources().getString(R.string.error_pass_less_length));
                    isConfirmPasswordCorrect=false;
                }
                else
                {
                    confirmPassword.setError(null);
                    isConfirmPasswordCorrect=true;
                }

                if(emailStr.isEmpty())
                {
                    email.setError(getResources().getString(R.string.error_email_empty));
                    isEmailCorrect=false;
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(emailStr).matches())
                {
                    email.setError(getResources().getString(R.string.error_email_wrong));
                    isEmailCorrect=false;
                }
                else
                {
                    email.setError(null);
                    isEmailCorrect=true;
                }

                if(phoneStr.isEmpty())
                {
                    phoneNumber.setError(getResources().getString(R.string.error_phn_empty));
                    isPhoneCorrect=false;
                }
                else if(!Patterns.PHONE.matcher(phoneStr).matches())
                {
                    phoneNumber.setError(getResources().getString(R.string.error_phn_wrong));
                    isPhoneCorrect=false;
                }
                else
                {
                    phoneNumber.setError(null);
                    isPhoneCorrect=true;
                }


                if(isUsernameCorrect && isPasswordCorrect && isConfirmPasswordCorrect && isPhoneCorrect && isEmailCorrect)
                {
                    password.setError(null);
                    confirmPassword.setError(null);
                    if((password.getText().toString()).equals(confirmPassword.getText().toString())) {
                        UserDetails artist = new UserDetails(usernameStr, passwordStr, phoneStr, emailStr);
                        try {
                            databaseUser.child(usernameStr).setValue(artist);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error" + e, Toast.LENGTH_LONG).show();
                        }

                        Toast.makeText(getApplicationContext(), "User added", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(), MainPage.class);
                        i.putExtra("name", usernameStr);
                        startActivity(i);
                    }
                    else
                    {
                        confirmPassword.setError(getResources().getString(R.string.password_dont_match));
                        password.setError(getResources().getString(R.string.password_dont_match));
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"You should enter details!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}