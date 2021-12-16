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
    private ArrayList<ChargerDTO> chargerList; //서버에서 받아온 충전소 정보들을 저장할 list
    private Button searchBtn; //메인화면의 충전소 검색버튼
    private Button logInBtn; // 메인화면의 로그인버튼
    private JSONArray jsonArray; // 서버에서 받아올 충전소를 위한 JSONArray
    private ChargerDTO selectedCharger; // 메인화면에서 선택하거나, 검색한 충전소를 저장할 chargerDTO 인스턴스
    private UserDTO userDTO=new UserDTO(); // 로그인 성공시 유저 정보를 저장할 userDTO 인스턴스
    private GoogleMap mMap; // 구글맵
    private TextView detailMore; // 충전소 클릭시 카드뷰가 나오는데, 오측 상단 "자세한정보" 라 표시된 TextView, 클릭시 상세정보화면으로 전환
    private boolean isLogIn=false; //지금 유저가 로그인 되어있는지 안되어 있는지 확인할 boolean, 초기값은 로그인이 되지 않은 상태.

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

        /**
         * 모든 충전소를 가져오기위한 networkTask , aynsctask를 상속받음.
         */
        NetworkTask networkTask=new NetworkTask("http://192.168.0.2:8088/chargers/all",null);
        networkTask.execute();

        /**
         * 맨 처음 맵을 로드했을 때 위치를 광주로 설정
         * 또한 구글 맵 타입은 normal로 지정
         */
        LatLng GwangJu = new LatLng(35.1595454, 126.8526012);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(GwangJu));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
        mMap.setMapType(1);

        /**
         * 다른 activity에서 보낸 Intent를 받아 처리하는 부분.
         */
        Intent intent=getIntent();
        if(intent.hasExtra("selectedCharger")){
            /**
             * 검색화면에서 보낸 ChargerDTO 를 받는 부분.
             * selectedCharger 라는 extra 를 받으면 실행 됨.
             * selectedCharger 는 검색화면에서 검색 후 선택한 결과 ChargerDTO 임.
             * ChargerDTO 를 받으면 그 충전소의 위도 경도를 받아 지도를 이동시키고 cardView를 통해 대략적인 정보를 알려줌.
             * makeCardViewText 는 선택된 충전소에 대한 대략적인 정보를 표시하기 위해 cardView 의 TextView 들을 초기화하는 함수
             */
            displayCardView();
            selectedCharger=(ChargerDTO)intent.getSerializableExtra("selectedCharger");
            double lat=selectedCharger.getLat();
            double lon=selectedCharger.getLon();
            LatLng want = new LatLng(lat, lon);
            makeCardViewText(selectedCharger);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(want));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));

        }
        else if(intent.hasExtra("userDTO")){
            /**
             * 로그인화면에서 보낸 userDTO 를 받는 부분
             * userDTO 라는 extra 를 받으면 실행 됨.
             * 이 부분은 로그인이 성공했을 경우만 작동함.
             * 로그인이 성공했으므로 로그인버튼의 Text 를 로그아웃으로 설정하고
             * isLogIn 을 true 로 설정해 현재 로그인 되어있음으로 설정.
             * 또한 현재 어플리케이션에서 userDTO 를 이 유저로 설정.
             */
            userDTO=(UserDTO)intent.getSerializableExtra("userDTO");
            logInBtn.setText("로그아웃");
            isLogIn=true;
        }
        else{
            /**
             * 더 이상 다른 곳에서 받는 extra 는 없음
             * 만약 다른 것을 받으면 에러 로그 표시.
             */
            Log.e("intent extra error", "has no extra in intent");
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            /**
             * 마커 클릭 리스너
             * 마커에는 Tag 값이 설정되어있는데 Tag 값은 데이터베이스 상의 충전소들의 pk 값들임.
             * 또한 이 Tag 값은 RestAPI 에서 사용됨
             * 예를 들면 localhost:8080/chargers/1 을 주소에 적으면
             * 1번 Tag 를 가진 충전소의 정보를 얻을 수 있음.
             * Thread 를 이용해 network 작업하고 받아온 결과를
             * selectedCharger 로 설정 및 카드뷰를 보여줌.
             * @param marker
             * @return
             */
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
                selectedCharger= makeSelectedChargerDTO(result);
                makeCardViewText(selectedCharger);
                displayCardView();
                return false;
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            /**
             * 지도 아무곳이나 클릭하면 cardView 가 사라지도록 하는 Listener
             * @param latLng
             */
            @Override
            public void onMapClick(LatLng latLng) {
                goneCardView();

            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 검색 버튼 리스너
                 * 충전소 검색화면으로 이동
                 */
                Intent intent=new Intent(MainActivity.this, ChargerSearch.class);
                startActivity(intent);
            }
        });

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * 로그인 버튼 리스너
             * 만약 로그인 되지 않았다면 로그인화면으로 이동
             * 다르게 로그인 되어 있다면 버튼의 Text 를 "로그인"으로 바꿈.
             * 또한 현재 어플리케이션의 userDTO 를 초기화.
             */
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
            /**
             * cardView 에 있는 "상세한정보" Text 를 누를시 발생
             * 선택된 충전소의 정보를 DatailActivity 로 넘겨주고 화면 전환.
             */
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("selectedCharger",selectedCharger);
                startActivity(intent);
            }
        });

    }


    /**
     *이 함수는 JSONArray 를 ArrayList 로 변환하는 함수
     * @param jsonArray 서버에서 받아올 모든 충전소의 Json
     * @return ArrayList<ChargerDTO> 리턴</ChargerDTO>
     *
     */
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

    /**
     * 맵위에 마커 표시하는 함수
     * Tag 값은 데이터베이스의 충전소들의 pk
     * @param chargerList 서버에서 받은 JSONArray 를 list 로 바꾼 chargerList
     */
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

    /**
     * 마커클릭시 서버에서 받아온 하나의 충전소를 ChargerDTO 로 바꾸는 함수.
     * @param result 마커 클릭시 서버에서 해당 충전소 하나의 정보
     * @return chargerDTO
     */
    public ChargerDTO makeSelectedChargerDTO(String result){
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

    /**
     * 충전소의 대략적인 정보를 나타낼 CardView 의 TextVIew 들을 설정하는 함수.
     * @param selectedCharger 선택된 충전소
     */
    public void makeCardViewText(ChargerDTO selectedCharger){
        TextView chargerCity = findViewById(R.id.city);
        TextView closeDates = findViewById(R.id.close_dates);
        TextView chargerAddress = findViewById(R.id.address);
        TextView chargerLocation = findViewById(R.id.location);
        chargerCity.setText(selectedCharger.getCity());
        closeDates.setText(selectedCharger.getClosedDates());
        chargerAddress.setText(selectedCharger.getAddress());
        chargerLocation.setText(selectedCharger.getChargerLocation());
    }

    /**
     * 카드뷰 표시하기
     */
    public void displayCardView(){

        LinearLayout cardView=findViewById(R.id.cardView);
        cardView.setVisibility(View.VISIBLE);
    }

    /**
     * 카드뷰 없애기
     */
    public void goneCardView(){

        LinearLayout cardView=findViewById(R.id.cardView);
        cardView.setVisibility(View.GONE);
    }


    /**
     * 네트워크에서 Thread 를 이용하여 충전소 위치를 받아올 class
     */
    public class SearchChargerDetailThread extends Thread {
        /**
         * id 는 tag 값 즉 해당 충전소의 데이터베이스 안의 pk 값
         * result 는 해당 충전소의 검색 결과
         */
        private Object id;
        private String result;

        /**
         * @param id tag 값
         */
        public SearchChargerDetailThread(Object id) {
            this.id = id;
        }

        /**
         * Thread 를 이용해 해당 id 를 가진 충전소 검색
         */
        @Override
        public void run() {
            result = new ChargerSearchRequest().oneOrMoreChargerSearchRequest("http://192.168.0.2:8088/chargers/" + id, null);
        }

        public String getResult() {
            return this.result;
        }
    }


    /**
     * 모든 충전소에 대한 정보를 가져올 NetworkTask
     */
    public class NetworkTask extends AsyncTask<Void, Void, String> {
        private String url;
        private ContentValues values;

        /**
         * 생성자
         * @param url url 은 사실상 localhost:8080/charger/all 임
         * @param values null
         */
        public NetworkTask(String url, ContentValues values) {
            this.url = url;
            this.values = values;
        }

        /**
         * AsyncTask 실행 전 준비단계 chargerList 초기화
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            chargerList = new ArrayList<>();

        }

        /**
         * 백그라운드에서 실행되는 함수
         * 모든 충전소 정보를 가져옴
         * @param params void
         * @return 모든 충전소에 대한 정보
         */
        public String doInBackground(Void... params) {
            String result;
            ChargerSearchRequest chargerSearchRequest =new ChargerSearchRequest();
            result = chargerSearchRequest.oneOrMoreChargerSearchRequest(url,values);
            return result;
        }

        /**
         * chargerList 만듦.
         * 또한 맵 위에 마커도 표시
         * @param s doInBackground 가 return 한 result
         */
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
