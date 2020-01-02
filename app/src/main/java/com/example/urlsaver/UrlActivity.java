package com.example.urlsaver;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class UrlActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    EditText link;
    EditText description;
    Button save;

    ListView listViewUrlEntry;

    DatabaseReference databaseUsers;

    String uid;
    List<UrlEntry> urlEntryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url);

        mAuth = FirebaseAuth.getInstance();

        link = findViewById(R.id.editTextLink);
        description = findViewById(R.id.editTextDescription);
        save = findViewById(R.id.buttonSave);
        listViewUrlEntry = findViewById(R.id.listViewUrlEntry);
        urlEntryList = new ArrayList<>();

        //https://stackoverflow.com/questions/44494430/retrieve-user-data-from-firebase-database

        FirebaseUser user = mAuth.getCurrentUser();
        try {
            uid = user.getUid();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        databaseUsers = FirebaseDatabase.getInstance().getReference(uid);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                long n = dataSnapshot.child("data").getChildrenCount();
                System.out.println(n);

                urlEntryList.clear();
                for (DataSnapshot urlEntrySnapshot : dataSnapshot.child("data").child("arr").getChildren()) {
                    System.out.println(urlEntrySnapshot.getValue());
                    Map m = Collections.synchronizedMap((Map) urlEntrySnapshot.getValue());

                    System.out.println(m.get("link"));

                    UrlEntry urlEntry = new UrlEntry(m.get("description").toString(), (long) m.get("id"), m.get("link").toString(), (long) m.get("time"));

                    //UrlEntry urlEntry = urlEntrySnapshot.getValue(UrlEntry.class);

                    urlEntryList.add(urlEntry);
                }


                UrlEntryList adapter = new UrlEntryList(UrlActivity.this, urlEntryList);
                listViewUrlEntry.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

