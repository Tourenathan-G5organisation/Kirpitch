package com.example.android.kirpitch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.kirpitch.model.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class NewTaskActivity extends AppCompatActivity {

    TextInputLayout jobProfileLayout;
    TextView jobProfile;
    TextInputLayout companyLayout;
    TextView company;
    TextInputLayout locationLayout;
    TextView location;
    TextInputLayout spinnerLayout;
    Spinner spinner;
    ValueEventListener taskListener;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseAuth mFirebaseAuth;
    Task mTask;

    boolean edit = false;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().hasExtra("edit")) {
            edit = getIntent().getBooleanExtra("edit", false);
            key = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        }

        jobProfile = findViewById(R.id.job_profile_name);
        jobProfileLayout = findViewById(R.id.job_profile_layout);

        companyLayout = findViewById(R.id.company_layout);
        company = findViewById(R.id.company_name);

        location = findViewById(R.id.location_name);
        locationLayout = findViewById(R.id.location_layout);

        spinner = findViewById(R.id.stage_name);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.array_item));
        spinner.setAdapter(adapter);

        mFirebaseAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("task/" + mFirebaseAuth.getCurrentUser().getUid());
        if (edit){
            myRef = database.getReference().child("task/" + mFirebaseAuth.getCurrentUser().getUid() + "/" + key);
            taskListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get Post object and use the values to update the UI
                    mTask = dataSnapshot.getValue(Task.class);
                    setValues();
                    // ...
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w("Detail", "loadPost:onCancelled", databaseError.toException());
                    // ...
                }
            };
            myRef.addValueEventListener(taskListener);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        } else if (id == R.id.action_new_task) {
            savedNewTask();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_task_menu, menu);
        return true;
    }


    void savedNewTask() {
        String jobProfileText = jobProfile.getText().toString().trim();
        if (jobProfileText.length() < 3) {
            jobProfileLayout.setError("Profile name not valid");
            return;
        }
        String companyText = company.getText().toString().trim();

        String locationText = location.getText().toString().trim();

        if (edit) {
            mTask.setTitle(jobProfileText);
            mTask.setCompanyName(companyText);
            mTask.setLocation(locationText);
            myRef.setValue(mTask);
        } else {
            Calendar currentDate = Calendar.getInstance();
            Task task = new Task(jobProfileText, companyText, locationText, currentDate.getTimeInMillis(), spinner.getSelectedItemPosition());
            myRef.push().setValue(task);
        }
        onBackPressed();
    }

    void setValues() {
        jobProfile.setText(mTask.getTitle());
        company.setText(mTask.getCompanyName());
        location.setText(mTask.getLocation());
        spinner.setSelection(mTask.getStatus());
        String[] arr = getResources().getStringArray(R.array.array_item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       if (myRef != null && taskListener != null){
           myRef.removeEventListener(taskListener);
       }
    }
}
