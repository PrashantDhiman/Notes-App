package com.prashant.notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> list=new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    Intent intent;
    static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences=this.getSharedPreferences("com.prashant.notes",MODE_PRIVATE);

        ListView listView=(ListView) findViewById(R.id.listView);

        try {
            list=(ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("list",ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent=new Intent(getApplicationContext(),Editor.class);
                intent.putExtra("index",position);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Do you want to delete this?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                list.remove(position);
                                arrayAdapter.notifyDataSetChanged();
                                save();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();

                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        intent=new Intent(getApplicationContext(),Editor.class);
        intent.putExtra("index",-1);
        startActivity(intent);
        return true;

    }

    public static int save(){  //this function updates the sharedpreferneces
        try {
            sharedPreferences.edit().putString("list",ObjectSerializer.serialize(list)).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
