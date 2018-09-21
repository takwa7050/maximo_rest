package com.nodomain.gercor.maximo_rest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!inMemoryGet("username").equals("") && !inMemoryGet("password").equals("")){
            postLogin();
        }
    }

    public void button_save(View view){
        EditText username = findViewById(R.id.editText_username);
        String user = username.getText().toString();
        EditText password = findViewById(R.id.editText_password);
        String pass = password.getText().toString();
        if (!user.equals("") && !pass.equals("")){
            inMemorySet("username",user);
            inMemorySet("password",pass);
            postLogin();
        }
        else{
            Toast.makeText(MainActivity.this,"Username & Password required", Toast.LENGTH_LONG).show();
        }
    }

    private void postLogin(){
        Intent intent = new Intent(this, PostLoginScreen.class);
        startActivity(intent);
    }

    /////////////////////////// INTERNAL MEMORY SAVE CODE ////////////////////////////
    @SuppressWarnings("unused") private String inMemoryGet(String key){SharedPreferences data = getSharedPreferences("inMemoryData", Context.MODE_PRIVATE);return data.getString(key, "");}
    @SuppressWarnings("unused") private void   inMemorySet(String key, String value){SharedPreferences data = getSharedPreferences("inMemoryData", Context.MODE_PRIVATE); SharedPreferences.Editor editor = data.edit(); editor.putString(key, value); editor.apply();}
    @SuppressWarnings("unused") private void   inMemoryRemove(String key){ SharedPreferences data = getSharedPreferences("inMemoryData", Context.MODE_PRIVATE);SharedPreferences.Editor editor = data.edit(); editor.remove(key); editor.apply(); }
    /////////////////////////////END INTERNAL MEMORY SAVE CODE ///////////////////////

}
