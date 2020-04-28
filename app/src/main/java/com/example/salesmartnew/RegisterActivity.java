package com.example.salesmartnew;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity {

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    //FirebaseAuth firebaseAuth;
    EditText rFullName, rEmail, rPhone, rPassword, rConfirmPass;
    Button btRegister;
    RelativeLayout RL1;
    ImageView bacKArrow;
    ProgressBar progressBar;

    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Initialize the view
        RL1 = findViewById(R.id.RL1_Register);
        bacKArrow = findViewById(R.id.image1_Register);
        rFullName = findViewById(R.id.ET1_Register);
        rEmail = findViewById(R.id.ET2_Register);
        rPhone = findViewById(R.id.ET3_Register);
        rPassword = findViewById(R.id.ET4_Register);
        rConfirmPass = findViewById(R.id.ET5_Register);
        btRegister = findViewById(R.id.button1_Register);
        progressBar = findViewById(R.id.progressBar_Reg);

        //Initialize Validation Style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //add validation for name
        awesomeValidation.addValidation(this,R.id.ET1_Register,RegexTemplate.NOT_EMPTY,R.string.invalid_name);

        //add validation for email
        awesomeValidation.addValidation(this,R.id.ET2_Register, Patterns.EMAIL_ADDRESS, R.string.invalid_email);

        //add validation for phoneNumber
        String PhoneVal ="[0-9]+";
        awesomeValidation.addValidation(this,R.id.ET3_Register,PhoneVal,R.string.phoneReg);

        String errorPassword = "[a-zA-Z0-9\\!\\@\\#\\$]{8,24}";
        //add validation for password
        awesomeValidation.addValidation(this,R.id.ET4_Register,errorPassword,R.string.invalid_password);

        //add validation for confirmPassword
        awesomeValidation.addValidation(this,R.id.ET5_Register,R.id.ET4_Register,R.string.invalid_confirm_password);


        bacKArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent goBack = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(goBack);
            }
        });



        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check the validation
                if(awesomeValidation.validate()){
                    //validate success
                    Toast.makeText(getApplicationContext(),"Use your phone number as a user name..",Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(getApplicationContext(),"All fields are required..",Toast.LENGTH_SHORT).show();

                }

                String cFullName = rFullName.getText().toString().trim();
                String cEmail = rEmail.getText().toString().trim();
                String cPhone = rPhone.getText().toString().trim();
                String cPassword = rPassword.getText().toString().trim();
                String cConfirmPass = rConfirmPass.getText().toString().trim();

                //if(TextUtils.isEmpty(cFullName)){
                   // rFullName.setError("Full Name is Required.");
                   // return;
               // }

                if(TextUtils.isEmpty(cEmail)){
                rEmail.setError("Email is Required.");
                    return;
                }
                if(TextUtils.isEmpty(cPhone)){
                   rPhone.setError("Phone Number is Required.");
                  return;
                }

                if(TextUtils.isEmpty(cPassword)){
                    rPassword.setError("Password is Required.");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");
                RegisterHelperClass registerHelperClass = new RegisterHelperClass(cFullName, cEmail, cPhone, cPassword, cConfirmPass);
                registerHelperClass.setImage("https://www.google.com/logos/doodles/2020/stay-and-play-at-home-with-popular-past-google-doodles-cricket-2017-6753651837108767-s.png");
                reference.child(cPhone).setValue(registerHelperClass);

                Intent signUP = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(signUP);
            }
        });

    }
}
