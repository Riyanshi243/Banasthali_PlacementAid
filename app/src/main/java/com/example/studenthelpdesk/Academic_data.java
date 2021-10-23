package com.example.studenthelpdesk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
                    float cal =cgpa1;
                    float ten=tenthm;
                    float twelve=twelthm;
                     if(cal>10 && cal<0) {
                         cgpa.setError("INVALID");
                     }
                  if(ten>100 && ten<0)
                  {
                      tenth.setError("INVALID");
                      return;
                  }
                  if(twelve>100 && twelve<0)
                  {
                      twelth.setError("INVALID");

                  }


        Intent intent=new Intent(Academic_data.this,SeeMyData.class);
        startActivity(intent);
    }

}