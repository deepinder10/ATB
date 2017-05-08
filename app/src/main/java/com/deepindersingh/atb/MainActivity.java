package com.deepindersingh.atb;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    FrameLayout homeFrame,requestsFrame,profileFrame;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    requestsFrame.setVisibility(View.GONE);
                    profileFrame.setVisibility(View.GONE);
                    homeFrame.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_dashboard:
                    profileFrame.setVisibility(View.GONE);
                    homeFrame.setVisibility(View.GONE);
                    requestsFrame.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_notifications:
                    requestsFrame.setVisibility(View.GONE);
                    homeFrame.setVisibility(View.GONE);
                    profileFrame.setVisibility(View.VISIBLE);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeFrame = (FrameLayout) findViewById(R.id.home);
        requestsFrame = (FrameLayout) findViewById(R.id.requests);
        profileFrame = (FrameLayout) findViewById(R.id.profile);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

}
