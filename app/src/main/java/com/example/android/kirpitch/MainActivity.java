package com.example.android.kirpitch;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.android.kirpitch.model.Task;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    //  arbitrary request code value
    private static final int RC_SIGN_IN = 123;

    private RecyclerView recyclerView;
    private List<Task> applications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar
                        .make(v, "Your action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

      /*  DrawerLayout drawerL = findViewById(R.id.nav_drawer_layout);
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this, drawerL, toolbar
                        , R.string.navigation_drawer_l_open, R.string.navigation_drawer_l_close);

        drawerL.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
*/

        mFirebaseAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user sign out

                    /*startActivity(new Intent(getApplicationContext()
                            , LoginActivity.class));
                    finish();*/
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.EmailBuilder().build(),
                                            new AuthUI.IdpConfig.GoogleBuilder().build()))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };

        recyclerView = findViewById(R.id.recycle_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //linearLayoutManager.setReverseLayout(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);

        initializeData();
        initializeAdapter();

    }


    private void initializeData() {
        applications = new ArrayList<>();
        applications
                .add(new Task("Software Engineer at Microsoft", "Moscow, Russia", "20/11/2018", 0));
        applications
                .add(new Task("Android Developer at Yandex", "Belin, Germany", "20/11/2018", 1));
        applications
                .add(new Task("QA Engineer at MTN Cameroon", "Douala, Cameroon", "20/11/2018", 2));
        applications
                .add(new Task("Android Developer at EPAM", "Moscow, Russia", "20/11/2018", 3));
        applications
                .add(new Task("ML Engineer at Skytech", "Accra, Ghana", "20/11/2018", 4));
        applications
                .add(new Task("Android Developer at Google", "Bay Area, California", "20/11/2018", 5));
        applications
                .add(new Task("Telecom Engineer at Vodafone", "Algeria", "20/11/2018", 0));

    }

    private void initializeAdapter() {
        ARVAdapter adapter = new ARVAdapter(this);
        adapter.setData(applications);
        recyclerView.setAdapter(adapter);
        //recyclerView.setItemAnimator(dividerItemDecoration);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            if (resultCode == RESULT_CANCELED){ // the cancel the login and left the app
                finish();
            }
        }
    }

    /*@Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.nav_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            AuthUI.getInstance().signOut(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

       /* int id = item.getItemId();

        if (id == R.id.nav_logout) {

            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext()
                    , LoginActivity.class));
            finish();

        } else if (id == R.id.nav_delete) {

            final FirebaseUser user = mFirebaseAuth.getCurrentUser();
            if (user != null) {
                user.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast
                                            .makeText(getApplicationContext()
                                                    , "User deleted", Toast.LENGTH_SHORT)
                                            .show();
                                    startActivity(new Intent(getApplicationContext()
                                            , RegisterActivity.class));
                                    finish();
                                }
                            }
                        });
            }

        }

        DrawerLayout drawer = findViewById(R.id.nav_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);*/
        return true;
    }

}

