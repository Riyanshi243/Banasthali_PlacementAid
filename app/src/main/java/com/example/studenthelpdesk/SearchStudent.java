package com.example.studenthelpdesk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.sql.Time;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

public class SearchStudent extends AppCompatActivity {
    Data data;
    static String SEARCH="";
    private static TextView name,fname,mname,phn,add,dob,gender,aadhar,pan,email1,rno,enro,course,branch,sem,ten,twe,cgpa;
    private EditText emails;
    private ImageView profile;
    private Button resume;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_student);

        emails=(EditText) findViewById(R.id.emailsearch);
        boolean iseaxta=getIntent().hasExtra("message");
        if(iseaxta) {
            Bundle bundle = getIntent().getExtras();
            SEARCH = bundle.getString("message");
            Toast.makeText(this,"HI "+SEARCH,Toast.LENGTH_SHORT).show();
            emails.setText(SEARCH);
            show(getWindow().getCurrentFocus());
        }
        profile=findViewById(R.id.dp);
        resume=findViewById(R.id.Resume);
        name=(TextView) findViewById(R.id.name);
        fname=(TextView) findViewById(R.id.fathersname1);
        mname=(TextView) findViewById(R.id.mothersname);
        phn=(TextView) findViewById(R.id.phonenumber);
        add=(TextView) findViewById(R.id.address);
        dob=(TextView) findViewById(R.id.dob1);
        gender=(TextView) findViewById(R.id.gender1);
        aadhar=(TextView) findViewById(R.id.aadhar);
        pan=(TextView) findViewById(R.id.pan1);
        email1=(TextView) findViewById(R.id.email1);
        rno=(TextView) findViewById(R.id.rollno1);
        enro=(TextView) findViewById(R.id.enrollment);
        course=(TextView) findViewById(R.id.course1);
        branch=(TextView) findViewById(R.id.branch1);
        sem=(TextView) findViewById(R.id.sem1);
        ten=(TextView) findViewById(R.id.ten);
        twe=(TextView) findViewById(R.id.twe);
        cgpa=(TextView) findViewById(R.id.cgpa1);
        Button b=findViewById(R.id.button3);

        b.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (MotionEvent.ACTION_UP == motionEvent.getAction())
                {
                    view.performClick();
                    return true;
                }
                return false;
            }
        });
    }
    static boolean flag=false;
    public void show(View v)
    {
        String email;
        if(SEARCH.length()==0)
        {
            email= emails.getText().toString().trim();
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            if (email.matches(emailPattern)==false)
            {
                emails.setError("ENTER VALID EMAIL");
                return;
            }
        }
        else
        {
            //yaha textview set kar do
           email=SEARCH;
           emails.setText(email);

        }
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        DocumentReference dref = firestore.collection("AllowedUser").document(email).collection("Permanent").document("perm");
        dref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();
                if(doc.exists())
                {
                    Map<String, Object> map2 = doc.getData();
                    //ab yaha par set kar dena sab
                    ProgressBar pbar =findViewById(R.id.progressBar2);
                    pbar.setVisibility(View.VISIBLE);

                    name.setText((String)map2.get("Name"));
                    branch.setText((String)map2.get("Branch"));
                    cgpa.setText((String)map2.get("CGPA"));
                    course.setText((String)map2.get("Course"));
                    emails.setText((String)map2.get("Email"));
                    enro.setText((String)map2.get("Enrollment"));
                    fname.setText((String)map2.get("Father Name"));
                    mname.setText((String)map2.get("Mother Name"));
                    rno.setText((String)map2.get("Roll Number"));
                    ten.setText((String)map2.get("Tenth"));
                    twe.setText((String)map2.get("Twelth"));
                    flag=true;
                    DocumentReference dref1 = firestore.collection("AllowedUser").document(email).collection("Change").document("change");
                    dref1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot doc = task.getResult();
                            if (doc.exists()) {
                                ProgressBar pbar = findViewById(R.id.progressBar2);
                                pbar.setVisibility(View.INVISIBLE);
                                Map<String, Object> map1 = doc.getData();
                                aadhar.setText((String) map1.get("Aadhar"));
                                add.setText((String) map1.get("Address"));
                                dob.setText((String) map1.get("DOB"));
                                gender.setText((String) map1.get("Gender"));
                                pan.setText((String) map1.get("PAN"));
                                phn.setText((String) map1.get("PhoneNumber"));
                                sem.setText((String) map1.get("Semester"));
                                email1.setText(email);
                                FirebaseStorage storage = FirebaseStorage.getInstance();
                                DocumentReference dref1 = firestore.collection("AllowedUser").document(email);
                                dref1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        StorageReference storageRef = storage.getReference("ProfilePic").child(String.valueOf(documentSnapshot.getData().get("Username")));
                                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    Glide.with(SearchStudent.this)
                                                            .load(uri).diskCacheStrategy(DiskCacheStrategy.ALL)
                                                            .error(R.drawable.profile_pic)
                                                            .placeholder(R.drawable.profile_pic)
                                                            .into(profile);
                                                }
                                            });

                                        Toast.makeText(SearchStudent.this,documentSnapshot.getData().get("Username")+"",Toast.LENGTH_SHORT).show();
                                        resume.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                download((String)documentSnapshot.getData().get("Username"));
                                            }
                                        });
                                    }
                                });

                                ScrollView scrollView = findViewById(R.id.scrollView3);
                                scrollView.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    
                    

                }
                else
                {
                    Toast.makeText(SearchStudent.this,"Email does not exist",Toast.LENGTH_SHORT).show();

                    ProgressBar pbar =findViewById(R.id.progressBar2);
                    pbar.setVisibility(View.INVISIBLE);
                    ScrollView scrollView=findViewById(R.id.scrollView3);
                    scrollView.setVisibility(View.INVISIBLE);
                    
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SearchStudent.this,e.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void download(String uname)
    {
        AlertDialog.Builder ab=new AlertDialog.Builder(SearchStudent.this);
        ab.setTitle("Select 1");
        ab.setPositiveButton("View", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference("Resume").child(uname);
                Task<Uri> message = storageRef.getDownloadUrl();
                message.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Toast.makeText(getActivity(),uri.toString(),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SearchStudent.this, ViewPDFActivity.class);
                        intent.putExtra("url", uri.toString());
                        startActivity(intent);
                    }
                });



            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setNeutralButton("Download", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference("Resume").child(uname);
                Task<Uri> message = storageRef.getDownloadUrl();
                message.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Toast.makeText(getActivity(),uri.toString(),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });

            }
        });
        ab.create().show();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }//logout
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
                        Intent intent = new Intent(SearchStudent.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("NO ", new DialogInterface.OnClickListener() {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(SEARCH.length()==0)
        {
            Intent intent = new Intent(SearchStudent.this,Admin_page.class);
            startActivity(intent);
            finish();
        }
        else
        {
            SEARCH="";
            Intent intent = new Intent(SearchStudent.this,admin_view_requests.class);
            startActivity(intent);
            finish();
        }
    }
}