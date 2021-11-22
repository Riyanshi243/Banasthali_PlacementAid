package com.example.studenthelpdesk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class admin_view_requests extends AppCompatActivity {
private EditText req_d;
private TextView textV5,name1,textV6,textV7;
private LinearLayout scroll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_requests);
        scroll=(LinearLayout)findViewById(R.id.ll1);
         set();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //intent to admin page
        Intent intent = new Intent(admin_view_requests.this, Admin_page.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    boolean flag[]={true};
    public void set()
    {
        FirebaseFirestore fs=FirebaseFirestore.getInstance();
        CollectionReference col = fs.collection("Request");
        col.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> d = queryDocumentSnapshots.getDocuments();
                if(d.size()==0)
                    Toast.makeText(admin_view_requests.this,"e.toString()"+d.size(),Toast.LENGTH_SHORT).show();
                int i[]={0};
                for(;i[0]<d.size();i[0]++)
                {
                    String id = d.get(i[0]).getId();
                    DocumentReference doc = col.document(id);
                    doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Map<String, Object> m = documentSnapshot.getData();
                            long r= (long) m.get("Request Number");
                           for(int j=1;j<=r;j++)
                            {
                                String change[]=new String[2];
                                View v= LayoutInflater.from(admin_view_requests.this).inflate(R.layout.adminallreq, null);
                                TextView name,header,reason,request_D;
                                LinearLayout ll11;
                                name=(TextView) v.findViewById(R.id.name);
                                header=(TextView) v.findViewById(R.id.textView6);
                                reason=(TextView) v.findViewById(R.id.textView7);
                                request_D=(TextView) v.findViewById(R.id.request_Date);
                                name.setText(id);
                                name.setClickable(true);
                                name.setFocusable(true);
                                name.setFocusableInTouchMode(false);
                                name.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        SearchStudent.SEARCH=id;
                                        //intent to searchstudent
                                        Intent intent=(new Intent(admin_view_requests.this,SearchStudent.class));
                                        intent.putExtra("message", id);
                                        startActivity(intent);
                                        finish();
                                        
                                    }
                                });
                                Map<String, Object> cur[]=new Map[1];
                                DocumentReference d1 = doc.collection("Request " + j).document("Request");
                                d1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        Map<String, Object> details =documentSnapshot.getData();
                                        cur[0]=details;
                                        change[0]=(String) details.get("Change what");
                                        change[1]= (String) details.get("Value");
                                        header.setText("Change " + details.get("Change what") + " to " + details.get("Value"));
                                        reason.setText((String) details.get("Reason"));Timestamp t = (Timestamp) details.get("Applied Date");
                                        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                                        cal.setTimeInMillis(t.getSeconds() * 1000L);
                                        String date = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();
                                        request_D.setText(date);

                                        long status=(long)details.get("Status");
                                        if(status==1l) {
                                            getitem(cur[0],v,d1,change,id);
                                        }
                                        else if(status==2l) {
                                            flag[0]=false;
                                        }
                                        else if(status==0l) {
                                            flag[0]=false;
                                        }
                                    }
                                });


                            }
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(admin_view_requests.this,e.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        ProgressBar pb=findViewById(R.id.progressBar3);
        pb.setVisibility(View.GONE);

    }

    private void getitem(Map<String, Object> cur, View v, DocumentReference d1, String[] change, String id) {
        Button accept=v.findViewById(R.id.acc);
        Button rej=v.findViewById(R.id.rej);
        accept.setClickable(true);
        accept.setFocusable(true);
        accept.setFocusableInTouchMode(false);
        rej.setClickable(true);
        rej.setFocusable(true);
        rej.setFocusableInTouchMode(false);
        scroll.addView(v);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ab = new AlertDialog.Builder(admin_view_requests.this);
                ab.setTitle("Are you sure?");
                ab.setMessage("This change will automatically be shown in user's data");
                ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        changeinuser(change[0],change[1],id);
                        if(flag1[0]==true)
                        {
                            scroll.removeView(v);
                            cur.put("Status",2);
                            d1.set(cur);
                            Date time = Calendar.getInstance().getTime();
                            cur.put("Reviewed Date",time);
                            d1.set(cur);

                            Toast.makeText(admin_view_requests.this,"REQUEST ACCEPTED",Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing
                    }
                });
                ab.create().show();
            }
        });
        rej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ab = new AlertDialog.Builder(admin_view_requests.this);
                ab.setTitle("Are you sure?");
                ab.setMessage("Please provide a reason to reject this request");
                EditText et=new EditText(admin_view_requests.this);
                ab.setView(et);
                ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(et.getText().length()==0)
                            et.setError("Give a reason");
                        else
                        {
                            scroll.removeView(v);
                            cur.put("Status",0);
                            cur.put("Reason return",et.getText().toString());
                            Date time = Calendar.getInstance().getTime();
                            cur.put("Reviewed Date",time);
                            d1.set(cur);
                            Toast.makeText(admin_view_requests.this,"REQUEST REJECTED",Toast.LENGTH_SHORT).show();

                        }
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing
                    }
                });
                ab.create().show();
            }
        });
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                AlertDialog.Builder ab = new AlertDialog.Builder(this);
                ab.setTitle("LOGOUT");
                ab.setMessage("Are you sure?");
                ab.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                        firebaseAuth.signOut();
                        Intent intent = new Intent(admin_view_requests.this, LoginActivity.class);
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
    boolean flag1[]={false};
    public void changeinuser(String changeWhat,String changeTO,String id)
    {
        DocumentReference userloc = FirebaseFirestore.getInstance().collection("AllowedUser").document(id).collection("Permanent").document("perm");

        userloc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                Map<String, Object> m = task.getResult().getData();
                    if(m==null) {
                        Toast.makeText(admin_view_requests.this,  "Invalid request", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    m.put(changeWhat,changeTO);
                    userloc.set(m).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            flag1[0]=true;
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            flag1[0]=false;
                            Toast.makeText(admin_view_requests.this,e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    });

                }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                flag1[0]=false;
                Toast.makeText(admin_view_requests.this,e.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
