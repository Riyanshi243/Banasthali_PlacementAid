package com.example.studenthelpdesk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;

public class SignIn extends AppCompatActivity {
    private DocumentReference documentReference;
    private FirebaseAuth mAuth;
    private EditText unameet, Email,phone,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        EditText name=(EditText) findViewById(R.id.uname1);
        EditText password=(EditText) findViewById(R.id.password2);
        EditText phone=(EditText) findViewById(R.id.phone2);
        EditText email=(EditText) findViewById(R.id.email2);

        
    }
    public void submit(View view)
    {
        
    }
}