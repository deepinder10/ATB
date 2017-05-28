package com.deepindersingh.atb;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.deepindersingh.atb.adapter.RequestsAdapter;
import com.deepindersingh.atb.model.Requests;
import com.deepindersingh.atb.model.User;
import com.deepindersingh.atb.rest.ApiClient;
import com.deepindersingh.atb.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    FrameLayout homeFrame,requestsFrame,profileFrame;

    private UserRequestTask mAuthTask = null;

    // UI references.
    private EditText rEmail;
    private View mProgressView;
    private View mLoginFormView;
    private EditText rName;
    private EditText rAge;
    private EditText rPhone;
    private EditText rHospital;
    private Spinner blood_spinner;
    private RadioGroup mGender;
    private RadioButton radioButton;

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

        rEmail = (EditText) findViewById(R.id.rEmail);
        blood_spinner = (Spinner) findViewById(R.id.blood_group_spinner);
        rName = (EditText) findViewById(R.id.rName);
        rAge = (EditText) findViewById(R.id.rAge);
        rPhone = (EditText) findViewById(R.id.rPhone);
        mGender = (RadioGroup) findViewById(R.id.m_gender);
        rHospital = (EditText) findViewById(R.id.rHospital);

        Button requestButton = (Button) findViewById(R.id.request_button);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRequest();
            }
        });

        mLoginFormView = findViewById(R.id.request_form);
        mProgressView = findViewById(R.id.login_progress);

        ArrayAdapter<CharSequence> bloodAdapter = ArrayAdapter.createFromResource(this,
                R.array.blood_group_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        bloodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        blood_spinner.setAdapter(bloodAdapter);
        
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        SharedPreferences sharedPreferences = getSharedPreferences("authentication", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

        Call<User> call = apiService.getRequestList(token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                List<Requests> movies = response.body().getRequests();
                if(response.body().getFlag() == 143){
                    recyclerView.setAdapter(new RequestsAdapter(movies, R.layout.list_item_request, getApplicationContext()));
                }else if (response.body().getFlag() == 101){
                    SharedPreferences sharedPreferences = getSharedPreferences("authentication", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("token");
                    editor.apply();
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Log error here since request failed
                Log.e("error", t.toString());
            }
        });
    }

    private void attemptRequest() {
        if (mAuthTask != null) {
            return;
        }
        
        // Store values at the time of the login attempt.
        String hospital = rHospital.getText().toString();
        String email = rEmail.getText().toString();
        String donorName = rName.getText().toString();
        String phoneNumber = rPhone.getText().toString();
        String donorAge = rAge.getText().toString();
        String blood_group = blood_spinner.getSelectedItem().toString();
        
        int selectedId = mGender.getCheckedRadioButtonId();

        radioButton = (RadioButton) findViewById(selectedId);

        String gender = radioButton.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(hospital)) {
            rHospital.setError(getString(R.string.error_field_required));
            focusView = rHospital;
            cancel = true;
        }

        if(TextUtils.isEmpty(donorAge)){
            rAge.setError(getString(R.string.error_field_required));
            focusView = rAge;
            cancel = true;
        }

        if(TextUtils.isEmpty(donorName)){
            rName.setError(getString(R.string.error_field_required));
            focusView = rName;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            rEmail.setError(getString(R.string.error_field_required));
            focusView = rEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            rEmail.setError(getString(R.string.error_invalid_email));
            focusView = rEmail;
            cancel = true;
        }

        if(TextUtils.isEmpty(phoneNumber)){
            rPhone.setError(getString(R.string.error_invalid_email));
            focusView = rPhone;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserRequestTask(email, hospital,donorName,donorAge,phoneNumber,gender,blood_group);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public class UserRequestTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mHospital;
        private final String mDonorName;
        private final String mDonorAge;
        private final String mPhone;
        private final String mGender;
        private final String blood_group;

        UserRequestTask(String email, String hospital,String donorName,String donorAge,String phoneNumber,String gender,String blood) {
            mEmail = email;
            mHospital = hospital;
            mDonorName = donorName;
            mDonorAge = donorAge;
            mPhone = phoneNumber;
            mGender = gender;
            blood_group = blood;

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);

                Call<User> call = apiService.requestCreate(mEmail,mHospital,mDonorName,mGender,
                        mDonorAge,mPhone,blood_group);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User>call, Response<User> response) {
                        showProgress(false);

                        Log.d("success", "" + response.body().getFlag());
                        if(response.body().getFlag() == 143){
                            Toast.makeText(MainActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                            showProgress(false);
                            finish();
                            startActivity(getIntent());
                        }else{
                            Toast.makeText(MainActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User>call, Throwable t) {
                        // Log error here since request failed
                        Log.e("error", t.toString());
                        showProgress(false);
                    }
                });
                mAuthTask = null;


            } catch (Exception e) {
                Log.e("error", e.toString());

            }
            return null;

        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }


}
