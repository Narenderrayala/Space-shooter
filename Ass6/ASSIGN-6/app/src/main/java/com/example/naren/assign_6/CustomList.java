/*package com.example.naren.assign_6;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;



public class CustomList extends BaseAdapter{
    Context theActivity;
    String [] allnames;
    AssetManager a_manager;
    ImageView img,img1,img2;
    TextView t1,t2,t3,t4;
    Uri[] images;
    public CustomList(Context ctx, String []names, AssetManager assets)
    {
        theActivity = ctx;
        allnames = names;
        a_manager = assets;
    }
    @Override
    public int getCount() {

        return allnames.length;
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
            LayoutInflater inflater=LayoutInflater.from(theActivity);

            newView = inflater.inflate(R.layout.ex,null,false);
            //TextView  nameText = (TextView)newView.findViewById(R.id.nametext);
            //nameText.setText(allnames[pos].substring(0,allnames[pos].length()-4));
             img = (ImageView)newView.findViewById(R.id.imageView5);
            img1 = (ImageView)newView.findViewById(R.id.imageView6);
            img2 = (ImageView)newView.findViewById(R.id.imageView7);
            t1=(TextView)newView.findViewById(R.id.textView5);
            t2=(TextView)newView.findViewById(R.id.textView6);
            t3=(TextView)newView.findViewById(R.id.textView7);
            t4=(TextView)newView.findViewById(R.id.textView9);

            img.setImageResource(R.drawable.famale);
            img1.setImageResource(R.drawable.poultry);
            img2.setImageResource(R.drawable.vegetarian);
            img1.setMinimumWidth(100);
            img1.setMinimumHeight(100);
            img1.setMinimumWidth(100);
            img1.setMinimumHeight(100);


            //TextView  subText = (TextView)newView.findViewById(R.id.subText);
            //subText.setText(pos+"");
        }
        return newView;
    }
}
*/