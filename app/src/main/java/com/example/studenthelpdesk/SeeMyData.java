package com.example.studenthelpdesk;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class SeeMyData extends AppCompatActivity {

    FirebaseFirestore firestore;
    DocumentReference documentReference,documentReference2;
    Data data;
    TextView name,pno,cgpa,gender,dob,rollno,fathersname,mothersname,pan,email,course,branch,enro,ten,twelve;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_my_data);
        name=(TextView)findViewById(R.id.name);
        pno=(TextView) findViewById(R.id.phonenumber);
        cgpa=(TextView) findViewById(R.id.cgpa1);
        gender=(TextView) findViewById(R.id.gender1);
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
        /* documentReference=firestore.collection("AllowedUser").document(email).collection("Change").document("change");
        documentReference2=firestore.collection("AllowedUser").document(email).collection("Permanent").document("perm");
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String, Object> doc = documentSnapshot.getData();
                show(doc);
            }
        });*/
    }
    public void show()
    {
        data=SignIn.data;
        name.setText(data.getName());
        fathersname.setText(data.getFname());
        mothersname.setText(data.getMname());
        cgpa.setText(Float.toString(data.getCgpa()));
        enro.setText(data.getEno());
        pno.setText(data.getPno());
        gender.setText(data.getGender());
        branch.setText(data.getBranch());
        course.setText(data.getCourse());
        dob.setText(data.getDob());
        rollno.setText(data.getRno());
        ten.setText(Float.toString(data.getTen()));
        twelve.setText(Float.toString(data.getTwel()));
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
        }).setNeutralButton("Uploads", new DialogInterface.OnClickListener() {
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
                data.ToDatabase();
                Intent intent=new Intent(SeeMyData.this,LoginActivity.class);
                Toast.makeText(SeeMyData.this,"SIGN UP SUCCESSFULL\nNOW LOGIN",Toast.LENGTH_SHORT).show();
                finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //close
            }
        });
        ab.create().show();
    }

}