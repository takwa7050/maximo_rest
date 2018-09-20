package com.nodomain.gercor.maximo_rest;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void button_save(View view){
        EditText username = findViewById(R.id.editText_username);
        String user = username.getText().toString();
        EditText password = findViewById(R.id.editText_password);
        String pass = password.getText().toString();
        if (!user.equals("") && !pass.equals("")){
            System.out.println("Usuario: " + user + ". Password: " + pass);
        }
        else{
            System.out.println("Usuario o clave vacios");
        }
    }

    /////////////////////////// INTERNAL MEMORY SAVE CODE ////////////////////////////
    @SuppressWarnings("unused") private String inMemoryGet(String key){SharedPreferences data = getSharedPreferences("inMemoryData", Context.MODE_PRIVATE);return data.getString(key, "");}
    @SuppressWarnings("unused") private void   inMemorySet(String key, String value){SharedPreferences data = getSharedPreferences("inMemoryData", Context.MODE_PRIVATE); SharedPreferences.Editor editor = data.edit(); editor.putString(key, value); editor.apply();}
    @SuppressWarnings("unused") private void   inMemoryRemove(String key){ SharedPreferences data = getSharedPreferences("inMemoryData", Context.MODE_PRIVATE);SharedPreferences.Editor editor = data.edit(); editor.remove(key); editor.apply(); }
    /////////////////////////////END INTERNAL MEMORY SAVE CODE ///////////////////////

}
