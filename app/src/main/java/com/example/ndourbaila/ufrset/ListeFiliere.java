package com.example.ndourbaila.ufrset;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
 * Created by ndourbaila on 15/11/2017.
 */
public class ListeFiliere extends Activity {
    ArrayList<Etudiant> listeEtudiant;ListView listView;

    public  String filiere;
    public  String niv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listefliere);
        listView= (ListView)findViewById(R.id.listView);
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        String fli = getIntent().getStringExtra("filiere");
        String niv = getIntent().getStringExtra("niveau");
        if(fli!= null && niv !=null)
        backgroundWorker.execute(niv,fli);
    }

    private class BackgroundWorker extends AsyncTask<String,Void,String> {


        Context context;
        AlertDialog alertDialog;
        BackgroundWorker(Context ctx){
            context = ctx;
        }
        protected String doInBackground(String... params) {

            String url_login = "http://192.168.56.1/inscription/liste.php";
            String niv = params[0];
            String fliere = params[1];

            try {
                URL url = new URL(url_login);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("niv","UTF-8")+"="+URLEncoder.encode(niv,"UTF-8")+"&"+
                        URLEncoder.encode("fliere","UTF-8")+"="+URLEncoder.encode(fliere,"UTF-8");
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

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if(result.equals("vide")){
                alertDialog.setMessage("Cette liste est vide");
                alertDialog.show();
                Intent intent = new Intent(ListeFiliere.this,Listes.class);
                startActivity(intent);
            }
            else{
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("resulta");
                    int taille = jsonArray.length();
                    JSONObject jo;
                    Etudiant etudiant;
                    ArrayList<Etudiant> etudiants = new ArrayList<Etudiant>();
                    Log.i("fghgfdfgh", result);
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

                    ListeEtudiant listeEtudiant1 = new ListeEtudiant(ListeFiliere.this, etudiants);
                    listView.setAdapter(listeEtudiant1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
