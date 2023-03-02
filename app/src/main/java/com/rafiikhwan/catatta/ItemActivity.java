package com.rafiikhwan.catatta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rafiikhwan.catatta.Config.APIConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ItemActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView saveId, tvNoInduk, tvJudul, tvPemilik, tvPembimbing, tvTempat, tvTahun;
    private Button buttonUpdate, buttonDelete;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Intent intent = getIntent();

        id = intent.getStringExtra(APIConfig.KEY_ID);

        tvNoInduk       = (TextView) findViewById(R.id.tvNoInduk);
        tvJudul         = (TextView) findViewById(R.id.tvJudul);
        tvPemilik       = (TextView) findViewById(R.id.tvPemilik);
        tvPembimbing    = (TextView) findViewById(R.id.tvPembimbing);
        tvTempat        = (TextView) findViewById(R.id.tvTempatPKL);
        tvTahun         = (TextView) findViewById(R.id.tvAngkatan);
        saveId          = (TextView) findViewById(R.id.tvId);

        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);

        buttonUpdate.setOnClickListener(v->{
            Intent i = new Intent(this, UpdateDataActivity.class);
            i.putExtra(APIConfig.KEY_ID, id);
            startActivity(i);
            finish();
        });
        buttonDelete.setOnClickListener(this);

        saveId.setText(id);

        getData();
    }



    private void getData(){
        class GetData extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ItemActivity.this,"Mengambil Data...","Tunggu...",false,false);
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

            tvNoInduk.setText(NoInduk);
            tvJudul.setText(Judul);
            tvPemilik.setText(Pemilik);
            tvPembimbing.setText(Pembimbing);
            tvTempat.setText(Tempat);
            tvTahun.setText(Year);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


//    private void updateSiswa(){
//        final String id = saveId.getText().toString().trim();
//        final String NoInduk = tvNoInduk.getText().toString().trim();
//        final String Judul = editTextJudul.getText().toString().trim();
//
//        class UpdateSiswa extends AsyncTask<Void,Void,String>{
//            ProgressDialog loading;
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                loading = ProgressDialog.show(TampilSiswa.this,"Updating...","Wait...",false,false);
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                loading.dismiss();
//                Toast.makeText(TampilSiswa.this,s,Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            protected String doInBackground(Void... params) {
//                HashMap<String,String> hashMap = new HashMap<>();
//                hashMap.put(APIConfig.KEY_ID,id);
//                hashMap.put(APIConfig.KEY_NO_INDUK,NoInduk);
//                hashMap.put(APIConfig.KEY_JUDUL,Judul);
//
//                ReqHandler rh = new ReqHandler();
//
//                String s = rh.sendPostRequest(APIConfig.URL_UPDATE_DATA,hashMap);
//
//                return s;
//            }
//        }
//
//        UpdateSiswa ue = new UpdateSiswa();
//        ue.execute();
//    }

    private void deleteData(){
        class DeleteData extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ItemActivity.this, "Updating...", "Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(ItemActivity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                ReqHandler rh = new ReqHandler();
                String s = rh.sendGetRequestParam(APIConfig.URL_DELETE_DATA, id);
                return s;
            }
        }

        DeleteData de = new DeleteData();
        de.execute();
    }

    private void confirmDeleteSiswa(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Yakin Ingin Menghapus Data ini?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteData();
                        startActivity(new Intent(ItemActivity.this,MainActivity.class));
                    }
                });

        alertDialogBuilder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonDelete){
            confirmDeleteSiswa();
        }
    }

    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }
}