package com.prashant.notes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import org.w3c.dom.Text;

public class Editor extends AppCompatActivity {

    EditText text;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Intent intent=getIntent();
        index=intent.getIntExtra("index",-1);
        text=findViewById(R.id.editText);

        if(index!=-1){  /* index other than -1 means that user has reached this activity by tapping on already
                            present element in array list */
            text.setText(MainActivity.list.get(index));
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(index==-1){  // -1 means that user has reached this activity by tapping New Note option
            MainActivity.list.add(String.valueOf(text.getText()));
            MainActivity.arrayAdapter.notifyDataSetChanged();
            MainActivity.save();
        }
        else{   /* index other than -1 means that user has reached this activity by tapping on already
                   present element in array list */
            MainActivity.list.set(index,String.valueOf(text.getText()));
            MainActivity.arrayAdapter.notifyDataSetChanged();
            MainActivity.save();
        }
    }
}
