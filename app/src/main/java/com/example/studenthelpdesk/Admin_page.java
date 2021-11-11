package com.example.studenthelpdesk;

import androidx.appcompat.app.AppCompatActivity;

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
            public void onClick(View v) {
                if (flag == 0) {
                    lock_data_base.setImageResource(R.drawable.unlock_database);
                    flag = 1;
                } else if (flag == 1) {
                    lock_data_base.setImageResource(R.drawable.lock_database);
                    flag = 0;
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
        if(backPressedTime+2000>System.currentTimeMillis()){
            super.onBackPressed();
            System.exit(1);
            return;
        }else {
            Toast.makeText(Admin_page.this,"Press again to EXIT", Toast.LENGTH_LONG).show();
        }
        backPressedTime=System.currentTimeMillis();
        }
        public void searchuser(View view)
        {
            //search user
        }
}

