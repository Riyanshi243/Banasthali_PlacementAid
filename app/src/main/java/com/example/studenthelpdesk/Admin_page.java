package com.example.studenthelpdesk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Admin_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        Button create_new_member=(Button) findViewById(R.id.createnew);
create_new_member.setOnClickListener(new View.OnClickListener(){
         @Override
         public void onClick(View v){
              Intent intent = new Intent(Admin_page.this,CreateStudents.class);
        startActivity(intent);
         }
         });
    }
}

