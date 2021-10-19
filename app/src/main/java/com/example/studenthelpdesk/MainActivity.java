package com.example.studenthelpdesk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         TextView text= (TextView) findViewById(R.id.textView);
         text.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 //Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                 //testing
                 Intent intent = new Intent(MainActivity.this,CreateStudents.class);
                 startActivity(intent);
             }
         });

        Intent intent = new Intent(MainActivity.this,CreateStudents.class);
        startActivity(intent);
    }
}