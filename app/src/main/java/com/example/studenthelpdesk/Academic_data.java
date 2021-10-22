package com.example.studenthelpdesk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class Academic_data extends AppCompatActivity {
private EditText course,branch,rollno,erollno,cgpa,tenth,twelth,dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_data);
        dob=(EditText) findViewById(R.id.dob);
    }
}