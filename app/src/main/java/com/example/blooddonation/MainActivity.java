package com.example.blooddonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.blooddonation.ui.viewmodel.ViewModel_AllDistrictList;
import com.example.blooddonation.ui.viewmodel.ViewModel_SearchingTutor;
import com.example.blooddonation.ui.viewmodel.ViewModel_UserProfileInfo;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static ViewModel_UserProfileInfo model;
    public static ViewModel_AllDistrictList districtListModel;
    public static String Extra_Login = "null";
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    private ViewModel_SearchingTutor model_searchingBlood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.MainActivity_DrawerLayout);
        navigationView = findViewById(R.id.ActivityMain_NavDrawer_NavigationView);

        toolbar = findViewById(R.id.ActivityMain_ToolBar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle
                (this, drawerLayout, toolbar, R.string.OpenDrawer, R.string.CloseDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        ///

        ///fetching district


        ///Livedata section
        model = new ViewModelProvider(this).get(ViewModel_UserProfileInfo.class);

        model.getSignUserInfo().observe(MainActivity.this, new Observer<HashMap<String, String>>() {
            @Override
            public void onChanged(HashMap<String, String> userInfo) {

                String email = userInfo.get("Email");
               if (email.equals("null")) {
                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.menu_nav_drawer_when_user_not_signed_in);

                }
                String isDonor = userInfo.get("isDonor");
                if (isDonor.equals("true")) {
                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.menu_nav_drawer_when_user_tutor);
                } else {
                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.menu_nav_drawer_when_user_not_tutor);
                }


            }
        });

        districtListModel = new ViewModelProvider(this).get(ViewModel_AllDistrictList.class);
        districtListModel.getDistrictListHashMap().observe(this, new Observer<HashMap<String, List<String>>>() {
            @Override
            public void onChanged(HashMap<String, List<String>> stringListHashMap) {

                stringListHashMap.remove("");
                Log.i("Getting", String.valueOf(stringListHashMap));
            }
        });
        districtListModel.getDistrictList().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                Log.i("Getting", String.valueOf(strings));

            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.ActivityMain_NavDrawerMenu_Login) {
                    Intent intent = new Intent(MainActivity.this, Activity_Login.class);
                    startActivity(intent);
                } else if (id == R.id.ActivityMain_NavDrawerMenu_BecomeADonor) {
                    Intent intent = new Intent(MainActivity.this, Activity_Become_Tutor.class);
                    startActivity(intent);
                } else if (id == R.id.ActivityMain_NavDrawerMenu_AboutUs) {
                    Intent intent = new Intent(MainActivity.this, Activity_AboutUs.class);
                    startActivity(intent);


                } else if (id == R.id.ActivityMain_NavDrawerMenu_SearchBlood) {
                    Intent intent = new Intent(MainActivity.this, SearchTutors_Activity.class);
                    startActivity(intent);
                } else if (id == R.id.ActivityMain_NavDrawerMenu_WhyDonateBlood) {
                    Intent intent = new Intent(MainActivity.this, Activity_Why.class);
                    startActivity(intent);
                } else if (id == R.id.ActivityMain_NavDrawerMenu_ContactUs) {
                    Intent intent = new Intent(MainActivity.this, Activity_ContactSendMessage.class);
                    startActivity(intent);

                } else if (id == R.id.ActivityMain_NavDrawerMenu_LogOut) {
                    FirebaseAuth.getInstance().signOut();
                    Log.i("Curr", "signout");
//                    TextView t=findViewById(R.id.headerTextView);
//                    t.setText("something");
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);

                } else if (id == R.id.ActivityMain_NavDrawerMenu_showProfile) {
                    Intent intent = new Intent(MainActivity.this, Activity_ReadUserProfile.class);
                    startActivity(intent);
                }
                return true;
            }
        });
//
        TextView allDonor = findViewById(R.id.All_DonorList);
        allDonor.setOnClickListener(view -> {
            Intent intent = new Intent(this, Activity_AllUserInfoList.class);
            intent.putExtra(Activity_AllUserInfoList.Extra_ComingFrom, "Main");
            intent.putExtra(Activity_AllUserInfoList.EXTRA_bloodGroup, "null");
            intent.putExtra(Activity_AllUserInfoList.EXTRA_District, "null");
            intent.putExtra(Activity_AllUserInfoList.EXTRA_SubDistrict, "null");

            startActivity(intent);
        });
        TextView t = findViewById(R.id.SearchBloodMain_Activity);
        t.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SearchTutors_Activity.class);
            startActivity(intent);

        });


//        ViewModel_SearchingBlood obj = new ViewModel_SearchingBlood();
//        obj.AllUserInfoList();

    }

    @Override
    protected void onResume() {
        HashMap<String, String> data = model.getSignUserInfo().getValue();
        String email = data.get("Email");
        if (email.equals("null")) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.menu_nav_drawer_when_user_not_signed_in);

        }
        Intent i = getIntent();
        String s = i.getStringExtra(MainActivity.Extra_Login);
        if (s != null && s.equals("FromLogin")) {
            Snackbar.make(toolbar, "Login Successful", Snackbar.LENGTH_SHORT).show();
        }
        super.onResume();


    }


}



