package com.example.studenthelpdesk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SearchStudent extends AppCompatActivity {
    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_student);
        name=(EditText) findViewById(R.id.uname3);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main,menu);

        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        Intent intent = new Intent(SearchStudent.this,LoginActivity.class);
        startActivity(intent);
        finish();
        return super.onCreateOptionsMenu(menu);
    }//logout
    public void seedata(View view)
    {
        ScrollView sv=findViewById(R.id.scroll);
        //(View child);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //intent to admin page
        Intent intent = new Intent(SearchStudent.this,Admin_page.class);
        startActivity(intent);
        finish();
    }
}