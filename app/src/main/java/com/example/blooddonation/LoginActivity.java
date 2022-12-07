package com.example.blooddonation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.blooddonation.firebasetemplate.FirebaseAuthCustom;
import com.example.blooddonation.firebasetemplate.FormFillUpInfo;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private ProgressBar p;
    Toolbar toolbar;
    EditText emailET, passwordET;
    String emailStr, passwordStr;
    Button loginBTN, registerBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_scrolable);
        initialize();
        setToolbar();
        loginBTN.setOnClickListener(view -> {
            getUserInfo();
            new FirebaseAuthCustom().signIn(emailStr,passwordStr);
            startActivity(new Intent(this,ShowProfileActivity.class));
        });

    }

    private void initialize() {

        passwordET = findViewById(R.id.password_ET);
        emailET = findViewById(R.id.email_ET);
        toolbar = findViewById(R.id.NonHomeActivity_Toolbar);
        loginBTN = findViewById(R.id.login_btn);
        registerBtn = findViewById(R.id.register_btn);

    }

    private void setToolbar() {

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Login");
    }

    private void getUserInfo() {
        emailStr = emailET.getText().toString();
        passwordStr = passwordET.getText().toString();
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


}