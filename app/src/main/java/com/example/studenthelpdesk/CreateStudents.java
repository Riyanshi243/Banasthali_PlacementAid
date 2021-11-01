package com.example.studenthelpdesk;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Console;
import java.util.HashMap;
import java.util.Map;

public class CreateStudents extends AppCompatActivity {

    private String uname, email;
    private DocumentReference documentReference;
    private FirebaseAuth mAuth;
    private EditText unameet, Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_students);
        Email = findViewById(R.id.email);
        unameet = findViewById(R.id.uname);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onStart() {

        super.onStart();
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //testing
        /*if (currentUser == null) {

            Intent intent = new Intent(CreateStudents.this, LoginActivity.class);
            startActivity(intent);
        }*/
    }

    public void save_data(View view) {

        uname = unameet.getText().toString();
        email = Email.getText().toString();
        Log.w("Hello",uname+email);

        mAuth = FirebaseAuth.getInstance();
        Boolean[] flag = {true};
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        documentReference = db.collection("AllowedUser").document(email);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        unameet.setError("This Email Already Exists");
                        flag[0] = false;
                    }
                    else
                    {
                        adddata();
                    }
                }
                else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }
    public void adddata()
    {
        Map<String, Object> user = new HashMap<>();
        user.put("Username", uname);
        user.put("Email", email);
        user.put("New",true);
        documentReference.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(CreateStudents.this, "User Created", Toast.LENGTH_LONG).show();
                unameet.setText("");
                Email.setText("");
                //testing
                Intent intent=new Intent(CreateStudents.this,SignIn.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(CreateStudents.this, "User cant be created", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //intent to admin page
        Intent intent = new Intent(CreateStudents.this,Admin_page.class);
        startActivity(intent);
    }
}