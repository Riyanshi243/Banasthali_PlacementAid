package com.example.studenthelpdesk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Student_page extends AppCompatActivity {
    static Data data;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_page);
        data=new Data();
        firebaseAuth=FirebaseAuth.getInstance();
        String f = data.FromDatabase(firebaseAuth.getCurrentUser().getEmail());
        if(f!=null)
        {
            Toast.makeText(Student_page.this,f,Toast.LENGTH_LONG).show();
            startActivity(new Intent(Student_page.this,LoginActivity.class));
            finish();
        }
    }

    public void test(View v)
    {
        startActivity(new Intent(Student_page.this,Student_viewData.class));
    }
}