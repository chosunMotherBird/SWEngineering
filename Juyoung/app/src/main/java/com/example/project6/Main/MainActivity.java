package com.example.project6.Main;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project6.ChargerDTO;
import com.example.project6.ChargerSearch;
import com.example.project6.R;
import com.example.project6.RequestHttpConnection;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ArrayList<ChargerDTO> chargerList;
    private Button searchButton;
    private JSONArray jsonArray;
    private ChargerDTO selectedCharger;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        searchButton=findViewById(R.id.search);
        String url="http://192.168.0.2:8088/chargers/all";
        NetworkTask networkTask=new NetworkTask(url,null);
        networkTask.execute();

        LatLng SEOUL = new LatLng(37.56d, 126.97d);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
        mMap.setMapType(1);

        Intent intent=getIntent();
        if(intent.hasExtra("selected")){
            LinearLayout cardView = findViewById(R.id.cardView);
            ChargerDTO selectedCharger=(ChargerDTO)intent.getSerializableExtra("selected");
            double lat=selectedCharger.getLat();
            double lon=selectedCharger.getLon();
            LatLng want = new LatLng(lat, lon);
            makeCardviewText(selectedCharger);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(want));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
            cardView.setVisibility(View.VISIBLE);
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                LinearLayout cardView = findViewById(R.id.cardView);
                SearchChargerDetail searchChargerDetail = new SearchChargerDetail(marker.getTag());
                Thread t = new Thread(searchChargerDetail);
                t.start();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String result = searchChargerDetail.getResult();
                selectedCharger=makeSelectedCharerDTO(result);
                makeCardviewText(selectedCharger);
                cardView.setVisibility(View.VISIBLE);
                return false;
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                LinearLayout cardView=findViewById(R.id.cardView);
                cardView.setVisibility(View.GONE);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, ChargerSearch.class);
                startActivity(intent);
            }
        });

    }




    public ArrayList<ChargerDTO> makeChargerList(JSONArray jsonArray) {
        ArrayList<ChargerDTO> list=new ArrayList<>();
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
                list.add(new ChargerDTO(id, chargerName, chargerLocation, city, closedDates, fastChargeType, slowNum, fastNum, parkingFee, lat, lon, address));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return list;
    }

    public void setMarkerOnMap(ArrayList<ChargerDTO> chargerList) {
        for (int i = 0; i < chargerList.size(); i++) {
            LatLng latLng=new LatLng(chargerList.get(i).getLat(), chargerList.get(i).getLon());
            MarkerOptions markerOptions=new MarkerOptions()
                    .position(latLng)
                    .title(chargerList.get(i).getChargerName());
            mMap
                    .addMarker(markerOptions)
                    .setTag(chargerList.get(i).getId());
        }
    }
    public ChargerDTO makeSelectedCharerDTO(String result){
        ChargerDTO selected=null;
        try {
            JSONObject jsonObject = new JSONObject(result);
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
            selected=new ChargerDTO(id, chargerName, chargerLocation, city, closedDates, fastChargeType, slowNum, fastNum, parkingFee, lat, lon, address);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return selected;
    }

    public void makeCardviewText(ChargerDTO selectedCharger){
        TextView chargerCity = findViewById(R.id.city);
        TextView closeDates = findViewById(R.id.close_dates);
        TextView chargerAddress = findViewById(R.id.address);
        TextView chargerLocation = findViewById(R.id.location);
        chargerCity.setText(selectedCharger.getCity());
        closeDates.setText(selectedCharger.getClosedDates());
        chargerAddress.setText(selectedCharger.getAddress());
        chargerLocation.setText(selectedCharger.getChargerLocation());
    }

    public void displayCardView(){

    }
    public class SearchChargerDetail extends Thread {
        private Object id;
        private String result;

        public SearchChargerDetail(Object id) {
            this.id = id;
        }

        @Override
        public void run() {
            result = new RequestHttpConnection().request("http://192.168.0.2:8088/chargers/" + id, null);
        }

        public String getResult() {
            return this.result;
        }
    }


    public class NetworkTask extends AsyncTask<Void, Void, String> {
        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {
            this.url = url;
            this.values = values;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            chargerList = new ArrayList<>();

        }

        public String doInBackground(Void... params) {
            String result;
            RequestHttpConnection requestHttpConnection=new RequestHttpConnection();
            result = requestHttpConnection.request(url,values);
            return result;
        }

        public void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                jsonArray= new JSONArray(s);
                chargerList=makeChargerList(jsonArray);
                setMarkerOnMap(chargerList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
