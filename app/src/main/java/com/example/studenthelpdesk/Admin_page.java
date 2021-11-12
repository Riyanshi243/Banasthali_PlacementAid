package com.example.studenthelpdesk;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


public class Admin_page extends AppCompatActivity {

    private long backPressedTime;
    ImageView lock_data_base;
    int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        lock_data_base = (ImageView) findViewById(R.id.lockdatabase);

        lock_data_base.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (flag == 0) {
                    AlertDialog.Builder ab=new AlertDialog.Builder(view.getContext());
                    ab.setTitle("UNLOCK DATABASE");
                    ab.setMessage("Are you sure you want to unLock Database");
                    ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //intent to student send request
                            lock_data_base.setImageResource(R.drawable.unlock_database);
                            flag = 1;
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    ab.create().show();
                } else if (flag == 1) {
                     AlertDialog.Builder ab=new AlertDialog.Builder(view.getContext());
                    ab.setTitle("LOCK DATABASE");
                    ab.setMessage("Are you sure you want to Lock Database");
                    ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //intent to student send request
                            lock_data_base.setImageResource(R.drawable.lock_database);
                            flag = 0;
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    ab.create().show();
                    //lock_data_base.setImageResource(R.drawable.lock_database);
                    //flag = 0;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public void new_user(View v)
    {
        Intent intent = new Intent(Admin_page.this,CreateStudents.class);
        startActivity(intent);
        finish();
    }
    public void search_user(View v)
    {
        Intent intent = new Intent(Admin_page.this,SearchStudent.class);
        startActivity(intent);
        finish();
    }
    public void view_all_data(View v)
    {
        Intent intent = new Intent(Admin_page.this,admin_view_all_data.class);
        startActivity(intent);
        finish();
    }
    public void see_request(View v)
    {
        Intent intent = new Intent(Admin_page.this,admin_view_requests.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog.Builder ab=new AlertDialog.Builder(Admin_page.this);
        ab.setTitle("Exit");
        ab.setMessage("Are you sure you want to Exit?");
        ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //intent to student send request
               // Intent intent=new Intent(view.getContext(),student_send_request.class);
                //startActivity(intent);
                //finish();
                if(backPressedTime+2000>System.currentTimeMillis()){

            System.exit(1);
            return;
        }else {
            Toast.makeText(Admin_page.this,"Press again to EXIT", Toast.LENGTH_LONG).show();
        }
        backPressedTime=System.currentTimeMillis();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //do nothing
            }
        });
        ab.create().show();

        }
        public void searchuser(View view)
        {
            //search user
        }
}

