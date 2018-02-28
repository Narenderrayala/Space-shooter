package com.example.naren.assign_6;

import android.content.ContentValues;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by naren on 04-05-2017.
 */

public class sql123 extends AppCompatActivity {
    SQL1 db;
    Button writebtn, readbtn, deletebtn;
    TextView seeTxt;
    SQLiteDatabase qdb, qdb1;
    String[] nameList = new String[]{"age.txt", "colors.txt", "diet.txt", "food.txt", "food2.txt", "gender.txt", "names.txt", "salary.txt"};
    String[] paths = new String[900];
    int i = 0;
    int l = 0;
    long ins = 0;
    String ret = "";
    ListView lv;
    ArrayAdapter adapter;
    String[] nameList1;
    ArrayList nameList2;
    Button addbtn, clearbtn;
    List deleteThese;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.sql_activity);


        setContentView(R.layout.listview);

        nameList1 = new String[]{"car", "moon", "plain", "fire", "ghost", "green",
                "walk", "random", "money", "keys", "coins", "flames", "Narender", "Hello", "ME"};
        //nameList2 = new ArrayList(Arrays.asList(nameList));

        lv = (ListView) findViewById(R.id.listview);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_activated_1, nameList1);
        //adapter = new ArrayAdapter(this, R.layout.simo_right, nameList2);
        lv.setAdapter(adapter);
        AssetManager assets = getAssets();
        InputStream imageStream;
        int k = 0;
        while (k < 8) {
            try {
                assets = getAssets();
                imageStream = assets.open(nameList[k]);

                if (imageStream != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(imageStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String receiveString = "";
                    StringBuilder stringBuilder = new StringBuilder();

                    while ((receiveString = bufferedReader.readLine()) != null) {
                        stringBuilder.append(receiveString);
                        paths[i] = receiveString;
                        //receiveString = "";

                        i++;
                    }
                    imageStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            k++;
        }
       /* writebtn = (Button)findViewById(R.id.writeBTN);
        writebtn.setOnClickListener(clickme);
        readbtn = (Button)findViewById(R.id.readBTN);
        readbtn.setOnClickListener(clickme);
        seeTxt  =(TextView)findViewById(R.id.DBtxt);
        deletebtn = (Button)findViewById(R.id.deletebtn);
        deletebtn.setOnClickListener(clickme);*/
        db = new SQL1(this, "mydb1.db", null, 1);
        qdb = db.getReadableDatabase();
        qdb1 = db.getWritableDatabase();




        //View.OnClickListener clickme = new View.OnClickListener() {

        //  @Override
        //public void onClick(View v) {
        //  switch(v.getId())
        //{
        //  case R.id.readBTN:
        Cursor recordset1 = qdb.query("employees", null, null, null, null, null, null);
        Cursor recordset2 = qdb.rawQuery("SELECT * FROM employees", null);
        StringBuilder theRow = new StringBuilder();
        recordset2.moveToFirst();

        //Toast.makeText(sql.this, String.valueOf(recordset2.getString(99)), Toast.LENGTH_SHORT).show();
        Toast.makeText(sql123.this,String.valueOf(recordset2.getCount()),Toast.LENGTH_SHORT).

                show();

        if(recordset2.getCount()>0)

        {
            for (int i = 0; i < recordset2.getCount(); i++) {
                theRow.append(recordset2.getString(0));
                theRow.append(",");
                theRow.append(recordset2.getString(1));
                theRow.append(",");
                theRow.append(recordset2.getString(2));
                theRow.append(",");
                theRow.append(recordset2.getString(3));
                theRow.append(",");
                theRow.append(recordset2.getString(4));
                theRow.append(",");
                theRow.append(recordset2.getString(5));
                theRow.append(",");
                theRow.append(recordset2.getString(6));
                theRow.append(",");
                theRow.append(recordset2.getString(7));
                theRow.append("," + "\n");
                recordset2.moveToNext();


            }
            //seeTxt.setText("row info: " + theRow.toString());
        }
        else
            //                  seeTxt.setText("no rows in the cursor");
            //break;

            //case R.id.writeBTN:



            for(l=0;l<100;l++)

            {
                ContentValues values = new ContentValues();
                values.put("age", paths[l]);
                values.put("colors", paths[l + 100]);
                values.put("diet", paths[l + 200]);
                values.put("food", paths[l + 300]);

                values.put("food2", paths[l + 400]);
                values.put("gender", paths[l + 500]);
                values.put("names", paths[l + 600]);
                values.put("salary", paths[l + 700]);


                //returns row id or -1 if error
                ins = qdb1.insert("employees", null, values);

            }

        if(ins >0)

        {
            seeTxt.setText("row inserted, row# " + ins);
            seeTxt.setText(paths[700]);
        } else
            seeTxt.setText("row not insertd");

        // break;


        //case R.id.deletebtn:
        boolean deleted = sql123.this.deleteDatabase("mydb1.db");
        if(deleted)
            seeTxt.setText("database deleted");
        else
            seeTxt.setText("database not deleted");
        // break;


    }
}





