package com.zozancan.todolistapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zozancan.todolistapp.model.ToDoList;
import com.zozancan.todolistapp.model.ToDoListItem;
import com.zozancan.todolistapp.model.User;
import com.zozancan.todolistapp.toDoList.ToDoListDetail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddItemActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    private EditText itemName;
    private EditText itemDescription;
    private TextView dateTimePickerText;
    private String outputDateStr;
    ToDoList toDoList;

    private ToDoListItem item;
    private Date deadline;
    User authUser;

    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;


    int day, month, year, hour, minute;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        itemName = findViewById(R.id.etItemName);
        itemDescription = findViewById(R.id.etItemDescription);
        Intent intent = getIntent();
        toDoList = (ToDoList) intent.getSerializableExtra("toDoList");

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("users");

        authUser = new User(user.getUid(),user.getEmail());

        dateTimePickerText = findViewById(R.id.tvDateTimePicker);
        dateTimePickerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddItemActivity.this, AddItemActivity.this,
                        year, month, day);
                datePickerDialog.show();

            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int i, int i1, int i2) {
        yearFinal = i;
        monthFinal = i1 + 1;
        dayFinal = i2;
        Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(AddItemActivity.this, AddItemActivity.this,
                hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();

        SimpleDateFormat inputFormat = new SimpleDateFormat("d-M-yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        String inputDateStr=dayFinal + "-" + monthFinal + "-" + yearFinal;
        deadline = null;
        try {
            deadline = inputFormat.parse(inputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        outputDateStr = outputFormat.format(deadline);

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        hourFinal = hourOfDay;
        minuteFinal = minute;

        dateTimePickerText.setText(outputDateStr + " " + hourFinal + ":" + minuteFinal);

        deadline.setHours(hourFinal);
        deadline.setMinutes(minuteFinal);
    }

    public void addItem(View view){
        item = new ToDoListItem();
        Date date = new Date();
        item.setCreatedDate(date);
        item.setDescription(itemDescription.getText().toString());
        item.setName(itemName.getText().toString());
        item.setDeadline(deadline.getTime());
        item.setStatus(false);
        myRef.child(authUser.getId()).child("lists").child(toDoList.getId()).child("items").push().setValue(item);

        Intent intent = new Intent(AddItemActivity.this, ToDoListDetail.class);
        intent.putExtra("toDoList", toDoList);
        startActivity(intent);
    }
}
