package com.example.studenthelpdesk;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AutomaticZenRule;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class SignIn extends AppCompatActivity {
    private DocumentReference documentReference;
    private EditText name, email,password,confirmpassword;
    private Button btnsubmit;
    static Data data;
    protected Map<String, Object> doc;
    public static String password1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
         name=(EditText) findViewById(R.id.uname2);
         password=(EditText) findViewById(R.id.password2);
         confirmpassword=(EditText) findViewById(R.id.confirmpassword2);
         email=(EditText) findViewById(R.id.email2);
        btnsubmit= (Button) findViewById(R.id.button2);


    }
    public void tologinin(View view)
    {
        Intent intent = new Intent(SignIn.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
    public void submit(View view)
    {

        btnsubmit.setEnabled(false);
        ProgressBar pbar =findViewById(R.id.progressBar5);
        pbar.setVisibility(View.VISIBLE);
        data=new Data();
        if (name.getText().toString().length()==0){
            name.setError("NAME IS REQUIRED");
            pbar.setVisibility(View.INVISIBLE);
            btnsubmit.setEnabled(true);
            return;
        }


        if(email.getText().toString().length()==0||!email.getText().toString().contains("@")) {
            email.setError("E-MAIL INVALID");
            pbar.setVisibility(View.INVISIBLE);
            btnsubmit.setEnabled(true);
            return;

        }
        if(password.getText().toString().length()==0) {
            password.setError("PASSWORD IS REQUIRED");
            pbar.setVisibility(View.INVISIBLE);
            btnsubmit.setEnabled(true);
            return;
        }
        if(password.getText().toString().length()<6)
        {
            password.setError("MUST BE GREATER THAN 6");
            pbar.setVisibility(View.INVISIBLE);
            btnsubmit.setEnabled(true);
            return;
        }
        if(!confirmpassword.getText().toString().equals(password.getText().toString()))
        {
            confirmpassword.setError("PASSWORDS DO NOT MATCH");
            pbar.setVisibility(View.INVISIBLE);
            btnsubmit.setEnabled(true);
            return;
        }
        String uname = name.getText().toString().toLowerCase();
        String email1 = email.getText().toString();
        data.setEmail(email1);
        data.setUname(uname);
        Boolean[] flag = {true};
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        documentReference = db.collection("AllowedUser").document(email1);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //username exist
                        doc = document.getData();
                        if(doc.containsKey("Admin") && (Boolean) doc.get("Admin")==true)
                        {
                                FirebaseAuth fauth=FirebaseAuth.getInstance();
                                String email1 = email.getText().toString();
                                String password1 = password.getText().toString();
                                fauth.createUserWithEmailAndPassword(email1,password1).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Toast.makeText(SignIn.this,"YOU MAY LOGIN NOW",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignIn.this,LoginActivity.class));
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(SignIn.this,"UNABLE TO CREATE.",Toast.LENGTH_SHORT).show();
                                        pbar.setVisibility(View.INVISIBLE);
                                        btnsubmit.setEnabled(true);
                                    }
                                });

                        }
                        else if(doc.containsKey("Username"))
                        {
                            if(doc.get("Username").toString().trim().equalsIgnoreCase(uname))
                            {
                                if(doc.containsKey("New")&&(Boolean) doc.get("New")==true)
                                {
                                    //transfer signin to see data activity after save
                                    password1=password.getText().toString();

                                    Intent intent = new Intent(SignIn.this,Personal_Details.class);
                                    startActivity(intent);
                                    finish();

                                }
                                else
                                {
                                    Toast.makeText(SignIn.this,"YOU ARE ALREADY A USER.\nYou must log in",Toast.LENGTH_SHORT).show();
                                    //intent to login
                                    Intent intent = new Intent(SignIn.this,LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                            else {
                                name.setError("THIS IS NOT AN ALLOWED USERNAME");
                                pbar.setVisibility(View.INVISIBLE);
                                btnsubmit.setEnabled(true);

                            }
                        }
                    }

                    else
                    {
                        email.setError("Invalid EMAIL");
                        pbar.setVisibility(View.INVISIBLE);
                        btnsubmit.setEnabled(true);

                    }
                }
                else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //intent to login
        Intent intent = new Intent(SignIn.this,LoginActivity.class);
        startActivity(intent);
        data=null;
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(data!=null){
            name.setText(data.getUname());
            email.setText(data.getEmail());
        }

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(data!=null){
            name.setText(data.getUname());
            email.setText(data.getEmail());
        }
    }
}