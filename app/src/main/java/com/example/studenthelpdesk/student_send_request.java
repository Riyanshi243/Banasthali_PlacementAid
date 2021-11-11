package com.example.studenthelpdesk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class student_send_request extends AppCompatActivity {
TextView wel,curval,newcurval,rsn;
    Spinner spinnerEditDetails;
    Data data;
    String what[]=new String[1];
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_student_send_request);
        wel=(TextView)findViewById(R.id.welcome);
        curval=(TextView)findViewById(R.id.currentVal);
        newcurval=(TextView)findViewById(R.id.newcurrentval);
        rsn=(TextView)findViewById(R.id.reason);
        data=Student_page.data;
        wel.setText("HELLO "+data.getUname());
        spinnerEditDetails= findViewById(R.id.spinner_edit_fields);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.edit_details, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerEditDetails.setAdapter(adapter);
        what[0]="";
        spinnerEditDetails.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(l==0)
                {
                    Toast.makeText(student_send_request.this,"Select a value",Toast.LENGTH_LONG).show();
                }
                if(l==1)
                {
                    curval.setText(data.getName());
                    what[0]="Name";
                }
                if(l==2) {
                    curval.setText(data.getRno());
                    what[0]="Rno";
                }
                if(l==3)
                {
                    curval.setText(data.getEno());

                }
                if(l==4)
                {
                    curval.setText(data.getTen());
                }
                if(l==5)
                {
                    curval.setText(data.getTwel());
                }
                if(l==6)
                {
                    curval.setText(data.getCgpa());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(student_send_request.this,"Hey",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void send_req(View view)
    {
        AlertDialog.Builder ab=new AlertDialog.Builder(view.getContext());
        ab.setTitle("Confirm Request");
        ab.setMessage("Are you sure you want to send request");
        ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                RequestHandler requestHandler=new RequestHandler();
                String a[]=requestHandler.ToDatabase(what[0],newcurval.getText().toString());
                if(a[0].equalsIgnoreCase("1"))
                {
                    Toast.makeText(student_send_request.this,a[1],Toast.LENGTH_LONG);
                    //set everything blank
                }
                else
                {
                    Toast.makeText(student_send_request.this,a[1],Toast.LENGTH_LONG);
                }
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //do nothing
            }
        });
        ab.create().show();

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
                        Intent intent = new Intent(student_send_request.this,LoginActivity.class);
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
    public void submit(View v)
    {
        TextView textView = (TextView)spinnerEditDetails.getSelectedView();
        String result = textView.getText().toString();
        Toast.makeText(this,result,Toast.LENGTH_LONG);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(student_send_request.this,Student_viewData.class);
                        startActivity(intent);
                        finish();
        //to view data
    }
}
