package com.example.blooddonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.blooddonation.firebasetemplate.CallbackUserProfile;
import com.example.blooddonation.firebasetemplate.DomainUserInfo;
import com.example.blooddonation.firebasetemplate.FirebaseAuthCustom;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    FirebaseAuthCustom currentUser;
    DomainUserInfo userInfo;

    CallbackUserProfile callbackUserProfile = new CallbackUserProfile() {
        @Override
        public void getProfile(DomainUserInfo profile) {
            userInfo = profile;
            hideMenuItem();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        initialize();
        setToolbar();
        hideMenuItem();
        //we have to called hideMenu option two times
        //1:when the main activity is just stared and we check the user singed in or not
        //2:after main activity start,read the user profile(if singed in) then
        //2.1 based on signed in info hide some menu

        //getting the login user profile info,

        FirebaseAuthCustom authCustom = new FirebaseAuthCustom();
        authCustom.getUserInfo(callbackUserProfile);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.login)
                    item.setVisible(false);
                if (id == R.id.login) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else if (id == R.id.about_us) {
                    Intent intent = new Intent(MainActivity.this, Activity_AboutUs.class);
                    startActivity(intent);


                } else if (id == R.id.search_tutor) {
                    Intent intent = new Intent(MainActivity.this, SearchResultActivity.class);
                    startActivity(intent);
                } else if (id == R.id.contact_us) {
                    Intent intent = new Intent(MainActivity.this, Activity_ContactSendMessage.class);
                    startActivity(intent);

                } else if (id == R.id.logout) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                } else if (id == R.id.show_profile) {
                    Intent intent = new Intent(MainActivity.this, ShowProfileActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });

    }

    private void hideMenuItem() {
        Menu menu = navigationView.getMenu();
        // if the user not singed in then hide
        //become a donor,profile and logout option from the menu
        if (currentUser.getUser() == null) {
            menu.removeItem(R.id.logout);
            menu.removeItem(R.id.show_profile);
        }
        //if the user is already login and he is donor then
        //hide the login option ->since he already login
        //hide the become donor because he is already a donor
        //if the user is already login and he is not donor then
        //hide the login option ->since he already login
        else {
            menu.removeItem(R.id.login);
        }


    }

    private void initialize() {
        drawerLayout = findViewById(R.id.MainActivity_DrawerLayout);
        navigationView = findViewById(R.id.ActivityMain_NavDrawer_NavigationView);
        userInfo = new DomainUserInfo();
        currentUser = new FirebaseAuthCustom();
        drawerLayout = findViewById(R.id.MainActivity_DrawerLayout);
        navigationView = findViewById(R.id.ActivityMain_NavDrawer_NavigationView);
        currentUser = new FirebaseAuthCustom();
    }
    private void setToolbar() {
        toolbar = findViewById(R.id.ActivityMain_ToolBar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle
                (this, drawerLayout, toolbar, R.string.OpenDrawer, R.string.CloseDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }



}



