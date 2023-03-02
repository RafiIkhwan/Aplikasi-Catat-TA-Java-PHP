//package com.rafiikhwan.catatta;
//
//import android.app.AlertDialog;
//import android.app.ProgressDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.rafiikhwan.catatta.Config.APIConfig;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.HashMap;
//
//public class TampilSiswa extends AppCompatActivity implements View.OnClickListener {
//    private EditText editTextJudul;
//    private TextView saveId, tvNoInduk ;
//    private Button buttonUpdate, buttonDelete;
//    private String id;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_action);
//
//        Intent intent = getIntent();
//
//        id = intent.getStringExtra(APIConfig.KEY_ID);
//
//        editTextJudul   = (EditText) findViewById(R.id.etJudul);
//        tvNoInduk       = (TextView) findViewById(R.id.tvNoInduk);
//        saveId          = (TextView) findViewById(R.id.tvId);
//
//        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
//        buttonDelete = (Button) findViewById(R.id.buttonDelete);
//
//        buttonUpdate.setOnClickListener(this);
//        buttonDelete.setOnClickListener(this);
//
//        saveId.setText(id);
//
//        getSiswa();
//    }
//
//    private void getSiswa(){
//        class GetSiswa extends AsyncTask<Void,Void,String> {
//            ProgressDialog loading;
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                loading = ProgressDialog.show(TampilSiswa.this,"Mengambil Data...","Tunggu...",false,false);
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                loading.dismiss();
//                showSiswa(s);
//            }
//
//            @Override
//            protected String doInBackground(Void... params) {
//                ReqHandler rh = new ReqHandler();
//                String s = rh.sendGetRequestParam(APIConfig.URL_GET_DATA,id);
//                return s;
//            }
//        }
//        GetSiswa ge = new GetSiswa();
//        ge.execute();
//    }
//
//    private void showSiswa(String json){
//        try {
//            JSONObject jsonObject = new JSONObject(json);
//            JSONArray result = jsonObject.getJSONArray(APIConfig.TAG_JSON_ARRAY);
//            JSONObject c = result.getJSONObject(0);
//            String NoInduk = c.getString(APIConfig.TAG_NO_INDUK);
//            String Judul = c.getString(APIConfig.TAG_JUDUL);
//
//            tvNoInduk.setText(NoInduk);
//            editTextJudul.setText(Judul);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//
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
//
//    private void deleteSiswa(){
//        class DeleteSiswa extends AsyncTask<Void,Void,String> {
//            ProgressDialog loading;
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                loading = ProgressDialog.show(TampilSiswa.this, "Updating...", "Tunggu...", false, false);
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                loading.dismiss();
//                Toast.makeText(TampilSiswa.this, s, Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            protected String doInBackground(Void... params) {
//                ReqHandler rh = new ReqHandler();
//                String s = rh.sendGetRequestParam(APIConfig.URL_DELETE_DATA, id);
//                return s;
//            }
//        }
//
//        DeleteSiswa de = new DeleteSiswa();
//        de.execute();
//    }
//
//    private void confirmDeleteSiswa(){
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//        alertDialogBuilder.setMessage("Apakah Kamu Yakin Ingin Menghapus Data ini?");
//
//        alertDialogBuilder.setPositiveButton("Ya",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface arg0, int arg1) {
//                        deleteSiswa();
//                        startActivity(new Intent(TampilSiswa.this,MainActivity.class));
//                    }
//                });
//
//        alertDialogBuilder.setNegativeButton("Tidak",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface arg0, int arg1) {
//
//                    }
//                });
//
//        AlertDialog alertDialog = alertDialogBuilder.create();
//        alertDialog.show();
//    }
//
//    @Override
//    public void onClick(View v) {
//        if(v == buttonUpdate){
//            updateSiswa();
//        }
//
//        if(v == buttonDelete){
//            confirmDeleteSiswa();
//        }
//    }
//
//    public void onBackPressed() {
//        Intent i = new Intent(getApplicationContext(), MainActivity.class);
//        startActivity(i);
//        finish();
//    }
//}
