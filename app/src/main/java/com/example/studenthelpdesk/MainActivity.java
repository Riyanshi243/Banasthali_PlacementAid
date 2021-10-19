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
         TextView button= (TextView) findViewById(R.id.textView);
         button.setOnClickListener(new View.OnClickListener(){
         @Override
         public void onClick(View v){
              Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
//.set
         }
         });
    }
}