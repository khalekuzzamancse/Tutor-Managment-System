package com.example.blooddonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.blooddonation.firebasetemplate.CallbackUserProfile;
import com.example.blooddonation.firebasetemplate.DomainUserInfo;
import com.example.blooddonation.firebasetemplate.FirebaseAuthCustom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Objects;

public class ShowProfileActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView nameTV, emailTV, phoneTV, classTV,subjectTV,districtTV;
    DomainUserInfo userInfo;
    private CallbackUserProfile callbackUserProfile = new CallbackUserProfile() {
        @Override
        public void getProfile(DomainUserInfo profile) {
            userInfo = profile;
            setProfile(profile);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_profile_activity);
        initialize();
        setToolbar();

        FirebaseAuthCustom authCustom = new FirebaseAuthCustom();
        authCustom.getUserInfo(callbackUserProfile);


    }


    private void setProfile(DomainUserInfo info) {
        nameTV.setText("Name :" + info.name);
        emailTV.setText("Email :" + info.email);
        phoneTV.setText("Phone No :" + info.phoneNo);
        districtTV.setText("District :" + info.district);

    }

    private void setToolbar() {

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");
    }

    private void initialize() {
        userInfo = new DomainUserInfo();
        toolbar = findViewById(R.id.ShowProfileToolbar);
        nameTV = findViewById(R.id.name_TV);
        emailTV = findViewById(R.id.email_TV);
        phoneTV = findViewById(R.id.phone_TV);
        districtTV = findViewById(R.id.district_TV);
        classTV=findViewById(R.id)
    }

    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_profile, menu);
        return true;


    }

    //@Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.Activity_ProifleMenuEditProfile)
//            startActivity(new Intent(this, EditProfileActivity.class));
//        else if (id == R.id.Activity_ProifleMenuDonatDateUpdate)
//            startActivity(new Intent(this, UpdateDonateDateActvity.class));
//
//        return super.onOptionsItemSelected(item);
//    }
}
