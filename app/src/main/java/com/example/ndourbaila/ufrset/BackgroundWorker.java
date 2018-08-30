package com.example.ndourbaila.ufrset;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

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

/**
 * Created by ndourbaila on 15/10/2017.
 */
public class BackgroundWorker extends AsyncTask<String,Void,String> {


    Context context;
    AlertDialog alertDialog;
    BackgroundWorker(Context ctx){
        context = ctx;
    }
    protected String doInBackground(String... params) {

        String type = params[0];



        if(type.equals("inscription")){
            try {
                String url_login = "http://192.168.56.1/inscription/recup.php";
                URL url = new URL(url_login);

                String etud_nom = params[1];
                String etud_prenom = params[2];
                String etud_fliere = params[3];
                String etud_email = params[4];
                String etud_ine = params[5];
                String sexe = params[6];
                String niv = params[7];
                String ad = params[8];

                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String post_data = URLEncoder.encode("etud_nom", "UTF-8")+"="+ URLEncoder.encode(etud_nom, "UTF-8")+"&"+
                                   URLEncoder.encode("etud_prenom", "UTF-8")+"="+ URLEncoder.encode(etud_prenom, "UTF-8")+"&"+
                                   URLEncoder.encode("etud_fliere", "UTF-8")+"="+ URLEncoder.encode(etud_fliere, "UTF-8")+"&"+
                                   URLEncoder.encode("etud_email", "UTF-8")+"="+ URLEncoder.encode(etud_email, "UTF-8")+"&"+
                                   URLEncoder.encode("etud_ine", "UTF-8")+"="+ URLEncoder.encode(etud_ine, "UTF-8")+"&"+
                                   URLEncoder.encode("sexe", "UTF-8")+"="+ URLEncoder.encode(sexe, "UTF-8")+"&"+
                                   URLEncoder.encode("niv", "UTF-8")+"="+ URLEncoder.encode(niv, "UTF-8")+"&"+
                                   URLEncoder.encode("adresse", "UTF-8")+"="+ URLEncoder.encode(ad, "UTF-8");


                bufferedWriter.write(post_data);
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

                return  result;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
            if (type.equals("listeNiv")) {
                String url_login = "http://192.168.56.1/inscription/liste.php";
                String niv = params[1];
                String fliere = params[2];

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


            }
        else if(type.equals("chercher")){
                String url_login = "http://192.168.56.1/inscription/chercher.php";
                String ine = params[1];


                try {
                    URL url = new URL(url_login);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();

                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    String data = URLEncoder.encode("ine","UTF-8")+"="+URLEncoder.encode(ine,"UTF-8");

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

        if(result.equals("errorch"))
        {
            result = "cet etudiant n'existe pas";


        }

        alertDialog.setMessage(result);
        alertDialog.show();

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
