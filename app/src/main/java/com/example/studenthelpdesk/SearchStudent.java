package com.example.studenthelpdesk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SearchStudent extends AppCompatActivity {
    private TextView name,fname,mname,phn,add,dob,gender,aadhar,pan,email,rno,enro,course,branch,sem,ten,twe,cgpa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_student);
        name=(TextView) findViewById(R.id.name);
        fname=(TextView) findViewById(R.id.fathersname1);
        mname=(TextView) findViewById(R.id.mothersname);
        phn=(TextView) findViewById(R.id.phonenumber);
        add=(TextView) findViewById(R.id.address);
        dob=(TextView) findViewById(R.id.dob1);
        gender=(TextView) findViewById(R.id.gender1);
        aadhar=(TextView) findViewById(R.id.aadhar);
        pan=(TextView) findViewById(R.id.pan1);
        email=(TextView) findViewById(R.id.email1);
        rno=(TextView) findViewById(R.id.rollno1);
        enro=(TextView) findViewById(R.id.enrollment);
        course=(TextView) findViewById(R.id.course1);
        branch=(TextView) findViewById(R.id.branch1);
        sem=(TextView) findViewById(R.id.sem1);
        ten=(TextView) findViewById(R.id.ten);
        twe=(TextView) findViewById(R.id.twe);
        cgpa=(TextView) findViewById(R.id.cgpa1);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }//logout
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_logout:
                AlertDialog.Builder ab=new AlertDialog.Builder(this);
                ab.setTitle("LOGOUT");
                ab.setMessage("Are you sure?");
                ab.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //logut
                        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                        firebaseAuth.signOut();
                        Intent intent = new Intent(SearchStudent.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("No ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing;
                    }
                });
                ab.create().show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //intent to admin page
        Intent intent = new Intent(SearchStudent.this,Admin_page.class);
        startActivity(intent);
        finish();
    }
}