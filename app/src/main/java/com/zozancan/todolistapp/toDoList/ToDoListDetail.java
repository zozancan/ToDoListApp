package com.zozancan.todolistapp.toDoList;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zozancan.todolistapp.AddItemActivity;
import com.zozancan.todolistapp.ItemAdapter;
import com.zozancan.todolistapp.OnItemClick;
import com.zozancan.todolistapp.R;
import com.zozancan.todolistapp.model.ToDoList;
import com.zozancan.todolistapp.model.ToDoListItem;
import com.zozancan.todolistapp.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ToDoListDetail extends AppCompatActivity implements OnItemClick{

    private TextView tvListName;
    private ImageView sortImageView;

    ToDoList toDoList;
    User authUser;

    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

    private List<ToDoListItem> toDoListItems;

    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list_detail);

        toDoListItems = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("users");

        authUser = new User(user.getUid(),user.getEmail());

        tvListName = findViewById(R.id.tvListName);
        sortImageView = findViewById(R.id.ivSort);
        sortImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(ToDoListDetail.this, sortImageView);
                popup.getMenuInflater()
                        .inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(ToDoListDetail
                                .this,
                                "You Clicked : " + item.getTitle(),
                                Toast.LENGTH_SHORT
                        ).show();
                        return true;
                    }
        });

                popup.show();
    }
        });

        RecyclerView list = findViewById(R.id.todolist_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
        adapter = new ItemAdapter((OnItemClick) this);
        list.setAdapter(adapter);

        final ImageView ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ToDoListDetail.this, ToDoListActivity.class);
                startActivity(intent);
            }
        });

        final ImageView ivAdd = findViewById(R.id.ivAdd);
        ivAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ToDoListDetail.this, AddItemActivity.class);
                intent.putExtra("toDoList", toDoList);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        toDoList = (ToDoList) intent.getSerializableExtra("toDoList");

        tvListName.setText(toDoList.getName());

        getItemLists();
    }

    private void getItemLists() {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                toDoListItems.clear();
                for (DataSnapshot ds : dataSnapshot.child(authUser.getId()).child("lists").child(toDoList.getId()).child("items").getChildren()) {
                    HashMap<Object, Object> hashMap = (HashMap<Object, Object>) ds.getValue();
                    ToDoListItem toDoListItem = new ToDoListItem();

                    toDoListItem.setName((String) hashMap.get("name"));
                    toDoListItem.setDescription((String) hashMap.get("description"));
                    toDoListItem.setDeadline(new Date());
                    toDoListItem.setStatus((Boolean) hashMap.get("status"));
                    toDoListItem.setId(ds.getKey());

                    toDoListItems.add(toDoListItem);
                }

                displayItemLists();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void displayItemLists() { adapter.setItemList(toDoListItems); }

    @Override
    public void onItemClick(ToDoListItem ToDoListItem) {

    }

    @Override
    public void onItemDeleteClick(ToDoListItem ToDoListItem) {

    }
}
