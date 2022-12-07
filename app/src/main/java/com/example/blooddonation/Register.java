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
    EditText nameET, emailET, phoneET, passwordET, userNameET;
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
    String nameStr, phoneStr, emailStr, userNameStr, classNameStr, districtStr, passwordStr, subjectStr;

    CallbackStringList callbackClassList = this::setClassList;
    CallbackStringList callbackSubjectList = list -> {
        subjectAdapter = new ArrayAdapter<>(this, R.layout.layout_drop_down_menu_single_item, list);
        subjectACTV.setAdapter(subjectAdapter);
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
            } else {
                teacherInfoContainer.setVisibility(View.GONE);
            }

        });
        submitBTN.setOnClickListener(view -> {
            if (userType.equals("Student"))
                clearTeacherInfo();
            setUserInfo();
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
        userNameET = findViewById(R.id.userNameET);

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
            Log.i("ClassSS", className);
            fillUpInfo.getSubjectList(className, callbackSubjectList);

        });
    }


    private void setUserInfo() {
        getUserInfo();
        HashMap<String, Object> data = new HashMap<>();
        data.put("name", nameStr);
        data.put("phoneNo", phoneStr);
        data.put("email", emailStr);
        data.put("userName", userNameStr);
        data.put("userType", userType);
        data.put("class", classNameStr);
        data.put("subject", subjectStr);
        data.put("district", districtStr);
        data.put("password", passwordStr);
        Log.i("UserData", String.valueOf(data));

    }

    private void getUserInfo() {
        nameStr = nameET.getText().toString();
        phoneStr = phoneET.getText().toString();
        emailStr = emailET.getText().toString();
        userNameStr = userNameET.getText().toString();
        passwordStr = passwordET.getText().toString();
        if(userType.equals("Teacher"))
        {
            classNameStr = classACTV.getText().toString();
            districtStr = districtACTV.getText().toString();
            subjectStr = subjectACTV.getText().toString();
        }

    }

    private void clearTeacherInfo() {
        classNameStr = "";
        subjectStr = "";
        districtStr = "";
    }


}