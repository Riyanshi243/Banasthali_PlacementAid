package com.example.studenthelpdesk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    String email,editTextPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void tosignin(View view)
    {
         Intent intent = new Intent(LoginActivity.this,SignIn.class);
                startActivity(intent);
              //  finish();
    }
    public void login(View view)
    {EditText EditTextTextPersonName=findViewById(R.id.editTextTextPersonName);

        EditText EditTextTextPassword=findViewById(R.id.editTextTextPassword);
        editTextPassword= EditTextTextPassword.getText().toString();
        firebaseAuth=FirebaseAuth.getInstance();
        email= EditTextTextPersonName.getText().toString();
        String regex = "^@(.+)$";
        Pattern pattern = Pattern.compile(regex);
/*for(String ){
    Matcher matcher = pattern.matcher(email);
}
*/
        if(editTextPassword.trim().length()==0)
        {
            EditTextTextPassword.setError("ENTER PASSWORD");
            return;
        }
        else if(editTextPassword.trim().length()<6)
        {
            EditTextTextPassword.setError("ENTER ATLEAST 6 CHARACTERS");
        }
        firebaseAuth.signInWithEmailAndPassword(email,editTextPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                Toast.makeText(LoginActivity.this,"LOGED IN",Toast.LENGTH_LONG).show();
                //testing
                startActivity(new Intent(LoginActivity.this,Student_page.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}