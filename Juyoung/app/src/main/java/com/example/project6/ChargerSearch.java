package com.example.project6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChargerSearch extends AppCompatActivity {
    private ArrayList<ChargerDTO> chargerList;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private EditText searchBar;
    private Button searchButtom;
    private JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charger_search);
        searchBar = findViewById(R.id.search_bar);
        searchButtom = findViewById(R.id.search_button);
        searchButtom.setOnClickListener(new customButtonEventListener());

    }

    private void setCycler() {
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerAdapter(new ArrayList<ChargerDTO>(),getApplicationContext());
    }

    class customButtonEventListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            setCycler();
            String Url = "http://192.168.0.2:8088/chargers/address";
            String Address = String.valueOf(searchBar.getText());
            chargerList=new ArrayList<>();
            SearchCharger searchCharger = new SearchCharger(Url, Address);
            Thread t = new Thread(searchCharger);
            t.start();

            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String result = searchCharger.getResult();

            try {
                if(result!=null) {
                    jsonArray = new JSONArray(result);
                    makeChargerList(jsonArray);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for(int i=0; i<chargerList.size(); i++)
                adapter.addItemp(chargerList.get(i));

            recyclerView.setAdapter(adapter);
        }
    }

    public class SearchCharger extends Thread {
        private String Url;
        private String Addresss;
        private String result;


        public SearchCharger(String Url, String Address) {
            this.Url = Url;
            this.Addresss=Address;
        }

        @Override
        public void run() {
            RequestHttpConnection requestHttpConnection = new RequestHttpConnection();
            result = requestHttpConnection.requestSearch(Url, Addresss);
        }

        public String getResult() {
            return result;
        }
    }

    public void makeChargerList(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = Integer.parseInt(jsonObject.getString("id"));
                String chargerName = jsonObject.getString("chargerName");
                String chargerLocation = jsonObject.getString("chargerLocation");
                String city = jsonObject.getString("city");
                String closedDates = jsonObject.getString("closedDates");
                String fastChargeType = jsonObject.getString("fastChargeType");
                Integer slowNum = Integer.parseInt(jsonObject.getString("slowNum"));
                Integer fastNum = Integer.parseInt(jsonObject.getString("fastNum"));
                String parkingFee = jsonObject.getString("parkingFee");
                Double lat = Double.parseDouble(jsonObject.getString("lat"));
                Double lon = Double.parseDouble(jsonObject.getString("lon"));
                String address = jsonObject.getString("address");
                chargerList.add(new ChargerDTO(id, chargerName, chargerLocation, city, closedDates, fastChargeType, slowNum, fastNum, parkingFee, lat, lon, address));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}