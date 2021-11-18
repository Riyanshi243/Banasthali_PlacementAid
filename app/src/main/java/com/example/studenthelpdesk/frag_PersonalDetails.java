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
 * Use the {@link frag_PersonalDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frag_PersonalDetails extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Data data=Student_page.data;

    TextView name, pno, gender, dob, fname, mname, aadhar,address,pan;
    
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public frag_PersonalDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frag_PersonalDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static frag_PersonalDetails newInstance(String param1, String param2) {
        frag_PersonalDetails fragment = new frag_PersonalDetails();
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
            //ImageView imageView=(ImageView) findViewById(R.id.)
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    static Boolean flag=true;
    @Override
    public void onStart() {
        super.onStart();
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        DocumentReference dref = firestore.collection("AllowedUser").document("AdminSettings");
        dref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String, Object> doc = documentSnapshot.getData();
                flag= (boolean) doc.get("Lock");
            }
        });
        pan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==false)
                      changePan(view);
                else
                    Toast.makeText(getActivity(),"DATA IS LOCKED",Toast.LENGTH_LONG).show();
            
            }
        });
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(flag==false){
                compulsory(view);
                Student_viewData.change="Name";
            }
                else
                    Toast.makeText(getActivity(),"DATA IS LOCKED",Toast.LENGTH_LONG).show();
                
            }
        });
        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==false)
                    changeGender(view);
                else
                    Toast.makeText(getActivity(),"DATA IS LOCKED",Toast.LENGTH_LONG).show();
                
            }
        });
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==false)
                   changeDOB(view);
                else
                    Toast.makeText(getActivity(),"DATA IS LOCKED",Toast.LENGTH_LONG).show();
                
            }
        });
        fname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==false){
                    compulsory(view);
                    Student_viewData.change="Father's Name";
                }
                else
                    Toast.makeText(getActivity(),"DATA IS LOCKED",Toast.LENGTH_LONG).show();
                
            }
        });
        mname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==false){
                    compulsory(view);
                    Student_viewData.change="Mother's name";
                }
                else
                    Toast.makeText(getActivity(),"DATA IS LOCKED",Toast.LENGTH_LONG).show();
                
            }
        });
        aadhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==false)
                     changeAadhar(view);
                else
                    Toast.makeText(getActivity(),"DATA IS LOCKED",Toast.LENGTH_LONG).show();
               
            }
        });
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==false)
                    changeAddress(view);
                else
                    Toast.makeText(getActivity(),"DATA IS LOCKED",Toast.LENGTH_LONG).show();
                
            }
        });
        pno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==false)
                   changePhone(view);
                else
                    Toast.makeText(getActivity(),"DATA IS LOCKED",Toast.LENGTH_LONG).show();
                
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_frag__personal_details, container, false);
        ImageView imageView = (ImageView) v.findViewById(R.id.profile);
        imageView.setImageResource(R.drawable.profile_pic);
        Data data = Student_page.data;
        name = v.findViewById(R.id.editName);
        pno = v.findViewById(R.id.editphone);
        mname = v.findViewById(R.id.editmothersname);
        fname = v.findViewById(R.id.editfathername);
        gender = v.findViewById(R.id.editGender);
        aadhar = v.findViewById(R.id.editaadhar);
        dob = v.findViewById(R.id.editdob);
        address=v.findViewById(R.id.editaddesss);
        pan=v.findViewById(R.id.editpan);
        name.setText(data.getName());
        pno.setText(data.getPno());
        mname.setText(data.getMname());
        fname.setText(data.getFname());
        gender.setText(data.getGender());
        aadhar.setText(data.getAadhar());
        dob.setText(data.getDob());
        address.setText(data.getAddress());
        pan.setText(data.getPan());

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        name.setText(data.getName());
        pno.setText(data.getPno());
        mname.setText(data.getMname());
        fname.setText(data.getFname());
        gender.setText(data.getGender());
        aadhar.setText(data.getAadhar());
        dob.setText(data.getDob());
        address.setText(data.getAddress());
        pan.setText(data.getPan());
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
    public void changeAadhar(View v)
    {
        AlertDialog.Builder ab=new AlertDialog.Builder(v.getContext());
        ab.setTitle("Edit Aadhar");
        ab.setMessage("Enter new Aadhar number");
        EditText et=new EditText(v.getContext());
        et.setText(data.getAadhar());
        ab.setView(et);
        ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //close;
            }
        }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                data.setAadhar(et.getText().toString());
                aadhar.setText(et.getText().toString());
            }
        });
        ab.create().show();
    }
    public void changeAddress(View v)
    {
        AlertDialog.Builder ab=new AlertDialog.Builder(v.getContext());
        ab.setTitle("Edit Name");
        ab.setMessage("Enter new Name");
        EditText et=new EditText(v.getContext());
        et.setText(data.getAddress());
        ab.setView(et);
        ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //close;
            }
        }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                data.setAddress(et.getText().toString());
                address.setText(et.getText().toString());
            }
        });
        ab.create().show();
    }
    public void changeDOB(View v)
    {
        AlertDialog.Builder ab=new AlertDialog.Builder(v.getContext());
        ab.setTitle("Edit Date of Birth");
        ab.setMessage("Enter new Date of Birth");
        EditText et=new EditText(v.getContext());
        et.setText(data.getDob());
        ab.setView(et);
        ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //close;
            }
        }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                data.setDob(et.getText().toString());
                dob.setText(et.getText().toString());
            }
        });
        ab.create().show();
    }
    public void changeGender(View v)
    {
        AlertDialog.Builder ab=new AlertDialog.Builder(v.getContext());
        ab.setTitle("Edit Gender");
        ab.setMessage("Enter new Gender");
        EditText et=new EditText(v.getContext());
        et.setText(data.getGender());
        ab.setView(et);
        ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //close;
            }
        }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                data.setGender(et.getText().toString());
                gender.setText(et.getText().toString());
            }
        });
        ab.create().show();
    }
    public void changePan(View v)
    {
        AlertDialog.Builder ab=new AlertDialog.Builder(v.getContext());
        ab.setTitle("Edit PAN");
        ab.setMessage("Enter new PAN");
        EditText et=new EditText(v.getContext());
        et.setText(data.getPan());
        ab.setView(et);
        ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //close;
            }
        }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                data.setPan(et.getText().toString());
                pan.setText(et.getText().toString());
            }
        });
        ab.create().show();
    }
    public void changePhone(View v)
    {
        AlertDialog.Builder ab=new AlertDialog.Builder(v.getContext());
        ab.setTitle("Edit Phone Number");
        ab.setMessage("Enter new Phone Number");
        EditText et=new EditText(v.getContext());
        et.setText(data.getPno());
        ab.setView(et);
        ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //close;
            }
        }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                data.setPno(et.getText().toString());
                pno.setText(et.getText().toString());
            }
        });
        ab.create().show();
    }

}