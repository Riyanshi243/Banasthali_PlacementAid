package com.example.studenthelpdesk;

import static com.example.studenthelpdesk.R.color.green_bg;

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
import android.widget.ImageView;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

public class Student_see_request_status extends AppCompatActivity {
LinearLayout scroll;
ImageView del;
long r[]={0};
static RequestData rd[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_see_request_status);
        scroll=(LinearLayout) findViewById(R.id.lll);
        set();
    }
    public int display(RequestData rd[],int j)
    {
        Toast.makeText(Student_see_request_status.this,j+" h"+r[0]+"",Toast.LENGTH_SHORT).show();
        if(rd[j].getDelforme()==1)
            return -1;
        View v = LayoutInflater.from(Student_see_request_status.this).inflate(R.layout.reqstatus, null);
        TextView header = (TextView) v.findViewById(R.id.header);
        TextView reason = (TextView) v.findViewById(R.id.description);
        TextView dateapp = (TextView) v.findViewById(R.id.date1);
        TextView stat = (TextView) v.findViewById(R.id.status);
        TextView date2 = (TextView) v.findViewById(R.id.date2);
        TextView rev = v.findViewById(R.id.reviewedDateText);
        TextView reson2 = (TextView) v.findViewById(R.id.reason);
        TextView reason1 = (TextView) v.findViewById(R.id.reason_);
        del=(ImageView) v.findViewById(R.id.DELETE);
        header.setText(rd[j].getHeader());
        reason.setText(rd[j].getReason());
        dateapp.setText(rd[j].getApplieddate());
        long status=rd[j].getStatus();
        if(status==1)
        {
            stat.setText("Under Consideration");
            date2.setVisibility(View.GONE);
            rev.setVisibility(View.GONE);
            reson2.setVisibility(View.GONE);
            reason1.setVisibility(View.GONE);
        }
        if(status==2)
        {
            stat.setText("ACCEPTED");
            date2.setText(rd[j].getReviewedDate());
            stat.setBackgroundColor((getResources().getColor(green_bg)));
            reson2.setVisibility(View.GONE);
            reason1.setVisibility(View.GONE);
        }
        if(status==0)
        {
            stat.setText("REJECTED");
            date2.setText(rd[j].getReviewedDate());
            reson2.setText(rd[j].getReviewReason());
            stat.setBackgroundColor((getResources().getColor(R.color.red_bg)));
            reason1.setVisibility(View.VISIBLE);
        }
        del.setId(j);
        Toast.makeText(Student_see_request_status.this,j+" "+r[0]+"",Toast.LENGTH_SHORT).show();
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ab=new AlertDialog.Builder(Student_see_request_status.this);
                ab.setTitle("Delete?");
                ab.setPositiveButton("Delete for me", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        rd[j].setDelforme(1);
                        scroll.removeView(v);
                    }
                }).setNegativeButton("Delete for everyone", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Student_see_request_status.this,del.getId()+"",Toast.LENGTH_SHORT).show();
                    }
                }).setNeutralButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing
                    }
                });
                ab.create().show();
            }
        });
        scroll.addView(v);
            return 2;
    }
    public void set()
    {
        FirebaseFirestore fs=FirebaseFirestore.getInstance();
        DocumentReference doc=fs.collection("Request").document(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String, Object> m = documentSnapshot.getData();
                if(m==null)
                {
                    Toast.makeText(Student_see_request_status.this,"NO REQUESTS HERE",Toast.LENGTH_SHORT).show();
                    return;
                }
                r[0] = (long) m.get("Request Number");
                if(r[0]==0)
                {
                    Toast.makeText(Student_see_request_status.this,"NO REQUESTS HERE",Toast.LENGTH_SHORT).show();
                    return;
                }
                rd=new RequestData[(int)r[0]+2];
                for(int j[]={1};j[0]<=r[0];)
                {
                    Toast.makeText(Student_see_request_status.this,j[0]+" "+r[0]+"",Toast.LENGTH_SHORT).show();
                    doc.collection("Request " + j[0]).document("Request").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Map<String, Object> details = documentSnapshot.getData();
                            rd[j[0]]=new RequestData();
                            rd[j[0]].setHeader("Change " + details.get("Change what") + " to " + details.get("Value"));
                            rd[j[0]].setReason((String) details.get("Reason"));
                            rd[j[0]].setStatus((long)details.get("Status"));
                            Timestamp t = (Timestamp) details.get("Applied Date");
                            Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                            cal.setTimeInMillis(t.getSeconds() * 1000L);
                            String date = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();
                            rd[j[0]].setApplieddate(date);
                            rd[j[0]].setId(j[0]);
                            if(details.containsKey("Delete for me"))
                            {
                                rd[j[0]].setDelforme((long)details.get("Delete for me"));
                            }
                            if(rd[j[0]].getStatus()==2) {
                                Timestamp t1 = (Timestamp) details.get("Reviewed Date");
                                Calendar cal2 = Calendar.getInstance(Locale.ENGLISH);
                                cal2.setTimeInMillis(t1.getSeconds() * 1000L);
                                String dat2 = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal2).toString();
                                rd[j[0]].setReviewedDate(dat2);
                                display(rd,j[0]);
                                Toast.makeText(Student_see_request_status.this,j[0]+" "+r[0]+"",Toast.LENGTH_SHORT).show();

                            }
                            else if(rd[j[0]].getStatus()==0)
                            {
                                Timestamp t1 = (Timestamp) details.get("Reviewed Date");
                                Calendar cal2 = Calendar.getInstance(Locale.ENGLISH);
                                cal2.setTimeInMillis(t1.getSeconds() * 1000L);
                                String dat2 = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal2).toString();
                                rd[j[0]].setReviewedDate(dat2);
                                rd[j[0]].setReviewReason((String) details.get("Reason return"));
                                display(rd,j[0]);
                            }
                            else
                            {
                                display(rd,j[0]);
                                Toast.makeText(Student_see_request_status.this,j[0]+" "+r[0]+"",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Student_see_request_status.this,e.toString(),Toast.LENGTH_SHORT).show();

                        }
                    });
                    if(j[0]==r[0]){
                        ProgressBar progressBar=findViewById(R.id.progressBar);
                        progressBar.setVisibility(View.GONE);
                    }
                    j[0]++;
                }

            }
        });
    }
    public void set(int i)
    {
        FirebaseFirestore fs=FirebaseFirestore.getInstance();
        DocumentReference doc = fs.collection("Request").document(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String, Object> m = documentSnapshot.getData();
                if(m==null)
                {
                    Toast.makeText(Student_see_request_status.this,"NO REQUESTS HERE",Toast.LENGTH_SHORT).show();
                    return;
                }
                long r = (long) m.get("Request Number");
                if(r==0)
                {
                    Toast.makeText(Student_see_request_status.this,"NO REQUESTS HERE",Toast.LENGTH_SHORT).show();
                    return;
                }
                outer: for (int j[] = {1}; j[0] <= r; j[0]++) {
                    View v = LayoutInflater.from(Student_see_request_status.this).inflate(R.layout.reqstatus, null);
                    doc.collection("Request " + j[0]).document("Request").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Map<String, Object> details = documentSnapshot.getData();
                            TextView header = (TextView) v.findViewById(R.id.header);
                            header.setText("Change " + details.get("Change what") + " to " + details.get("Value"));
                            TextView reason = (TextView) v.findViewById(R.id.description);
                            reason.setText((String) details.get("Reason"));
                            TextView dateapp = (TextView) v.findViewById(R.id.date1);
                            Timestamp t = (Timestamp) details.get("Applied Date");
                            Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                            cal.setTimeInMillis(t.getSeconds() * 1000L);
                            String date = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();
                           // dateapp.setText("hi");
                            dateapp.setText(date);
                            long status = (long) details.get("Status");
                            TextView stat = (TextView) v.findViewById(R.id.status);
                            TextView date2 = (TextView) v.findViewById(R.id.date2);
                            TextView rev = v.findViewById(R.id.reviewedDateText);
                            TextView reson2 = (TextView) v.findViewById(R.id.reason);
                            del=(ImageView) v.findViewById(R.id.DELETE);
                            if(details.containsKey("Deleted for me")&&(long)details.get("Deleted for me")==1) {
                                Toast.makeText(Student_see_request_status.this,"hi",Toast.LENGTH_SHORT).show();
                            }

                            v.setId(j[0]);
                            del.setId(j[0]);
                            //res_
                                  TextView reason1 = (TextView) v.findViewById(R.id.reason_);
                            if (status == 1) {
                                stat.setText("Under Consideration");
                                date2.setVisibility(View.GONE);
                                rev.setVisibility(View.GONE);
                                reson2.setVisibility(View.GONE);
                                reason1.setVisibility(View.GONE);
                            }
                            if (status == 2) {
                                stat.setText("ACCEPTED");
                                Timestamp t1 = (Timestamp) details.get("Reviewed Date");
                                Calendar cal2 = Calendar.getInstance(Locale.ENGLISH);
                                cal2.setTimeInMillis(t1.getSeconds() * 1000L);
                                String dat2 = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal2).toString();
                                date2.setText(dat2);
                                reson2.setText((String) details.get("Reason return"));
                                stat.setBackgroundColor((getResources().getColor(green_bg)));
                                reson2.setVisibility(View.GONE);
                                reason1.setVisibility(View.GONE);
                            }
                            if (status == 0) {
                                stat.setText("REJECTED");
                                Timestamp t1 = (Timestamp) details.get("Reviewed Date");
                                Calendar cal2 = Calendar.getInstance(Locale.ENGLISH);
                                cal2.setTimeInMillis(t1.getSeconds() * 1000L);
                                String dat2 = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal2).toString();
                                date2.setText(dat2);
                                reson2.setText((String) details.get("Reason return"));
                                stat.setBackgroundColor((getResources().getColor(R.color.red_bg)));
                                reason1.setVisibility(View.VISIBLE);
                            }
                            //on
                            del.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    AlertDialog.Builder ab=new AlertDialog.Builder(Student_see_request_status.this);
                                    ab.setTitle("DELETE");
                                    ab.setMessage("Are you sure want to delete ?");
                                    ab.setPositiveButton("Delete for me", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            //do nothing;
                                        }
                                    }).setNeutralButton("Delete for Everyone", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            DocumentReference doc1 = FirebaseFirestore.getInstance().collection("Request").document(Student_page.data.getEmail());
                                            doc1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    Map<String, Object> map = task.getResult().getData();
                                                    long req=(long)map.get("Request Number");
                                                    Map<String,Object> m[]=new Map[(int)req];
                                                    int t[]={0};
                                                    deleteforeever(m,t,v,1,req);
                                                    map.put("Request Number",req-1);
                                                    doc1.set(map);
                                                }
                                            });
                                        }
                               });
                                    ab.create().show();

                                }
                            });

                        }
                    });

                    scroll.addView(v);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Student_see_request_status.this,e.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        ProgressBar pb=findViewById(R.id.progressBar);
        pb.setVisibility(View.GONE);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);

    }//logout
    public void deleteforeever(Map<String, Object>[] m, int[] t, View v,int k,long req)
    {
        if(k==req)
        {
            Toast.makeText(Student_see_request_status.this,"REQUEST DELETED",Toast.LENGTH_SHORT).show();
            scroll.removeView(v);
            return;
        }
        if(k==v.getId())
            deleteforeever(m,t,v,k+1,req);
        DocumentReference doc2 = FirebaseFirestore.getInstance().collection("Request").document(Student_page.data.getEmail()).collection("Request " + k).document("Request");
        doc2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Map<String, Object> m1 = task.getResult().getData();
                m[t[0]]=m1;
                DocumentReference doc3 = FirebaseFirestore.getInstance().collection("Request").document(Student_page.data.getEmail()).collection("Request " + t[0]).document("Request");
                doc3.update(m1);
                t[0]++;
                deleteforeever(m,t,v,k+1,req);
            }
        });
    }
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
                        //logout
                        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                        firebaseAuth.signOut();
                        Intent intent = new Intent(Student_see_request_status.this,LoginActivity.class);
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
        //intent to students page
        Intent intent = new Intent(Student_see_request_status.this,Student_page.class);
        startActivity(intent);
        finish();
    }
}