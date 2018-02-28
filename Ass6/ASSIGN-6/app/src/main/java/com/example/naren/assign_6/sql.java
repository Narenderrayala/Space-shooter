package com.example.naren.assign_6;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
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

public class sql extends AppCompatActivity {
    SQL1 db;
    Button writebtn, readbtn, deletebtn;
    TextView seeTxt;
    SQLiteDatabase qdb, qdb1;
    String[] nameList = new String[]{"age.txt", "colors.txt", "diet.txt", "food.txt", "food2.txt", "gender.txt", "names.txt", "salary.txt"};
    String[] paths = new String[900];
    int i = 0;
    int l = 0;
    String[] age = new String[110];
    String[] colors = new String[110];
    String[] diet = new String[110];
    String[] food = new String[110];
    String[] food2 = new String[110];
    String[] gender = new String[110];
    String[] names = new String[110];
    String[] salary = new String[110];
    long ins = 0;
    CustomAdapter customAdapter;
    String ret = "";
    ListView lv;
    ArrayAdapter adapter;
    String[] nameList1;
    ArrayList nameList2;
    Button addbtn, clearbtn;
    List deleteThese;
    boolean insertflag = true;
    ImageView iv;
    View v;
    Context activity;
    boolean deleted;
    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.sql_activity);


        setContentView(R.layout.listview);
//        LayoutInflater inflater=LayoutInflater.from(activity);

        // v=inflater.inflate(R.layout.sql_activity,null);
        t = (TextView) findViewById(R.id.textView);


        AssetManager assets = getAssets();
        InputStream imageStream;
