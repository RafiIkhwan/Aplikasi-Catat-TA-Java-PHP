package com.rafiikhwan.catatta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rafiikhwan.catatta.Config.APIConfig;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class add_data_activity extends AppCompatActivity {
    private EditText etNoInduk, etJudul, etPemilik, etPembimbing, etTempat, etAngkatan;
    private String date, year;
    Button btnSubmit, btnList;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        tvTitle     = (TextView) findViewById(R.id.exTitle);
        etNoInduk   = (EditText) findViewById(R.id.et_no_induk);
        etJudul     = (EditText) findViewById(R.id.et_judul);
        etPemilik   = (EditText) findViewById(R.id.et_pemilik);
        etPembimbing= (EditText) findViewById(R.id.et_pembimbing);
        etTempat    = (EditText) findViewById(R.id.et_tempat);
        etAngkatan  = (EditText) findViewById(R.id.et_angkatan);
        btnSubmit   = (Button) findViewById(R.id.btn_submit);

        tvTitle.setText("Tambah Data");

        year = new SimpleDateFormat("yyyy", new Locale("in", "ID")).format(new Date());
        etAngkatan.setText(year);

        btnSubmit.setOnClickListener(v ->{
            Intent i =  new Intent(this, MainActivity.class);
            final String NoInduk    = etNoInduk.getText().toString().trim();
            final String Judul      = etJudul.getText().toString().trim();
            final String Pemilik    = etPemilik.getText().toString().trim();
            final String Pembimbing = etPembimbing.getText().toString().trim();
            final String Tempat     = etTempat.getText().toString().trim();
            final String Angkatan   = etAngkatan.getText().toString().trim();
            final int year          = Integer.parseInt(Angkatan);

            class AddData extends AsyncTask<Void, Void, String> {
                ProgressDialog loading;

                @Override
                protected void onPreExecute(){
                    super.onPreExecute();
                    loading = ProgressDialog.show(add_data_activity.this,
                            "Menambahkan...", "Tunggu...", false, false);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    loading.dismiss();
                    Toast.makeText(add_data_activity.this, s, Toast.LENGTH_SHORT).show();
                }

                @Override
                protected String doInBackground(Void... voids) {
                    HashMap<String, String> params = new HashMap<>();
                    params.put(APIConfig.KEY_NO_INDUK, NoInduk);
                    params.put(APIConfig.KEY_JUDUL, Judul);
                    params.put(APIConfig.KEY_PEMILIK, Pemilik);
                    params.put(APIConfig.KEY_PEMBIMBING, Pembimbing);
                    params.put(APIConfig.KEY_TEMPAT_PKL, Tempat);
                    params.put(APIConfig.KEY_ANGKATAN, Angkatan);


                    ReqHandler requestHandler = new ReqHandler();
                    String res = requestHandler.sendPostRequest(APIConfig.URL_ADD_DATA, params);
                    return res;
                }
            }
            AddData addData = new AddData();
            addData.execute();
            startActivity(i);
        });
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }
}