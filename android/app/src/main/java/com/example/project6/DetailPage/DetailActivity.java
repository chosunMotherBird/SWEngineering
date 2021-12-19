package com.example.project6.DetailPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.project6.Charger.ChargerDTO;
import com.example.project6.R;

/**
 * 상세정보화면
 */
public class DetailActivity extends AppCompatActivity {

    ChargerDTO selectedChargerDTO;
    private TextView chargerName;
    private TextView chargerLocation;
    private TextView closeDates;
    private TextView fastChargerType;
    private TextView slowNum;
    private TextView fastNum;
    private TextView parkingFee;
    private TextView chargerAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        chargerName=findViewById(R.id.chargerName_inDetail);
        chargerLocation=findViewById(R.id.chargerLocation_inDetail);
        closeDates=findViewById(R.id.closeDates_inDetail);
        fastChargerType=findViewById(R.id.fastChargerType_inDetail);
        slowNum=findViewById(R.id.slowNum_inDetail);
        fastNum=findViewById(R.id.fastNum_inDetail);
        parkingFee=findViewById(R.id.parkingFee_inDetail);
        chargerAddress=findViewById(R.id.chargerAddress_inDetail);


        Intent intent=getIntent();
        /**
         * 인텐트에서 selectedCharger extra 를 받으면 실행하고
         * 화면에 상세정보 출력
         */
        if(intent.hasExtra("selectedCharger")){
            selectedChargerDTO=(ChargerDTO) intent.getSerializableExtra("selectedCharger");
            makeTextView(selectedChargerDTO);
        }
    }

    /**
     * TextView 들을 설정하는 함수
     * @param chargerDTO 선택된 충전소
     */
    public void makeTextView(ChargerDTO chargerDTO){
        chargerName.setText(chargerDTO.getChargerName());
        chargerLocation.setText(chargerDTO.getChargerLocation());
        closeDates.setText(chargerDTO.getClosedDates());
        fastChargerType.setText(chargerDTO.getFastChargeType());
        slowNum.setText(String.valueOf(chargerDTO.getSlowNum()));
        fastNum.setText(String.valueOf(chargerDTO.getFastNum()));
        parkingFee.setText(chargerDTO.getParkingFee());
        chargerAddress.setText(chargerDTO.getAddress());
    }
}