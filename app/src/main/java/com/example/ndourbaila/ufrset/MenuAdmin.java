package com.example.ndourbaila.ufrset;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by ndourbaila on 14/11/2017.
 */
public class MenuAdmin extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button inscrir = (Button)findViewById(R.id.binscri_etud);
        Button liste = (Button)findViewById(R.id.bliste);
        Button chercher = (Button)findViewById(R.id.bcherche);
        Button quit = (Button)findViewById(R.id.bquit);

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(MenuAdmin.this,MenuAdmin.class));
            }
        });


        inscrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inscription= new Intent(MenuAdmin.this,Inscription.class);

                startActivity(inscription);
            }
        });

        liste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent liste = new Intent(MenuAdmin.this,Listes.class);
                startActivity(liste);
            }
        });

        chercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent liste = new Intent(MenuAdmin.this,Chercher.class);
                startActivity(liste);
            }
        });
    }
}
