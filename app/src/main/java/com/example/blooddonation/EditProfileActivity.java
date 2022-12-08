package com.example.blooddonation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.blooddonation.firebasetemplate.CallbackResult;
import com.example.blooddonation.firebasetemplate.CallbackStringList;
import com.example.blooddonation.firebasetemplate.CallbackUserProfile;
import com.example.blooddonation.firebasetemplate.DomainUserInfo;
import com.example.blooddonation.firebasetemplate.FirebaseAuthCustom;
import com.example.blooddonation.firebasetemplate.FormFillUpInfo;
import com.example.blooddonation.firebasetemplate.WritingDocument;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity {
    AutoCompleteTextView  districtACTV, classACTV, subjectACTV;
    Button submitBTN, cancelBTN;
    EditText nameET, phoneET;
    Toolbar toolbar;
    DomainUserInfo info;
    ArrayAdapter<String> classAdapter,subjectAdapter,districtAdapter;
    FormFillUpInfo fillUpInfo;

    CallbackStringList callbackClassList = this::setClassList;
    CallbackStringList callbackSubjectList = list -> {
        subjectAdapter = new ArrayAdapter<>(this, R.layout.layout_drop_down_menu_single_item, list);
        subjectACTV.setAdapter(subjectAdapter);
    };
    private CallbackResult result = response -> {
        // progressBar.setVisibility(View.INVISIBLE);
        if (response)
            showSnackBar("Registration Successful");
        else
            showSnackBar("Failed");

    };


    //after reading the data we will update the view ....
    private CallbackUserProfile callbackUserProfile = new CallbackUserProfile() {
        @Override
        public void getProfile(DomainUserInfo profile) {
            info = profile;
            if(!info.className.isEmpty())
                hideView();
            nameET.setText(info.name);
            phoneET.setText(info.phoneNo);
            //if the user is a donor then it have age other wise age is null
            // because non donor use not store the age information
//            if(info.=null)
//                ageET.setText(info.Age);

        }
    };

    //
    private CallbackResult callbackResult = new CallbackResult() {

        @Override
        public void isSuccess(boolean response) {
            //  progressBar.setVisibility(View.INVISIBLE);
            if (response)
                showSnackBar("Updated Successfully");
            else
                showSnackBar("Failed");
//            Intent intent = getIntent();
//            finish();
//            startActivity(intent);
//            Intent i = new Intent(Become_Donor_Activity.this, MainActivity.class);
//            startActivity(i);
        }
    };
    //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_scrollable);

        initialize();
        setToolbar();
        initialize();
        setToolbar();
        setDistrict();


        FirebaseAuthCustom authCustom = new FirebaseAuthCustom();
        authCustom.getUserInfo(callbackUserProfile);


        //
        fillUpInfo.getClassList(callbackClassList);


        submitBTN.setOnClickListener(view -> {
            updateInfo();

        });
        cancelBTN.setOnClickListener(view->{
            onBackPressed();
        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }



    private void initialize() {
        toolbar = findViewById(R.id.NonHomeActivity_Toolbar);
        info = new DomainUserInfo();
        fillUpInfo = new FormFillUpInfo();
        nameET = findViewById(R.id.nameET);
        phoneET = findViewById(R.id.phone_noET);
        cancelBTN = findViewById(R.id.cancelBTN);
        classACTV = findViewById(R.id.class_ACTV);
        submitBTN = findViewById(R.id.submitBTN);
        toolbar = findViewById(R.id.NonHomeActivity_Toolbar);
        //  progressBar = findViewById(R.id.progressBar);
        districtACTV = findViewById(R.id.districtACTV);
        subjectACTV = findViewById(R.id.subject_ACTV);

    }


    private void setDistrict() {
        String[] list = {"Dhaka", "Jashore", "Khulna", "Rangpur"};
        districtAdapter = new ArrayAdapter<>(this, R.layout.layout_drop_down_menu_single_item, list);
        districtACTV.setAdapter(districtAdapter);
    }



    private void setClassList(List<String> list) {
        classAdapter = new ArrayAdapter<>(this, R.layout.layout_drop_down_menu_single_item, list);
        classACTV.setAdapter(classAdapter);
        classACTV.setOnItemClickListener((parent, view, position, id) -> {

            String className = parent.getItemAtPosition(position).toString();
            Log.i("ClassSS", className);
            fillUpInfo.getSubjectList(className, callbackSubjectList);

        });
    }

    private void hideView()
    {
        classACTV.setVisibility(View.INVISIBLE);
        subjectACTV.setVisibility(View.INVISIBLE);
        districtACTV.setVisibility(View.INVISIBLE);
    }

    private void setToolbar() {

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Update Profile");
    }
    private void updateInfo() {
        HashMap<String, Object> data = new HashMap<>();
        String name=nameET.getText().toString().trim();
        data.put("name",name);
        String phone=phoneET.getText().toString().trim();
        data.put("phoneNo",phone);
        String className = classACTV.getText().toString().trim();
        data.put("className", className);
        String dis = districtACTV.getText().toString().trim();
        data.put("district", dis);
        WritingDocument document = new WritingDocument();
        document.updateDocument(info.email, data, callbackResult);
        Log.i("DataGot", String.valueOf(data));
    }
    void showSnackBar(String msg) {
        Snackbar snackbar = Snackbar
                .make(submitBTN, msg, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.purple_500));
        snackbar.show();
    }
    ////replace the back button with navigationUp because
    //1.Main activity read the data from the database
    //2.based on user data some menu item will be hide
    //3.if we use navigation up then main activity will be recreated
    //4.as a result we got the updated data
    //5.according to updated data menu item list will be updated
    @Override
    public void onBackPressed() {
        onNavigateUp();
    }


}