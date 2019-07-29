package com.zozancan.todolistapp.toDoList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.zozancan.todolistapp.AddItemActivity;
import com.zozancan.todolistapp.R;
import com.zozancan.todolistapp.model.ToDoList;

public class ToDoListDetail extends AppCompatActivity {

    private TextView tvListName;
    private ImageView sortImageView;

    ToDoList toDoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list_detail);

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
    }
}
