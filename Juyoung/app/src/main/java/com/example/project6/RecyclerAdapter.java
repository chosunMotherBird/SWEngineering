package com.example.project6;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project6.Main.MainActivity;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {
    private ArrayList<ChargerDTO> chargerList=new ArrayList<>();
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

    void addItemp(ChargerDTO chargerDTO){
        chargerList.add(chargerDTO);
    }





    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView chargerAddress;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            chargerAddress=itemView.findViewById(R.id.chargerAddressRecycler);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position= getAdapterPosition();
                    ChargerDTO chargerDTO=chargerList.get(position);
                    Intent intent=new Intent(v.getContext(), MainActivity.class);
                    intent.putExtra("selected", chargerDTO);
                    context.startActivity(intent);
                }
            });
        }

        void onBind(ChargerDTO chargerDTO){
            chargerAddress.setText(chargerDTO.getAddress());
        }
    }

}
