package com.example.studenthelpdesk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class Student_page extends AppCompatActivity {
    static Data data;
    FirebaseAuth firebaseAuth;
    private long backPressedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_page);
        data=new Data();
        firebaseAuth=FirebaseAuth.getInstance();
        String f = data.FromDatabase(firebaseAuth.getCurrentUser().getEmail());
        if(f!=null)
        {
            Toast.makeText(Student_page.this,f,Toast.LENGTH_LONG).show();
            startActivity(new Intent(Student_page.this,LoginActivity.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main,menu);


        return super.onCreateOptionsMenu(menu);
    }//logout

    @Override
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
                        Intent intent = new Intent(Student_page.this,LoginActivity.class);
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

    public void test(View v)
    {

    }
    public void view_my_data(View v)
    {

        startActivity(new Intent(Student_page.this,Student_viewData.class));
        finish();
    }
    public void see_request_status(View v)
    {
        startActivity(new Intent(Student_page.this,Student_see_request_status.class));
        finish();
    }
    public void edit_my_data(View v)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("AllowedUser").document("AdminSettings");
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String, Object> m = documentSnapshot.getData();
                if((Boolean) m.get("Lock")==false)
                {
                    startActivity(new Intent(Student_page.this,Student_viewData.class));
                    finish();
                }
                else
                {
                    AlertDialog.Builder ab=new AlertDialog.Builder(Student_page.this);
                    ab.setTitle("DATABASE LOCKED");
                    ab.setMessage("Admin has locked the database.\nYou cannot edit it.");
                    ab.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //do nothing
                        }
                    });
                    ab.create().show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Student_page.this,e.toString(),Toast.LENGTH_LONG).show();
            }
        });

        
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //ask twice and exit
        if(backPressedTime+2000>System.currentTimeMillis()){
            super.onBackPressed();
            System.exit(1);
            return;
        }else {
            Toast.makeText(Student_page.this,"Press again to EXIT", Toast.LENGTH_LONG).show();
        }
        backPressedTime=System.currentTimeMillis();

    }
}