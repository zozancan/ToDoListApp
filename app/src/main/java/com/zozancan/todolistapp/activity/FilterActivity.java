package com.zozancan.todolistapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.zozancan.todolistapp.R;
import com.zozancan.todolistapp.model.Filter;
import com.zozancan.todolistapp.model.ToDoList;
import com.zozancan.todolistapp.toDoList.ToDoListDetail;

public class FilterActivity extends AppCompatActivity {

    Filter filter;
    ToDoList toDoList;
    EditText etFilterName;
    CheckBox cbItemCompleted, cbItemExpired;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        cbItemCompleted = findViewById(R.id.cbItemCompleted);
        cbItemExpired = findViewById(R.id.cbItemExpired);
        etFilterName = findViewById(R.id.etFilterName);


        filter = new Filter();

        Intent intent = getIntent();
        if(intent.getSerializableExtra("filter") != null) {
            filter = (Filter) intent.getSerializableExtra("filter");
        }

        toDoList = (ToDoList) intent.getSerializableExtra("toDoList");
        cbItemCompleted.setChecked(filter.getCompleted());
        etFilterName.setText(filter.getName());
        cbItemExpired.setChecked(filter.getExpired());
    }

    public void applyFilter(View view){
        filter.setName(etFilterName.getText().toString());
        filter.setCompleted(cbItemCompleted.isChecked());
        filter.setExpired(cbItemExpired.isChecked());

        Intent intent = new Intent(FilterActivity.this, ToDoListDetail.class);
        intent.putExtra("filter", filter);
        intent.putExtra("toDoList", toDoList);
        startActivity(intent);
    }

    public void backClick(View view) {
        Intent intent = new Intent(FilterActivity.this, ToDoListDetail.class);
        intent.putExtra("filter", filter);
        intent.putExtra("toDoList", toDoList);
        startActivity(intent);
    }
}
