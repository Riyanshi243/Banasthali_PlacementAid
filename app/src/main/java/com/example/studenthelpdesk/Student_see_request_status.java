package com.example.studenthelpdesk;

import static com.example.studenthelpdesk.R.color.green_bg;
import static com.example.studenthelpdesk.R.color.red_bg;

import static java.util.concurrent.TimeUnit.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.Date;

import org.w3c.dom.Text;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Student_see_request_status extends AppCompatActivity {
LinearLayout scroll;
long r[]={0};
static RequestData rd[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_see_request_status);
        scroll=(LinearLayout) findViewById(R.id.lll);
        set();
    }
    public void set()
    {
        FirebaseFirestore fs=FirebaseFirestore.getInstance();
        DocumentReference doc=fs.collection("Request").document(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());
        doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                //user's particular request map
                Map<String, Object> req = documentSnapshot.getData();
                int rnum=Integer.parseInt(String.valueOf( req.get("Request Number")));
                Map<Long,Boolean> m = null;
                if(req.containsKey("Deleted"))
                {
                    m= (Map<Long, Boolean>) req.get("Deleted");
                }
                RequestData requestData[]=new RequestData[rnum+1];
                final int[] rdlen = {0};
                for(int i = 1; i<=rnum; i++)
                {
                    DocumentReference rq = doc.collection("Request " + i).document("Request");
                    int finalI = i;
                    final Map<Long, Boolean>[] finalM = new Map[]{m};
                    if(finalM[0]!=null)
                    {
                        if(finalM[0].containsKey(i))
                            continue;
                    }
                    rq.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            //accept 2
                            //reject 0
                            //1 peding
                            //
                            Map<String, Object> det = documentSnapshot.getData();
                            if (det==null)
                                return;
                            //Toast.makeText(Student_see_request_status.this,finalI+"",Toast.LENGTH_SHORT).show();
                            requestData[rdlen[0]]=new RequestData();
                            requestData[rdlen[0]].setId(finalI);
                            Timestamp appliedDate = (Timestamp) det.get("Applied Date");
                            requestData[rdlen[0]].setApplied(appliedDate);
                            String changew = (String) det.get("Change what");
                            String reason = (String) det.get("Reason");
                            requestData[rdlen[0]].setReason(reason);
                            Long status = (Long) det.get("Status");
                            requestData[rdlen[0]].setStatus(status);
                            String value = (String) det.get("Value");

                            requestData[rdlen[0]].setHeader("Change "+changew +" to "+value);
                            if(status!=1)
                            {
                                Timestamp t = (Timestamp) det.get("Reviewed Date");
                                Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                                cal.setTimeInMillis(t.getSeconds() * 1000L);
                                String date = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();
                                requestData[rdlen[0]].setReviewedDate(date);
                            }
                            if(status==0)
                            {
                                String rreason = (String) det.get("Reason return");
                                requestData[rdlen[0]].setReviewReason(rreason);
                            }Log.e("datad,",rdlen[0]+"");
                            rdlen[0]++;
                            if(finalI==rnum)
                            {
                                Arrays.sort(requestData,0,rdlen[0]-1);
                                for(int i1=0;i1<rdlen[0];i1++)
                                {
                                    View v=LayoutInflater.from(Student_see_request_status.this).inflate(R.layout.reqstatus,null);
                                    TextView header = v.findViewById(R.id.header);
                                    TextView date1=v.findViewById(R.id.date1);
                                    TextView date2=v.findViewById(R.id.date2);
                                    TextView date3=v.findViewById(R.id.reviewedDateText);
                                    TextView reasonapp=v.findViewById(R.id.description);
                                    TextView returnreason=v.findViewById(R.id.reason);
                                    TextView _reason=v.findViewById(R.id.reason_);
                                    TextView status1=v.findViewById(R.id.status);
                                    ImageView del=v.findViewById(R.id.DELETE);
                                    Timestamp t = requestData[i1].getApplied();
                                    Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                                    cal.setTimeInMillis(t.getSeconds() * 1000L);
                                    String date = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();
                                    date1.setText(date);
                                    header.setText(requestData[i1].getHeader());
                                    reasonapp.setText(requestData[i1].getReason());
                                    long stat=  requestData[i1].getStatus();
                                    if(stat==1)
                                    {
                                        status1.setText("Under Consideration");
                                        date2.setVisibility(View.GONE);
                                        returnreason.setVisibility(View.GONE);
                                        date3.setVisibility(View.GONE);
                                        _reason.setVisibility(View.GONE);
                                    }
                                    else if(stat==2)
                                    {
                                        status1.setText("Accepted");
                                        status1.setBackgroundColor(getResources().getColor(green_bg));
                                        date2.setText(requestData[i1].getReviewedDate());
                                        returnreason.setVisibility(View.GONE);
                                        _reason.setVisibility(View.GONE);
                                    }
                                    else
                                    {
                                        status1.setText("Rejected");
                                        status1.setBackgroundColor(getResources().getColor(red_bg));
                                        date2.setText(requestData[i1].getReviewedDate());
                                        returnreason.setText(requestData[i1].getReviewReason());
                                    }
                                    if(i1==rnum)
                                    {
                                        ProgressBar p=findViewById(R.id.progressBar);
                                        p.setVisibility(View.GONE);
                                    }
                                    long id=requestData[i1].getId();
                                    del.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            AlertDialog.Builder ab=new AlertDialog.Builder(Student_see_request_status.this);
                                            ab.setTitle("Are you sure you want to delete?");
                                            ab.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    //delete using id
                                                    DocumentReference db = fs.collection("Request").document(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString()).collection("Request " + id).document("Request");
                                                    db.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            Toast.makeText(Student_see_request_status.this,id+" Request Deleted",Toast.LENGTH_SHORT).show();
                                                            if(finalM[0] !=null)
                                                                finalM[0].put(id,true);
                                                            else
                                                            {
                                                                finalM[0] =new HashMap<>();
                                                                finalM[0].put(id,true);
                                                                Map<Object,Object> map=new HashMap<>();
                                                                map.put("Request Number",rnum);
                                                                map.put("Deleted",finalM[0]);
                                                                doc.set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void unused) {
                                                                        Toast.makeText(Student_see_request_status.this,id+" Done",Toast.LENGTH_SHORT).show();

                                                                    }
                                                                });

                                                            }
                                                        }
                                                    });
                                                    scroll.removeView(v);
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
                                    scroll.addView(v);
                                    if(i1==rdlen[0]-1)
                                    {
                                        ProgressBar pb=findViewById(R.id.progressBar);
                                        pb.setVisibility(View.GONE);
                                    }
                                }
                            }
                        }
                    });

                }
            }
        });

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