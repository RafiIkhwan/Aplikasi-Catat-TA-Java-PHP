package com.rafiikhwan.catatta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.rafiikhwan.catatta.Config.APIConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView listView;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        listView        =  (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener((AdapterView.OnItemClickListener) this);
        getJSON();
    }

    private void showData(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String ,String>> list = new
                ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result =
                    jsonObject.getJSONArray(APIConfig.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++){
                JSONObject jsonObject1 = result.getJSONObject(i);
                String Id           = jsonObject1.getString(APIConfig.TAG_ID);
                String NoInduk      = jsonObject1.getString(APIConfig.TAG_NO_INDUK);
                String Judul        = jsonObject1.getString(APIConfig.TAG_JUDUL);
                String Pemilik      = jsonObject1.getString(APIConfig.TAG_PEMILIK);
                String Pembimbing   = jsonObject1.getString(APIConfig.TAG_PEMBIMBING);
                String Tempat       = jsonObject1.getString(APIConfig.TAG_TEMPAT_PKL);
                String Angkatan     = jsonObject1.getString(APIConfig.TAG_ANGKATAN);
                HashMap<String,String> showData = new HashMap<>();
                showData.put(APIConfig.TAG_ID, Id);
                showData.put(APIConfig.TAG_NO_INDUK, NoInduk);
                showData.put(APIConfig.TAG_JUDUL, Judul);
                showData.put(APIConfig.TAG_PEMILIK, Pemilik);
                showData.put(APIConfig.TAG_PEMBIMBING, Pembimbing);
                showData.put(APIConfig.TAG_TEMPAT_PKL, Tempat);
                showData.put(APIConfig.TAG_ANGKATAN, Angkatan);
                list.add(showData);
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        ListAdapter adapter = new SimpleAdapter(
                ShowActivity.this, list, R.layout.list_datas,
                new String[]{
                        APIConfig.TAG_ID,
                        APIConfig.TAG_NO_INDUK,
                        APIConfig.TAG_JUDUL,
                        APIConfig.TAG_PEMILIK,
                        APIConfig.TAG_PEMBIMBING,
                        APIConfig.TAG_TEMPAT_PKL,
                        APIConfig.TAG_ANGKATAN,
                },
                new  int[]{
                        R.id.tvId,
                        R.id.tvNoInduk,
                        R.id.tvJudul,
                        R.id.tvPemilik,
                        R.id.tvPembimbing,
                        R.id.tvTempatPKL,
                        R.id.tvAngkatan,
                }
        );
        listView.setAdapter(adapter);
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String > {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ShowActivity.this, "Mengambil Data", "Mohon Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showData();
            }

            @Override
            protected String doInBackground(Void... params) {
                ReqHandler requestHandler = new ReqHandler();
                String s = requestHandler.sendGetRequest(APIConfig.URL_GET_DATAS);
                return s;
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(this, ItemActivity.class);
        HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
        String Id   = map.get(APIConfig.TAG_ID).toString();
        i.putExtra(APIConfig.KEY_ID, Id);
        startActivity(i);
        finish();
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }
}