package com.example.studenthelpdesk;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class student_view_request extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        int activity_student_send_request = 0;
        setContentView(activity_student_send_request);
        Spinner spinnerEditDetails = findViewById(R.id.spinner_edit_fields);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.edit_details, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerEditDetails.setAdapter(adapter);
    }
}
