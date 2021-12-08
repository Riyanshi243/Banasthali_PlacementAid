package com.example.studenthelpdesk;

import androidx.annotation.NonNull;
//package com.example.hp.splashscreen;

import android.content.Intent;
import android.os.Handler;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
   private static int SPLASH_SCREEN_TIME_OUT=2000;
    static boolean isadmin=true,done=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                  //   WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        /*
         new Handler().postDelayed(new Runnable() {
             @Override
             public void run() {
                 Intent i = new Intent(MainActivity.this,
                         LoginActivity.class);
                         startActivity(i);
                         finish();
                          }
        }, SPLASH_SCREEN_TIME_OUT);
        */
                 TextView text = (TextView) findViewById(R.id.textView);
        int SPLASH_SCREEN = 4000;


       //Toast.makeText(MainActivity.this,"hi",Toast.LENGTH_SHORT).show();
        if(done==false)
            new Handler().postDelayed(() -> {



            FirebaseUser firebaseAuth=FirebaseAuth.getInstance().getCurrentUser();
            if(firebaseAuth!=null)
            {
                String email=firebaseAuth.getEmail();
                FirebaseFirestore fstore=FirebaseFirestore.getInstance();
                DocumentReference dref = fstore.collection("AllowedUser").document(email);
                dref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Map<String, Object> m = documentSnapshot.getData();
                        isadmin= (boolean) m.get("Admin");
                        if(isadmin)
                        {
                            startActivity(new Intent(MainActivity.this,Admin_page.class));
                            finish();
                        }
                        else
                        {

                            startActivity(new Intent(MainActivity.this,Student_page.class));
                            finish();
                        }
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
            else
            {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }}, SPLASH_SCREEN);

                 text.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         done = true;
                         //testinfg
                         FirebaseUser firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
                         if (firebaseAuth != null) {
                             String email = firebaseAuth.getEmail();
                             FirebaseFirestore fstore = FirebaseFirestore.getInstance();
                             DocumentReference dref = fstore.collection("AllowedUser").document(email);
                             dref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                 @Override
                                 public void onSuccess(DocumentSnapshot documentSnapshot) {
                                     Map<String, Object> m = documentSnapshot.getData();
                                     isadmin = (boolean) m.get("Admin");
                                     if (isadmin) {
                                         startActivity(new Intent(MainActivity.this, Admin_page.class));
                                         finish();
                                     } else {

                                         startActivity(new Intent(MainActivity.this, Student_page.class));
                                         finish();
                                     }
                                 }

                             }).addOnFailureListener(new OnFailureListener() {
                                 @Override
                                 public void onFailure(@NonNull Exception e) {
                                     Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                 }
                             });

                         } else {
                             Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                             startActivity(intent);
                             SPLASH_SCREEN_TIME_OUT=-1;
                             finish();
                         }
                     }
                 });

             }

         }
