package com.example.naren.androidemotionidentify;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.microsoft.projectoxford.emotion.contract.FaceRectangle;

import java.util.BitSet;

/**
 * Created by naren on 22-09-2017.
 */

public class ImageHelper {
    public static Bitmap drawRectOnBitmap(Bitmap mBitmap, FaceRectangle faceRectangle, String status )
    {
        Bitmap bitmap=mBitmap.copy(Bitmap.Config.ARGB_8888,true);
        Canvas canvas=new Canvas(bitmap);
        Paint paint=new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(8);

        canvas.drawRect(faceRectangle.left,
                faceRectangle.top,
                faceRectangle.left+faceRectangle.width,
                faceRectangle.top+faceRectangle.height,paint);
        int cX=faceRectangle.left+faceRectangle.width;
        int cY =faceRectangle.top+faceRectangle.height;
        drawTextonBitmap(canvas,50,cX/2+cX/5,cY+70,Color.WHITE,status );
        return bitmap;
    }

    private static void drawTextonBitmap(Canvas canvas, int textSize, int cX, int cY, int color, String status) {

        Paint paint=new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        paint.setTextSize(textSize);
        canvas.drawText(status,cX,cY,paint);




    }
}
