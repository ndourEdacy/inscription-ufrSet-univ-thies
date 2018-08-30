package com.example.ndourbaila.ufrset;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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
public class Chercher extends Activity {

    TextView inech;String ine_etud;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cherche);
         Button cherche = (Button)findViewById(R.id.idineCh);
        Button retour = (Button)findViewById(R.id.annulch);
         inech = (TextView)findViewById(R.id.inech);

        cherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ine_etud = inech.getText().toString();


                BackgroundWorker backgroundWorker = new BackgroundWorker(Chercher.this);
                backgroundWorker.execute(ine_etud);

            }
        });

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Chercher.this,MenuAdmin.class);

                startActivity(intent);

                /*BackgroundWorker backgroundWorker = new BackgroundWorker(Chercher.this);
                backgroundWorker.execute(type,ine_etud);*/
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

            String ine = params[0];


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






            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            //  alertDialog.setMessage(result);
            // alertDialog.show();
            if(result.equals("errorch")){
                alertDialog.setTitle("cet etudiant n'existe pas");
                alertDialog.show();
                inech.setText("");
            }
            else{
                Intent intent = new Intent(Chercher.this,ChercheEtudiant.class);
                intent.putExtra("ine",ine_etud);
                startActivity(intent);
            }

        }

        @Override
        protected void onPreExecute() {
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Alert");
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


}
