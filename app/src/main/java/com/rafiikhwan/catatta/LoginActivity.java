package com.rafiikhwan.catatta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rafiikhwan.catatta.Config.APIConfig;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private TextView tvDate, tvTitle, tvTitleDesc, btnChange, btnLogin, btnRegister, tvSwitch;
    private String date;
    private LinearLayout background;
    private boolean loginResult;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvTitle     = (TextView) findViewById(R.id.tv_title);
        tvTitleDesc = (TextView) findViewById(R.id.tv_title_desc);
        tvDate      = (TextView) findViewById(R.id.tvHari);
        tvSwitch    = (TextView) findViewById(R.id.tv_switch);
        etUsername  = (EditText) findViewById(R.id.et_username);
        etPassword  = (EditText) findViewById(R.id.et_passwords);
        btnSubmit   = (Button) findViewById(R.id.btn_login);
        btnChange   = (TextView) findViewById(R.id.btn_switch);

        date = new SimpleDateFormat("yyyy-MM-d", new Locale("in", "ID")).format(new Date());
        tvDate.setText(date);

        btnChange.setOnClickListener(this::viewRegister);
        btnSubmit.setOnClickListener(this::login);

    }

    private void login(View view){
        if (etUsername.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Isi Form Username!!!", Toast.LENGTH_SHORT).show();
        }
        else if(etPassword.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Isi Form Password!!!", Toast.LENGTH_SHORT).show();
        }
        else {
            final String username = etUsername.getText().toString().trim();
            final String password = etPassword.getText().toString().trim();

            class CheckData extends AsyncTask<Void, Void, String> {
                ProgressDialog loading;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    loading = ProgressDialog.show(LoginActivity.this,
                            "Mencari...", "Tunggu...", false, false);
                }

                @Override
                protected void onPostExecute(String result) {
                    super.onPostExecute(result);
                    loading.dismiss();
                    if (result.equals("success")) {
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        Toast.makeText(LoginActivity.this, "Selamat datang kembali " + username, Toast.LENGTH_SHORT).show();
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Username atau Password Salah!!!", Toast.LENGTH_SHORT).show();
                        etPassword.setText("");
                    }
                }

                @Override
                protected String doInBackground(Void... voids) {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("password", password);

                    ReqHandler requestHandler = new ReqHandler();
                    String response = requestHandler.sendPostRequest(APIConfig.URL_CHECK_USER, params);
                    return response;
                }

            }
            CheckData checkData = new CheckData();
            checkData.execute();
        }
    }

    private void Register(View view){
        btnSubmit.setOnClickListener(v ->{
            if (etUsername.getText().toString().trim().isEmpty()){
                Toast.makeText(this, "Isi Form Username!!!", Toast.LENGTH_SHORT).show();
            }
            else if(etPassword.getText().toString().trim().isEmpty()){
                Toast.makeText(this, "Isi Form Password!!!", Toast.LENGTH_SHORT).show();
            }
            else {
                final String username = etUsername.getText().toString().trim();
                final String password = etPassword.getText().toString().trim();
                final String date = tvDate.getText().toString().trim();

                class AddData extends AsyncTask<Void, Void, String> {
                    ProgressDialog loading;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        loading = ProgressDialog.show(LoginActivity.this,
                                "Menambahkan...", "Tunggu...", false, false);
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        loading.dismiss();
                        Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected String doInBackground(Void... voids) {
                        HashMap<String, String> params = new HashMap<>();
                        params.put(APIConfig.KEY_USERNAME, username);
                        params.put(APIConfig.KEY_PASSWORD, password);
                        params.put(APIConfig.KEY_DATE, date);

                        ReqHandler requestHandler = new ReqHandler();
                        String res = requestHandler.sendPostRequest(APIConfig.URL_ADD_USER, params);
                        return res;
                    }
                }
                AddData addData = new AddData();
                addData.execute();
            }
        });
    }
    private void viewRegister(View view){
        background = (LinearLayout) findViewById(R.id.background);
        background.setBackgroundColor(Color.parseColor("#FEFEFE"));
        btnLogin = (TextView) findViewById(R.id.btn_switch);
        btnLogin.setOnClickListener(this::viewLogin);
        tvTitle.setText("Register");
        tvTitleDesc.setText("Buat akun baru untuk masuk!");
        tvSwitch.setText("Sudah memiliki akun ? ");
        btnSubmit.setText("Register");
        btnLogin.setText("Masuk");
        etUsername.setText("");
        etPassword.setText("");
        Register(view);
    }
    private void viewLogin(View view){
        background = (LinearLayout) findViewById(R.id.background);
        background.setBackgroundColor(Color.parseColor("#f8f6f8"));
        btnRegister = (TextView) findViewById(R.id.btn_switch);
        tvTitle.setText("Login");
        btnSubmit.setText("Login");
        tvTitleDesc.setText("Gunakan akun yang sudah terdaftar untuk masuk!");
        tvSwitch.setText("Belum punya akun ? ");
        btnRegister.setText("Register sekarang");
        etUsername.setText("");
        etPassword.setText("");
        btnRegister.setOnClickListener(v->{
            viewRegister(view);
        });
        btnSubmit.setOnClickListener(v ->{
            login(view);
        });
    }


}