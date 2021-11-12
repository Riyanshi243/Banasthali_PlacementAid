package com.example.studenthelpdesk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    String email,editTextPassword;
    private long backPressedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void tosignin(View view)
    {
         Intent intent = new Intent(LoginActivity.this,SignIn.class);
                startActivity(intent);
                finish();
    }
    public void login(View view)
    {EditText EditTextTextPersonName=findViewById(R.id.editTextTextPersonName);

        EditText EditTextTextPassword=findViewById(R.id.editTextTextPassword);
        editTextPassword= EditTextTextPassword.getText().toString();
        firebaseAuth=FirebaseAuth.getInstance();
        email= EditTextTextPersonName.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.matches(emailPattern)==false)
        {
            EditTextTextPersonName.setError("ENTER VALID EMAIL");
            return;
        }
       // Pattern pattern = Pattern.compile(regex);
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
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference documentReference = db.collection("AllowedUser").document(email);
                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            DocumentSnapshot document = task.getResult();
                            if(document.exists())
                            {

                                Map<String, Object> doc = document.getData();
                                if(doc.containsKey("Admin"))
                                {
                                    if((Boolean) doc.get("Admin")==true)
                                    {
                                        //intent to admin page and also toast "welcom to admin page
                                        Toast.makeText(LoginActivity.this,"Weclome to admin page",Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(LoginActivity.this,Admin_page.class);
                                         startActivity(intent);
                                        finish();
                                    }
                                    else
                                    {
                                        //intent to student page and also toast
                                        Toast.makeText(LoginActivity.this,"Weclome to Student page",Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(LoginActivity.this,Student_page.class);
                                         startActivity(intent);
                                         finish();
                                    }
                                }
                                else
                                {
                                    //intent to student page and also toast
                                    Toast.makeText(LoginActivity.this,"Weclome to Student page",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(LoginActivity.this,Student_page.class);
                                     startActivity(intent);
                                     finish();
                                }

                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this,"User doesnot exist",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        super.onBackPressed();
        AlertDialog.Builder ab=new AlertDialog.Builder(LoginActivity.this);
        ab.setTitle("Exit");
        ab.setMessage("Are you sure you want to Exit?");
        ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(backPressedTime+2000>System.currentTimeMillis()){

            System.exit(1);
            return;
        }else {
            Toast.makeText(LoginActivity.this,"Press again to EXIT", Toast.LENGTH_LONG).show();
        }
        backPressedTime=System.currentTimeMillis();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //do nothing
            }
        });
        ab.create().show();
        
    }
}