package com.example.studenthelpdesk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
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
        req_d=(EditText) findViewById(R.id.request_Date);
         textV5=(TextView) findViewById(R.id.textView5);
         name1=(TextView) findViewById(R.id.name);
         textV6=(TextView) findViewById(R.id.textView6);
         textV7=(TextView) findViewById(R.id.textView7);
         scroll=(LinearLayout) findViewById(R.id.ll1);
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
                                boolean flag[]={true};
                                View v= LayoutInflater.from(admin_view_requests.this).inflate(R.layout.adminallreq, null);
                                //sare adminallreq k items le lo
                                TextView name,textView6,textView7;
                                doc.collection("Request " + j).document("Request").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        Map<String, Object> details =documentSnapshot.getData();
                                        long status=(long)details.get("Status");
                                        if(status==1) {

                                        }
                                        if(status==2) {

                                        }
                                        if(status==0) {
                                            flag[0]=false;
                                        }
                                    }
                                });
                                if(flag[0]==true)
                                    scroll.addView(v);
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                AlertDialog.Builder ab = new AlertDialog.Builder(this);
                ab.setTitle("LOGOUT");
                ab.setMessage("Are you sure?");
                ab.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //logut
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
}
//public void set()
//    {
//        FirebaseFirestore fs=FirebaseFirestore.getInstance();
//        CollectionReference col = fs.collection("Request");
//        col.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                List<DocumentSnapshot> d = queryDocumentSnapshots.getDocuments();
//                if(d.size()==0)
//                    Toast.makeText(Student_see_request_status.this,"e.toString()"+d.size(),Toast.LENGTH_SHORT).show();
//                int i[]={0};
//                for(;i[0]<d.size();i[0]++)
//                {
//                    String id = d.get(i[0]).getId();
//                    DocumentReference doc = col.document(id);
//                    doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                        @Override
//                        public void onSuccess(DocumentSnapshot documentSnapshot) {
//                            Map<String, Object> m = documentSnapshot.getData();
//                            long r= (long) m.get("Request Number");
//                            for(int j=1;j<=r;j++)
//                            {
//                                View v= LayoutInflater.from(Student_see_request_status.this).inflate(R.layout.reqstatus, null);
//
//                                doc.collection("Request " + j).document("Request").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                                    @Override
//                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                        Map<String, Object> details =documentSnapshot.getData();
//                                        TextView header=(TextView)v.findViewById(R.id.header);
//                                        header.setText("Change "+details.get("Change what")+" to "+details.get("Value"));
//                                        TextView reason=(TextView)  v.findViewById(R.id.description);
//                                        reason.setText((String)details.get("Reason"));
//                                        TextView dateapp=(TextView)  v.findViewById(R.id.date1);
//                                        Timestamp t=(Timestamp) details.get("Applied Date");
//                                        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
//                                        cal.setTimeInMillis(t.getSeconds() * 1000L);
//                                        String date = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();
//                                        dateapp.setText(date);
//                                        long status=(long)details.get("Status");
//                                        TextView stat=(TextView) v.findViewById(R.id.status);
//                                        TextView date2=(TextView) v.findViewById(R.id.date2);
//                                        TextView rev=v.findViewById(R.id.reviewedDateText);
//                                        TextView reson2=(TextView) v.findViewById(R.id.reason);
//                                        if(status==1) {
//                                            stat.setText("Under Consideration");
//                                            date2.setVisibility(View.GONE);
//                                            rev.setVisibility(View.GONE);
//                                            reson2.setVisibility(View.GONE);
//                                        }
//                                        if(status==2) {
//                                            stat.setText("ACCEPTED");
//                                            Timestamp t1=(Timestamp) details.get("Reviewed Date");
//                                            Calendar cal2 = Calendar.getInstance(Locale.ENGLISH);
//                                            cal2.setTimeInMillis(t1.getSeconds() * 1000L);
//                                            String dat2 = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal2).toString();
//                                            date2.setText(dat2);
//                                            reson2.setText((String)details.get("Reason return"));
//                                        }
//                                        if(status==0) {
//                                            stat.setText("Rejected");
//                                            Timestamp t1=(Timestamp) details.get("Reviewed Date");
//                                            Calendar cal2 = Calendar.getInstance(Locale.ENGLISH);
//                                            cal2.setTimeInMillis(t1.getSeconds() * 1000L);
//                                            String dat2 = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal2).toString();
//                                            date2.setText(dat2);
//                                            reson2.setText((String)details.get("Reason return"));
//                                        }
//                                    }
//                                });
//
//                                scroll.addView(v);
//                            }
//                        }
//                    });
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(Student_see_request_status.this,e.toString(),Toast.LENGTH_SHORT).show();
//            }
//        });
//        ProgressBar pb=findViewById(R.id.progressBar);
//        pb.setVisibility(View.GONE);
//
//    }