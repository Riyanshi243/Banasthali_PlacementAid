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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class SeeMyData extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    Data data;
    TextView aadhar,name,pno,cgpa,gender,dob,rollno,fathersname,mothersname,pan,email,course,branch,enro,ten,twelve,semester,address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_my_data);
        name=(TextView)findViewById(R.id.name);
        pno=(TextView) findViewById(R.id.phonenumber);
        semester=(TextView)findViewById(R.id.sem1);
        aadhar=(TextView)findViewById(R.id.aadhar);
        cgpa=(TextView) findViewById(R.id.cgpa1);
        gender=(TextView) findViewById(R.id.gender1);
        address=(TextView)findViewById(R.id.address);
        dob=(TextView) findViewById(R.id.dob1);
        rollno=(TextView) findViewById(R.id.rollno1);
        fathersname=(TextView) findViewById(R.id.fathersname1);
        mothersname=(TextView) findViewById(R.id.mothersname);
        pan=(TextView) findViewById(R.id.pan1);
        email= (TextView) findViewById(R.id.email1);
        course= (TextView) findViewById(R.id.course1);
        branch= (TextView) findViewById(R.id.branch1);
        enro= (TextView) findViewById(R.id.enrollment);
        ten= (TextView) findViewById(R.id.ten);
        twelve= (TextView) findViewById(R.id.twe);

        show();
    }
    public void show()
    {
        data=SignIn.data;
        name.setText(data.getName());
        fathersname.setText(data.getFname());
        mothersname.setText(data.getMname());
        cgpa.setText((data.getCgpa()));
        enro.setText(data.getEno());
        pno.setText(data.getPno());
        email.setText(data.getEmail());
        aadhar.setText(data.getAadhar());
        gender.setText(data.getGender());
        branch.setText(data.getBranch());
        course.setText(data.getCourse());
        pan.setText(data.getPan());
        address.setText(data.getAddress());
        semester.setText(data.getSemester());
        dob.setText(data.getDob());
        rollno.setText(data.getRno());
        ten.setText((data.getTen()));
        twelve.setText((data.getTwel()));
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        show();
    }

    public void edit(View v)
    {
        AlertDialog.Builder ab=new AlertDialog.Builder(v.getContext());
        ab.setTitle("What do you want to edit?");
        ab.setPositiveButton("Personal Details", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(SeeMyData.this,Personal_Details.class);
                startActivity(intent);
                finish();
            }
        }).setNeutralButton("Academic Details", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(SeeMyData.this,Academic_data.class);
                startActivity(intent);
                finish();
            }
        }).setNegativeButton("Uploads", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(SeeMyData.this,Upload_data.class);
                startActivity(intent);
                finish();
            }
        });
        ab.create().show();
    }
    public void save(View v)
    {
        AlertDialog.Builder ab=new AlertDialog.Builder(v.getContext());
        ab.setTitle("Are you sure?");
        ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                firebaseAuth=FirebaseAuth.getInstance();
                firebaseAuth.createUserWithEmailAndPassword(data.getEmail(),SignIn.password1).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        data.ToDatabase();
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        String f[]={null};
                        DocumentReference documentReference = db.collection("AllowedUser").document(data.getEmail());
                        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Map<String, Object> m = documentSnapshot.getData();
                                m.put("New",false);
                                documentReference.set(m);
                            }
                        });
                        Intent intent=new Intent(SeeMyData.this,Student_page.class);
                        Toast.makeText(SeeMyData.this,"SIGN UP SUCCESSFULL",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SeeMyData.this,"SIGN UP Failed\nTry Again",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //close
            }
        });
        ab.create().show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //intent to acedemic
        Intent intent = new Intent(SeeMyData.this,Academic_data.class);
        startActivity(intent);
        finish();
    }
}