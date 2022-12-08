package com.example.blooddonation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.blooddonation.firebasetemplate.BloodInfo;
import com.example.blooddonation.firebasetemplate.CallbackDomainList;
import com.example.blooddonation.firebasetemplate.DomainUserInfo;
import com.example.blooddonation.ui.adapters.SearchResultAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class SearchResultActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recycler;
    SearchResultAdapter adapter;
    CallbackDomainList callback = List -> {
      //  progressIndicator.setVisibility(View.INVISIBLE);
        //  Log.i("ReceivedData-AllUserInfo", String.valueOf(List));
        if (List.isEmpty())
            showSnackbar();
        updateAdapter(List);



    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_activity);
        initialize();
        setToolbar();
        BloodInfo db = new BloodInfo();
        db.getTutors(callback);

//

    }


    void updateAdapter(List<DomainUserInfo> List) {
        adapter = new SearchResultAdapter(this, List);
        recycler.setLayoutManager(new LinearLayoutManager(SearchResultActivity.this));
        recycler.setAdapter(adapter);
    }

    void showSnackbar() {
        Snackbar snackbar = Snackbar
                .make(recycler, "Not Found", Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.purple_500));
        snackbar.show();
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
        toolbar = findViewById(R.id.NonHomeActivity_Toolbar);
        recycler = findViewById(R.id.recycler);

    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("User List");
    }

}


