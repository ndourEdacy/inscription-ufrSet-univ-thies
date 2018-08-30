package com.example.ndourbaila.ufrset;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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


public class MainActivity extends Activity {
    EditText login,motpass;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button connection= (Button)findViewById(R.id.bconnect);
        login = (EditText)findViewById(R.id.idlogin);
        motpass = (EditText)findViewById(R.id.motpass);

        connection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String log_admin = login.getText().toString();
                    String motpass_admin = motpass.getText().toString();
                    BackgroundWorker backgroundWorker = new BackgroundWorker(MainActivity.this);
                    backgroundWorker.execute(log_admin,motpass_admin);


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


                try {
                    String url_login = "http://192.168.56.1/inscription/connexion.php";
                    URL url = new URL(url_login);

                    String login = params[0];
                    String motpass = params[1];


                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();

                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                    String post_data = URLEncoder.encode("login", "UTF-8")+"="+ URLEncoder.encode(login, "UTF-8")+"&"+
                                       URLEncoder.encode("motpass", "UTF-8")+"="+ URLEncoder.encode(motpass, "UTF-8");


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



            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            if(result.equals("success")){
                Intent myIntent = new Intent(MainActivity.this,MenuAdmin.class);
                startActivity(myIntent);
            }
            else{
                alertDialog.setMessage(result);
                alertDialog.show();
                login.setText("");
                motpass.setText("");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
