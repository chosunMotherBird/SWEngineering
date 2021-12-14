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
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for(int i=0; i<chargerList.size(); i++)
                    adapter.addItem(chargerList.get(i));

                recyclerView.setAdapter(adapter);
            }
        });

    }

    private void setCycler() {
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerAdapter(new ArrayList<ChargerDTO>(),getApplicationContext());
    }


    public class SearchChargerThread extends Thread {
        private String Url;
        private String Addresss;
        private String result;


        public SearchChargerThread(String Url, String Address) {
            this.Url = Url;
            this.Addresss=Address;
        }

        @Override
        public void run() {
            ChargerSearchRequest chargerSearchRequest = new ChargerSearchRequest();
            result = chargerSearchRequest.searchChargerDeailRequest(Url, Addresss);
        }

        public String getResult() {
            return result;
        }
    }

}