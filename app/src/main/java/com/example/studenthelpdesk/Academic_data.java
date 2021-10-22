package com.example.studenthelpdesk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Academic_data extends AppCompatActivity {
    private EditText course,branch,rollno,erollno,cgpa,tenth,twelth;
    private String course1,branch1,rno,erno;
    private float tenthm,twelthm,cgpa1;
    Data data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_data);
        course=(EditText) findViewById(R.id.course);
        branch=(EditText) findViewById(R.id.branch);
        rollno=(EditText) findViewById(R.id.rollno);
        erollno=(EditText) findViewById(R.id.erollno);
        cgpa=(EditText) findViewById(R.id.cgpa);
        tenth=(EditText) findViewById(R.id.tenth);
        twelth=(EditText) findViewById(R.id.twelth);
    }
    public void savedata(View V)
    {
        data=SignIn.data;
        course1=course.getText().toString();
        data.setCourse(course1);
        branch1=branch.getText().toString();
        data.setBranch(branch1);
        rno=rollno.getText().toString();
        data.setRno(rno);
        erno=erollno.getText().toString();
        data.setEno(erno);
         cgpa1=Float.parseFloat(cgpa.getText().toString());
         data.setCgpa(cgpa1);
         tenthm=Float.parseFloat(tenth.getText().toString());
         data.setTen(tenthm);
         twelthm=Float.parseFloat(twelth.getText().toString());
         data.setTen(twelthm);

        Toast.makeText(Academic_data.this,"Hello",Toast.LENGTH_SHORT);


        //testing
        if(1==data.ToDatabase())
            Toast.makeText(Academic_data.this,"Success",Toast.LENGTH_SHORT);
        else
            Toast.makeText(Academic_data.this,"Failed",Toast.LENGTH_SHORT);




    }

}