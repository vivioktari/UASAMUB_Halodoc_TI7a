package ac.id.atmaluhur.uas_amub_halodoc_ti7a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeAct extends AppCompatActivity {
    ImageButton a,b,c,d,e,f;
    ImageView photo;
    TextView user,balance,bio;

    DatabaseReference reference;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String useranme_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getUsernameLocal();

        a = findViewById(R.id.a);
        b = findViewById(R.id.b);
        c = findViewById(R.id.c);
        d = findViewById(R.id.d);
        e = findViewById(R.id.e);
        f = findViewById(R.id.f);

        photo = findViewById(R.id.imageView4);
        user = findViewById(R.id.textView16);
        bio = findViewById(R.id.textView17);
        balance = findViewById(R.id.textView18);

        //child user harus sesuai dengan database firebase
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(useranme_key_new);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                bio.setText(dataSnapshot.child("bio").getValue().toString());
                balance.setText("Rp. "+dataSnapshot.child("nama_lengkap").getValue().toString());
                Picasso.with(HomeAct.this).load(dataSnapshot.child("url_photo_profile").getValue().toString()).centerCrop().fit().into(photo);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoprofile = new Intent( HomeAct.this,MyProfileAct.class);

                startActivity(gotoprofile);
            }
        });

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu_a = new Intent(HomeAct.this,DetailAct.class);
                menu_a.putExtra("jenis tiket", "torri");
                startActivity(menu_a);

            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu_b = new Intent(HomeAct.this,DetailAct.class);
                menu_b.putExtra("jenis tiket", "pagoda");
                startActivity(menu_b);

            }
        });

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu_c = new Intent(HomeAct.this,DetailAct.class);
                menu_c.putExtra("jenis tiket", "pisa");
                startActivity(menu_c);

            }
        });

        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu_d = new Intent(HomeAct.this,DetailAct.class);
                menu_d.putExtra("jenis tiket", "candi");
                startActivity(menu_d);

            }
        });

        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu_e = new Intent(HomeAct.this,DetailAct.class);
                menu_e.putExtra("jenis tiket", "spinx");
                startActivity(menu_e);

            }
        });

        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu_f = new Intent(HomeAct.this,DetailAct.class);
                menu_f.putExtra("jenis tiket", "monas");
                startActivity(menu_f);

            }
        });
    }

    public void getUsernameLocal () {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        useranme_key_new = sharedPreferences.getString(username_key, "");
    }
}

