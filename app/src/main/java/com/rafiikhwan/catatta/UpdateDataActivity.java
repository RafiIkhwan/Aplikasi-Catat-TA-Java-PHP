package com.rafiikhwan.catatta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rafiikhwan.catatta.Config.APIConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class UpdateDataActivity extends AppCompatActivity {
    private EditText etNoInduk, etJudul, etPemilik, etPembimbing, etTempat, etAngkatan;
    private String date, year, id;
    Button btnSubmit, btnList;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        Intent intent = getIntent();

        id = intent.getStringExtra(APIConfig.KEY_ID);

        tvTitle     = (TextView) findViewById(R.id.exTitle);
        etNoInduk   = (EditText) findViewById(R.id.et_no_induk);
        etJudul     = (EditText) findViewById(R.id.et_judul);
        etPemilik   = (EditText) findViewById(R.id.et_pemilik);
        etPembimbing= (EditText) findViewById(R.id.et_pembimbing);
        etTempat    = (EditText) findViewById(R.id.et_tempat);
        etAngkatan  = (EditText) findViewById(R.id.et_angkatan);
        btnSubmit   = (Button) findViewById(R.id.btn_submit);

        tvTitle.setText("Edit Data");
        btnSubmit.setText("Simpan Ubah");

        getData();

        btnSubmit.setOnClickListener(v ->{
            Intent i =  new Intent(this, MainActivity.class);
            final String NoInduk    = etNoInduk.getText().toString().trim();
            final String Judul      = etJudul.getText().toString().trim();
            final String Pemilik    = etPemilik.getText().toString().trim();
            final String Pembimbing = etPembimbing.getText().toString().trim();
            final String Tempat     = etTempat.getText().toString().trim();
            final String Angkatan   = etAngkatan.getText().toString().trim();
            final int year          = Integer.parseInt(Angkatan);

            class UpdateData extends AsyncTask<Void, Void, String> {
                ProgressDialog loading;

                @Override
                protected void onPreExecute(){
                    super.onPreExecute();
                    loading = ProgressDialog.show(UpdateDataActivity.this,
                            "Mengubah Data...", "Tunggu...", false, false);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    loading.dismiss();
                    Toast.makeText(UpdateDataActivity.this, s, Toast.LENGTH_SHORT).show();
                }

                @Override
                protected String doInBackground(Void... voids) {
                    HashMap<String, String> params = new HashMap<>();
                    params.put(APIConfig.KEY_ID, id);
                    params.put(APIConfig.KEY_NO_INDUK, NoInduk);
                    params.put(APIConfig.KEY_JUDUL, Judul);
                    params.put(APIConfig.KEY_PEMILIK, Pemilik);
                    params.put(APIConfig.KEY_PEMBIMBING, Pembimbing);
                    params.put(APIConfig.KEY_TEMPAT_PKL, Tempat);
                    params.put(APIConfig.KEY_ANGKATAN, Angkatan);


                    ReqHandler requestHandler = new ReqHandler();
                    String res = requestHandler.sendPostRequest(APIConfig.URL_UPDATE_DATA, params);
                    return res;
                }
            }
            UpdateData updateData = new UpdateData();
            updateData.execute();
            startActivity(i);
        });
    }
    private void getData(){
        class GetData extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UpdateDataActivity.this,"Mengambil Data...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showData(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                ReqHandler rh = new ReqHandler();
                String s = rh.sendGetRequestParam(APIConfig.URL_GET_DATA,id);
                return s;
            }
        }
        GetData getData = new GetData();
        getData.execute();
    }

    private void showData(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(APIConfig.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String NoInduk      = c.getString(APIConfig.TAG_NO_INDUK);
            String Judul        = c.getString(APIConfig.TAG_JUDUL);
            String Pemilik      = c.getString(APIConfig.TAG_PEMILIK);
            String Pembimbing   = c.getString(APIConfig.TAG_PEMBIMBING);
            String Tempat       = c.getString(APIConfig.TAG_TEMPAT_PKL);
            String Year         = c.getString(APIConfig.TAG_ANGKATAN);

            etNoInduk.setText(NoInduk);
            etJudul.setText(Judul);
            etPemilik.setText(Pemilik);
            etPembimbing.setText(Pembimbing);
            etTempat.setText(Tempat);
            etAngkatan.setText(Year);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), ItemActivity.class);
        i.putExtra(APIConfig.KEY_ID, id);
        startActivity(i);
        finish();
    }
}
