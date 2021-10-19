package com.example.studenthelpdesk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText EditTextTextPersonName=findViewById(R.id.editTextTextPersonName);
        String uname= EditTextTextPersonName.getText().toString();

        EditText EditTextTextPassword=findViewById(R.id.editTextTextPassword);
        String editTextTextPassword= EditTextTextPassword.getText().toString();

    }
}