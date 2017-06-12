package com.kumar.user.app_21mywishlistsqlitedatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import data.DatabaseHandler;

public class Wish_Detail extends AppCompatActivity {
    private TextView title,date,content;
    private Button deleteButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish__detail);
        title= (TextView) findViewById(R.id.detailsTitle);
        date= (TextView) findViewById(R.id.detailsDateText);
        content= (TextView) findViewById(R.id.detailsTextView);
        deleteButton= (Button) findViewById(R.id.deleteButton);


        Bundle bundle=getIntent().getExtras();
        if (bundle !=null){
            title.setText(bundle.getString("title"));
            date.setText("Created :  " + bundle.getString("date"));
            content.setText(" \" " + bundle.getString("content") + " \" ");

            final int id= bundle.getInt("id");
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler databaseHandler=new DatabaseHandler(getApplicationContext());
                    databaseHandler.deleteWishes(id);
                    Toast.makeText(Wish_Detail.this, "Wish Deleted ", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Wish_Detail.this,DisplayWishes.class));
                }
            });

        }

    }
}
