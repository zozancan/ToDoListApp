package com.zozancan.todolistapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zozancan.todolistapp.model.ToDoList;

import java.util.ArrayList;
import java.util.List;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.MyViewHolder>  {

    private List<ToDoList> toDoLists = new ArrayList<>();

    private OnToDoListClick toDoListClickListener;

    public ToDoListAdapter(OnToDoListClick toDoListClickListener) {
        this.toDoListClickListener = toDoListClickListener;
    }

    public void setToDoLists(List<ToDoList> toDoLists){
        this.toDoLists = toDoLists;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_to_do_list, parent, false);

        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ToDoList toDoList = toDoLists.get(position);
        holder.toDoListNameTextView.setText(toDoList.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDoListClickListener.onToDoListClick(toDoList);
            }
        });

    }

    @Override
    public int getItemCount() {
        return toDoLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView toDoListNameTextView;

        public MyViewHolder(View view) {
            super(view);
            toDoListNameTextView = view.findViewById(R.id.tvListId);

        }
    }
}
