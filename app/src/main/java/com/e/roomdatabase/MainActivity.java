package com.e.roomdatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.room.RoomDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button btAdd,btReset;
    RecyclerView recyclerView;

    List<MainData> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText=findViewById(R.id.edit_txt);
        btAdd=findViewById(R.id.btn_add);
        btReset=findViewById(R.id.btn_reset);
        recyclerView=findViewById(R.id.recycler_view);

        //initialize database
        database = RoomDB.getInstance(this);
        //store database value in data list
        dataList=database.mainDao().getAll();

        //initialize linear layout manager

        linearLayoutManager = new LinearLayoutManager(this);
        //set layout manager
        recyclerView.setLayoutManager(linearLayoutManager);

        //initialize adapter
        mainAdapter=new MainAdapter(MainActivity.this,dataList);
        //set adapter
        recyclerView.setAdapter(mainAdapter);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get string from edit text
                String sText = editText.getText().toString().trim();
                //check condition
                if(!sText.equals(""))
                {
                    //when text is not empty
                    //Initialize main data
                    MainData data =new MainData();
                    //set text on main data
                    data.setText(sText);
                    //insert text in database
                    database.mainDao().insert(data);
                    //clear edit text
                    editText.setText("");
                    //notify when data is inserted
                    dataList.clear();dataList.addAll(database.mainDao().getAll());
                    mainAdapter.notifyDataSetChanged();

                }


            }
        });


        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete all data from database
                database.mainDao().reset(dataList);
                //Notify when all data is deleted
                dataList.clear();
                dataList.addAll(database.mainDao().getAll());
                mainAdapter.notifyDataSetChanged();

            }
        });


    }
}