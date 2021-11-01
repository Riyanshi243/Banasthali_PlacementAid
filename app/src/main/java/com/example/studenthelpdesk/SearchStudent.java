package com.example.studenthelpdesk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;

public class SearchStudent extends AppCompatActivity {
    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_student);
        name=(EditText) findViewById(R.id.uname3);


    }
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
    }
}