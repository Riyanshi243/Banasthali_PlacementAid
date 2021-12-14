package com.example.studenthelpdesk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.security.acl.AclEntry;

public class Academic_data extends AppCompatActivity {
    private EditText course,branch,rollno,erollno,cgpa,tenth,twelth,semester;
    private String course1,branch1,rno,erno;
    private String tenthm,twelthm,cgpa1,sem;
    private Button btnsubmit;
    Data data;
    String backto="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_data);
        boolean iseaxta=getIntent().hasExtra("End");
        if(iseaxta) {
            Bundle bundle = getIntent().getExtras();
            backto= (bundle.getString("End"));
        }
        course=(EditText) findViewById(R.id.course);
        branch=(EditText) findViewById(R.id.branch);
        rollno=(EditText) findViewById(R.id.rollno);
        erollno=(EditText) findViewById(R.id.erollno);
        semester=(EditText)findViewById(R.id.sem1);
        cgpa=(EditText) findViewById(R.id.cgpa);
        tenth=(EditText) findViewById(R.id.tenth);
        twelth=(EditText) findViewById(R.id.twelth);
        btnsubmit= (Button) findViewById(R.id.next);
        show();
    }
    public void show()
    {
        data=SignIn.data;
        course.setText(data.getCourse());
        branch.setText(data.getBranch());
        rollno.setText(data.getRno());
        erollno.setText(data.getEno());
        cgpa.setText(data.getCgpa());
        tenth.setText(data.getTen());
        semester.setText(data.getSemester());
        twelth.setText(data.getTwel());
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
        cgpa1=cgpa.getText().toString();
        data.setCgpa(cgpa1);
        tenthm=(tenth.getText().toString());
        data.setTen(tenthm);
        twelthm=(twelth.getText().toString());
        data.setTwel(twelthm);
       sem=(semester.getText().toString());
       data.setSemester(sem);
        

    }
    public void savedata(View V)
    {
        btnsubmit.setEnabled(false);
        ProgressBar pbar =findViewById(R.id.progressBar6);
        pbar.setVisibility(View.VISIBLE);
        save();

                    float cal =Float.parseFloat(cgpa1);
                    float ten=Float.parseFloat(tenthm);
                    float twelve=Float.parseFloat(twelthm);
                  //  float se=Float.parseFloat(sem);
                    if(rollno.getText().toString().trim().length()==0)
                    {
                        rollno.setError("ENTER ROLL NUMBER");
                        pbar.setVisibility(View.INVISIBLE);
                        btnsubmit.setEnabled(true);
                        return;
                    }
                    if(erollno.getText().toString().trim().length()==0)
                    {
                       erollno.setError("ENTER ENROLLMENT NUMBER");
                       pbar.setVisibility(View.INVISIBLE);
                       btnsubmit.setEnabled(true);
                       return;
                     }
                    if(course.getText().toString().trim().length()==0)
                    {
                        course.setError("ENTER COURSE");
                        pbar.setVisibility(View.INVISIBLE);
                        btnsubmit.setEnabled(true);
                        return;
                     }
                    if(branch.getText().toString().trim().length()==0)
                    {
                        branch.setError("ENTER BRANCH");
                        pbar.setVisibility(View.INVISIBLE);
                        btnsubmit.setEnabled(true);
                        return;
                    }

                   if(Float.toString(cal).trim().length()==0)
                   {
                       cgpa.setError("ENTER CGPA(in SCALE of 10)");
                       pbar.setVisibility(View.INVISIBLE);
                       btnsubmit.setEnabled(true);
                       return;
                   }
                   else if((cal>10 || cal<0))
                   {
                         cgpa.setError("INVALID CGPA(in SCALE of 10)");
                         pbar.setVisibility(View.INVISIBLE);
                         btnsubmit.setEnabled(true);
                         return;
                   }
                   if( Float.toString(ten).trim().length()==0)
                    {
                         tenth.setError("ENTER MARKS OF CLASS 10th");
                        pbar.setVisibility(View.INVISIBLE);
                        btnsubmit.setEnabled(true);
                         return;
                   }
                   else if((ten>100 || ten<0))
                  {
                      tenth.setError("INVALID MARKS");
                      pbar.setVisibility(View.INVISIBLE);
                      btnsubmit.setEnabled(true);
                      return;
                  }

                 if( Float.toString(twelve).trim().length()==0)
                 {
                     twelth.setError("ENTER MARKS OF CLASS 12th");
                     pbar.setVisibility(View.INVISIBLE);
                     btnsubmit.setEnabled(true);
                      return;
                 }
                  else if((twelve>100 || twelve<0))
                  {
                      twelth.setError("INVALID MARKS");
                      pbar.setVisibility(View.INVISIBLE);
                      btnsubmit.setEnabled(true);
                      return;
                  }

                 if(semester.getText().toString().trim().length()==0)
                      {
                      semester.setError("ENTER SEMESTER");
                          pbar.setVisibility(View.INVISIBLE);
                          btnsubmit.setEnabled(true);
                     return;
                     }
                     else if(semester.getText().toString().length()<0 ||semester.getText().toString().length()>10)
                      {
                      semester.setError("ENTER VALID SEMESTER");
                          pbar.setVisibility(View.INVISIBLE);
                          btnsubmit.setEnabled(true);
                     return;
                     }
                     

        Intent intent=new Intent(Academic_data.this,Upload_data.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        show();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        if(backto.equalsIgnoreCase("1")) {
            Intent intent = new Intent(Academic_data.this, SeeMyData.class);
            startActivity(intent);
            finish();
        }
        else {
            Intent intent = new Intent(Academic_data.this, Personal_Details.class);
            startActivity(intent);
            finish();
        }
    }
}