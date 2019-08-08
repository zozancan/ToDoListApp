package com.zozancan.todolistapp.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.zozancan.todolistapp.R;
import com.zozancan.todolistapp.toDoList.ToDoListActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText mailText, passwordText;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mailText = findViewById(R.id.etRegisterMail);
        passwordText = findViewById(R.id.etRegisterPassword);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(RegisterActivity.this, ToDoListActivity.class);
            startActivity(intent);
        }

        final ImageView ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void register(View view) {

       if (mailText.getText().toString().length() > 0 && passwordText.getText().toString().length() > 0) {
            mAuth.createUserWithEmailAndPassword(mailText.getText().toString(), passwordText.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                Intent intent = new Intent(RegisterActivity.this, ToDoListActivity.class);
                                startActivity(intent);

                            } else {

                                Toast.makeText(RegisterActivity.this, "The email and password you entered did not match our records. Please try again.", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
        } else {
            Toast.makeText(RegisterActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
        }
    }
}
