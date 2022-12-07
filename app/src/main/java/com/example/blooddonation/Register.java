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

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;

public class Register extends AppCompatActivity {
    private ProgressBar p;
    EditText classET, subjectET;
    LinearLayout Container;
    String userType = "";
    AutoCompleteTextView dropdownTv,districtACTV;
    ArrayAdapter<String> dropDownAdapter;
    String categories[] = {"Teacher", "Student"};
    String [] list={"Dhaka","Jashore","Khulna","Rangpur"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_scrolable);

        classET = findViewById(R.id.class_name);
        subjectET = findViewById(R.id.subject);
        Container = findViewById(R.id.Container);
        //setting the category
        dropdownTv=findViewById(R.id.dropdown);
        dropDownAdapter=new ArrayAdapter<String>(this,R.layout.drop_down_item_layout,categories);
        dropdownTv.setAdapter(dropDownAdapter);

        ///
        districtACTV = findViewById(R.id.districtACT);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(Register.this,
                R.layout.layout_drop_down_menu_single_item, list);
        districtACTV.setAdapter(adapter);

        districtACTV.setOnItemClickListener((parent, view, position, id) -> {
            String s = parent.getItemAtPosition(position).toString();
            Log.i("Clickeed", s);

        });

        ///

        Toolbar toolbar = findViewById(R.id.NonHomeActivity_Toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Register");

        Button submit = findViewById(R.id.Activity_Register_Button_Submit);
        submit.setOnClickListener(view -> {
            p = findViewById(R.id.ActivityRegister_ProgressBar);
            p.setVisibility(View.VISIBLE);
            SetUserInfo();
        });

        dropdownTv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                userType = (String) parent.getItemAtPosition(position);
                if (!userType.equals("Teacher")) {
                    Container.setVisibility(View.GONE);
                } else {
                    Container.setVisibility(View.VISIBLE);
                }
            }
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


    private void SetUserInfo() {
        EditText Name = findViewById(R.id.Activity_Register_TextInputLayout_EditText_Name);
        String name = Name.getText().toString().trim();
        if (name.isEmpty()) {
            Name.setError("Name can not be empty!");
            return;
        }
        EditText Email = findViewById(R.id.Activity_Register_TextInputLayout_EditText_Email);
        String email = Email.getText().toString().trim();
        if (email.isEmpty()) {
            Email.setError("Email can not be empty!");
            return;
        } else if (!email.contains("@")) {
            Email.setError("Invalid Email!");
            return;
        }
        EditText PhoneNumber = findViewById(R.id.Activity_Register_TextInputLayout_EditText_PhoneNumber);
        String phoneNumber = PhoneNumber.getText().toString().trim();
        if (phoneNumber.isEmpty()) {
            PhoneNumber.setError("Phone Number can not be empty!");
            return;
        } else if (phoneNumber.length() != 11) {
            PhoneNumber.setError("Total Digit Must be 11");
            return;
        }

        EditText UserName = findViewById(R.id.Activity_Register_TextInputLayout_EditText_UserName);
        String userName = UserName.getText().toString().trim();
        if (userName.isEmpty()) {
            UserName.setError("Username can not be empty!");
            return;
        }
        EditText PassWord = findViewById(R.id.Activity_Register_TextInputLayout_EditText_Password);
        String password = PassWord.getText().toString().trim();
        if (password.isEmpty()) {
            PassWord.setError("Password can not be empty!");
            return;
        } else if (password.length() < 6) {
            PassWord.setError("Password length is less than 6!");
            return;
        }

        EditText ConfirmPassWord = findViewById(R.id.Activity_Register_TextInputLayout_EditText_ConfirmPassword);
        String confirmPassword = ConfirmPassWord.getText().toString().trim();
        if (confirmPassword.isEmpty()) {
            ConfirmPassWord.setError("Re-Enter the password again!");
            return;
        }
        if (!password.equals(confirmPassword)) {
            ConfirmPassWord.setError("Password does not match");
            return;
        }
        register(email, password);

        HashMap<String, Object> Data = new HashMap<>();
        Data.put("Name", name);
        Data.put("Email", email);
        Data.put("UserName", userName);
        Data.put("Password", password);
        Data.put("PhoneNumber", phoneNumber);
        Data.put("isDonor", "false");

        setDataToDatabase(Data);

        Log.i("Alhamdulillah", name + "\n" + email + "\n" + phoneNumber + "\n" + userName + "\n" + password + "\n" + confirmPassword);


    }

    private void register(String email, String password) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Task<AuthResult> task) -> {
                    if (!task.isSuccessful()) {
                        Log.i("Registraion Failed", "Next,Inshallah");
                    } else {
                        Log.i("Registered", "Alhadulliah");
                        p.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(this, Activity_Login.class);
                        intent.putExtra(Activity_Login.Extra_Login, "FromRegister");
                        startActivity(intent);
                    }
                });

    }

    private void setDataToDatabase(HashMap<String, Object> Data) {
        String email = (String) Data.get("Email");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
//  db.collection("UserInfo")
        // UserInfo_Sojib
        db.collection("UserInfo")
                .document(email)
                .set(Data)
                .addOnCompleteListener((Task<Void> task) -> {
                    if (!task.isSuccessful()) {
                        Log.i("Failed to Added to database", "Next,Inshallah");
                    } else {
                        Log.i("Added to Database", "Alhadulliah");
                    }
                });

    }


}