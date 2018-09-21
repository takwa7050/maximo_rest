package com.nodomain.gercor.maximo_rest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import android.util.Base64;

public class PostLoginScreen extends AppCompatActivity {

    private ProgressDialog mProgress;
    private HttpURLConnection myConnection;
    private InputStream responseBody;
    private String userCredentials;
    private String basicAuth;
    private String url;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_login_screen);
    }

    protected void onStart() {
        super.onStart();
        if(inMemoryGet("username").equals("") || inMemoryGet("password").equals("")){
            goLogin();
        }
        else{
            userCredentials = inMemoryGet("username") + ":" + inMemoryGet("password");
            try {
                basicAuth = Base64.encodeToString(userCredentials.getBytes("UTF-8"), Base64.DEFAULT);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            url = "http://200.110.132.176/maxrest/rest/mbo/person/?personid=" + inMemoryGet("username");
        }
    }

    public void button_send(View view){
        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Loading ...");
        mProgress.setCanceledOnTouchOutside(false);
        mProgress.setCancelable(false);
        mProgress.show();

        AsyncTask.execute(new Runnable(){@Override public void run(){
            try {
                //Thread.sleep(2000);
                URL endPoint = new URL(url);
                myConnection = (HttpURLConnection) endPoint.openConnection();
                myConnection.setRequestMethod("GET");
                myConnection.setRequestProperty("User-Agent", "my-rest-app-v0.1");
                myConnection.setRequestProperty ("MAXAUTH", basicAuth);
                if (myConnection.getResponseCode() == 200) {
                    responseBody = myConnection.getInputStream();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProgress.dismiss();
                            TextView textView_sendResult = findViewById(R.id.textView_sendResult);
                            textView_sendResult.setText(responseBody.toString());
                        }
                    });

                }
                else{
                    System.out.println(url);
                    System.out.println(userCredentials);}
                    System.out.println(basicAuth.toString());
                    System.out.println(myConnection.getResponseCode());
                }
              catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }});
    }

    public void button_newLogin(View view){
        inMemoryRemove("username");
        inMemoryRemove("password");
        goLogin();
    }

    private void goLogin(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /////////////////////////// INTERNAL MEMORY SAVE CODE ////////////////////////////
    @SuppressWarnings("unused") private String inMemoryGet(String key){SharedPreferences data = getSharedPreferences("inMemoryData", Context.MODE_PRIVATE);return data.getString(key, "");}
    @SuppressWarnings("unused") private void   inMemorySet(String key, String value){SharedPreferences data = getSharedPreferences("inMemoryData", Context.MODE_PRIVATE); SharedPreferences.Editor editor = data.edit(); editor.putString(key, value); editor.apply();}
    @SuppressWarnings("unused") private void   inMemoryRemove(String key){ SharedPreferences data = getSharedPreferences("inMemoryData", Context.MODE_PRIVATE);SharedPreferences.Editor editor = data.edit(); editor.remove(key); editor.apply(); }
    /////////////////////////////END INTERNAL MEMORY SAVE CODE ///////////////////////
}
