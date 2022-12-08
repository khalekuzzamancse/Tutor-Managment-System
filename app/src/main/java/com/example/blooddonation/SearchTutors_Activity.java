package com.example.blooddonation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class SearchTutors_Activity extends AppCompatActivity {
    TextInputLayout DivisionLayout;

    List<String> subDistrictList = new ArrayList<>();
    ProgressBar p;
    EditText divisionTv;
    String subDis="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_tutors_activity);
        Toolbar toolbar = findViewById(R.id.NonHomeActivity_Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Search Tutors");
        setBloodGroup();
        setLocation();

        divisionTv = findViewById(R.id.divisionACTv);
        DivisionLayout = findViewById(R.id.division);

        Button search = findViewById(R.id.submit);
        //check if the blood group filed is empty or not
        //if the there are any error  message then the checkedBloodGroupField() will clear or do not clear the message
        checkBloodGroupField();
        search.setOnClickListener(view -> {

            p = findViewById(R.id.ActivitySearch_ProgressBar);
            //  p.setVisibility(View.VISIBLE);

            String blood = divisionTv.getText().toString().trim();


            EditText Dis = findViewById(R.id.district);
            String dis = Dis.getText().toString().trim();
            if (dis.equals(""))
                dis = "null";
          //  EditText SubDis = findViewById(R.id.subDistrict);
           // String subDis = SubDis.getText().toString().trim();

            if (subDis.equals(""))
                subDis = "null";

//            Intent intent = new Intent(this, SearchResultActivity.class);
//            intent.putExtra(SearchResultActivity.EXTRA_bloodGroup, blood);
//            intent.putExtra(SearchResultActivity.EXTRA_District, dis);
//            intent.putExtra(SearchResultActivity.EXTRA_SubDistrict, subDis);
//            intent.putExtra(SearchResultActivity.Extra_ComingFrom, "SearchBlood");//Change later--Sojib
            //  p.setVisibility(View.INVISIBLE);
            //if the user not choosen a bloodGroup then we do not show the list

//            if (!divisionTv.getText().toString().isEmpty())
//                startActivity(intent);
//            else {
//                //if user not chosen a bloodGroup but clicked the submit button.
//                //then show the error message
//                DivisionLayout.setError("Please,Choose a Division");
//            }


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

    private void setBloodGroup() {
        List<String> DivisionList = new ArrayList<>();
        DivisionList.add("Rajshahi");
        DivisionList.add("Khulna");
        DivisionList.add("Dhaka");
        DivisionList.add("Rangpur");
        DivisionList.add("Chottogram");
        DivisionList.add("Barishal");
        DivisionList.add("Mymensingh");
        DivisionList.add("Sylhet");
//
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.layout_drop_down_menu_single_item, DivisionList);
        AutoCompleteTextView bloodGroup = findViewById(R.id.divisionACTv);
        bloodGroup.setAdapter(adapter);

        bloodGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = parent.getItemAtPosition(position).toString();


            }
        });
    }


    private void setLocation() {
        List<String> districtList = new ArrayList<>();
        districtList = MainActivity.districtListModel.getDistrictList().getValue();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(SearchTutors_Activity.this, R.layout.layout_drop_down_menu_single_item, districtList);
        AutoCompleteTextView d =
                findViewById(R.id.district);
        d.setAdapter(adapter);

        d.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = parent.getItemAtPosition(position).toString();
                //setSubDistrict(s);
            }
        });

    }

//    private void setSubDistrict(String s) {
//        List<String> subDistrictList = new ArrayList<>();
//        subDistrictList = MainActivity.districtListModel.getDistrictListHashMap().getValue().get(s);
//        Log.i("SubDistrict", String.valueOf(subDistrictList));
//
////        ArrayAdapter<String> adapter = new ArrayAdapter<>(SearchTutors_Activity.this, R.layout.layout_drop_down_menu_single_item, subDistrictList);
////     //   AutoCompleteTextView d = findViewById(R.id.subDistrict);
////        d.setAdapter(adapter);
////
////        d.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////            @Override
////            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                String s = parent.getItemAtPosition(position).toString();
////
////
////            }
////        });
//
//    }

    private void checkBloodGroupField() {
        //since the text has changed so the bloodGroupField is not empty
        //so we removing the error message.
        divisionTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //keep empty,beacase we remove the error message after text changed
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //keep empty,beacase we remove the error message after text changed
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //removing the error message
                DivisionLayout.setError(null);
            }
        });
    }


}