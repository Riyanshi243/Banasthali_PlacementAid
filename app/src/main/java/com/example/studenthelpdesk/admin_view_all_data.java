package com.example.studenthelpdesk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class admin_view_all_data extends AppCompatActivity {
    TextView srno,email,name,gender,phoneno,DOB,rollno,enrollment,course,branch,sem,fname,mname,adhar,pan,ten,twelve,CGPA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_all_data);
        
        {
            //isme lo
            View v = LayoutInflater.from(admin_view_all_data.this).inflate(R.layout.view_data_table, null);
           srno =(TextView) v.findViewById(R.id.srno);
           email =(TextView) v.findViewById(R.id.email);
           name =(TextView) v.findViewById(R.id.name);
           gender=(TextView) v.findViewById(R.id.gender);
           phoneno=(TextView) v.findViewById(R.id.phoneno);
           DOB=(TextView) v.findViewById(R.id.DOB);
           rollno=(TextView) v.findViewById(R.id.rollno);
           enrollment=(TextView) v.findViewById(R.id.enrollment);
           course=(TextView) v.findViewById(R.id.course);
           branch=(TextView) v.findViewById(R.id.branch);
           sem=(TextView) v.findViewById(R.id.sem);
            fname=(TextView) v.findViewById(R.id.fname);
            mname=(TextView) v.findViewById(R.id.mname);
            adhar=(TextView) v.findViewById(R.id.adhar);
             pan=(TextView) v.findViewById(R.id.pan);
              ten=(TextView) v.findViewById(R.id.ten);
               twelve=(TextView) v.findViewById(R.id.twelve);
                CGPA=(TextView) findViewById(R.id.CGPA);

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //intent to admin page

        Intent intent = new Intent(admin_view_all_data.this,Admin_page.class);
        startActivity(intent);
        finish();
    }

}