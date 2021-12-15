package com.example.project6.SearchPage;

import static com.example.project6.Main.MainActivity.makeChargerList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.project6.Charger.ChargerDTO;
import com.example.project6.ChargerSearch.ChargerSearchRequest;
import com.example.project6.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * 충전소 검색 페이지
 */
public class ChargerSearch extends AppCompatActivity {
    private ArrayList<ChargerDTO> chargerList;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private EditText searchBar;
    private Button searchButton;
    private JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charger_search);
        searchBar = findViewById(R.id.search_bar);
        searchButton = findViewById(R.id.search_btn_inSearch);

        searchButton.setOnClickListener(new View.OnClickListener() {
            /**
             * 검색 바에 주소정보를 입력하면 서버에서 관련된 정보를 모두 return 해줌
             * 관련된 모든 정보를 jsonArray 로 바꿈
             * jsonArray 를 chargerList 로 바꾼 후
             * 리사이클러뷰 어댑터에 전달 후 뷰로 전환.
             * @param v searchButton, 검색화면에 있는 검색버튼임.
             */
            @Override
            public void onClick(View v) {
                setCycler();
                String Url = "http://192.168.0.2:8088/chargers/address";
                String Address = String.valueOf(searchBar.getText());
                chargerList=new ArrayList<>();
                SearchChargerThread searchChargerThread = new SearchChargerThread(Url, Address);
                Thread t = new Thread(searchChargerThread);
                t.start();

                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String result = searchChargerThread.getResult();

                try {
                    if(result!=null) {
                        jsonArray = new JSONArray(result);
                        chargerList= makeChargerList(jsonArray);
                    }
                    else
                    {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for(int i=0; i<chargerList.size(); i++)
                    adapter.addItem(chargerList.get(i));

                recyclerView.setAdapter(adapter);
            }
        });

    }

    /**
     * 리사이클러뷰와 어탭터 초기화.
     */
    private void setCycler() {
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerAdapter(new ArrayList<ChargerDTO>(),getApplicationContext());
    }


    /**
     * 비동기 검색 처리를 위한 class
     */
    public class SearchChargerThread extends Thread {

        private String Url;
        private String Addresss;
        private String result;

        /**
         *
         * @param Url localhost:8080/chargers/address
         * @param Address editText 인 검색 바에서 getText 한 값. 즉 찾으려고 하는 주소
         */
        public SearchChargerThread(String Url, String Address) {
            this.Url = Url;
            this.Addresss=Address;
        }

        @Override
        public void run() {
            ChargerSearchRequest chargerSearchRequest = new ChargerSearchRequest();
            result = chargerSearchRequest.searchChargerDetailRequest(Url, Addresss);
        }

        /**
         * @return 서버로 부터 받은 주소와 관련된 모든 충전소들
         */
        public String getResult() {
            return result;
        }
    }

}