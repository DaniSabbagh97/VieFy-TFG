package com.example.biometricthings.RetrofitThings;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import com.example.biometricthings.R;

public class PreLogInANDSingUpActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preloginandsingup);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        findViewById(R.id.btnPreLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLoginDialog();
            }
        });

        findViewById(R.id.btnPreSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSignupDialog();
            }
        });

    }

    private void handleLoginDialog() {

        View view = getLayoutInflater().inflate(R.layout.login_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(view).show();

        Button loginBtn = view.findViewById(R.id.btnPreLogin);
        final EditText emailEdit = view.findViewById(R.id.etMail);
        final EditText passwordEdit = view.findViewById(R.id.etPassword);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, String> map = new HashMap<>();

                map.put("email", emailEdit.getText().toString());
                map.put("password", passwordEdit.getText().toString());

                Call<LoginResult> call = retrofitInterface.executeLogin(map);

                call.enqueue(new Callback<LoginResult>() {
                    @Override
                    public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {

                        if (response.code() == 200) {

                            LoginResult result = response.body();

                            AlertDialog.Builder builder1 = new AlertDialog.Builder(PreLogInANDSingUpActivity.this);
                            builder1.setTitle(result.getName());
                            builder1.setMessage(result.getEmail());

                            builder1.show();

                        } else if (response.code() == 404) {
                            Toast.makeText(PreLogInANDSingUpActivity.this, "Wrong Credentials",
                                    Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<LoginResult> call, Throwable t) {
                        Toast.makeText(PreLogInANDSingUpActivity.this, t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

    }

    private void handleSignupDialog() {

        View view = getLayoutInflater().inflate(R.layout.signup_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view).show();

        Button signupBtn = view.findViewById(R.id.btnPreSignUp);
        final EditText nameEdit = view.findViewById(R.id.etName);
        final EditText emailEdit = view.findViewById(R.id.etEmail);
        final EditText passwordEdit = view.findViewById(R.id.etPassword);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, String> map = new HashMap<>();

                map.put("name", nameEdit.getText().toString());
                map.put("email", emailEdit.getText().toString());
                map.put("password", passwordEdit.getText().toString());

                Call<Void> call = retrofitInterface.executeSignup(map);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        if (response.code() == 200) {
                            Toast.makeText(PreLogInANDSingUpActivity.this,
                                    "Signed up successfully", Toast.LENGTH_LONG).show();
                        } else if (response.code() == 400) {
                            Toast.makeText(PreLogInANDSingUpActivity.this,
                                    "Already registered", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(PreLogInANDSingUpActivity.this, t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

    }
}