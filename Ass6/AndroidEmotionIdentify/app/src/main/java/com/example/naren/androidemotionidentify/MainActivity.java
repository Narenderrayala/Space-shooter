package com.example.naren.androidemotionidentify;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
//import android.support.v4.app.ActivityCompatApi23;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.microsoft.projectoxford.emotion.EmotionServiceClient;
import com.microsoft.projectoxford.emotion.EmotionServiceRestClient;
import com.microsoft.projectoxford.emotion.contract.RecognizeResult;
import com.microsoft.projectoxford.emotion.contract.Scores;
import com.microsoft.projectoxford.emotion.rest.EmotionServiceException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.jar.Manifest;

import static android.R.id.list;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    Button btnTakePicture, btnProcess;
    EmotionServiceClient restClient=new EmotionServiceRestClient("91595f977c9140b5bc4ce3dd92d1885f");
    int TAKE_PICTURE_CODE=100,REQUEST_PERMISSION_CODE=101;
    Bitmap mBitmap;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       if(requestCode==REQUEST_PERMISSION_CODE)
       {
           if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
               Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
           else
               Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();

       }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        //min sdk changed in build check actually it is 15
            if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED&&
                checkSelfPermission(android.Manifest.permission.WRITE_CONTACTS)!= PackageManager.PERMISSION_GRANTED)
        {

            requestPermissions(new String[]{
                   android. Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.INTERNET
            },REQUEST_PERMISSION_CODE);
        }



    }

    private void initViews() {
        btnTakePicture=(Button)findViewById(R.id.btnTakePic);
        btnProcess=(Button)findViewById(R.id.btnProcess);
        imageView=(ImageView)findViewById(R.id.imageview);
         btnTakePicture.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View v)
             {
                 takePicFromGallery();
             }



         });

        btnProcess.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                processImage();
            }

        });
    }

    private void processImage() {
        //convert image to stream
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        final ByteArrayInputStream inputStream=new ByteArrayInputStream(outputStream.toByteArray());
        //create async task to process data
        AsyncTask<InputStream,String,List<RecognizeResult>>processAsync=new AsyncTask<InputStream, String, List<RecognizeResult>>() {
           ProgressDialog mDialog=new ProgressDialog(MainActivity.this);

            @Override
            protected void onPreExecute() {
                mDialog.show();
            }

            @Override
            protected void onProgressUpdate(String... values) {
                mDialog.setMessage(values[0]);
            }

            //params replaced with input streams
            @Override
            protected List<RecognizeResult> doInBackground(InputStream... inputStreams) {
               publishProgress("Please wait...");
                List<RecognizeResult>result= null;
                try {
                    result = restClient.recognizeImage(inputStreams[0]);
                } catch (EmotionServiceException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(List<RecognizeResult> recognizeResults) {
                mDialog.dismiss();
                for(RecognizeResult res:recognizeResults )
                {
                    String status=getEmotion(res);
                    imageView.setImageBitmap(ImageHelper.drawRectOnBitmap(mBitmap,res.faceRectangle,status));

                }
            }
        };
        processAsync.execute(inputStream);
    }

    private String getEmotion(RecognizeResult res) {
        List <Double> list=new ArrayList<>();
         Scores scores=res.scores;
            list.add(scores.anger);
            list.add(scores.happiness);
            list.add(scores.contempt);
            list.add(scores.disgust);
            list.add(scores.fear);
        list.add(scores.neutral);
        list.add(scores.sadness);
        list.add(scores.surprise);
        //sort list
        Collections.sort(list);
        //get max value from list

        double maxNum=list.get(list.size()-1);

        if(maxNum==scores.anger)
            return "Anger";
        else if(maxNum==scores.happiness)
            return "Happy";
        else if(maxNum==scores.contempt)
            return "Contempt";
        else if(maxNum==scores.disgust)
            return "Disgust";
        else if(maxNum==scores.fear)
            return "Fear";
        else if(maxNum==scores.neutral  )
            return "Neutral";
        else if(maxNum==scores.sadness)
            return "Sad";
        else if(maxNum==scores.surprise)
            return "Suprised";
        else
            return "can't detect";





    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==TAKE_PICTURE_CODE)
        {
            Uri selectedImageUri=data.getData();
            InputStream in=null;
            try {
                in=getContentResolver().openInputStream(selectedImageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            mBitmap= BitmapFactory.decodeStream(in);
            imageView.setImageBitmap(mBitmap);

        }
    }

    private void takePicFromGallery() {
        Intent intent= new Intent();
         intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,TAKE_PICTURE_CODE);

    }
}
