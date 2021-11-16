package com.example.studenthelpdesk;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frag_AcademicDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frag_AcademicDetails extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
     TextView rno,enrno,course1,branch1,ten,twe,cgpa1,semester;
    private Data data=Student_page.data;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frag_AcademicDetails() {
        // Required empty public constructor`
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frag_AcademicDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static frag_AcademicDetails newInstance(String param1, String param2) {
        frag_AcademicDetails fragment = new frag_AcademicDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    static boolean flag=true;
    @Override
    public void onStart() {
        super.onStart();
        data=Student_page.data;
        rno.setText(data.getRno());
        enrno.setText(data.getEno());
        course1.setText(data.getCourse());
        branch1.setText(data.getBranch());
        cgpa1.setText( data.getCgpa()+" ");
        ten.setText(data.getTen()+" ");
        twe.setText(data.getTwel()+" ");
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        DocumentReference dref = firestore.collection("AllowedUser").document("AdminSettings");
        dref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String, Object> doc = documentSnapshot.getData();
                flag= (boolean) doc.get("Lock");
            }
        });
        rno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==false)
                    compulsory(view);
                else
                    Toast.makeText(getActivity(),"DATABASE IS LOCKED",Toast.LENGTH_LONG).show();
            }
        });
        enrno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if(flag==false)
                    compulsory(view);
                else
                    Toast.makeText(getActivity(),"DATABASE IS LOCKED",Toast.LENGTH_LONG).show();
            
            }
        });
        course1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if(flag==false)
                    compulsory(view);
                else
                    Toast.makeText(getActivity(),"DATABASE IS LOCKED",Toast.LENGTH_LONG).show();
                
            }
        });
        branch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==false)
                    compulsory(view);
                else
                    Toast.makeText(getActivity(),"DATABASE IS LOCKED",Toast.LENGTH_LONG).show();
            }
        });
        cgpa1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if(flag==false)
                    compulsory(view);
                else
                    Toast.makeText(getActivity(),"DATABASE IS LOCKED",Toast.LENGTH_LONG).show();
            }
        });
        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if(flag==false)
                    compulsory(view);
                else
                    Toast.makeText(getActivity(),"DATABASE IS LOCKED",Toast.LENGTH_LONG).show();
            }
        });
        twe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==false)
                    compulsory(view);
                else
                    Toast.makeText(getActivity(),"DATABASE IS LOCKED",Toast.LENGTH_LONG).show();
                           }
        });
        semester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if(flag==false)
                    changeSemester(view);
                else
                    Toast.makeText(getActivity(),"DATABASE IS LOCKED",Toast.LENGTH_LONG).show();
                
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_frag__academic_details, container, false);
        //ImageView imageView=(ImageView) v.findViewById(R.id.profile);
        Data data=Student_page.data;
        rno=v.findViewById(R.id.editrollno);
        enrno=v.findViewById(R.id.editenrollno);
        course1=v.findViewById(R.id.editcourse);
        branch1=v.findViewById(R.id.editbranch);
        cgpa1=v.findViewById(R.id.editcgpa);
        ten=v.findViewById(R.id.edittenth);
        twe=v.findViewById(R.id.edittwelve);
        semester=v.findViewById(R.id.editsem);
        rno.setText(data.getRno());
        enrno.setText(data.getEno());
        course1.setText(data.getCourse());
        branch1.setText(data.getBranch());
        cgpa1.setText( data.getCgpa()+" ");
        ten.setText(data.getTen()+" ");
        twe.setText(data.getTwel()+" ");
        return v;


    }

    public void compulsory(View view)
    {
        AlertDialog.Builder ab=new AlertDialog.Builder(view.getContext());
        ab.setTitle("Compulsory field");
        ab.setMessage("Do you want to send a change Request?");
        ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //intent to student send request
                Intent intent=new Intent(view.getContext(),student_send_request.class);
                startActivity(intent);
                getActivity().finish();

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //do nothing
            }
        });
        ab.create().show();
    }

    public void changeSemester(View v)
    {
        AlertDialog.Builder ab=new AlertDialog.Builder(v.getContext());
        ab.setTitle("Edit Semester");
        ab.setMessage("Enter new Semester");
        EditText et=new EditText(v.getContext());
        et.setText(data.getSemester());
        ab.setView(et);
        ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //close;
            }
        }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                data.setSemester(et.getText().toString());
                semester.setText(et.getText().toString());
            }
        });
        ab.create().show();
    }

}