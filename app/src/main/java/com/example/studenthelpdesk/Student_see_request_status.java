package com.example.studenthelpdesk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Student_see_request_status extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_see_request_status);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //intent to students page
        Intent intent = new Intent(Student_see_request_status.this,Student_page.class);
        startActivity(intent);
    }
}