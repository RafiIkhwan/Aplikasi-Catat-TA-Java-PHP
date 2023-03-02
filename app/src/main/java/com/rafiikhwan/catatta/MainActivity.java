package com.rafiikhwan.catatta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rafiikhwan.catatta.Config.APIConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private EditText etSearch;
    private TextView tvDate;
    private String date;
    Button btnList;
    private FloatingActionButton btnAdd;
    private ImageView btnSearch;
    private long backPressedTime;
    private Toast backToast;
    private ListView listView;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvDate      = (TextView) findViewById(R.id.tvHari);
        etSearch     = (EditText) findViewById(R.id.et_search);
        btnSearch   = (ImageView) findViewById(R.id.btn_search);
        btnAdd      = (FloatingActionButton) findViewById(R.id.add);
        btnList   = (Button) findViewById(R.id.btn_list);

        date = new SimpleDateFormat("HH", new Locale("in", "ID")).format(new Date());
        int dateint = Integer.parseInt(date);

        if (dateint <=  10){
            tvDate.setText("Selamat Pagi!");
        }else if (dateint <=  14){
            tvDate.setText("Selamat Siang!");
        }else if (dateint <=  18){
            tvDate.setText("Selamat Sore!");
        }else if (dateint <=  24){
            tvDate.setText("Selamat Malam");
        }

        listView        =  (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener((AdapterView.OnItemClickListener) this);

        Intent bundle    = getIntent();
        Bundle extras = bundle.getExtras();
        if (extras == null){
            getJSON(null);
        }else {
            getJSON(extras.getString("search"));
            etSearch.setText(extras.getString("search"));
        }

        btnSearch.setOnClickListener(v->{
            Intent search = new Intent(this, MainActivity.class);
            search.putExtra("search", etSearch.getText().toString());
            startActivity(search);
            finish();
        });

        btnAdd.setOnClickListener(v ->{
            Intent i = new Intent(this, add_data_activity.class);
            startActivity(i);
            finish();
        });

        btnList.setOnClickListener(v ->{
            Intent i = new Intent(this, ShowActivity.class);
            startActivity(i);
            finish();
        });
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
                MainActivity.this, list, R.layout.list_data,
                new String[]{
                        APIConfig.TAG_ID,
                        APIConfig.TAG_NO_INDUK,
                        APIConfig.TAG_JUDUL,
                        APIConfig.TAG_PEMILIK,
                },
                new  int[]{
                        R.id.tvId,
                        R.id.tvNoInduk,
                        R.id.tvJudul,
                        R.id.tvPemilik,
                }
        );
        listView.setAdapter(adapter);
    }

    private void getJSON(String search) {
        class GetJSON extends AsyncTask<Void, Void, String > {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Mengambil Data", "Mohon Tunggu...", false, false);
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
                String s;
                if (search == null){
                    s = requestHandler.sendGetRequest(APIConfig.URL_GET_DATAS);
                }else {
                    s = requestHandler.sendGetRequestParam(APIConfig.URL_SEARCH_DATA, search);
                }

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
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}