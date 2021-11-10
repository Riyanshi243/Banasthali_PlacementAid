package com.example.studenthelpdesk;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class SignIn extends AppCompatActivity {
    private DocumentReference documentReference;
    private FirebaseAuth mAuth;
    private EditText name, email,password;
    FirebaseAuth firebaseAuth;
    static Data data;
    protected Map<String, Object> doc;
    public static String password1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
         name=(EditText) findViewById(R.id.uname2);
         password=(EditText) findViewById(R.id.password2);
         email=(EditText) findViewById(R.id.email2);


    }
    public void tologinin(View view)
    {
        Intent intent = new Intent(SignIn.this,LoginActivity.class);
        startActivity(intent);
        //  finish();
    }
    public void submit(View view)
    {
        data=new Data();
        if (name.getText().toString().length()==0){
            name.setError("NAME IS REQUIRED");
            return;
        }


        if(email.getText().toString().length()==0||!email.getText().toString().contains("@")) {
            email.setError("E-MAIL INVALID");
            return;

        }
        if(password.getText().toString().length()==0) {
            password.setError("PASSWORD IS REQUIRED");
            return;
        }
        if(password.getText().toString().length()<6)
        {
            password.setError("MUST BE GREATER THAN 6");
            return;
        }
        String uname = name.getText().toString();
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
                        if(doc.containsKey("Username"))
                        {
                            if(doc.get("Username").toString().equalsIgnoreCase(uname))
                            {
                                if(doc.containsKey("New"))
                                {
                                    //transfer signin to see data activity after save
                                    password1=password.getText().toString();

                                    Intent intent = new Intent(SignIn.this,Personal_Details.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(SignIn.this,"YOU ARE ALREADY A USER",Toast.LENGTH_LONG).show();
                                    //intent to login
                                }
                            }
                            else {
                                email.setError("Invalid MAIL");
                            }
                        }
                    }
                    else
                    {
                        name.setError("THIS IS NOT AN ALLOWED USERNAME");
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
        finish();
    }
}