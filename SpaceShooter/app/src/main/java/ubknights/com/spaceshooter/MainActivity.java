package ubknights.com.spaceshooter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //create a menu with two options: play round 1 or play round two
        super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE,0,Menu.NONE,"Stage one");
        menu.add(Menu.NONE,1,Menu.NONE,"Stage two");
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 0:
                //start round1
                Intent t = new Intent(MainActivity.this, TheGameLevel1X.class);
                startActivity(t);
                return true;
            case 1:
                Intent t1 = new Intent(MainActivity.this, TheGameLevel2.class);
                startActivity(t1);

            default:
                return super.onOptionsItemSelected(item);

        }
    }


}





