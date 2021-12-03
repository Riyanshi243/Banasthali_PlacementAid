package com.example.studenthelpdesk;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.InsetDialogOnTouchListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frag_Resume#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frag_Resume extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private ImageView i10,i9,i8;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frag_Resume() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frag_Resume.
     */
    // TODO: Rename and change types and number of parameters
    public static frag_Resume newInstance(String param1, String param2) {
        frag_Resume fragment = new frag_Resume();
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
    public void onStart() {
        super.onStart();

        i10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                download(view);
            }
        });
        i9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadResume(view);
            }
        });
    }
    public void UploadResume(View view)
    {
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    ProgressDialog dialog;
    static private Uri imageuri2;
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                dialog = new ProgressDialog(getActivity());
                dialog.setMessage("Uploading");
                dialog.show();
                frag_Resume.imageuri2 = data.getData();
                uploadResume();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getActivity(),"Nothing uploaded",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void uploadResume(){
        if(frag_Resume.imageuri2!=null)
        {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference("Resume");
            final StorageReference filepath = storageReference.child(Student_page.data.getUname());
            Toast.makeText(getActivity(), filepath.getName()+".pdf SAVED", Toast.LENGTH_SHORT).show();
            filepath.putFile(imageuri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Upload upload=new Upload(imageuri2.toString()+"."+getFileExtension((imageuri2)));
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Profile");
                    String uploadid=databaseReference.push().getKey();
                    Student_page.data.setResume(uploadid);
                    if(dialog.isShowing())
                        dialog.dismiss();

                }
            });
        }
        else
        {
            Toast.makeText(getActivity(),"No PDF selected",Toast.LENGTH_LONG).show();
        }
    }
    private  String getFileExtension(Uri uri)
    {
        ContentResolver cR=getActivity().getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    public void download(View v)
    {
        AlertDialog.Builder ab=new AlertDialog.Builder(getActivity());
        ab.setTitle("Select 1");
        ab.setPositiveButton("View", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference("Resume").child(Student_page.data.getUname());
                Task<Uri> message = storageRef.getDownloadUrl();
                message.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Toast.makeText(getActivity(),uri.toString(),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(v.getContext(), ViewPDFActivity.class);
                        intent.putExtra("url", uri.toString());
                        startActivity(intent);
                    }
                });



            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setNeutralButton("Download", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference("Resume").child(Student_page.data.getUname());
                Task<Uri> message = storageRef.getDownloadUrl();
                message.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Toast.makeText(getActivity(),uri.toString(),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });

            }
        });
        ab.create().show();


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_frag__resume, container, false);
        i10=v.findViewById(R.id.imageView10);
         i9= v.findViewById(R.id.imageView9);
          i8= v.findViewById(R.id.imageView8);
          i10.setClickable(true);
        i10.setFocusable(true);
        i10.setFocusableInTouchMode(false);
        return v;

    }
}