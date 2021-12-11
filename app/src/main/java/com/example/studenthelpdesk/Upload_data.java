package com.example.studenthelpdesk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;

public class Upload_data extends AppCompatActivity {
    Button pic,resume;
    static private Uri imageuri,imageuri2;
    ImageView profilepic;
    Data data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_data);
        pic=findViewById(R.id.uppic);
        profilepic=findViewById(R.id.imageView8);
        resume=findViewById(R.id.upres);
        downloadImageFromFireBase();
    }

    public void showPic()
    {
        downloadImageFromFireBase();
    }
    public void uploadpicbutton(View view)
    {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }
    public void UploadResume(View view)
    {
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,2);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            if(resultCode==RESULT_OK) {
                dialog = new ProgressDialog(this);
                dialog.setMessage("Uploading");
                dialog.show();
                imageuri = data.getData();
                profilepic.setImageURI(imageuri);
                uploadPic();
                profilepic.setImageURI(imageuri);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(Upload_data.this, "ACTIVITY CANCELLED", Toast.LENGTH_SHORT).show();
            }
            }
        if (requestCode == 2) {
            if(resultCode == Activity.RESULT_OK){
                dialog = new ProgressDialog(this);
                dialog.setMessage("Uploading");
                dialog.show();
                Upload_data.imageuri2 = data.getData();
                uploadResume();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(Upload_data.this,"ACTIVITY CANCELLED",Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    ProgressDialog dialog;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Upload_data.this,Academic_data.class);
        startActivity(intent);
        finish();
    }

    public void next(View view)
    {

        startActivity(new Intent(Upload_data.this,SeeMyData.class));
        //uploadPic();
        //uploadResume();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        data=SignIn.data;
    }
    public void uploadResume(){
        if(Upload_data.imageuri2!=null)
        {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference("Resume");
            //Toast.makeText(Upload_data.this, imageuri2.toString()+".pdf SAVED", Toast.LENGTH_SHORT).show();
            final StorageReference filepath = storageReference.child(SignIn.data.getUname());
            filepath.putFile(imageuri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Upload upload=new Upload(imageuri2.toString()+"."+getFileExtension((imageuri2)));
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Profile");
                    String uploadid=databaseReference.push().getKey();
                    SignIn.data.setResume(uploadid);
                    if(dialog.isShowing()) {
                        dialog.setMessage("Uploaded");
                        Toast.makeText(Upload_data.this, filepath.getName()+".pdf SAVED", Toast.LENGTH_SHORT).show();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                }
            });
        }
        else
        {
            Toast.makeText(this,"No PDF selected",Toast.LENGTH_LONG).show();
        }
    }
    public void uploadPic() {
        Data data=SignIn.data;
        if(Upload_data.imageuri!=null)
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
                    SignIn.data.setProfile(uploadid);
                    if(dialog.isShowing())
                        dialog.dismiss();
                    showPic();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Upload_data.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
        else
        {
            Toast.makeText(this,"No Image selected",Toast.LENGTH_LONG).show();
        }

    }
    private  String getFileExtension(Uri uri)
    {
        ContentResolver cR=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void downloadImageFromFireBase()
    {

        StorageReference storageReference = FirebaseStorage.getInstance().getReference("ProfilePic").child(SignIn.data.getUname());
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(Upload_data.this)
                        .load(uri).diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.profile_pic)
                        .placeholder(R.drawable.profile_pic)
                        .into(profilepic);
            }
        });

    }
}
