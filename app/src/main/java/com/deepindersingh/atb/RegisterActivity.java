package com.deepindersingh.atb;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.deepindersingh.atb.model.User;
import com.deepindersingh.atb.rest.ApiClient;
import com.deepindersingh.atb.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity  {

    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private EditText mDonorName;
    private EditText mDonorAge;
    private EditText mDonorPhone;
    private Spinner blood_spinner;
    private Spinner states_spinner;
    private RadioGroup mGender;
    private RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmailView = (EditText) findViewById(R.id.donorEmail);
        mPasswordView = (EditText) findViewById(R.id.donorPassword);
        blood_spinner = (Spinner) findViewById(R.id.blood_group_spinner);
        states_spinner = (Spinner) findViewById(R.id.state_spinner);
        mDonorName = (EditText) findViewById(R.id.donorName);
        mDonorAge = (EditText) findViewById(R.id.donorAge);
        mDonorPhone = (EditText) findViewById(R.id.donorPhone);
        mGender = (RadioGroup) findViewById(R.id.m_gender);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignup();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        ArrayAdapter<CharSequence> bloodAdapter = ArrayAdapter.createFromResource(this,
                R.array.blood_group_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        bloodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        blood_spinner.setAdapter(bloodAdapter);

        ArrayAdapter<CharSequence> stateAdapter = ArrayAdapter.createFromResource(this,
                R.array.states_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        states_spinner.setAdapter(stateAdapter);

    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptSignup() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String password = mPasswordView.getText().toString();
        String email = mEmailView.getText().toString();
        String donorName = mDonorName.getText().toString();
        String phoneNumber = mDonorPhone.getText().toString();
        String donorAge = mDonorAge.getText().toString();
        String blood_group = blood_spinner.getSelectedItem().toString();
        String state = states_spinner.getSelectedItem().toString();


        int selectedId = mGender.getCheckedRadioButtonId();

        radioButton = (RadioButton) findViewById(selectedId);

        String gender = radioButton.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if(TextUtils.isEmpty(donorAge)){
            mDonorAge.setError(getString(R.string.error_field_required));
            focusView = mDonorAge;
            cancel = true;
        }

        if(TextUtils.isEmpty(donorName)){
            mDonorName.setError(getString(R.string.error_field_required));
            focusView = mDonorName;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if(TextUtils.isEmpty(phoneNumber)){
            mDonorPhone.setError(getString(R.string.error_invalid_email));
            focusView = mDonorPhone;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            int b_id = 13;
            int state_id=9;
            int city_id=4;
            int district_id=1;
            switch (blood_group){
                case "O+":
                    b_id = 13;
                    break;
                case "O-":
                    b_id = 14;
                    break;
                case "AB+":
                    b_id = 15;
                    break;
                case "AB-":
                    b_id = 16;
                    break;
                case "A+":
                    b_id = 17;
                    break;
                case "A-":
                    b_id = 18;
                    break;
                case "B+":
                    b_id = 19;
                    break;
                case "B-":
                    b_id = 20;
                    break;
            }
            switch (state){
                case "Punjab":
                    state_id = 9;
                    city_id = 4;
                    district_id = 1;
                    break;
                case "Harayana":
                    state_id = 10;
                    city_id = 11;
                    district_id = 23;
                    break;
                case "Himachal Pradesh":
                    state_id = 11;
                    city_id = 4;
                    district_id = 1;
                    break;
                case "Delhi":
                    state_id = 13;
                    city_id = 4;
                    district_id = 1;
                    break;
                case "Jammu and Kashmir":
                    state_id = 12;
                    city_id = 4;
                    district_id = 1;
                    break;
                case "Rajasthan":
                    state_id = 14;
                    city_id = 4;
                    district_id = 1;
                    break;
            }
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password,donorName,donorAge,phoneNumber,gender,b_id,state_id,city_id,district_id);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
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

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private final String mDonorName;
        private final String mDonorAge;
        private final String mPhone;
        private final String mGender;
        private final int b_id;
        private final int state_id;
        private final int city_id;
        private final int district_id;


        UserLoginTask(String email, String password,String donorName,String donorAge,String phoneNumber,String gender,int blood,int state,int city,int district) {
            mEmail = email;
            mPassword = password;
            mDonorName = donorName;
            mDonorAge = donorAge;
            mPhone = phoneNumber;
            mGender = gender;
            b_id = blood;
            city_id = city;
            state_id = state;
            district_id = district;

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);

                Call<User> call = apiService.register(mEmail,mPassword,mDonorName,mGender,
                        mDonorAge,mPhone,b_id,state_id,city_id,district_id);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User>call, Response<User> response) {
                        showProgress(false);

                        Log.d("success", "" + response.body().getError());
                        if(response.body().getFlag() == 143){
                            Toast.makeText(RegisterActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                        }else{
                            Toast.makeText(RegisterActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User>call, Throwable t) {
                        // Log error here since request failed
                        Log.e("error", t.toString());
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

