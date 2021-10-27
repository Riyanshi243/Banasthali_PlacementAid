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
        save();
        show();
    }
    public void show()
    {
        data=SignIn.data;
        course.setText(data.getCourse());
        branch.setText(data.getBranch());
        rollno.setText(data.getRno());
        erollno.setText(data.getEno());
        cgpa.setText(data.getCgpa()+"");
        tenth.setText(data.getTen()+"");
        twelth.setText(data.getTwel()+"");
    }
    public void save()
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
    }
    public void savedata(View V)
    {
        save();
                    float cal =cgpa1;
                    float ten=tenthm;
                    float twelve=twelthm;
                    if(course.getText().toString().trim().length()==0)
                      {
               course.setError("ENTER COURSE");
               return;
                     }
                     if(branch.getText().toString().trim().length()==0)
                      {
                      branch.setError("ENTER BRANCH");
                     return;
                     }
                     if(rollno.getText().toString().trim().length()==0)
                      {
                      rollno.setError("ENTER  ROLL NUMBER");
                     return;
                     }
                     if(erollno.getText().toString().trim().length()==0)
                      {
                      erollno.setError("ENTER ENROLLMENT");
                     return;
                     }
                     if((cal>10 && cal<0) ||Float.toString(cgpa1).trim().length()==0)
                     {
                         cgpa.setError("INVALID CGPA");
                         return;
                     }
                  if((ten>100 && ten<0) || Float.toString(tenthm).trim().length()==0)
                  {
                      tenth.setError("INVALID MARKS");
                      return;
                  }
                  if((twelve>100 && twelve<0) || Float.toString(twelthm).trim().length()==0)
                  {
                      twelth.setError("INVALID MARKS");
                      return;

                  }


        Intent intent=new Intent(Academic_data.this,SeeMyData.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        show();
    }
}