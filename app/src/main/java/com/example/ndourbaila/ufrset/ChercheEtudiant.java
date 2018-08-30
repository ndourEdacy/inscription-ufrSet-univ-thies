package com.example.ndourbaila.ufrset;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

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
 * Created by ndourbaila on 16/11/2017.
 */
public class ChercheEtudiant extends Activity{
    ArrayList<Etudiant> EtudiantAdapter;ListView listView;
    Button modif,sup;
    AlertDialog.Builder alertDialog ,alertmodif ;
    String ine;
    Etudiant etudiant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.etudiant_chercher);
        listView= (ListView)findViewById(R.id.listview2);
           ine = getIntent().getStringExtra("ine");
        BackgroundWorker backgroundWorker = new BackgroundWorker(ChercheEtudiant.this);
        backgroundWorker.execute("chercher",ine);

        modif = (Button)findViewById(R.id.idmodifier);
        sup = (Button)findViewById(R.id.idsupprimer);


        alertDialog = new AlertDialog.Builder(this).setTitle("Attention")
                     .setMessage("Voulez vous supprimer cet etudiant")
                     .setPositiveButton("annuler",new DialogInterface.OnClickListener(){

                         @Override
                         public void onClick(DialogInterface dialog, int which) {

                         }
                     })
                     .setNegativeButton("ok",new DialogInterface.OnClickListener(){

            @Override
                        public void onClick(DialogInterface dialog, int which) {
                                    BackgroundWorker backgroundWorker = new BackgroundWorker(ChercheEtudiant.this);

                                    backgroundWorker.execute("supprimer",ine);
                          }
                    });




        sup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });
        modif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChercheEtudiant.this,Modification.class);
                intent.putExtra("ine",ine);
                intent.putExtra("nom",etudiant.getNom());
                intent.putExtra("prenom",etudiant.getPrenom());
                intent.putExtra("niveau",etudiant.getNiveau());
                intent.putExtra("filiere",etudiant.getFiliere());
                intent.putExtra("adresse",etudiant.getAdresse());

                startActivity(intent);

            }
        });
    }
    private class BackgroundWorker extends AsyncTask<String,Void,String> {


        Context context;
        AlertDialog alertDialog;
        BackgroundWorker(Context ctx){
            context = ctx;
        }
        protected String doInBackground(String... params) {

                String url_chercher = "http://192.168.56.1/inscription/chercher.php";
                String url_supprimer = "http://192.168.56.1/inscription/supprimer.php";
                String type = params[0];
                String ine = params[1];

            if(type.equals("chercher")){
                try {
                    URL url = new URL(url_chercher);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();

                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    String data = URLEncoder.encode("ine", "UTF-8")+"="+URLEncoder.encode(ine,"UTF-8");

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                    String result ="";
                    String line ="";

                    while ((line = bufferedReader.readLine()) != null){
                        result+=line;
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



            }
            else if(type.equals("supprimer")){
                try {
                    URL url = new URL(url_supprimer);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();

                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    String data = URLEncoder.encode("ine", "UTF-8")+"="+URLEncoder.encode(ine,"UTF-8");

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                    String result ="";
                    String line ="";

                    while ((line = bufferedReader.readLine()) != null){
                        result+=line;
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
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
          //  alertDialog.setMessage(result);
           // alertDialog.show();
               if(result.equals("errorch")){
                   alertDialog.setTitle("cet etudiant n'existe pas");
                   alertDialog.show();
                   finish();
               }
            if(result.equals("supprimer")){
                alertDialog.setTitle("cet etudiant supprimer avec succes");
                alertDialog.show();
                Intent intent = new Intent(ChercheEtudiant.this,Chercher.class);
                startActivity(intent);
            }
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("resulta");
                int taille = jsonArray.length();
                JSONObject jo;

                ArrayList<Etudiant> etudiants = new ArrayList<Etudiant>();

                for (int i = 0; i < taille; i++) {
                    jo = jsonArray.getJSONObject(i);
                    etudiant = new Etudiant();
                    etudiant.setNom(jo.getString("nom"));
                    etudiant.setIne(jo.getString("ine"));
                    etudiant.setPrenom(jo.getString("prenom"));
                    etudiant.setFiliere(jo.getString("fliere"));
                    etudiant.setNiveau(jo.getString("niveau"));
                    etudiant.setAdresse(jo.getString("adresse"));

                    etudiants.add(etudiant);
                }

                EtudiantAdapter listeEtudiant1 = new EtudiantAdapter(ChercheEtudiant.this, etudiants);
                listView.setAdapter(listeEtudiant1);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void onPreExecute() {
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("loginStatu");
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
