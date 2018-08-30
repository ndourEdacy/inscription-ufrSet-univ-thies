package com.example.ndourbaila.ufrset;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
 * Created by ndourbaila on 18/11/2017.
 */
public class ModifierEtudiant extends Activity {
      Button valid,annul;
      ListView listView;
    String ine,nom_mod,prenom_mod,adresse_modif,niveau_modif,filiere_modif;
    Etudiant etudiant;
    EditText nom_modif,prenom_modif,ine_modif;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modif_form);
        listView = (ListView)findViewById(R.id.listView_modif);
        ine = getIntent().getStringExtra("ine");
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute("modif",ine);
        valid  =(Button)findViewById(R.id.valid_mdif);
        annul = (Button)findViewById(R.id.annul_modif);
        annul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModifierEtudiant.this,ChercheEtudiant.class);
                //intent.putExtra("ine",ine);
                startActivity(intent);
            }
        });
     ine_modif = (EditText)findViewById(R.id.ine_modif);
        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = etudiant.getNom();
                String prenom = etudiant.getPrenom();
                String filiere = etudiant.getFiliere();
                String adresse = etudiant.getAdresse();
                String ine1  = "baila";
                String niveau  = etudiant.getNiveau();

                BackgroundWorker backgroundWorker1 = new BackgroundWorker(ModifierEtudiant.this);
                backgroundWorker1.execute("modif_valid",ine,nom,prenom,adresse,niveau,filiere,ine1);

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
            String url_modif = "http://192.168.56.1/inscription/modifier.php";


            String type = params[0];
            String ine = params[1];


                if(type.equals("modif")){
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
            else{
                    String nom = params[2];
                    String prenom = params[3];
                    String adresse = params[4];
                    String niveau = params[5];
                    String filiere = params[6];
                    String ine1 = params[7];
                    try {
                        URL url = new URL(url_modif);
                        HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput(true);

                        OutputStream outputStream = httpURLConnection.getOutputStream();

                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                        String data = URLEncoder.encode("ine", "UTF-8")+"="+URLEncoder.encode(ine,"UTF-8")+'&'+
                                        URLEncoder.encode("nom", "UTF-8")+"="+URLEncoder.encode(nom,"UTF-8")+'&'+
                                        URLEncoder.encode("prenom", "UTF-8")+"="+URLEncoder.encode(prenom,"UTF-8")+'&'+
                                        URLEncoder.encode("adresse", "UTF-8")+"="+URLEncoder.encode(adresse,"UTF-8")+'&'+
                                        URLEncoder.encode("niveau", "UTF-8")+"="+URLEncoder.encode(niveau,"UTF-8")+'&'+
                                        URLEncoder.encode("filiere", "UTF-8")+"="+URLEncoder.encode(filiere,"UTF-8")+'&'+
                                        URLEncoder.encode("inemodif", "UTF-8")+"="+URLEncoder.encode(ine1,"UTF-8")
                                       ;

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
            alertDialog.setMessage(result);
            alertDialog.show();

         /*   if(result.equals("supprimer")){
                Intent intent = new Intent(ModifierEtudiant.this,Chercher.class);
                startActivity(intent);
            }*/
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

                ModifAdapter listeEtudiant1 = new ModifAdapter(ModifierEtudiant.this, etudiants);
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
