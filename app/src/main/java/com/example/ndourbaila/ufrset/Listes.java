package com.example.ndourbaila.ufrset;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

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
public class Listes extends Activity{
    private static String fliere[] = new String[]{"LGI","LMI","LPC","LSEE"};
    private static String Fliste[] = new String[]{"L1","L2","L3","M1","M2"};
    AutoCompleteTextView niv , Lfliere;
    String niveau1 ;
    String fli ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listes);
        AutoCompleteTextView auto = (AutoCompleteTextView)findViewById(R.id.idFliste);
        AutoCompleteTextView niveau = (AutoCompleteTextView)findViewById(R.id.idniveau);

        auto.setThreshold(0);
        niveau.setThreshold(0);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,fliere);
        ArrayAdapter<String> adapterniv = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,Fliste);

        auto.setAdapter(adapter);
        niveau.setAdapter(adapterniv);
        Lfliere = (AutoCompleteTextView)findViewById(R.id.idFliste);
        niv=(AutoCompleteTextView)findViewById(R.id.idniveau);
        Button vliste=(Button)findViewById(R.id.idVliste);
        Button vretour=(Button)findViewById(R.id.Lretour);

        vretour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Listes.this,MenuAdmin.class);
                startActivity(intent);
            }
        });

        vliste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 niveau1 = niv.getText().toString();
                  fli = Lfliere.getText().toString();
                 BackgroundWorker backgroundWorker = new BackgroundWorker(Listes.this);
                 backgroundWorker.execute(niveau1,fli);



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
                String data = URLEncoder.encode("niv", "UTF-8")+"="+URLEncoder.encode(niv,"UTF-8")+"&"+
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

            }
            else{


                Intent intent = new Intent(Listes.this,ListeFiliere.class);
                intent.putExtra("filiere",fli);
                intent.putExtra("niveau",niveau1);
                startActivity(intent);
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
