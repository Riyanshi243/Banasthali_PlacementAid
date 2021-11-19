package com.example.studenthelpdesk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Student_see_request_status extends AppCompatActivity {
LinearLayout scroll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_see_request_status);
        scroll=(LinearLayout) findViewById(R.id.lll);
        set();

    }
    //
    public void set()
    {
        FirebaseFirestore fs=FirebaseFirestore.getInstance();
        DocumentReference doc = fs.collection("Request").document(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String, Object> m = documentSnapshot.getData();
                long r = (long) m.get("Request Number");
                for (int j = 1; j <= r; j++) {
                    View v = LayoutInflater.from(Student_see_request_status.this).inflate(R.layout.reqstatus, null);

                    doc.collection("Request " + j).document("Request").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
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
                            dateapp.setText(date);
                            long status = (long) details.get("Status");
                            TextView stat = (TextView) v.findViewById(R.id.status);
                            TextView date2 = (TextView) v.findViewById(R.id.date2);
                            TextView rev = v.findViewById(R.id.reviewedDateText);
                            TextView reson2 = (TextView) v.findViewById(R.id.reason);
                            if (status == 1) {
                                stat.setText("Under Consideration");
                                date2.setVisibility(View.GONE);
                                rev.setVisibility(View.GONE);
                                reson2.setVisibility(View.GONE);
                            }
                            if (status == 2) {
                                stat.setText("ACCEPTED");
                                Timestamp t1 = (Timestamp) details.get("Reviewed Date");
                                Calendar cal2 = Calendar.getInstance(Locale.ENGLISH);
                                cal2.setTimeInMillis(t1.getSeconds() * 1000L);
                                String dat2 = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal2).toString();
                                date2.setText(dat2);
                                reson2.setText((String) details.get("Reason return"));
                            }
                            if (status == 0) {
                                stat.setText("Rejected");
                                Timestamp t1 = (Timestamp) details.get("Reviewed Date");
                                Calendar cal2 = Calendar.getInstance(Locale.ENGLISH);
                                cal2.setTimeInMillis(t1.getSeconds() * 1000L);
                                String dat2 = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal2).toString();
                                date2.setText(dat2);
                                reson2.setText((String) details.get("Reason return"));
                            }
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //intent to students page
        Intent intent = new Intent(Student_see_request_status.this,Student_page.class);
        startActivity(intent);
        finish();
    }
}