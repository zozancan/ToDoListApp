package com.zozancan.todolistapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zozancan.todolistapp.model.ToDoList;
import com.zozancan.todolistapp.model.User;

import java.util.List;

public class ToDoListActivity extends AppCompatActivity {

    EditText listNameText;
    ImageView ivAddList;
    User authUser;
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Girdi mi");
        setContentView(R.layout.activity_to_do_list);

        listNameText = findViewById(R.id.etListName);

        ivAddList = findViewById(R.id.ivAddList);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("users");

        authUser = new User(user.getUid(),user.getEmail());

    }

    public void addList(View view){
        ToDoList toDoList = new ToDoList(myRef.push().getKey(),listNameText.getText().toString());
        authUser.addNewList(toDoList);
        myRef.child(authUser.getId()).child("lists").setValue(authUser.getToDoLists());
    }
}
