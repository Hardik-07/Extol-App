package com.example.extol;


import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

//implement the interface OnNavigationItemSelectedListener in your activity class
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getting bottom navigation view and attaching the listener
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selfragment = null;
                    switch (item.getItemId()){
                        case R.id.navigation_home:
                            selfragment = new HomeFragment();
                            break;
                        case R.id.navigation_dashboard:
                            selfragment = new SearchFragment();
                            break;
                        case R.id.navigation_notifications:
                            selfragment = new ProfileFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragment_container,
                                    selfragment).commit();
                    return true;
                }

            };
}
