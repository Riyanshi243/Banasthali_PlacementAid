package com.example.studenthelpdesk;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frag_PersonalDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frag_PersonalDetails extends Fragment implements DatePickerDialog.OnDateSetListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Data data=Student_page.data;
    private  ImageView profile,editpic;

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
    static Boolean flag[]={true};
    @Override
    public void onStart() {
        super.onStart();
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        DocumentReference dref = firestore.collection("AllowedUser").document("AdminSettings");
        dref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String, Object> doc = documentSnapshot.getData();
                flag[0]= (boolean) doc.get("Lock");
            }
        });
        pan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[0]==false)
                      changePan(view);
                else
                    Toast.makeText(getActivity(),"DATA IS LOCKED",Toast.LENGTH_SHORT).show();
            
            }
        });
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(flag[0]==false){
                compulsory(view);
                Student_viewData.change="Name";
            }
                else
                    Toast.makeText(getActivity(),"DATA IS LOCKED",Toast.LENGTH_SHORT).show();
                
            }
        });
        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[0]==false)
                    changeGender(view);
                else
                    Toast.makeText(getActivity(),"DATA IS LOCKED",Toast.LENGTH_SHORT).show();
                
            }
        });
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[0]==false)
                   changeDOB(view);
                else
                    Toast.makeText(getActivity(),"DATA IS LOCKED",Toast.LENGTH_SHORT).show();
                
            }
        });
        fname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[0]==false){
                    compulsory(view);
                    Student_viewData.change="Father Name";
                }
                else
                    Toast.makeText(getActivity(),"DATA IS LOCKED",Toast.LENGTH_SHORT).show();
                
            }
        });
        mname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[0]==false){
                    compulsory(view);
                    Student_viewData.change="Mother Name";
                }
                else
                    Toast.makeText(getActivity(),"DATA IS LOCKED",Toast.LENGTH_SHORT).show();
                
            }
        });
        aadhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[0]==false)
                     changeAadhar(view);
                else
                    Toast.makeText(getActivity(),"DATA IS LOCKED",Toast.LENGTH_SHORT).show();
               
            }
        });
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[0]==false)
                    changeAddress(view);
                else
                    Toast.makeText(getActivity(),"DATA IS LOCKED",Toast.LENGTH_SHORT).show();
                
            }
        });
        pno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag[0]==false)
                   changePhone(view);
                else
                    Toast.makeText(getActivity(),"DATA IS LOCKED",Toast.LENGTH_SHORT).show();
                
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ab= new AlertDialog.Builder(getActivity());
                ab.setTitle("Change Profile Picture");
                ab.setMessage("Do you want to change your profile picture?");
                ab.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        uploadpicbutton(view);
                    }
                });
                ab.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //nothing
                    }
                });
                ab.create().show();
            }
        });
    }
    static private Uri imageuri;
    ProgressDialog dialog;
    public void uploadpicbutton(View view)
    {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            if(resultCode==RESULT_OK) {
                dialog = new ProgressDialog(getActivity());
                dialog.setMessage("Uploading");
                dialog.show();
                imageuri = data.getData();
                profile.setImageURI(imageuri);
                uploadPic();
                profile.setImageURI(imageuri);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getActivity(), "Nothing Uploaded", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void uploadPic() {
        Data data=Student_page.data;
        if(frag_PersonalDetails.imageuri!=null)
        {
            FirebaseStorage storage = FirebaseStorage.getInstance();

            StorageReference storageRef=storage.getReference("ProfilePic");

            StorageReference fileReference =storageRef.child((data.getUname()));
            fileReference.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Upload upload=new Upload(imageuri.toString()+"."+getFileExtension((imageuri)));
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Profile");
                    String uploadid=databaseReference.push().getKey();
                    Student_page.data.setProfile(uploadid);
                    profile.setImageURI(imageuri);
                    if(dialog.isShowing())
                        dialog.dismiss();
                    showPic();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
        else
        {
            Toast.makeText(getActivity(),"No Image selected",Toast.LENGTH_LONG).show();
        }

    }
    public void showPic()
    {
        downloadImageFromFireBase();
    }

    private  String getFileExtension(Uri uri)
    {
        ContentResolver cR=getActivity().getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_frag__personal_details, container, false);
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
        profile=v.findViewById(R.id.profile);
        name.setText(data.getName());
        pno.setText(data.getPno());
        mname.setText(data.getMname());
        fname.setText(data.getFname());
        gender.setText(data.getGender());
        aadhar.setText(data.getAadhar());
        dob.setText(data.getDob());
        address.setText(data.getAddress());
        pan.setText(data.getPan());
        editpic=v.findViewById(R.id.profile);
        profile.setImageResource(R.drawable.profile_pic);
        downloadImageFromFireBase();
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                FirebaseFirestore firestore=FirebaseFirestore.getInstance();
                DocumentReference dref = firestore.collection("AllowedUser").document("AdminSettings");
                dref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Map<String, Object> doc = documentSnapshot.getData();
                        flag[0]= (boolean) doc.get("Lock");

                    }
                });

            }

        }, 0, 1000);
        return v;
    }
   private void downloadImageFromFireBase()
    {

        StorageReference storageReference = FirebaseStorage.getInstance().getReference("ProfilePic").child(Student_page.data.getUname());
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getActivity())
                        .load(uri).diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.profile_pic)
                        .placeholder(R.drawable.profile_pic)
                        .into(profile);
            }
        });

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
        downloadImageFromFireBase();
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
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
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
        ab.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //close;
            }
        }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                data.setAadhar(et.getText().toString());
                aadhar.setText(et.getText().toString());
                if(Student_page.data!=null)
                    Student_page.data.ToDatabase();
            }
        });
        ab.create().show();
    }
    public void changeAddress(View v)
    {
        AlertDialog.Builder ab=new AlertDialog.Builder(v.getContext());
        ab.setTitle("Edit Address");
        ab.setMessage("Enter New Address");
        EditText et=new EditText(v.getContext());
        et.setText(data.getAddress());
        ab.setView(et);
        ab.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //close;
            }
        }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                data.setAddress(et.getText().toString());
                address.setText(et.getText().toString());
                if(Student_page.data!=null)
                    Student_page.data.ToDatabase();
            }
        });
        ab.create().show();
    }

   @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime());
        dob.setText(selectedDate);
        SignIn.data.setDob(selectedDate);
    }
    public void changeDOB(View v)
    {
        /*AlertDialog.Builder ab=new AlertDialog.Builder(v.getContext());
        ab.setTitle("Edit Date of Birth");
        ab.setMessage("Enter new Date of Birth");
        EditText et=new EditText(v.getContext());
        et.setText(data.getDob());
        ab.setView(et);
        ab.setNegativeButton("N0", new DialogInterface.OnClickListener() {
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
*/

        
    }
    public void changeGender(View v)
    {
        AlertDialog.Builder ab=new AlertDialog.Builder(v.getContext());
        ab.setTitle("Edit Gender");
        ab.setMessage("Select new Gender");
        RadioGroup et=new RadioGroup(getActivity());
        RadioButton r1=new RadioButton(getActivity());
        RadioButton r2=new RadioButton(getActivity());
        RadioButton r3=new RadioButton(getActivity());
        r1.setText("Male");
        r2.setText("Female");
        r3.setText("Others");
        et.addView(r1);
        et.addView(r2);
        et.addView(r3);
        String gender11 = data.getGender();
        if(gender11!=null)
            switch (gender11)
            {
                case "Female":
                    r2.setChecked(true);
                    break;
                case "Male":
                    r1.setChecked(true);
                    break;
                case "Others":
                    r3.setChecked(true);
                    break;
                default:

            }
        ab.setView(et);
        ab.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //close;
            }
        }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String gen=gender11;
                if(r1.isChecked())
                    gen="Male";
                if(r2.isChecked())
                    gen="Female";
                if(r3.isChecked())
                    gen="Others";
                data.setGender(gen);
                gender.setText(gen);
                if(Student_page.data!=null)
                    Student_page.data.ToDatabase();
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
        ab.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //close;
            }
        }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                data.setPan(et.getText().toString());
                pan.setText(et.getText().toString());
                if(Student_page.data!=null)
                    Student_page.data.ToDatabase();
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
        ab.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //close;
            }
        }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                data.setPno(et.getText().toString());
                pno.setText(et.getText().toString());
                if(Student_page.data!=null)
                    Student_page.data.ToDatabase();
            }
        });
        ab.create().show();
    }

}