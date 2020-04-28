package com.example.salesmartnew;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

   CircleImageView profImage;
   EditText fullNameUp, contactUp, emailUp, passwordUp, confirmPassUp;
   Button updateProf, closeProf;

   private Uri imageUri;
   private String myUrl = "";
   private StorageReference storeProfImage;
   private String checker = "";
   private StorageTask uploadTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        storeProfImage = FirebaseStorage.getInstance().getReference().child("image");

        //value assignment
        profImage = (CircleImageView)findViewById(R.id.EPImage);
        fullNameUp = (EditText)findViewById(R.id.text1_EP);
        contactUp = (EditText)findViewById(R.id.text2_EP);
        emailUp =  (EditText)findViewById(R.id.text3_EP);
        passwordUp = (EditText)findViewById(R.id.text4_EP);
        confirmPassUp = (EditText)findViewById(R.id.text5_EP);
        updateProf = (Button)findViewById(R.id.buttonUp);
        closeProf = (Button)findViewById(R.id.buttonDel);
        
        userDetailsDisplay(profImage, fullNameUp, contactUp, emailUp, passwordUp, confirmPassUp);

        closeProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        updateProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checker.equals("clicked")){
                    userDetailsSave();
                }else {
                    updateUserOnly();
                }

            }
        });

        profImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checker = "clicked";

                CropImage.activity(imageUri)
                        .setAspectRatio(1,1)
                        .start(EditProfileActivity.this);

            }
        });

    }

    private void userDetailsSave() {
        if(TextUtils.isEmpty((contactUp.getText().toString()))){
            Toast.makeText(this,"Contact Number is required", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty((fullNameUp.getText().toString()))){
            Toast.makeText(this,"Full name is required", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty((emailUp.getText().toString()))){
            Toast.makeText(this,"Email is required", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty((passwordUp.getText().toString()))){
            Toast.makeText(this,"Password is required", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty((confirmPassUp.getText().toString()))){
            Toast.makeText(this,"Confirm Password is required", Toast.LENGTH_SHORT).show();
        }

    }

    private void userDetailsDisplay(final CircleImageView profImage, final EditText fullNameUp, final EditText contactUp, final EditText emailUp, final EditText passwordUp, final EditText confirmPassUp) {
        DatabaseReference userDBRef = FirebaseDatabase.getInstance().getReference().child(PrevelantUser.currentUser.getContactNo());

        userDBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    if(dataSnapshot.child("image").exists()){
                        String image = dataSnapshot.child("image").getValue().toString();
                        String uContactNu = dataSnapshot.child("contactNo").getValue().toString();
                        String uFullName = dataSnapshot.child("fullName").getValue().toString();
                        String uEmail = dataSnapshot.child("emailCustomer").getValue().toString();
                        String uPassword = dataSnapshot.child("passwordCustomer").getValue().toString();
                        String uConfirmPassword = dataSnapshot.child("confirmPasswordCustomer").getValue().toString();


                        Picasso.get().load(image).into(profImage);
                        fullNameUp.setText(uFullName);
                        contactUp.setText(uContactNu);
                        emailUp.setText(uEmail);
                        passwordUp.setText(uPassword);
                        confirmPassUp.setText(uConfirmPassword);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void updateUserOnly() {

        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference().child("users");

        HashMap<String, Object> userMapDetails = new HashMap<>();
        userMapDetails.put("fullName", fullNameUp.getText().toString());
        userMapDetails.put("contactNo", contactUp.getText().toString());
        userMapDetails.put("emailCustomer", emailUp.getText().toString() );
        userMapDetails.put("passwordCustomer", passwordUp.getText().toString());
        userMapDetails.put("confirmPasswordCustomer", confirmPassUp.getText().toString());
        dbReference.child(PrevelantUser.currentUser.getContactNo()).updateChildren(userMapDetails);

        startActivity(new Intent(EditProfileActivity.this, DashBoard.class));
        Toast.makeText(EditProfileActivity.this, "Profile Details update Successfully", Toast.LENGTH_SHORT).show();
        finish();

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null){

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
            profImage.setImageURI(imageUri);

        }else{
            Toast.makeText(this, "Error , Try Again.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(EditProfileActivity.this,EditProfileActivity.class));
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
