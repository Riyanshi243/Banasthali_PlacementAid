package com.example.studenthelpdesk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Student_page extends AppCompatActivity {
    static Data data;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_page);
    }

    @Override
    public void onSupportActionModeStarted(@NonNull ActionMode mode) {
        super.onSupportActionModeStarted(mode);
        data=new Data();
        if(firebaseAuth.getCurrentUser()==null)
        {
            Intent i =new Intent(Student_page.this,LoginActivity.class);
            startActivity(i);
            finish();
        }
        String f=data.FromDatabase( firebaseAuth.getCurrentUser().getEmail());
        if(f!=null)
        {
            Toast.makeText(Student_page.this,f,Toast.LENGTH_LONG).show();
            startActivity(new Intent(Student_page.this,LoginActivity.class));
            finish();
        }
    }
}