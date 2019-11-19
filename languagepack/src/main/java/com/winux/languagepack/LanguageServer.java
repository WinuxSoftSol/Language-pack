package com.winux.languagepack;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LanguageServer extends AsyncTask<String, String, String> {

    private HttpURLConnection urlConnection;
    private BufferedReader reader;

    @Override
    protected String doInBackground(String... strings) {
        String resultJson = null;
        try {
            URL url = new URL(strings[0]);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
          /* urlConnection.setRequestProperty("Authorization", "application_id");
            urlConnection.setRequestProperty("package_name", "package_name");
            urlConnection.setRequestProperty("content-type", "application/json");*/
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            resultJson = buffer.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultJson;
    }
}
