package com.example.ndourbaila.ufrset;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

/**
 * Created by ndourbaila on 03/11/2017.
 */
public class Inscription1 extends Activity {
    private static String fliere[] = new String[]{"LGI","LMI","LPC"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription1);
        AutoCompleteTextView auto = (AutoCompleteTextView)findViewById(R.id.fliere);

        auto.setThreshold(1);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,fliere);

        auto.setAdapter(adapter);


    }
}
