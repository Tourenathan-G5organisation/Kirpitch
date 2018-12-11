package com.example.android.kirpitch;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.kirpitch.model.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetailActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    DatabaseReference myRef;
    FirebaseDatabase database;
    String key;
    Task mTask;

    TextView jobProfile;
    TextView companyName;
    TextView locationName;
    TextView statusName;
    TextView day;
    TextView weekday;
    TextView monthAndYear;

    ValueEventListener taskListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFirebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        if (getIntent().hasExtra(Intent.EXTRA_TEXT)) {
            key = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        }

        jobProfile = findViewById(R.id.job_profile_name);
        companyName = findViewById(R.id.company_name);
        locationName = findViewById(R.id.location_name);
        statusName = findViewById(R.id.stage_name);
        day = findViewById(R.id.day);
        weekday = findViewById(R.id.week_day);
        monthAndYear = findViewById(R.id.month_year);

        myRef = database.getReference().child("task/" + mFirebaseAuth.getCurrentUser().getUid() + "/" + key);

        taskListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                mTask = dataSnapshot.getValue(Task.class);
                setText();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        } else if (id == R.id.action_edit_task) {
            Intent intent = new Intent(this, NewTaskActivity.class);
            intent.putExtra("edit", true);
            intent.putExtra(Intent.EXTRA_TEXT, key);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_delete_task) {
            myRef.removeValue();
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_activity_menu, menu);
        return true;
    }

    void setText() {
        if (mTask != null) {
            jobProfile.setText(mTask.getTitle());
            companyName.setText(mTask.getCompanyName());
            locationName.setText(mTask.getLocation());
            statusName.setText(Utility.getStage(mTask.getStatus()));
            Calendar currentDate = Calendar.getInstance();
            currentDate.setTimeInMillis(mTask.getDate());
            day.setText(String.valueOf(currentDate.get(Calendar.DAY_OF_MONTH)));
            weekday.setText(currentDate.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()));
            monthAndYear.setText(String.format("%s %d", currentDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()), currentDate.get(Calendar.YEAR)));

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myRef.removeEventListener(taskListener);
    }
}
