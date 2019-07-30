package com.zozancan.todolistapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.zozancan.todolistapp.model.ToDoListItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder>{

    private List<ToDoListItem> toDoListItems = new ArrayList<>();

    private OnItemClick itemClickListener;

    public ItemAdapter(OnItemClick itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setItemList(List<ToDoListItem> toDoListItems){
        this.toDoListItems = toDoListItems;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rv, parent, false);

        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(ItemAdapter.MyViewHolder holder, int position) {

        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        final ToDoListItem toDoListItem = toDoListItems.get(position);
        holder.tvItemName.setText(toDoListItem.getName());
        holder.cbItemStatus.setChecked(toDoListItem.getStatus());
        holder.tvItemDeadline.setText(outputFormat.format(toDoListItem.getDeadline()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(toDoListItem);
            }
        });

        holder.ivDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemDeleteClick(toDoListItem);
            }
        });

        holder.cbItemStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemStatusClick(toDoListItem);
            }
        });

    }

    @Override
    public int getItemCount() {
        return toDoListItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvItemName;
        private TextView tvItemDeadline;
        private CheckBox cbItemStatus;
        private ImageView ivDeleteItem;

        public MyViewHolder(View view) {
            super(view);
            tvItemName = view.findViewById(R.id.tvItemName);
            ivDeleteItem = view.findViewById(R.id.ivDeleteItem);
            tvItemDeadline = view.findViewById(R.id.tvItemDeadline);
            cbItemStatus = view.findViewById(R.id.cbItemStatus);

        }
    }
}
