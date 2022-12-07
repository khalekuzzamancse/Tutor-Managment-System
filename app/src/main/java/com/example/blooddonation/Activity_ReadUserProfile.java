package com.example.blooddonation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

public class Activity_ReadUserProfile extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_user_profile);

        Toolbar toolbar = findViewById(R.id.ShowProfileToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");


//        auth=FirebaseAuth.getInstance();
//       currentUser=auth.getCurrentUser();
//        if(currentUser!=null) {
        //}
        setProfile();


//        Button update = findViewById(R.id.button);
//        update.setOnClickListener(view -> {
//            Intent in = new Intent(this, Activity_UpdateProfile.class);
//            startActivity(in);
//
//        });
//        Button button = findViewById(R.id.delete);
//
//        button.setOnClickListener(view -> {
//            Intent in = new Intent(this, Activity_Account_Delete.class);
//            startActivity(in);
//        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_profile, menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Activity_ProifleMenuEditProfile)
            startActivity(new Intent(this, Activity_UpdateProfile.class));
        else if (id == R.id.Activity_ProifleMenuDonatDateUpdate)
            startActivity(new Intent(this, Activity_UpdateDate.class));

        return super.onOptionsItemSelected(item);
    }

    private void setProfile() {
        HashMap<String, String> data = MainActivity.model.getSignUserInfo().getValue();
        String currentUsrEmail = MainActivity.model.getSignUserInfo().getValue().get("Email");

        String name = data.get("Name");
        String email = data.get("Email");
        String phone = data.get("PhoneNumber");
        String dis = data.get("District");
        if (dis != null)
            dis = "District: " + dis;
        else
            dis = "District: .....";
        String subDis = data.get("SubDistrict");
        if (subDis != null)
            subDis = "Sub District: " + subDis;
        else
            subDis = "Sub District: .....";
        String age = data.get("Age");
        if (age != null)
            age = "Age: " + age;
        else
            age = "Age: .....";
        String gender = data.get("Gender");
        if (gender != null)
            gender = "Gender: " + gender;
        else
            gender = "Gender: .....";
        String division = data.get("BloodGroup");
        if (division != null)
            division = "Division: " + division;
        else
            gender = "Gender: .....";
        String password = data.get("Password");
//    Activity_Account_Delete.get_PassWord=password;
//    Log.i("Password set for delete", Activity_Account_Delete.get_PassWord);
        TextView tName = findViewById(R.id.Activity_showProfile_Name);
        if (tName != null)
            tName.setText(name);
        TextView tEmail = findViewById(R.id.Activity_showProfile_Email);
        if (tEmail != null)
            tEmail.setText(email);
        TextView tPhone = findViewById(R.id.Activity_showProfile_Phone);
        if (tPhone != null)
            tPhone.setText(phone);
        TextView tBlood = findViewById(R.id.Activity_showProfile_Division);
        if (tBlood != null && division != null)
            tBlood.setText(division);
        TextView tGender = findViewById(R.id.Activity_showProfile_Gender);
        if (tGender != null & gender != null)
            tGender.setText(gender);
        TextView tDistrict = findViewById(R.id.Activity_showProfile_District);
        if (tDistrict != null && dis != null)
            tDistrict.setText(dis);
        TextView tSubDis = findViewById(R.id.Activity_showProfile_subDistrict);
        if (tSubDis != null && subDis != null)
            tSubDis.setText(subDis);
        TextView tAge = findViewById(R.id.Activity_showProfile_Age);
        if (tAge != null && age != null)
            tAge.setText(age);
        Log.i("Curre,Read Profile,Set", String.valueOf(data));

    }


}
