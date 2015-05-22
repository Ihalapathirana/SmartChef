package com.example.anu.cook;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;




/**
 * Created by Anu on 4/8/2015.
 */
public class WebService extends AsyncTask<String,Void,String> {

    InputStream inputStream;
    String response;


    @Override
    protected String doInBackground(String... params) {
        DefaultHttpClient defaultHttpClient=new DefaultHttpClient();
        String url=params[0].toString();

        HttpGet httpGet =  new HttpGet(url);
        try {
            HttpResponse httpResponse = defaultHttpClient.execute(httpGet);
            HttpEntity httpEntity=httpResponse.getEntity();
            inputStream=httpEntity.getContent();

            BufferedReader reader;
            reader = new BufferedReader(new InputStreamReader(
                    inputStream,"iso-8859-1"),8);
            StringBuilder sb=new StringBuilder();
            String line=null;

            while((line=reader.readLine())!=null){
                sb.append(line+"");
            }
            response=sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}
