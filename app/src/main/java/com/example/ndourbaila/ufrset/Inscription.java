package com.example.ndourbaila.ufrset;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ndourbaila on 24/10/2017.
 */
public class Inscription extends Activity{
    TextView nom,prenom,filiere,email,ine,niveau,adresse;String sexe;
    private static String fliere[] = new String[]{"LGI","LMI","LPC","LSEE"};
    private static String nive[] = new String[]{"L1","L2","L3","M1","M2"};
    RadioGroup radioGroup;
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_inscription);

      Button suiv =(Button)findViewById(R.id.bsuiv);
      Button retour =(Button)findViewById(R.id.bretour);
      nom =(TextView)findViewById(R.id.idnom);
      adresse =(TextView)findViewById(R.id.idadresse);
      prenom =(TextView)findViewById(R.id.idPrenom);
      filiere =(AutoCompleteTextView)findViewById(R.id.idfliere);
      niveau =(AutoCompleteTextView)findViewById(R.id.niv);
      email=(TextView)findViewById(R.id.idmail);
      ine=(TextView)findViewById(R.id.idine);
      radioGroup = (RadioGroup)findViewById(R.id.sexe);
      if(radioGroup.getCheckedRadioButtonId() == R.id.homme){
          sexe="homme";
      }
      else{
          sexe="femme";
      }
      AutoCompleteTextView auto = (AutoCompleteTextView)findViewById(R.id.idfliere);
      AutoCompleteTextView autoniv = (AutoCompleteTextView)findViewById(R.id.niv);

      auto.setThreshold(0); autoniv.setThreshold(0);

      ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,fliere);

      auto.setAdapter(adapter);
      ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,nive);

      autoniv.setAdapter(adapter1);

      suiv.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String etud_nom = nom.getText().toString();
              String etud_prenom = prenom.getText().toString();
              String etud_fliere = filiere.getText().toString();
              String etud_email = email.getText().toString();
              String etud_ine = ine.getText().toString();
              String etud_niv = niveau.getText().toString();
              String etud_ad = adresse.getText().toString();

              BackgroundWorker backgroundWorker = new BackgroundWorker(Inscription.this);
              String type ="inscription";
              backgroundWorker.execute(type,etud_nom,etud_prenom,etud_fliere,etud_email,etud_ine,sexe,etud_niv,etud_ad);
              nom.setText("");
              prenom.setText("");
              email.setText("");
              adresse.setText("");
              ine.setText("");
             // Intent myIntent = new Intent(Inscription.this,Inscription1.class);
              //startActivity(myIntent);

          }
      });

      retour.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              Intent intent = new Intent(Inscription.this,MenuAdmin.class);

              startActivity(intent);

                /*BackgroundWorker backgroundWorker = new BackgroundWorker(Chercher.this);
                backgroundWorker.execute(type,ine_etud);*/
          }
      });


  }

}
