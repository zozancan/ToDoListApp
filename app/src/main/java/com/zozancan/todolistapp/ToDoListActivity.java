package com.zozancan.todolistapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zozancan.todolistapp.model.ToDoList;
import com.zozancan.todolistapp.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ToDoListActivity extends AppCompatActivity implements OnToDoListClick{

    EditText listNameText;
    ImageView ivAddList;
    User authUser;

    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

    private List<ToDoList> toDoLists;

    private ToDoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        toDoLists = new ArrayList<>();
        listNameText = findViewById(R.id.etListName);


        ivAddList = findViewById(R.id.ivAddList);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("users");

        authUser = new User(user.getUid(),user.getEmail());

        RecyclerView list = findViewById(R.id.todolist_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
        adapter = new ToDoListAdapter((OnToDoListClick) this);
        list.setAdapter(adapter);

        getToDoLists();

    }

    private void getToDoLists() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    toDoLists.clear();
                for (DataSnapshot ds : dataSnapshot.child(authUser.getId()).child("lists").getChildren()) {
                    HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                    ToDoList toDoList = new ToDoList();

                    toDoList.setName(hashMap.get("name"));
                    toDoList.setId(ds.getKey());

                    toDoLists.add(toDoList);
                }

                displayToDoLists();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void displayToDoLists() { adapter.setToDoLists(toDoLists); }

    public void addList(View view){
        ToDoList toDoList = new ToDoList(myRef.push().getKey(),listNameText.getText().toString());
        authUser.addNewList(toDoList);
        myRef.child(authUser.getId()).child("lists").push().setValue(toDoList);
    }


    @Override
    public void onToDoListClick(ToDoList toDoList) {

    }

    @Override
    public void onToDoListDeleteClick(ToDoList toDoList) {

        myRef.child(authUser.getId()).child("lists").child(toDoList.getId()).removeValue();
    }
}
