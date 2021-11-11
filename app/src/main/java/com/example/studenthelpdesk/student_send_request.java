package com.example.studenthelpdesk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class student_send_request extends AppCompatActivity {
TextView wel,curval,newcurval,rsn;
    Spinner spinnerEditDetails;
    Data data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_student_send_request);
        wel=(TextView)findViewById(R.id.welcome);
        curval=(TextView)findViewById(R.id.currentVal);
        newcurval=(TextView)findViewById(R.id.newcurrentval);
        rsn=(TextView)findViewById(R.id.reason);
        data=Student_page.data;
        wel.setText("HELLO "+data.getUname());
        spinnerEditDetails= findViewById(R.id.spinner_edit_fields);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.edit_details, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerEditDetails.setAdapter(adapter);
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
                        Intent intent = new Intent(student_send_request.this,LoginActivity.class);
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
    public void submit(View v)
    {
        TextView textView = (TextView)spinnerEditDetails.getSelectedView();
        String result = textView.getText().toString();
        Toast.makeText(this,result,Toast.LENGTH_LONG);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //to view data
    }
}
