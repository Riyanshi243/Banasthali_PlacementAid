package com.example.studenthelpdesk;

import static java.lang.System.currentTimeMillis;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class student_send_request extends AppCompatActivity {
TextView wel,curval,newcurval,rsn,title;
    //Spinner spinnerEditDetails;
    Data data;
    String what[]=new String[1];
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_student_send_request);
        wel=(TextView)findViewById(R.id.welcome);
        title=(TextView)findViewById(R.id.currentVal_);
        curval=(TextView)findViewById(R.id.currentVal);
        newcurval=(TextView)findViewById(R.id.newcurrentval);
        rsn=(TextView)findViewById(R.id.reason);
        data=Student_page.data;
        wel.setText("HELLO "+data.getUname());
        String t=title.getText().toString()+" "+Student_viewData.change;
        title.setText(t);
        t=getVal(Student_viewData.change);
        curval.setText(t);
    }
    public  String getVal(String val)
    {
        String currval="";
        if(val=="Course")
            currval=data.getCourse();
        else if(val=="Roll number")
            currval=data.getRno();
            else if(val=="Enrollment number")
            currval=data.getEno();
            else if(val=="Branch")
            currval=data.getBranch();
             else if(val=="CGPA")
            currval=data.getCgpa();
            else if(val=="Tenth Marks")
            currval=data.getTen();
            else if(val=="Twelth Marks")
            currval=data.getTwel();
            else if(val=="Semester")
            currval=data.getSemester();
            else if(val=="Name")
            currval=data.getName();
            else if(val=="Father's Name")
            currval=data.getFname();
            else if(val=="Mother's Name")
            currval=data.getMname();
            else
                currval="";
        return currval;
    }
    public void send_req(View view)
    {
        AlertDialog.Builder ab=new AlertDialog.Builder(view.getContext());
        ab.setTitle("Confirm Request");
        ab.setMessage("Are you sure you want to send request");
        ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseFirestore f=FirebaseFirestore.getInstance();
                final DocumentReference doc = f.collection("Request").document(data.getEmail());
                doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot do1 = task.getResult();
                        if(do1.exists())
                        {
                            Map<String , Object> m=do1.getData();
                            long no= (long) m.get("Request Number");
                            m.put("Request Number",no+1);
                            DocumentReference d2 = doc.collection("Request "+(no+1)).document("Request");
                            Map<String , Object> m2=new HashMap<String, Object>();
                            m2.put("Change what",Student_viewData.change);
                            m2.put("Value",newcurval.getText().toString());
                            m2.put("Reason",rsn.getText().toString());
                            m2.put("Status",1);//under consideration //2 suceeded
                            Date time = Calendar.getInstance().getTime();
                            m2.put("Applied Date",time);
                            d2.set(m2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    doc.set(m);
                                    Toast.makeText(student_send_request.this,"Request Sent",Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(student_send_request.this,"Request Sent",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else
                        {
                            Map<String , Object> m=new HashMap<String, Object>();
                            m.put("Request Number",1);
                            DocumentReference d2 = doc.collection("Request 1").document("Request");
                            Map<String , Object> m2=new HashMap<String, Object>();
                            m2.put("Change what",Student_viewData.change);
                            m2.put("Value",newcurval.getText().toString());
                            m2.put("Status",1);//under consideration //2 suceeded
                            m2.put("Reason",rsn.getText().toString());
                            Date time = Calendar.getInstance().getTime();
                            m2.put("Applied Date",time);
                            d2.set(m2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    doc.set(m);
                                    Toast.makeText(student_send_request.this,"Request Sent",Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    m.put("Request Number",0);
                                    doc.set(m);
                                    Toast.makeText(student_send_request.this,"Request Sent",Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }
                });
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
    /*public void submit(View v)
    {
        TextView textView = (TextView)spinnerEditDetails.getSelectedView();
        String result = textView.getText().toString();
        Toast.makeText(this,result,Toast.LENGTH_LONG);
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(student_send_request.this,Student_viewData.class);
                        startActivity(intent);
                        finish();
        //to view data
    }
}
