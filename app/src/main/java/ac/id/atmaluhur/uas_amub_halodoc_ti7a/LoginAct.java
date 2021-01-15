package ac.id.atmaluhur.uas_amub_halodoc_ti7a;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginAct extends AppCompatActivity {
    Button login;
    EditText username, password;


    DatabaseReference reference;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);


        login = findViewById(R.id.login);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setEnabled(false);
                login.setText("Loaading...");

                final String user = username.getText().toString();
                final String pass = password.getText().toString();

                if (user.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Ussername kosong", Toast.LENGTH_SHORT).show();
                    login.setEnabled(true);
                    login.setText("LOGIN");
                } else {
                    if (pass.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Password Kosong", Toast.LENGTH_SHORT).show();
                        login.setEnabled(true);
                        login.setText("LOGIN");
                    } else {
                        reference = FirebaseDatabase.getInstance().getReference()
                                .child("Users").child(user);
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    String passwordFromFirebase = dataSnapshot.child("password").getValue().toString();

                                    if (password.equals(passwordFromFirebase)) {

                                        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(username_key, username.getText().toString());
                                        editor.apply();

                                        Intent gotohome = new Intent(LoginAct.this, HomeAct.class);
                                        startActivity(gotohome);

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Password Salah", Toast.LENGTH_SHORT).show();
                                        login.setEnabled(true);
                                        login.setText("LOGIN");
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Username Salah", Toast.LENGTH_SHORT).show();
                                    login.setEnabled(true);
                                    login.setText("LOGIN");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(), "Database Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }
}
