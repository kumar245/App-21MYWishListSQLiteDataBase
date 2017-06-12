package com.kumar.user.app_21mywishlistsqlitedatabase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import data.DatabaseHandler;
import model.MyWish;

public class MainActivity extends AppCompatActivity {
        private EditText title;
        private EditText content;
        private Button saveButton;
        private DatabaseHandler dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dba=new DatabaseHandler(MainActivity.this);
        title= (EditText) findViewById(R.id.edittitle);
        content= (EditText) findViewById(R.id.wishEdit);
        saveButton= (Button) findViewById(R.id.button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToDB();
            }
        });
    }

    private void saveToDB() {
        MyWish wish=new MyWish();
        wish.setTitle(title.getText().toString().trim());
        wish.setContent(content.getText().toString().trim());
        dba.addWishes(wish);
        dba.close();
        //clear after the user clicks the button

        title.setText("");
        content.setText("");
        Intent i=new Intent(MainActivity.this,DisplayWishes.class);
        startActivity(i);

    }
}
