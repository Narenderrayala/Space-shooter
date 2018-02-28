package com.example.naren.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.net.URI;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

        public static void main(String[]args)
        {
            HttpClient httpClient = new DefaultHttpClient();

            try
            {
                // NOTE: You must use the same region in your REST call as you used to obtain your subscription keys.
                //   For example, if you obtained your subscription keys from westcentralus, replace "westus" in the
                //   URL below with "westcentralus".
                URIBuilder uriBuilder = new URIBuilder("https://westus.api.cognitive.microsoft.com/emotion/v1.0/recognize");

                URI uri = uriBuilder.build();
                HttpPost request = new HttpPost(uri);

                // Request headers. Replace the example key below with your valid subscription key.
                request.setHeader("Content-Type", "application/json");
                request.setHeader("Ocp-Apim-Subscription-Key", "91595f977c9140b5bc4ce3dd92d1885f");

                // Request body. Replace the example URL below with the URL of the image you want to analyze.
                StringEntity reqEntity = new StringEntity("{ \"url\": \"http://dreamatico.com/data_images/people/people-2.jpg\" }");
                request.setEntity(reqEntity);

                HttpResponse response = httpClient.execute(request);
                HttpEntity entity = response.getEntity();

                if (entity != null)
                {
                    System.out.println(EntityUtils.toString(entity));
                }
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }

}