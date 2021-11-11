package com.example.studenthelpdesk;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Time;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler {

    private CollectionReference collectionReference;
    public String[] ToDatabase(String changewhat,String newVal)
    {
        String flag[]={"0","TASK FAILED"};
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Data data =Student_page.data;

        return flag;
    }
}
