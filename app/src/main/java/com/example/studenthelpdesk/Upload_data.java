package com.example.studenthelpdesk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.google.firebase.auth.FirebaseAuth;

public class Upload_data extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //intent to acedemic data
        Intent intent = new Intent(Upload_data.this,Academic_data.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main,menu);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signOut();
         Intent intent = new Intent(Upload_data.this,LoginActivity.class);
        startActivity(intent);
        finish();
        return super.onCreateOptionsMenu(menu);
    }//logout
}