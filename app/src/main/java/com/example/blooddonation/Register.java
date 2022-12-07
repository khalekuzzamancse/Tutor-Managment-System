package com.example.blooddonation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.blooddonation.firebasetemplate.CallbackStringList;
import com.example.blooddonation.firebasetemplate.FormFillUpInfo;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Register extends AppCompatActivity {
    private ProgressBar p;
    EditText nameET, emailET, phoneET, passwordET;
    LinearLayout teacherInfoContainer;
    String userType = "";
    AutoCompleteTextView registrationAsACTV, districtACTV, classACTV, subjectACTV;
    ArrayAdapter<String> registrationAsAdapter;
    ArrayAdapter districtAdapter;
    ArrayAdapter<String> classAdapter;
    ArrayAdapter<String> subjectAdapter;
    Toolbar toolbar;
    Button submitBTN, cancelBTN;
    FormFillUpInfo fillUpInfo;

    CallbackStringList callbackClassList = list -> {
        setClassList(list);
        Log.i("LISTTTTT", String.valueOf(list));

    };
    CallbackStringList callbackSubjectList = list -> {
        subjectAdapter = new ArrayAdapter<>(this, R.layout.layout_drop_down_menu_single_item, list);
        subjectACTV.setAdapter(subjectAdapter);
        Log.i("SubejctList", String.valueOf(list));
    };





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_scrolable);
        initialize();
        setToolbar();
        setUserType();
        setDistrict();


//        CallbackStringList callback = new CallbackStringList() {
//            @Override
//            public void receivedList(List<String> list) {
//                Log.i("LISTTTTTT", String.valueOf(list));
//            }
//        };
        fillUpInfo.getClassList(callbackClassList);



        registrationAsACTV.setOnItemClickListener((parent, view, position, id) -> {
            userType = parent.getItemAtPosition(position).toString();
            if (userType.equals("Teacher")) {
                teacherInfoContainer.setVisibility(View.VISIBLE);
                //  fillUpInfo.getClassList(callbackClassList);
            } else
                teacherInfoContainer.setVisibility(View.GONE);
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item_for_non_home_activity_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }


    private void initialize() {
        nameET = findViewById(R.id.name_ET);
        phoneET = findViewById(R.id.phone_ET);
        passwordET = findViewById(R.id.password_ET);
        emailET = findViewById(R.id.email_ET);
        classACTV = findViewById(R.id.class_ACTV);
        subjectACTV = findViewById(R.id.subject_ACTV);
        registrationAsACTV = findViewById(R.id.registerAs_ACTV);
        toolbar = findViewById(R.id.NonHomeActivity_Toolbar);
        submitBTN = findViewById(R.id.submit_BTN);
        cancelBTN = findViewById(R.id.cancel_BTN);
        districtACTV = findViewById(R.id.districtACTV);
        teacherInfoContainer = findViewById(R.id.teacherInfoContainer);
        fillUpInfo = new FormFillUpInfo();

    }

    private void setToolbar() {

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Register");
    }

    private void setDistrict() {
        String[] list = {"Dhaka", "Jashore", "Khulna", "Rangpur"};
        districtAdapter = new ArrayAdapter<>(this, R.layout.layout_drop_down_menu_single_item, list);
        districtACTV.setAdapter(districtAdapter);
    }

    private void setUserType() {
        String[] list = {"Teacher", "Student"};
        registrationAsAdapter = new ArrayAdapter<>(this, R.layout.layout_drop_down_menu_single_item, list);
        registrationAsACTV.setAdapter(registrationAsAdapter);
    }

    private void setClassList(List<String> list) {
        classAdapter = new ArrayAdapter<>(this, R.layout.layout_drop_down_menu_single_item, list);
        classACTV.setAdapter(classAdapter);
        classACTV.setOnItemClickListener((parent, view, position, id) -> {

            String className = parent.getItemAtPosition(position).toString();
            Log.i("ClassSS",className);
            fillUpInfo.getSubjectList(className, callbackSubjectList);

        });
    }


}