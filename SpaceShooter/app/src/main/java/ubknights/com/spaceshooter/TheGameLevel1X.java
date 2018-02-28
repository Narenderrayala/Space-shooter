package ubknights.com.spaceshooter;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by carlos on 10/13/2016.
 */

public class TheGameLevel1X extends Activity implements View.OnTouchListener{

    TheView mySurfaceView;      //the surfaceview, where we draw
    TheSprites2 allsprites; //class that has all the location and sizes of the images in the sprite
    float xTouch,yTouch;          //the location when the screen is touched
    int s_width,s_height;
    //the size of the surfaceview
    //used for which sprite to use
    int loc = 0,eLoc=0,e1Loc=3,e2Loc=0,m1Loc=0,m2Loc=1,m3Loc=2;
    Point shipbullet,ship,enemy1,enemy2,enemy1bullet,enemy2bullet,meteor1,meteor2,meteor3;
    long enemyMoveTime = 0;
    Paint paint;
    //15 frames per seconds
    float skipTime =1000.0f/30.0f; //setting 30fps
    long lastUpdate;
    float dt;
    String timer;
    float exp1=0,exp2=0;
    int count=0;
    int enemyhitcount=0;
    int shiphitcount=0;
    View v1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.spritesheetbetter); //not setting a static xml
        //set all the sprite locations and sizes
        allsprites = new TheSprites2(getResources());//object ffor sprite is created.
        //make sure there is only ONE copy of the image and that the image
        //is in the drawable-nodpi. if it is not unwanted scaling might occur
        shipbullet = new Point();
        enemy1bullet=new Point();//used for canvas drawing location
        enemy2bullet=new Point();
        enemy1 = new Point();
        enemy2 = new Point();//used for canvas drawing location
        lastUpdate = 0;         //to check against now time
        ship=new Point();
        //hide the actoinbar and make it fullscreen
        hideAndFull();
        //our custom view
        mySurfaceView = new TheView(this);
        mySurfaceView.setOnTouchListener(this); //now we can touch the screen

        setContentView(mySurfaceView);






        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                //not passing an xml, using the surfaceview as the layout
                //custom way of setting the size of the surfaceview
                mySurfaceView.startGame();
            }
        },2000);



    }

    public void hideAndFull()
    {
        ActionBar bar = getActionBar();
        bar.hide();
        final View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch(motionEvent.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                xTouch = motionEvent.getX();
                yTouch = motionEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                xTouch = motionEvent.getX();
                yTouch = motionEvent.getY();
                view.performClick();//to get rid of the message, mimicking a click
                break;
            case MotionEvent.ACTION_MOVE:
                xTouch = motionEvent.getX();
                yTouch = motionEvent.getY();
                break;
        }
        return true;
    }

    //surface view used so we can draw is dedicated made for drawing
    //View is updated in main thread while SurfaceView is updated in another thread.
    public class TheView extends SurfaceView implements SurfaceHolder.Callback {
        //resize and edit pixels in a surface. Holds the display
        SurfaceHolder holder;
        Boolean change = true;
        Thread gameThread;
        AlertDialog.Builder build;
        AlertDialog done;
        int soundIds[] = new int[10];
         SoundPool sound_pool ;
        MediaPlayer mp;
        public TheView(Context context) {
            super(context);
            //get this holder

















            holder = getHolder();//gets the surfaceview surface
            holder.addCallback(this);
            paint = new Paint(Color.YELLOW);
            paint.setColor(Color.YELLOW);
            paint.setTextSize(50);
            sound_pool=new SoundPool(4,AudioManager.STREAM_MUSIC,0);
            HashMap<Integer,Integer>soundMap_H;
            soundMap_H=new HashMap<Integer,Integer>();
            mp=MediaPlayer.create(TheGameLevel1X.this,R.raw.mainsong);
            soundMap_H.put(1,sound_pool.load(TheGameLevel1X.this,R.raw.enemyboom,1));
            soundMap_H.put(2,sound_pool.load(TheGameLevel1X.this,R.raw.gameover,1));
            soundMap_H.put(3,sound_pool.load(TheGameLevel1X.this,R.raw.shipboom,1));
            soundMap_H.put(4,sound_pool.load(TheGameLevel1X.this,R.raw.shipbullet,1));
            soundMap_H.put(5,sound_pool.load(TheGameLevel1X.this,R.raw.success,1));
            soundMap_H.put(6,sound_pool.load(TheGameLevel1X.this,R.raw.gameover,1));










            new CountDownTimer(60000, 1000) {

                public void onTick(long millisUntilFinished) {
                    timer = String.valueOf(60-(millisUntilFinished / 1000));
                    invalidate();
                }

                public void onFinish() {
                }
            }.start();

            gameThread = new Thread(runn);





        }

        Runnable runn = new Runnable() {
            @Override
            public void run() {

                while (change == true) {
                    //perform drawing, does it have a surface?
                    if (!holder.getSurface().isValid()) {
                        continue;
                    }
                    mp.start();
                    dt = System.currentTimeMillis() - lastUpdate;
                    // Log.d("d", dt+" "+"latupdate: "+ lastUpdate);
                    if (dt >= skipTime) {
                        //look it to paint on it
                        Canvas c = holder.lockCanvas();
                        //draw the background color
                        c.drawARGB(255, 0, 0, 0);


                        c.drawBitmap(allsprites.back,0,0,null);
                        //draw ship 4x the original size, you should use some percentage of the screen to make it the same size on every device
                        Rect place = new Rect((int)ship.x,(int)yTouch,
                                (int)ship.x   + allsprites.shipSize.width() * 4, (int)yTouch + allsprites.shipSize.height() * 4);
                        c.drawBitmap(allsprites.space, allsprites.shipSprites[eLoc], place, null);
                        //draw the explosion
                        int i=0;


                        c.drawText(timer, s_width-90, 10, paint);
                        place = new Rect((int) (exp1), (int) (exp2), (int) (exp1) + allsprites.boomSize[loc].width() * 8,
                                (int) (exp2) + allsprites.boomSize[loc].height() * 8);
                        c.drawBitmap(allsprites.space, allsprites.boomsprites[loc], place, null);


                        //draw the enemy 1
                        place = new Rect(enemy1.x, enemy1.y, enemy1.x + allsprites.enemySize.width() * 4,
                                enemy1.y + allsprites.enemySize.height() * 4);
                        c.drawBitmap(allsprites.space, allsprites.enemy1sprites[e1Loc], place, null);
                        //draw the enemy 2
                        place = new Rect((int) (enemy2.x), (int) (enemy2.y), (int) (enemy2.x) + allsprites.enemySize.width() * 4,
                                (int) (enemy2.y) + allsprites.enemySize.height() * 4);
                        c.drawBitmap(allsprites.space, allsprites.enemy2sprites[e2Loc], place, null);
                        //meteor1, using a percentage of the actual screen to draw it
                        place = new Rect((int) (s_width * .05f), (int) (s_height * .05f), (int) (s_width * .05f) + (int) (s_width * .2),
                                (int) (s_height * .05f) + (int) (s_width * .2));
                        //c.drawBitmap(allsprites.space, allsprites.meteorsprites[m1Loc], place, null);
                        //meteor2, using a percentage of the actual screen to draw it
                        place = new Rect((int) (s_width * .4f), (int) (s_height * .05f), (int) (s_width * .4f) + (int) (s_width * .2),
                                (int) (s_height * .05f) + (int) (s_width * .2));
                        //c.drawBitmap(allsprites.space, allsprites.meteorsprites[m2Loc], place, null);
                        //meteor3, using a percentage of the actual screen to draw it
                        place = new Rect((int) (s_width * .7f), (int) (s_height * .05f), (int) (s_width * .7f) + (int) (s_width * .2),
                                (int) (s_height * .05f) + (int) (s_width * .2));
                        //c.drawBitmap(allsprites.space, allsprites.meteorsprites[m3Loc], place, null);
                        //ship shooting

                        place = new Rect((int) shipbullet.x, shipbullet.y, (int) shipbullet.x + allsprites.shipBulletSprite.width() * 4,
                                shipbullet.y + allsprites.shipBulletSprite.height() * 4);
                        c.drawBitmap(allsprites.space, allsprites.shipBulletSprite, place, null);
                        //enemy1 shooting
                        place = new Rect((int) enemy1bullet.x, enemy1bullet.y, (int) enemy1bullet.x + allsprites.enemybulletSprite.width() * 4,
                                enemy1bullet.y + allsprites.enemybulletSprite.height() * 4);
                        c.drawBitmap(allsprites.space, allsprites.enemybulletSprite, place, null);
                        //enemy2 shooting

                        place = new Rect((int) enemy2bullet.x, enemy2bullet.y, (int) enemy2bullet.x + allsprites.enemybulletSprite.width() * 4,
                                enemy2bullet.y + allsprites.enemybulletSprite.height() * 4);
                        c.drawBitmap(allsprites.space, allsprites.enemybulletSprite, place, null);

                        holder.unlockCanvasAndPost(c);

                        //move bullets and update sprites
                        shipbullet.x += s_width / 30;
                        enemy1bullet.x -= s_width / 30;
                        enemy2bullet.x -= s_width / 30;

                        //enemy1bullet
                        //enemy2bullet
                        loc = ((loc + 1) % 6);
                        eLoc = (eLoc + 1) % 2;
                        e1Loc = (e1Loc + 1) % 6;
                        e2Loc = (e2Loc + 1) % 6;
                        m1Loc = (m1Loc + 1) % 4;
                        m2Loc = (m2Loc + 1) % 4;
                        m3Loc = (m3Loc + 1) % 4;
                        //check if bullet hit enemy or meteors
                        checkHitEnemies();
                        checkHitSpaceship();

                        //check if enemies bullet hit the ship

                        //check if
                        //check if ship bullet is out of screen
                        if (shipbullet.x > s_width) {
                            resetShipBullet();
                            sound_pool.play(4,1,1,1,0,1f);
                        }
                        if (enemy1bullet.x < -1) {
                            resetEnemyBullet();
                        }
                        if (enemy2bullet.x < -1) {
                            resetEnemyBullet1();
                        }
                        lastUpdate = System.currentTimeMillis();
                        //moveEnemy();
                    }
                    if(enemyhitcount==10)
                    {
                        change=false;
                        //Intent first=new Intent(TheGameLevel1X.this,TheGameLevel2.class);
                        //startActivity(first);
                        mp.stop();

                        TheGameLevel1X.this.runOnUiThread(new Runnable() {
                            public void run() {
                                sound_pool.play(5,1,1,1,0,1f);
                                build=new AlertDialog.Builder(TheGameLevel1X.this);
                                build.setTitle("Game Success");
                                build.setMessage("Time taken  =  " + String.valueOf(timer)+" Seconds");
                                build.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        Intent first=new Intent(TheGameLevel1X.this,MainActivity.class);
                                        startActivity(first);


                                    }

                                });
                                done=build.create();
                                done.show();
                            }
                        });




                    }
                    if(shiphitcount>=5)
                    {
                        mp.stop();
                        change=false;

                        TheGameLevel1X.this.runOnUiThread(new Runnable() {
                            public void run() {
                                sound_pool.play(6,1,1,1,0,1f);
                                build=new AlertDialog.Builder(TheGameLevel1X.this);
                                build.setTitle("Game Over");
                                build.setMessage("Time taken  =  " + String.valueOf(timer)+" Seconds");
                                build.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        Intent first=new Intent(TheGameLevel1X.this,MainActivity.class);
                                        startActivity(first);


                                    }

                                });
                                done=build.create();
                                done.show();
                            }
                        });


                        //
                        //done.show();



                        //maked2();
                        //Intent first=new Intent(TheGameLevel1X.this,MainActivity.class);
                        //startActivity(first);



                    }
                    //checkHitSpaceship();
                }
            }
        };



        public void resetShipBullet()
        {
            shipbullet.y =(int)yTouch +(allsprites.shipSize.height()*4/2)-allsprites.shipBulletSprite.height()*4/2;

            shipbullet.x = s_width - (allsprites.shipSize.width()*20 +allsprites.shipBulletSprite.width()*20);
        }

        public void resetEnemyBullet()
        {
            enemy1bullet.y =(int)enemy1.y +(allsprites.enemySize.height()*4/2)-allsprites.enemybulletSprite.height()*4/2;

            enemy1bullet.x = s_width - (allsprites.enemySize.width()*11 +allsprites.enemybulletSprite.width()*10);
        }
        public void resetEnemyBullet1()
        {
            enemy2bullet.y =(int)enemy2.y +(allsprites.enemySize.height()*4/2)-allsprites.enemybulletSprite.height()*4/2;

            enemy2bullet.x = s_width - (allsprites.enemySize.width()*11 +allsprites.enemybulletSprite.width()*10);
        }

        public void checkHitEnemies()
        {
            exp1=s_width+1;
            exp2=s_height+1;
            //exp2=shipbullet.y;
            if(shipbullet.x > enemy1.x && shipbullet.x < enemy1.x+allsprites.enemySize.width()*4 &&
                    shipbullet.y > enemy1.y && shipbullet.y < enemy1.y+allsprites.enemySize.height()*4 )
            {
                //show explosion then spawn the enemy somewhere else and reset bullet
                //showExplosion()
                enemy1.x=s_width+1;
                enemy1.y=s_height+1;
                int x=0;
                sound_pool.play(1,1,1,1,0,1f);

                exp1 = shipbullet.x;//eexp1 explosion values
                exp2 = shipbullet.y;

                enemyhitcount++;
                resetShipBullet();
                moveEnemy1();

            }
            if(shipbullet.x > enemy2.x && shipbullet.x < enemy2.x+allsprites.enemySize.width()*4 &&
                    shipbullet.y > enemy2.y && shipbullet.y < enemy2.y+allsprites.enemySize.height()*4 )
            {
                //show explosion then spawn the enemy somewhere else and reset bullet
                //showExplosion()
                //enemy2.x=s_width+1;
                //enemy2.y=s_height+1;
                int x=0;
                sound_pool.play(1,1,1,1,0,1f);
                exp1 = shipbullet.x;//eexp1 explosion values
                exp2 = shipbullet.y;

                enemyhitcount++;
                resetShipBullet();
                moveEnemy2();

            }
            //check bullt for enemy 2
            //else if

            //check bullet for meteor1
            //else if
            //check bullet for meteor2
            //else if
            //check bullet for meteor3
            //else if
        }



        public void checkHitSpaceship()
        {
            // exp1=s_width+1;
            //exp2=s_height+1;
            //exp2=shipbullet.y;
            if(enemy1bullet.x < ship.x && enemy1bullet.x < ship.x+allsprites.shipSize.width()*4 &&
                    enemy1bullet.y > (int)yTouch && enemy1bullet.y < (int)yTouch +allsprites.shipSize.height()*4 )
            {
                //show explosion then spawn the enemy somewhere else and reset bullet
                //showExplosion()
                //ship.x=s_width+1;
                //ship.y=s_height+1;
                int x=0;
                sound_pool.play(3,1,1,1,0,1f);

                exp1 = enemy1bullet.x;//eexp1 explosion values
                exp2 = enemy1bullet.y;

                shiphitcount++;
                resetEnemyBullet();


            }

            if(enemy2bullet.x < ship.x && enemy2bullet.x < ship.x+allsprites.shipSize.width()*4 &&
                    enemy2bullet.y > (int)yTouch  && enemy2bullet.y < (int)yTouch +allsprites.shipSize.height()*4 )
            {
                //show explosion then spawn the enemy somewhere else and reset bullet
                //showExplosion()
                //ship.x=s_width+1;
                //ship.y=s_height+1;
                int x=0;
                sound_pool.play(3,1,1,1,0,1f);

                exp1 = enemy2bullet.x;//eexp1 explosion values
                exp2 = enemy2bullet.y;
                shiphitcount++;

                resetEnemyBullet1();


            }

            //check bullt for enemy 2
            //else if

            //check bullet for meteor1
            //else if
            //check bullet for meteor2
            //else if
            //check bullet for meteor3
            //else if
        }




        public void moveEnemy1()//set a new random location after 1 second
        {

            Random r = new Random();
            double shipvaluex = r.nextInt(10-6)+6;
            double shipvaluey=r.nextInt(10);
            shipvaluex=shipvaluex*0.1;
            shipvaluey=shipvaluey*0.1;
            enemy1.x =(int)(s_width*shipvaluex);
            enemy1.y = (int)(s_height*shipvaluey);

        }
        public void moveEnemy2()//set a new random location after 1 second
        {

            Random r = new Random();
            double shipvaluex = r.nextInt(10-6)+6;
            double shipvaluey=r.nextInt(10);
            shipvaluex=shipvaluex*0.1;
            shipvaluey=shipvaluey*0.1;
            enemy2.x =(int)(s_width*shipvaluex);
            enemy2.y = (int)(s_height*shipvaluey);

        }
        public void startGame()
        {
            gameThread.start();
        }
        public void gameDone(){
            change = false;
            //clean the surface and show the menu by removing fullscreen
        }
        // three methods for the surfaceview
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {

        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int pixelFormat, int width, int height) {
            s_width = width;
            s_height = height;
            enemy1.x =(int)(s_width*.8f);
            enemy1.y = (int)(s_height*.5f);
            enemy2.x=(int)(s_width*.5f);
            enemy2.y = (int)(s_height*.5f);
            //shipbullet.x = s_width - (allsprites.shipSize.width()*4 +allsprites.shipBulletSprite.width()*4);
            //add  the enemy bullets
            exp1= s_width * .1f;
            exp2=s_height * .7f ;
            ship.x=s_width - allsprites.shipSize.width() * 30;
            //ship.y=(int)yTouch;
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        }
    }




}
