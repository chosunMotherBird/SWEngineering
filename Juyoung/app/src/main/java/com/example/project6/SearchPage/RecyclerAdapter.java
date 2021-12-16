package com.example.project6.SearchPage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project6.Charger.ChargerDTO;
import com.example.project6.Main.MainActivity;
import com.example.project6.R;

import java.util.ArrayList;

/**
 * 리사이클러뷰에서 사용될 어댑터에 관한 class
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {
    private ArrayList<ChargerDTO> chargerList;
    private Context context;
    public RecyclerAdapter(ArrayList<ChargerDTO> chargerList, Context context) {
        this.chargerList = chargerList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_view,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ItemViewHolder holder, int position) {
        holder.onBind(chargerList.get(position));
    }

    @Override
    public int getItemCount() {
        return chargerList.size();
    }

    void addItem(ChargerDTO chargerDTO){
        chargerList.add(chargerDTO);
    }





    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView chargerAddress;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            chargerAddress=itemView.findViewById(R.id.chargerAddressRecycler);

            /**
             * 아이템 뷰 리스너로서
             * 아이템 뷰가 선택되면 그 아이템 즉 충전소 정보를 MainActivity 에 전달.
             * 선택된 뷰의 위치로 그 충전소에 대한 정보를 chargerDTO 에 저장
             * Intent 에 담아 선택된 충전소인 정보를  "selectedCharger" 로 전달
             */
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position= getAdapterPosition();
                    ChargerDTO chargerDTO=chargerList.get(position);
                    Intent intent=new Intent();
                    intent.putExtra("selectedCharger", chargerDTO);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ((Activity)context).setResult(Activity.RESULT_OK, intent);
                    ((Activity) context).finish();
                }
            });
        }

        /**
         * recycler_item_view_xml 을 보면 검색 결과로 보여주는 결과는 오직 주소임.
         * 추후에 표시가 필요하다면 추가 가능.
         * @param chargerDTO chargerDTO 의 주소만들 활용하여 item 에 사용 할 것임.
         */
        void onBind(ChargerDTO chargerDTO){
            chargerAddress.setText(chargerDTO.getAddress());
        }
    }

}
