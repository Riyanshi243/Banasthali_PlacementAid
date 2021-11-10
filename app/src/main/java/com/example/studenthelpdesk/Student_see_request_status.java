package com.example.studenthelpdesk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Student_see_request_status extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_see_request_status);}

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
                        Intent intent = new Intent(Student_see_request_status.this,LoginActivity.class);
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
        //intent to students page
        Intent intent = new Intent(Student_see_request_status.this,Student_page.class);
        startActivity(intent);
        finish();
    }
}