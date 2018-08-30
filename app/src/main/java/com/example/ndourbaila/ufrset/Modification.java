package com.example.ndourbaila.ufrset;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by ndourbaila on 19/11/2017.
 */
public class Modification extends Activity {
    EditText nom, prenom, adresse, filiere, ine, niveau;
    Button valider, annuler;
    String ine_modif,nom_modif, prenom_modif, adresse_modif, filiere_modif, niveau_modif,ine1;
    AlertDialog.Builder alerBuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificatin);
        nom = (EditText) findViewById(R.id.modifnom);
        prenom = (EditText) findViewById(R.id.modifprenom);
        ine = (EditText) findViewById(R.id.modifIne);
        filiere = (EditText) findViewById(R.id.modiffiliere);
        niveau = (EditText) findViewById(R.id.modifniveau);
        adresse = (EditText) findViewById(R.id.modifadresse);

        nom.setText(getIntent().getStringExtra("nom"));
        ine.setText(getIntent().getStringExtra("ine"));
        prenom.setText(getIntent().getStringExtra("prenom"));
        filiere.setText(getIntent().getStringExtra("filiere"));
        niveau.setText(getIntent().getStringExtra("niveau"));
        adresse.setText(getIntent().getStringExtra("adresse"));


        ine_modif = getIntent().getStringExtra("ine");

        alerBuilder = new AlertDialog.Builder(this).setTitle("Attention")
                .setMessage("Voulez vous modifier cet etudiant")
                .setPositiveButton("annuler",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("ok",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String m_nom = nom.getText().toString();
                        String m_ine = ine.getText().toString();
                        String m_prenom = prenom.getText().toString();
                        String m_filiere = filiere.getText().toString();
                        String m_niveau = niveau.getText().toString();
                        String m_adresse = adresse.getText().toString();
                        BackgroundWorker backgroundWorker = new BackgroundWorker(Modification.this);
                        backgroundWorker.execute(m_ine,m_nom,m_prenom,m_adresse,m_niveau,m_filiere,ine_modif);
                    }
                });

        valider = (Button) findViewById(R.id.modifvalid);
        annuler = (Button) findViewById(R.id.modifannul);
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alerBuilder.show();
            }
        });
    }

    private class BackgroundWorker extends AsyncTask<String, Void, String> {


        Context context;
        AlertDialog alertDialog;

        BackgroundWorker(Context ctx) {
            context = ctx;
        }

        protected String doInBackground(String... params) {

            String url_chercher = "http://192.168.56.1/inscription/chercher.php";
            String url_modif = "http://192.168.56.1/inscription/modifier.php";


            String ine = params[0];
            String nom = params[1];
            String prenom = params[2];
            String adresse = params[3];
            String niveau = params[4];
            String filiere = params[5];
            String ine1 = params[6];
            try {
                URL url = new URL(url_modif);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("ine", "UTF-8") + "=" + URLEncoder.encode(ine, "UTF-8") + '&' +
                        URLEncoder.encode("nom", "UTF-8") + "=" + URLEncoder.encode(nom, "UTF-8") + '&' +
                        URLEncoder.encode("prenom", "UTF-8") + "=" + URLEncoder.encode(prenom, "UTF-8") + '&' +
                        URLEncoder.encode("adresse", "UTF-8") + "=" + URLEncoder.encode(adresse, "UTF-8") + '&' +
                        URLEncoder.encode("niveau", "UTF-8") + "=" + URLEncoder.encode(niveau, "UTF-8") + '&' +
                        URLEncoder.encode("filiere", "UTF-8") + "=" + URLEncoder.encode(filiere, "UTF-8") + '&' +
                        URLEncoder.encode("inemodif", "UTF-8") + "=" + URLEncoder.encode(ine1, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if(result.equals("modifier")){
                alertDialog.setMessage("Etudiant modifier avec succes");
                alertDialog.setTitle("alert");
                alertDialog.show();

                Intent intent = new Intent(Modification.this,ChercheEtudiant.class);
                intent.putExtra("ine",ine.getText().toString());
                startActivity(intent);

            }
         /*   if(result.equals("supprimer")){
                Intent intent = new Intent(ModifierEtudiant.this,Chercher.class);
                startActivity(intent);
            }*/


        }

        @Override
        protected void onPreExecute() {
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("loginStatu");
        }

    }
}
