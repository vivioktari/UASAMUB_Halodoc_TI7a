package com.example.uasamub_halodoc_ti7a;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginAct extends AppCompatActivity {
    TextView account;
    Button login;
    EditText username, password;

    DatabaseReference ref;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.btn_login);
        account = findViewById(R.id.tv_account);
        username = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toregister = new Intent(LoginAct.this, RegisterAct.class);
                startActivity(toregister);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setEnabled(false);
                login.setText("LOADING...");

                final String usern = username.getText().toString();
                final String pass = password.getText().toString();

                if (usern.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Username Kosong", Toast.LENGTH_SHORT).show();
                    //ubah state
                    login.setEnabled(true);
                    login.setText("LOGIN");
                } else {
                    if (pass.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Password Kosong", Toast.LENGTH_SHORT).show();
                        //ubah state
                        login.setEnabled(true);
                        login.setText("LOGIN");
                    } else {
                        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(usern);
                        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    //ambil data dari firebase
                                    String passwordFromfirebase = dataSnapshot.child("password").getValue().toString();

                                    //validasi password dengan data di firebase
                                    if (pass.equals(passwordFromfirebase)) {
                                        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(username_key, username.getText().toString());
                                        editor.apply();

                                        //berpindah ke activity
                                        Intent gohome = new Intent(LoginAct.this, RegisterAct.class);
                                        startActivity(gohome);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Password salah", Toast.LENGTH_SHORT).show();
                                        //ubah state
                                        Toast.makeText(getApplicationContext(), "Username Kosong", Toast.LENGTH_SHORT).show();
                                        //ubah state
                                        login.setEnabled(true);
                                        login.setText("LOGIN");
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Username tidak ada", Toast.LENGTH_SHORT).show();
                                    //ubah state
                                    login.setEnabled(true);
                                    login.setText("LOGIN");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

        });

    }
}