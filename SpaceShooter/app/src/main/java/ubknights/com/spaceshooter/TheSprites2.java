package ubknights.com.spaceshooter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

/**
 * Created by carlos on 10/12/2016.
 */

public class TheSprites2 {

    Sizes shipSize,enemySize,meteorSize;
    Sizes [] boomSize;//each boom sprite is different in size
    Bitmap space,back;   //a reference to the bitmap that has all the images
    Rect[] shipSprites,enemy1sprites,enemy2sprites,meteorsprites,boomsprites;
    Rect shipBulletSprite,enemybulletSprite;
    TheSprites2(Resources r)
    {
        space = BitmapFactory.decodeResource(r, R.drawable.ship);
        back = BitmapFactory.decodeResource(r, R.drawable.background);
        setSpacevalues();
    }

    public void setSpacevalues()
    {
        Values[] ship,enemy1,enemy2,meteor1,boom;//in the sprite
        Values shipbullet,enemybullet;
        boom = new Values[6];//set the 6 explosion sprites size and location
        for (int i=0;i < 6;i++)
            boom[i] = new Values();
        boom[0].xval = 124;
        boom[0].yval = 78;
        boom[0].width = 29;
        boom[0].height = 32;//
        boom[1].xval = 44;
        boom[1].yval = 80;
        boom[1].width = 32;
        boom[1].height = 37;
        boom[2].xval = 108;
        boom[2].yval = 133;
        boom[2].width = 35;
        boom[2].height =41;
        boom[3].xval = 67;
        boom[3].yval = 132;
        boom[3].width = 39;
        boom[3].height =44;
        boom[4].xval = 1;
        boom[4].yval = 80;
        boom[4].width = 41;
        boom[4].height =48;
        boom[5].xval = 78;
        boom[5].yval = 78;
        boom[5].width = 44;
        boom[5].height =52;
        boomSize = new Sizes[6];
        boomsprites = new Rect[6];
        for(int i = 0; i < 6;i++)//saving each rectangle "chunk" to use it.
        {
            Rect cut = new Rect(boom[i].x(),boom[i].y(),boom[i].x()+boom[i].width(),boom[i].y()+boom[i].width());
            boomsprites[i] = cut;
            boomSize[i] = new Sizes(boom[i].width,boom[i].height);
        }
        ////ship
        ship = new Values[4];
        for (int i=0;i < 4;i++)
            ship[i] = new Values();
        ship[0].xval = 1;
        ship[0].yval = 130;
        ship[1].xval = 1;
        ship[1].yval = 161;
        ship[2].xval = 145;
        ship[2].yval = 153;
        ship[3].xval = 67;
        ship[3].yval = 178;
        for (Values v:ship)
        {
            v.width=64;
            v.height=29;
        }

        shipSprites = new Rect[4];
        shipSize = new Sizes(64,29);
        for(int i = 0; i < 4;i++)
        {
            Rect cut = new Rect(ship[i].x(),ship[i].y(),ship[i].x()+ship[i].width(),ship[i].y()+ship[i].height());
            shipSprites[i] = cut;
        }
        //enemy1
        enemy1 = new Values[6];
        for (int i=0;i < 6;i++)
            enemy1[i] = new Values();
        enemy1[0].xval = 1;
        enemy1[0].yval = 226;
        enemy1[1].xval = 85;
        enemy1[1].yval = 209;
        enemy1[2].xval = 1;
        enemy1[2].yval = 192;
        enemy1[3].xval = 85;
        enemy1[3].yval = 242;
        enemy1[4].xval = 133;
        enemy1[4].yval = 184;
        enemy1[5].xval = 43;
        enemy1[5].yval = 243;
        for (Values v:enemy1)
        {
            v.width=40;
            v.height=31;
        }
        enemy1sprites = new Rect[6];
        enemySize = new Sizes(40,31);
        for(int i = 0; i <6;i++)
        {
            Rect cut = new Rect(enemy1[i].x(),enemy1[i].y(),enemy1[i].x()+enemy1[i].width(),enemy1[i].y()+enemy1[i].height());
            enemy1sprites[i] = cut;
        }
        //enemy 2
        enemy2 = new Values[6];
        for (int i=0;i < 6;i++)
            enemy2[i] = new Values();
        enemy2[0].xval = 127;
        enemy2[0].yval = 218;
        enemy2[1].xval = 127;
        enemy2[1].yval = 251;
        enemy2[2].xval = 175;
        enemy2[2].yval = 184;
        enemy2[3].xval = 169;
        enemy2[3].yval = 218;
        enemy2[4].xval = 43;
        enemy2[4].yval = 209;
        enemy2[5].xval = 169;
        enemy2[5].yval = 251;
        for (Values v:enemy2)
        {
            v.width=40;
            v.height=31;
        }
        enemy2sprites = new Rect[6];
        for(int i = 0; i <6;i++)
        {
            Rect cut = new Rect(enemy2[i].x(),enemy2[i].y(),enemy2[i].x()+enemy2[i].width(),enemy2[i].y()+enemy2[i].height());
            enemy2sprites[i] = cut;
        }
        //meteor 1
        meteor1 = new Values[4];
        for (int i=0;i < 4;i++)
            meteor1[i] = new Values();
        meteor1[0].xval = 156;
        meteor1[0].yval = 1;
        meteor1[1].xval = 1;
        meteor1[1].yval = 1;
        meteor1[2].xval = 78;
        meteor1[2].yval = 1;
        meteor1[3].xval = 156;
        meteor1[3].yval = 77;
        for (Values v:meteor1)
        {
            v.width=75;
            v.height=75;
        }
        meteorsprites = new Rect[4];
        meteorSize = new Sizes(75,75);
        for(int i = 0; i <4;i++)
        {
            Rect cut = new Rect(meteor1[i].x(),meteor1[i].y(),meteor1[i].x()+meteor1[i].width(),meteor1[i].y()+meteor1[i].height());
            meteorsprites[i] = cut;
        }
        //ship bullet
        shipbullet = new Values();
        shipbullet.xval = 128;
        shipbullet.yval = 111;
        shipbullet.width = 21;
        shipbullet.height = 7;
        shipBulletSprite = new Rect();
        shipBulletSprite.left = 128;
        shipBulletSprite.top = 111;
        shipBulletSprite.right = 128+21;
        shipBulletSprite.bottom = 111+7;
        //enemy bullet
        enemybullet = new Values();
        enemybullet.xval = 131;
        enemybullet.yval = 124;
        enemybullet.width = 7;
        enemybullet.height = 21;
        enemybulletSprite = new Rect();
        enemybulletSprite.left = 131;
        enemybulletSprite.top = 124;
        enemybulletSprite.right = 131+7;
        enemybulletSprite.bottom = 124+21;
    }
    public class Sizes
    {
        private int width = 0,height = 0;
        Sizes(int xx,int yy)
        {
            width = xx;
            height = yy;
        }

        int width()
        {
            return width;
        }
        int height()
        {
            return height;
        }
    }
    public class Values
    {
        private int xval=0,yval=0,width=0,height=0;

        int x()
        {
            return xval;
        }
        int y()
        {
            return yval;
        }

        int width()
        {
            return width;
        }
        int height()
        {
            return height;
        }
    }
}
