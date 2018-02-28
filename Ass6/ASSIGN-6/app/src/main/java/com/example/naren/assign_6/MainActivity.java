package com.example.naren.assign_6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    ArrayAdapter adapter;
    String[] nameList;
    ArrayList nameList2;
    Button addbtn,clearbtn;
    List deleteThese;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        nameList = new String[]{"car", "moon", "plain", "fire","ghost","green",
                "walk","random","money","keys","coins","flames","Narender","Hello","ME"};
        //nameList2 = new ArrayList(Arrays.asList(nameList));

        lv = (ListView) findViewById(R.id.listview);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_activated_1, nameList);
        //adapter = new ArrayAdapter(this, R.layout.simo_right, nameList2);
        lv.setAdapter(adapter);



    }
}
