package com.example.project6.Main;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project6.Charger.ChargerDTO;
import com.example.project6.ChargerSearch.ChargerSearchRequest;
import com.example.project6.DetailPage.DetailActivity;
import com.example.project6.SearchPage.ChargerSearch;
import com.example.project6.R;
import com.example.project6.User.UserDTO;
import com.example.project6.LogInPage.LoginActivity;
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
    private Button searchBtn;
    private Button logInBtn;
    private JSONArray jsonArray;
    private ChargerDTO selectedCharger;
    private UserDTO userDTO=new UserDTO();
    private GoogleMap mMap;
    private TextView detailMore;
    private boolean isLogIn=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        searchBtn =findViewById(R.id.search_btn_inMain);
        logInBtn =findViewById(R.id.login_btn_inMain);
        detailMore=findViewById(R.id.detailMore);

        NetworkTask networkTask=new NetworkTask("http://192.168.0.2:8088/chargers/all",null);
        networkTask.execute();

        LatLng GwangJu = new LatLng(35.1595454, 126.8526012);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(GwangJu));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
        mMap.setMapType(1);

        Intent intent=getIntent();
        if(intent.hasExtra("selected")){
            LinearLayout cardView = findViewById(R.id.cardView);
            selectedCharger=(ChargerDTO)intent.getSerializableExtra("selected");
            double lat=selectedCharger.getLat();
            double lon=selectedCharger.getLon();
            LatLng want = new LatLng(lat, lon);
            makeCardviewText(selectedCharger);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(want));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
            cardView.setVisibility(View.VISIBLE);
        }
        else if(intent.hasExtra("userDTO")){
            userDTO=(UserDTO)intent.getSerializableExtra("userDTO");
            logInBtn.setText("로그아웃");
            isLogIn=true;
        }
        else{
            Log.e("intent extra error", "has no extra in intent");
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                SearchChargerDetailThread searchChargerDetailThread = new SearchChargerDetailThread(marker.getTag());
                Thread t = new Thread(searchChargerDetailThread);
                t.start();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String result = searchChargerDetailThread.getResult();
                selectedCharger=makeSelectedCharerDTO(result);
                makeCardviewText(selectedCharger);
                displayCardView();
                return false;
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                goneCardView();

            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, ChargerSearch.class);
                startActivity(intent);
            }
        });

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isLogIn) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else{
                    isLogIn=false;
                    userDTO=new UserDTO();
                    logInBtn.setText("로그인");
                }
            }
        });

        detailMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("selectedCharger",selectedCharger);
                startActivity(intent);
            }
        });

    }


    public static ArrayList<ChargerDTO> makeChargerList(JSONArray jsonArray) {
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
        LinearLayout cardView=findViewById(R.id.cardView);
        cardView.setVisibility(View.VISIBLE);
    }

    public void goneCardView(){
        LinearLayout cardView=findViewById(R.id.cardView);
        cardView.setVisibility(View.GONE);
    }



    public class SearchChargerDetailThread extends Thread {
        private Object id;
        private String result;

        public SearchChargerDetailThread(Object id) {
            this.id = id;
        }

        @Override
        public void run() {
            result = new ChargerSearchRequest().allChargerRequest("http://192.168.0.2:8088/chargers/" + id, null);
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
            ChargerSearchRequest chargerSearchRequest =new ChargerSearchRequest();
            result = chargerSearchRequest.allChargerRequest(url,values);
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
