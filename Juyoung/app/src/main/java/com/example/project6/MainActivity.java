package com.example.project6;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
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
    private JSONArray jsonArray;
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

        String url="http://192.168.0.2:8088/chargers/all";
        NetworkTask networkTask=new NetworkTask(url,null);
        networkTask.execute();

        LatLng SEOUL = new LatLng(37.56d, 126.97d);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
        mMap.setMapType(1);
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
                TextView chargerAddress = findViewById(R.id.chargerAddress);
                TextView chargerName = findViewById(R.id.chargerName);
                TextView chargerLocation = findViewById(R.id.chargerLocation);
                TextView parkingFee = findViewById(R.id.parkingFee);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    chargerAddress.setText(jsonObject.getString("address"));
                    chargerName.setText(jsonObject.getString("chargerName"));
                    chargerLocation.setText(jsonObject.getString("chargerLocation"));
                    parkingFee.setText(jsonObject.getString("parkingFee"));
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
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
    }


    public class SearchChargerDetail extends Thread {
        private Object id;
        private String result;

        public SearchChargerDetail(Object id) {
            this.id = id;
        }

        @Override
        public void run() {
            this.result = new RequestHttpConnection().request("http://192.168.0.2:8088/chargers/" + id, null);
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
                makeChargerList(jsonArray);
                setMarkerOnMap(chargerList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
}
