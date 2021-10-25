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
 * Use the {@link frag_AcademicDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frag_AcademicDetails extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
     TextView rno,enrno,course1,branch1,ten,twe,cgpa1;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frag_AcademicDetails() {
        // Required empty public constructor
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View v= inflater.inflate(R.layout.fragment_frag__personal_details, container, false);
        ImageView imageView=(ImageView) v.findViewById(R.id.profile);
        imageView.setImageResource(R.drawable.profile_pic);
        Data data=Student_page.data;
        rno=v.findViewById(R.id.editrollno);
        enrno=v.findViewById(R.id.editenrollno);
        course1=v.findViewById(R.id.editcourse);
        branch1=v.findViewById(R.id.editbranch);
        ten=v.findViewById(R.id.edittenth);
        twe=v.findViewById(R.id.edittwelve);
        cgpa1=v.findViewById(R.id.editcgpa);
        rno.setText(data.getRno());
        enrno.setText(data.getEno());
        course1.setText(data.getCourse());
        branch1.setText(data.getBranch());
        cgpa1.setText(Float.toString( data.getCgpa()));
        ten.setText(Float.toString(data.getTen()));
        twe.setText(Float.toString(data.getTwel()));
        return v;


    }

}