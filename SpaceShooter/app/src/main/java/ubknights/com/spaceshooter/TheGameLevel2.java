package ubknights.com.spaceshooter;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by carlos on 10/13/2016.
 */

public class TheGameLevel2 extends Activity implements View.OnTouchListener{

    TheView mySurfaceView;      //the surfaceview, where we draw
    TheSprites2 allsprites; //class that has all the location and sizes of the images in the sprite
    float xTouch,yTouch;          //the location when the screen is touched
    int s_width,s_height;       //the size of the surfaceview
    //used for which sprite to use
    int loc = 0,eLoc=0,e1Loc=3,e2Loc=0,m1Loc=0,m2Loc=1,m3Loc=2;
    Point shipbullet,ship,enemy1,enemy2,enemy1bullet,enemy2bullet,meteor1,meteor2,meteor3;
    long enemyMoveTime = 0;
    //15 frames per seconds
    float skipTime =1000.0f/30.0f; //setting 30fps
    long lastUpdate;
    float dt;
    float exp1=0,exp2=0;
    int count=0;
    int enemyhitcount=0;
    int meteorhit[]=new int [3];
    int shiphitcount=0;
    int meteor1movementx=0;
    int meteor1movementy=0;
    int meteor2movementx=0;
    int meteor2movementy=0;
    int meteor3movementx=0;
    int meteor3movementy=0;
    int valuemeteor=0;
    int valuemeteor2=0;
    boolean check=true;
    boolean check1=true;
    boolean check2=true;
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
        meteor1=new Point();
        meteor2=new Point();
        meteor3=new Point();


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
        SoundPool sound_pool ;
        Paint paint;
        String timer;
        AlertDialog.Builder build;
        AlertDialog done;
        MediaPlayer mp;
        public TheView(Context context) {
            super(context);
            //get this holder
            holder = getHolder();//gets the surfaceview surface
            holder.addCallback(this);

            paint = new Paint(Color.YELLOW);
            paint.setColor(Color.YELLOW);
            paint.setTextSize(50);
            sound_pool=new SoundPool(4, AudioManager.STREAM_MUSIC,0);
            HashMap<Integer,Integer> soundMap_H;
            soundMap_H=new HashMap<Integer,Integer>();
            mp= MediaPlayer.create(TheGameLevel2.this,R.raw.mainsong);

            soundMap_H.put(1,sound_pool.load(TheGameLevel2.this,R.raw.meteorboom,1));
            soundMap_H.put(2,sound_pool.load(TheGameLevel2.this,R.raw.gameover,1));
            soundMap_H.put(3,sound_pool.load(TheGameLevel2.this,R.raw.shipboom,1));
            soundMap_H.put(4,sound_pool.load(TheGameLevel2.this,R.raw.shipbullet,1));
            soundMap_H.put(5,sound_pool.load(TheGameLevel2.this,R.raw.success,1));
            soundMap_H.put(6,sound_pool.load(TheGameLevel2.this,R.raw.gameover,1));









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



                        place = new Rect((int) (exp1), (int) (exp2), (int) (exp1) + allsprites.boomSize[loc].width() * 14,
                                (int) (exp2) + allsprites.boomSize[loc].height() * 14);
                        c.drawBitmap(allsprites.space, allsprites.boomsprites[loc], place, null);


                        //draw the enemy 1
                        place = new Rect(enemy1.x, enemy1.y, enemy1.x + allsprites.enemySize.width() * 4,
                                enemy1.y + allsprites.enemySize.height() * 4);
                        // c.drawBitmap(allsprites.space, allsprites.enemy1sprites[e1Loc], place, null);
                        //draw the enemy 2
                        place = new Rect((int) (enemy2.x), (int) (enemy2.y), (int) (enemy2.x) + allsprites.enemySize.width() * 4,
                                (int) (enemy2.y) + allsprites.enemySize.height() * 4);
                        //c.drawBitmap(allsprites.space, allsprites.enemy2sprites[e2Loc], place, null);
                        //meteor1, using a percentage of the actual screen to draw it
                        place = new Rect((int) (meteor1.x), (int) (meteor1.y), (int) (meteor1.x) + (int) (s_width * .1),
                                (int) (meteor1.y) + (int) (s_width * .1));
                        c.drawBitmap(allsprites.space, allsprites.meteorsprites[m1Loc], place, null);
                        //meteor2, using a percentage of the actual screen to draw it
                        place = new Rect((int) (meteor2.x), (int) (meteor2.y), (int) (meteor2.x) + (int) (s_width * .1),
                                (int) (meteor2.y) + (int) (s_width * .1));
                        c.drawBitmap(allsprites.space, allsprites.meteorsprites[m2Loc], place, null);
                        //meteor3, using a percentage of the actual screen to draw it
                        place = new Rect((int) (meteor3.x), (int) (meteor3.y), (int) (meteor3.x) + (int) (s_width * .1),
                                (int) (meteor3.y) + (int) (s_width * .1));
                        c.drawBitmap(allsprites.space, allsprites.meteorsprites[m3Loc], place, null);
                        //ship shooting

                        place = new Rect((int) shipbullet.x, shipbullet.y, (int) shipbullet.x + allsprites.shipBulletSprite.width() * 4,
                                shipbullet.y + allsprites.shipBulletSprite.height() * 4);
                        c.drawBitmap(allsprites.space, allsprites.shipBulletSprite, place, null);


                        holder.unlockCanvasAndPost(c);

                        //move bullets and update sprites
                        shipbullet.x += s_width / 30;

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

                        valuemeteor++;
                        valuemeteor2++;
                       // if(meteor1.x>=s_width||meteor2.x>=s_width||meteor3.x>=s_width||meteor1.x<0||meteor2.x<0||meteor3.x<0)
                        generatemeterors();//generate meteors
                        generatemeterors1();
                        generatemeterors2();
                        movemeteor1();//code for moving meteros
                        if(valuemeteor2>=70) {
                            movemeteor2();

                            //valuemeteor2=0;

                        }
                        if(valuemeteor2>=90)
                        {
                            movemeteor3();
                        }
                        checkHitSpaceship();

                        //check if enemies bullet hit the ship

                        //check if
                        //check if ship bullet is out of screen
                        if (shipbullet.x > s_width) {
                            resetShipBullet();
                            sound_pool.play(4,1,1,1,0,1f);//playing ship sound
                        }

                        if(enemyhitcount>=10)
                        {
                            change=false;
                            mp.stop();
                            TheGameLevel2.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    sound_pool.play(5,1,1,1,0,1f);
                                    build=new AlertDialog.Builder(TheGameLevel2.this);
                                    build.setTitle("Game Success");
                                    build.setMessage("Time taken  =  " + String.valueOf(timer)+" Seconds");
                                    build.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                                        @Override
                                        public void onClick(DialogInterface dialog, int which)
                                        {
                                            Intent first=new Intent(TheGameLevel2.this,MainActivity.class);
                                            startActivity(first);


                                        }

                                    });
                                    done=build.create();
                                    done.show();
                                }
                            });



                        }

                        lastUpdate = System.currentTimeMillis();
                        //moveEnemy();
                    }

                    checkHitSpaceship();
                }
            }
        };

        public void resetShipBullet()
        {
            shipbullet.y =(int)yTouch +(allsprites.shipSize.height()*4/2)-allsprites.shipBulletSprite.height()*4/2;

            shipbullet.x = s_width - (allsprites.shipSize.width()*20 +allsprites.shipBulletSprite.width()*20);
        }



        public void checkHitEnemies()
        {
            exp1=s_width+1;
            exp2=s_height+1;
            //exp2=shipbullet.y;
            if(shipbullet.x > meteor1.x && shipbullet.x < meteor1.x+s_width*.1 &&
                    shipbullet.y > meteor1.y && shipbullet.y < meteor1.y+s_height*.2 )
            {
                //show explosion then spawn the enemy somewhere else and reset bullet
                //showExplosion()
                sound_pool.play(1,1,1,1,0,1f);
                meteorhit[0]++;
                if(meteorhit[0]==3) {
                    meteor1.x = s_width + 100;
                    meteor1.y = s_height + 100;
                    int x = 0;
                    enemyhitcount++;
                    exp1 = shipbullet.x;//eexp1 explosion values
                    exp2 = shipbullet.y;
                    meteorhit[0]=0;
                    generatemeterors();
                }

                resetShipBullet();
                //moveEnemy1();

            }

            if(shipbullet.x > meteor2.x && shipbullet.x < meteor2.x+s_width*.1 &&
                    shipbullet.y > meteor2.y && shipbullet.y < meteor2.y+s_height*.2 )
            {
                //show explosion then spawn the enemy somewhere else and reset bullet
                //showExplosion()
                sound_pool.play(1,1,1,1,0,1f);
                meteorhit[1]++;
                if(meteorhit[1]==3) {
                    meteor2.x = s_width + 100;
                    meteor2.y = s_height + 100;
                    int x = 0;

                    exp1 = shipbullet.x;//eexp1 explosion values
                    exp2 = shipbullet.y;
                    meteorhit[1]=0;
                    enemyhitcount++;
                    generatemeterors1();
                }

                resetShipBullet();
                //moveEnemy1();

            }

            if(shipbullet.x > meteor3.x && shipbullet.x < meteor3.x+s_width*.1 &&
                    shipbullet.y > meteor3.y && shipbullet.y < meteor3.y+s_height*.2 )
            {
                //show explosion then spawn the enemy somewhere else and reset bullet
                //showExplosion()
                sound_pool.play(1,1,1,1,0,1f);
                meteorhit[2]++;
                if(meteorhit[2]==3) {
                    meteor3.x = s_width + 100;
                    meteor3.y = s_height + 100;
                    int x = 0;

                    exp1 = shipbullet.x;//eexp1 explosion values
                    exp2 = shipbullet.y;
                    meteorhit[2]=0;
                    enemyhitcount++;
                    generatemeterors2();
                }

                resetShipBullet();
                //moveEnemy1();

            }

        }



        public void checkHitSpaceship() {
            if(meteor1.x<=ship.x||meteor2.x<=ship.x||meteor3.x<=ship.x)
            {

                mp.stop();
                sound_pool.play(6,1,1,1,0,1f);
                TheGameLevel2.this.runOnUiThread(new Runnable() {
                    public void run() {

                        build=new AlertDialog.Builder(TheGameLevel2.this);
                        change=false;
                        build.setTitle("Game Over");
                        build.setMessage("Time taken  =  " + String.valueOf(timer)+" Seconds");
                        build.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Intent first=new Intent(TheGameLevel2.this,MainActivity.class);
                                startActivity(first);


                            }

                        });
                        done=build.create();
                        done.show();
                    }
                });



            }
        }





        public void movemeteor1()
        {
            if(check==true) {

                ship.y=(int)yTouch;


                if(meteor1.y>ship.y) {
                    meteor1movementx = (meteor1.x - ship.x);
                    meteor1movementy = meteor1.y - ship.y;
                }
                if(meteor1.y<ship.y)
                {
                    meteor1movementx = (meteor1.x - ship.x);
                    meteor1movementy = ship.y-meteor1.y;
                }
                if(meteor1.y==ship.y)
                {
                    meteor1movementx = (meteor1.x - ship.x);
                    meteor1movementy = 0;
                }
                check=false;
            }
            // /meteor1.x=0;
            //meteor1.y=0;

            //meteor1.x=meteor1.x+meteor1movementx;
            //meteor1.y=meteor1.y+meteor1movementy;


            if(meteor1.y>ship.y) {



                meteor1.x-=(meteor1movementx)/90;
                meteor1.y-=(int)meteor1movementy/100;
            }
            else {

                meteor1.x-=(meteor1movementx)/90;
                meteor1.y+=(int)meteor1movementy/100;
            }

        }
        public void movemeteor2() {


            if (check1 == true) {

                ship.y = (int) yTouch;


                if (meteor2.y > ship.y) {
                    meteor2movementx = (meteor2.x - ship.x);
                    meteor2movementy = meteor2.y - ship.y;
                }
                if (meteor2.y < ship.y) {
                    meteor2movementx = (meteor2.x - ship.x);
                    meteor2movementy = ship.y - meteor2.y;
                }
                if (meteor2.y == ship.y) {
                    meteor2movementx = (meteor2.x - ship.x);
                    meteor2movementy = 0;
                }
                check1 = false;
            }
            // /meteor1.x=0;
            //meteor1.y=0;

            //meteor1.x=meteor1.x+meteor1movementx;
            //meteor1.y=meteor1.y+meteor1movementy;


            if (meteor2.y > ship.y) {

                meteor2.x -= (meteor2movementx) / 100;
                meteor2.y -= (int) meteor2movementy / 110;
            } else {

                meteor2.x -= (meteor2movementx) / 100;
                meteor2.y += (int) meteor2movementy / 110;
            }


        }


        public void movemeteor3() {


            if (check2 == true) {

                ship.y = (int) yTouch;


                if (meteor3.y > ship.y) {
                    meteor3movementx = (meteor3.x - ship.x);
                    meteor3movementy = meteor3.y - ship.y;
                }
                if (meteor3.y < ship.y) {
                    meteor3movementx = (meteor3.x - ship.x);
                    meteor3movementy = ship.y - meteor3.y;
                }
                if (meteor3.y == ship.y) {
                    meteor3movementx = (meteor3.x - ship.x);
                    meteor3movementy = 0;
                }
                check2 = false;
            }
            // /meteor1.x=0;
            //meteor1.y=0;

            //meteor1.x=meteor1.x+meteor1movementx;
            //meteor1.y=meteor1.y+meteor1movementy;


            if (meteor3.y > ship.y) {

                meteor3.x -= (meteor3movementx) / 110;
                meteor3.y -= (int) meteor3movementy / 120;
            } else {

                meteor3.x -= (meteor3movementx) / 110;
                meteor3.y += (int) meteor3movementy / 120;
            }


        }







        public void generatemeterors() {
            if (valuemeteor == 1) {

                Random r = new Random();
                double meteorvaluex = r.nextInt(10 - 8) + 8;
                double meteorvaluey = r.nextInt(8);
                meteorvaluex = meteorvaluex * 0.1;
                meteorvaluey = meteorvaluey * 0.1;
                meteor1.x = (int) (s_width * meteorvaluex);
                meteor1.y = (int) (s_height * meteorvaluey);
                check = true;
            }
        }
        public void generatemeterors1() {
            if (valuemeteor == 90) {

                //change=false;
                Random r = new Random();
                double meteorvaluex = r.nextInt(10 - 8) + 8;
                double meteorvaluey = r.nextInt(8);
                meteorvaluex = meteorvaluex * 0.1;
                meteorvaluey = meteorvaluey * 0.1;
                meteor3.x = (int) (s_width * meteorvaluex);
                meteor3.y = (int) (s_height * meteorvaluey);
                valuemeteor = 0;
                check2 = true;


            }

        }
        public void generatemeterors2() {
            if(valuemeteor==70)
            {
                /*Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {

                    }
                }, 2000);
                            */


                Random r = new Random();
                double meteorvaluex = r.nextInt(10 - 8) + 8;
                double meteorvaluey = r.nextInt(8);
                meteorvaluex = meteorvaluex * 0.1;
                meteorvaluey = meteorvaluey * 0.1;
                meteor2.x = (int) (s_width * meteorvaluex);
                meteor2.y = (int) (s_height * meteorvaluey);

                check1=true;


            }




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
            enemy2.x=(int)(s_width*.8f);
            enemy2.y = (int)(s_height*.5f);
            //shipbullet.x = s_width - (allsprites.shipSize.width()*4 +allsprites.shipBulletSprite.width()*4);
            //add  the enemy bullets
            exp1= s_width;
            exp2=s_height ;
            ship.x=s_width - allsprites.shipSize.width() * 30;
            meteor1.x=s_width+1000;
            meteor1.y=s_width+1000;
            meteor2.x=s_width+1000;
            meteor2.y=s_width+1000;
            meteor3.x=s_width+1000;
            meteor3.y=s_width+1000;
            //ship.y=(int)(s_height * .7f);
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        }
    }




}