//        iv=(ImageView)v.findViewById(R.id.imageView) ;

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
        db = new SQL1(this, "mydb5.db", null, 1);
        qdb = db.getReadableDatabase();
        qdb1 = db.getWritableDatabase();


        /*if (insertflag) {
            for (l = 0; l < 100; l++)

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
            if (ins > 0)

            {
                Toast.makeText(sql.this, "Rows inserted", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(sql.this, "Rows are not inserted", Toast.LENGTH_SHORT).show();

            insertflag = false;

        }*/


        lv = (ListView) findViewById(R.id.listview);


        try {
            paths = assets.list("");
        } catch (IOException e) {
            e.printStackTrace();
        }






    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.showall:



                Cursor recordset1 = qdb.query("employees", null, null, null, null, null, null);
                Cursor recordset2 = qdb.rawQuery("SELECT * FROM employees", null);

                StringBuilder theRow = new StringBuilder();
                recordset2.moveToFirst();
                t.setText(String.valueOf(recordset2.getCount()));
                Toast.makeText(sql.this, String.valueOf(recordset2.getCount()), Toast.LENGTH_SHORT).

                        show();


                if (recordset2.getCount() > 0)

                {
                    for (int i = 0; i < recordset2.getCount(); i++) {
                        age[i] = recordset2.getString(1);
                        colors[i] = recordset2.getString(2);
                        diet[i] = recordset2.getString(3);
                        food[i] = recordset2.getString(4);
                        food2[i] = recordset2.getString(5);
                        gender[i] = recordset2.getString(6);
                        names[i] = recordset2.getString(7);
                        salary[i] = recordset2.getString(8);
                        recordset2.moveToNext();


                        //t.setText(theRow);


                    }
                    Toast.makeText(sql.this, " rows", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(sql.this, "No rows", Toast.LENGTH_SHORT).show();
                }



                customAdapter = new CustomAdapter();
                lv.setAdapter(customAdapter);









                break;
            case R.id.milton1:



                //Cursor recordset1 = qdb.query("employees", null, null, null, null, null, null);
                Cursor recordset3 = qdb.query("employees", null, "food=? or food2=?", new String[]{"chicken", "chicken"}, null, null, null);

                //StringBuilder theRow = new StringBuilder();
                recordset3.moveToFirst();
                t.setText(String.valueOf(recordset3.getCount()));
                Toast.makeText(sql.this, String.valueOf(recordset3.getCount()), Toast.LENGTH_SHORT).

                        show();


                if (recordset3.getCount() > 0)

                {
                    for (int i = 0; i < recordset3.getCount(); i++) {
                        age[i] = recordset3.getString(1);
                        colors[i] = recordset3.getString(2);
                        diet[i] = recordset3.getString(3);
                        food[i] = recordset3.getString(4);
                        food2[i] = recordset3.getString(5);
                        gender[i] = recordset3.getString(6);
                        names[i] = recordset3.getString(7);
                        salary[i] = recordset3.getString(8);
                        recordset3.moveToNext();


                        //t.setText(theRow);


                    }
                    Toast.makeText(sql.this, " rows", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(sql.this, "No rows", Toast.LENGTH_SHORT).show();
                }



                customAdapter = new CustomAdapter();
                lv.setAdapter(customAdapter);











                break;
            case R.id.milton2:
                Cursor recordset4 = qdb.query("employees", null, "food=? or food2=?", new String[]{"goat", "goat"}, null, null, null);

                //StringBuilder theRow = new StringBuilder();
                recordset4.moveToFirst();
                t.setText(String.valueOf(recordset4.getCount()));
                Toast.makeText(sql.this, String.valueOf(recordset4.getCount()), Toast.LENGTH_SHORT).

                        show();


                if (recordset4.getCount() > 0)

                {
                    for (int i = 0; i < recordset4.getCount(); i++) {
                        age[i] = recordset4.getString(1);
                        colors[i] = recordset4.getString(2);
                        diet[i] = recordset4.getString(3);
                        food[i] = recordset4.getString(4);
                        food2[i] = recordset4.getString(5);
                        gender[i] = recordset4.getString(6);
                        names[i] = recordset4.getString(7);
                        salary[i] = recordset4.getString(8);
                        recordset4.moveToNext();


                        //t.setText(theRow);


                    }
                    Toast.makeText(sql.this, " rows", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(sql.this, "No rows", Toast.LENGTH_SHORT).show();
                }



                customAdapter = new CustomAdapter();
                lv.setAdapter(customAdapter);













                break;
            case R.id.milton3:


                Cursor recordset5 = qdb.query("employees", null, "salary<=?", new String[]{String.valueOf(60000)}, null, null, null);

                //StringBuilder theRow = new StringBuilder();
                recordset5.moveToFirst();
                t.setText(String.valueOf(recordset5.getCount()));
                Toast.makeText(sql.this, String.valueOf(recordset5.getCount()), Toast.LENGTH_SHORT).

                        show();


                if (recordset5.getCount() > 0)

                {
                    for (int i = 0; i < recordset5.getCount(); i++) {
                        age[i] = recordset5.getString(1);
                        colors[i] = recordset5.getString(2);
                        diet[i] = recordset5.getString(3);
                        food[i] = recordset5.getString(4);
                        food2[i] = recordset5.getString(5);
                        gender[i] = recordset5.getString(6);
                        names[i] = recordset5.getString(7);
                        salary[i] = recordset5.getString(8);
                        recordset5.moveToNext();


                        //t.setText(theRow);


                    }
                    Toast.makeText(sql.this, " rows", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(sql.this, "No rows", Toast.LENGTH_SHORT).show();
                }



                customAdapter = new CustomAdapter();
                lv.setAdapter(customAdapter);














                break;
            case R.id.milton4:


                Cursor recordset6 = qdb.query("employees", null, "age<?", new String[]{String.valueOf(38)}, null, null, null);

                //StringBuilder theRow = new StringBuilder();
                recordset6.moveToFirst();
                t.setText(String.valueOf(recordset6.getCount()));
                Toast.makeText(sql.this, String.valueOf(recordset6.getCount()), Toast.LENGTH_SHORT).

                        show();


                if (recordset6.getCount() > 0)

                {
                    for (int i = 0; i < recordset6.getCount(); i++) {
                        age[i] = recordset6.getString(1);
                        colors[i] = recordset6.getString(2);
                        diet[i] = recordset6.getString(3);
                        food[i] = recordset6.getString(4);
                        food2[i] = recordset6.getString(5);
                        gender[i] = recordset6.getString(6);
                        names[i] = recordset6.getString(7);
                        salary[i] = recordset6.getString(8);
                        recordset6.moveToNext();


                        //t.setText(theRow);


                    }
                    Toast.makeText(sql.this, " rows", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(sql.this, "No rows", Toast.LENGTH_SHORT).show();
                }



                customAdapter = new CustomAdapter();
                lv.setAdapter(customAdapter);








                break;
            case R.id.milton5:


                Cursor recordset7 = qdb.query("employees", null, "colors=? and gender=?", new String[]{"blue", "female"}, null, null, null);

                //StringBuilder theRow = new StringBuilder();
                recordset7.moveToFirst();
                t.setText(String.valueOf(recordset7.getCount()));
                Toast.makeText(sql.this, String.valueOf(recordset7.getCount()), Toast.LENGTH_SHORT).

                        show();


                if (recordset7.getCount() > 0)

                {
                    for (int i = 0; i < recordset7.getCount(); i++) {
                        age[i] = recordset7.getString(1);
                        colors[i] = recordset7.getString(2);
                        diet[i] = recordset7.getString(3);
                        food[i] = recordset7.getString(4);
                        food2[i] = recordset7.getString(5);
                        gender[i] = recordset7.getString(6);
                        names[i] = recordset7.getString(7);
                        salary[i] = recordset7.getString(8);
                        recordset7.moveToNext();


                        //t.setText(theRow);


                    }
                    Toast.makeText(sql.this, " rows", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(sql.this, "No rows", Toast.LENGTH_SHORT).show();
                }



                customAdapter = new CustomAdapter();
                lv.setAdapter(customAdapter);











                break;
            case R.id.grunt1:



                Cursor recordset8 = qdb.query("employees", null, "salary>?", new String[]{String.valueOf(70000)}, null, null, null);

                //StringBuilder theRow = new StringBuilder();
                recordset8.moveToFirst();
                t.setText(String.valueOf(recordset8.getCount()));
                Toast.makeText(sql.this, String.valueOf(recordset8.getCount()), Toast.LENGTH_SHORT).

                        show();


                if (recordset8.getCount() > 0)

                {
                    for (int i = 0; i < recordset8.getCount(); i++) {
                        age[i] = recordset8.getString(1);
                        colors[i] = recordset8.getString(2);
                        diet[i] = recordset8.getString(3);
                        food[i] = recordset8.getString(4);
                        food2[i] = recordset8.getString(5);
                        gender[i] = recordset8.getString(6);
                        names[i] = recordset8.getString(7);
                        salary[i] = recordset8.getString(8);
                        recordset8.moveToNext();


                        //t.setText(theRow);


                    }
                    Toast.makeText(sql.this, " rows", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(sql.this, "No rows", Toast.LENGTH_SHORT).show();
                }



                customAdapter = new CustomAdapter();
                lv.setAdapter(customAdapter);












                break;
            case R.id.grunt2:

                Cursor recordset9 = qdb.query("employees", null, "age>? and diet=?", new String[]{(String.valueOf(40)), "poultry"}, null, null, null);

                //StringBuilder theRow = new StringBuilder();
                recordset9.moveToFirst();
                t.setText(String.valueOf(recordset9.getCount()));
                Toast.makeText(sql.this, String.valueOf(recordset9.getCount()), Toast.LENGTH_SHORT).

                        show();


                if (recordset9.getCount() > 0)

                {
                    for (int i = 0; i < recordset9.getCount(); i++) {
                        age[i] = recordset9.getString(1);
                        colors[i] = recordset9.getString(2);
                        diet[i] = recordset9.getString(3);
                        food[i] = recordset9.getString(4);
                        food2[i] = recordset9.getString(5);
                        gender[i] = recordset9.getString(6);
                        names[i] = recordset9.getString(7);
                        salary[i] = recordset9.getString(8);
                        recordset9.moveToNext();


                        //t.setText(theRow);


                    }
                    Toast.makeText(sql.this, " rows", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(sql.this, "No rows", Toast.LENGTH_SHORT).show();
                }



                customAdapter = new CustomAdapter();
                lv.setAdapter(customAdapter);












                break;
            case R.id.grunt3:

                Cursor recordset10 = qdb.query("employees", null, "food=? and age<?", new String[]{"turkey", String.valueOf(40)}, null, null, null);

                //StringBuilder theRow = new StringBuilder();
                recordset10.moveToFirst();
                t.setText(String.valueOf(recordset10.getCount()));
                Toast.makeText(sql.this, String.valueOf(recordset10.getCount()), Toast.LENGTH_SHORT).

                        show();


                if (recordset10.getCount() > 0)

                {
                    for (int i = 0; i < recordset10.getCount(); i++) {
                        age[i] = recordset10.getString(1);
                        colors[i] = recordset10.getString(2);
                        diet[i] = recordset10.getString(3);
                        food[i] = recordset10.getString(4);
                        food2[i] = recordset10.getString(5);
                        gender[i] = recordset10.getString(6);
                        names[i] = recordset10.getString(7);
                        salary[i] = recordset10.getString(8);
                        recordset10.moveToNext();


                        //t.setText(theRow);


                    }
                    Toast.makeText(sql.this, " rows", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(sql.this, "No rows", Toast.LENGTH_SHORT).show();
                }



                customAdapter = new CustomAdapter();
                lv.setAdapter(customAdapter);











                break;
            case R.id.grunt4:
                Cursor recordset11 = qdb.query("employees", null, "gender=? and colors=? and diet=?", new String[]{"male", "red", "redMeat"}, null, null, null);

                //StringBuilder theRow = new StringBuilder();
                recordset11.moveToFirst();
                t.setText(String.valueOf(recordset11.getCount()));
                Toast.makeText(sql.this, String.valueOf(recordset11.getCount()), Toast.LENGTH_SHORT).

                        show();


                if (recordset11.getCount() > 0)

                {
                    for (int i = 0; i < recordset11.getCount(); i++) {
                        age[i] = recordset11.getString(1);
                        colors[i] = recordset11.getString(2);
                        diet[i] = recordset11.getString(3);
                        food[i] = recordset11.getString(4);
                        food2[i] = recordset11.getString(5);
                        gender[i] = recordset11.getString(6);
                        names[i] = recordset11.getString(7);
                        salary[i] = recordset11.getString(8);
                        recordset11.moveToNext();


                        //t.setText(theRow);


                    }
                    Toast.makeText(sql.this, " rows", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(sql.this, "No rows", Toast.LENGTH_SHORT).show();
                }



                customAdapter = new CustomAdapter();
                lv.setAdapter(customAdapter);











                break;
            case R.id.grunt5:
                Cursor recordset12 = qdb.query("employees", null, "colors=? and gender=?", new String[]{"blue", "female"}, null, null, null);

                //StringBuilder theRow = new StringBuilder();
                recordset12.moveToFirst();
                t.setText(String.valueOf(recordset12.getCount()));
                Toast.makeText(sql.this, String.valueOf(recordset12.getCount()), Toast.LENGTH_SHORT).

                        show();


                if (recordset12.getCount() > 0)

                {
                    for (int i = 0; i < recordset12.getCount(); i++) {
                        age[i] = recordset12.getString(1);
                        colors[i] = recordset12.getString(2);
                        diet[i] = recordset12.getString(3);
                        food[i] = recordset12.getString(4);
                        food2[i] = recordset12.getString(5);
                        gender[i] = recordset12.getString(6);
                        names[i] = recordset12.getString(7);
                        salary[i] = recordset12.getString(8);
                        recordset12.moveToNext();


                        //t.setText(theRow);


                    }
                    Toast.makeText(sql.this, " rows", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(sql.this, "No rows", Toast.LENGTH_SHORT).show();
                }



                customAdapter = new CustomAdapter();
                lv.setAdapter(customAdapter);











                break;
            case R.id.both1:
                Cursor recordset13 = qdb.query("employees", null, "colors=? and gender=?", new String[]{"blue", "female"}, null, null, null);

                //StringBuilder theRow = new StringBuilder();
                recordset13.moveToFirst();
                t.setText(String.valueOf(recordset13.getCount()));
                Toast.makeText(sql.this, String.valueOf(recordset13.getCount()), Toast.LENGTH_SHORT).

                        show();


                if (recordset13.getCount() > 0)

                {
                    for (int i = 0; i < recordset13.getCount(); i++) {
                        age[i] = recordset13.getString(1);
                        colors[i] = recordset13.getString(2);
                        diet[i] = recordset13.getString(3);
                        food[i] = recordset13.getString(4);
                        food2[i] = recordset13.getString(5);
                        gender[i] = recordset13.getString(6);
                        names[i] = recordset13.getString(7);
                        salary[i] = recordset13.getString(8);
                        recordset13.moveToNext();


                        //t.setText(theRow);


                    }
                    Toast.makeText(sql.this, " rows", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(sql.this, "No rows", Toast.LENGTH_SHORT).show();
                }



                customAdapter = new CustomAdapter();
                lv.setAdapter(customAdapter);












                break;
            case R.id.both2:
                Cursor recordset14 = qdb.query("employees", null, "colors=? and gender=?", new String[]{"blue", "female"}, null, null, null);

                //StringBuilder theRow = new StringBuilder();
                recordset14.moveToFirst();
                t.setText(String.valueOf(recordset14.getCount()));
                Toast.makeText(sql.this, String.valueOf(recordset14.getCount()), Toast.LENGTH_SHORT).

                        show();


                if (recordset14.getCount() > 0)

                {
                    for (int i = 0; i < recordset14.getCount(); i++) {
                        age[i] = recordset14.getString(1);
                        colors[i] = recordset14.getString(2);
                        diet[i] = recordset14.getString(3);
                        food[i] = recordset14.getString(4);
                        food2[i] = recordset14.getString(5);
                        gender[i] = recordset14.getString(6);
                        names[i] = recordset14.getString(7);
                        salary[i] = recordset14.getString(8);
                        recordset14.moveToNext();


                        //t.setText(theRow);


                    }
                    Toast.makeText(sql.this, " rows", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(sql.this, "No rows", Toast.LENGTH_SHORT).show();
                }



                customAdapter = new CustomAdapter();
                lv.setAdapter(customAdapter);











                break;
            //case R.id.delete: mydb5.deletdb();
              //  break;
        }
        return true;
    }





    public class CustomAdapter extends BaseAdapter {
        Context theActivity;
        String [] allnames;
        AssetManager a_manager;
        ImageView img,img1,img2;
        TextView t1,t2,t3,t4,t5;
        View rect;
        Uri[] images;

        @Override
        public int getCount() {

            return 100;
        }

        @Override
        public Object getItem(int i) {

            return null;
        }

        @Override
        public long getItemId(int i) {

            return 0;
        }

        @Override
        public View getView(int pos, View view, ViewGroup viewGroup) {
            View newView=view ;



            if (view == null)
            {

                newView = getLayoutInflater().inflate(R.layout.ex,null,false);
                //TextView  nameText = (TextView)newView.findViewById(R.id.nametext);
                //nameText.setText(allnames[pos].substring(0,allnames[pos].length()-4));
                img = (ImageView)newView.findViewById(R.id.imageView5);
                img1 = (ImageView)newView.findViewById(R.id.imageView6);
                //img2 = (ImageView)newView.findViewById(R.id.imageView7);
                t1=(TextView)newView.findViewById(R.id.textView5);
                t2=(TextView)newView.findViewById(R.id.textView6);
                t3=(TextView)newView.findViewById(R.id.textView7);
                t4=(TextView)newView.findViewById(R.id.textView9);
                t5=(TextView)newView.findViewById(R.id.textView4);
                rect=(View)newView.findViewById(R.id.rectangle_at_the_top);
                img.setImageResource(R.drawable.famale);
                img1.setImageResource(R.drawable.poultry);
//                img2.setImageResource(R.drawable.vegetarian);
                img1.setMinimumWidth(100);
                img1.setMinimumHeight(100);
                img1.setMinimumWidth(100);
                img1.setMinimumHeight(100);
                t1.setText("AGE:"+age[pos]);
                t2.setText("SALARY:"+salary[pos]);
                t3.setText(food[pos]);
                t4.setText(food2[pos]);
                t5.setText(names[pos]);


                if(colors[pos].equals("orange"))
                {
                    rect.setBackgroundColor((getResources().getColor(R.color.opaque_orange)));
                }
                if(colors[pos].equals("green"))
                {
                    rect.setBackgroundColor(0xFF00FF00);
                }
                if(colors[pos].equals("red"))
                {
                    rect.setBackgroundColor(0xffff0000);
                }
                if(colors[pos].equals("yellow"))
                {
                    rect.setBackgroundColor(0xffffff00);
                }
                if(colors[pos].equals("blue"))
                {
                    rect.setBackgroundColor( 0xff0000ff );
                }




                if(gender[pos].equals("female"))

                {
                    img.setImageResource(R.drawable.famale);
                }
                else
                {
                    img.setImageResource(R.drawable.male);
                }
                if(diet[pos].equals("poultry"))
                {
                    img1.setImageResource(R.drawable.poultry);
                }
                else if(diet[pos].equals("redMeat"))
                {
                    img1.setImageResource(R.drawable.redmeat);
                }
                else if(diet[pos].equals("vegetarian"))
                {
                    img1.setImageResource(R.drawable.vegetarian);
                }


                //TextView  subText = (TextView)newView.findViewById(R.id.subText);
                //subText.setText(pos+"");
            }



            return newView;
        }
    }




}

