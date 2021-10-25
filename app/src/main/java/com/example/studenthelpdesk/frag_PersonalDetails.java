package com.example.studenthelpdesk;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;

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
    TextView name,pno,gender,dob,fname,mname,aadhar;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_frag__personal_details, container, false);
        ImageView imageView=(ImageView) v.findViewById(R.id.profile);
        imageView.setImageResource(R.drawable.profile_pic);
        Data data=Student_page.data;
        name=v.findViewById(R.id.editName);
        pno=v.findViewById(R.id.edittwelve);
        mname=v.findViewById(R.id.editmothersname);
        fname=v.findViewById(R.id.editfathername);
        gender=v.findViewById(R.id.editGender);
        aadhar=v.findViewById(R.id.editaadhar);
        dob=v.findViewById(R.id.editdob);
        name.setText(data.getName());
        pno.setText(data.getPno());
        mname.setText(data.getMname());
        fname.setText(data.getFname());
        gender.setText(data.getGender());
        aadhar.setText(data.getAadhar());
        dob.setText(data.getDob());
        return v;
    }
    public  void putdata()
    {
        DocumentReference documentReference;
    }
}