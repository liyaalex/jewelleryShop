package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForgetPassword extends AppCompatActivity {

    TextView username,password,confirm_psd;
    Button update;
    DatabaseReference databaseUser;
    UserDetails userDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        username=findViewById(R.id.txt_username);
        password=findViewById(R.id.txt_password);
        confirm_psd=findViewById(R.id.txt_confirmpassword);
        update=findViewById(R.id.btn_update);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().isEmpty()) {
                    username.setError(getText(R.string.error_user_empty));
                } else if (username.getText().toString().length() < 5) {
                    username.setError(getText(R.string.error_user_less_length));
                } else {
                    username.setError(null);
                }

                if (password.getText().toString().isEmpty()) {
                    password.setError(getResources().getString(R.string.error_pass_empty));
                } else if (password.getText().toString().length() < 5) {
                    password.setError(getResources().getString(R.string.error_pass_less_length));
                } else {
                    password.setError(null);
                }

                if (confirm_psd.getText().toString().isEmpty()) {
                    confirm_psd.setError(getResources().getString(R.string.error_pass_empty));
                } else if (confirm_psd.getText().toString().length() < 5) {
                    confirm_psd.setError(getResources().getString(R.string.error_pass_less_length));
                } else {
                    confirm_psd.setError(null);
                }


                updateUser();

            }
        });


    }

    public void updateUser()
    {
        if (password.getText().toString().equals(confirm_psd.getText().toString())) {

            databaseUser = FirebaseDatabase.getInstance().getReference("users").child(username.getText().toString());
            databaseUser.orderByChild("username").equalTo(username.getText().toString())
                    .addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            if(!dataSnapshot.exists()) {

                                Toast.makeText(getApplicationContext(), "User does not exist", Toast.LENGTH_LONG).show();

                            } else {
                                userDetails = dataSnapshot.getValue(UserDetails.class);

                                UserDetails userupdate = new UserDetails(username.getText().toString(), password.getText().toString(), userDetails.getPhone(), userDetails.getEmail());
                                databaseUser.setValue(userupdate);
                                Toast.makeText(getApplicationContext(), "Password Updated", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.w("loadPost:onCancelled", databaseError.toException());
                        }


                    });
        }
        else {
            Toast.makeText(getApplicationContext(), "Passwords are not matching", Toast.LENGTH_LONG).show();
        }


    }
}