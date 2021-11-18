package com.example.studenthelpdesk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class admin_view_requests extends AppCompatActivity {
private EditText req_d;
private TextView textV5,name1,textV6,textV7;
private LinearLayout ll11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_requests);
        req_d=(EditText) findViewById(R.id.request_Date);
         textV5=(TextView) findViewById(R.id.textView5);
         name1=(TextView) findViewById(R.id.name);
         textV6=(TextView) findViewById(R.id.textView6);
         textV7=(TextView) findViewById(R.id.textView7);
         ll11=(LinearLayout) findViewById(R.id.ll1);
         
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //intent to admin page
        Intent intent = new Intent(admin_view_requests.this, Admin_page.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                AlertDialog.Builder ab = new AlertDialog.Builder(this);
                ab.setTitle("LOGOUT");
                ab.setMessage("Are you sure?");
                ab.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //logut
                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                        firebaseAuth.signOut();
                        Intent intent = new Intent(admin_view_requests.this, LoginActivity.class);
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
}