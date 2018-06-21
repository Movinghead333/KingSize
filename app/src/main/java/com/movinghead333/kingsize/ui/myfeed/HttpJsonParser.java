package com.movinghead333.kingsize.ui.myfeed;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

public class HttpJsonParser {

    private static JSONArray jsonArray;
    private static InputStream inputStream = null;
    private HttpURLConnection urlConnection = null;
    String data="";


    /**
     * This method helps in retrieving data from HTTP server using HttpURLConnection.
     *
     * @param url    The HTTP URL where JSON data inputStream exposed
     * @param method HTTP method: GET or POST
     * @param params Query parameters for the request
     * @return This method returns the JSON object fetched from the server
     */
    public JSONArray makeHttpRequest(String url, String method,
                                      Map<String, String> params) {

        try {
            Uri.Builder builder = new Uri.Builder();
            URL urlObj;
            String encodedParams = "";
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    builder.appendQueryParameter(entry.getKey(), entry.getValue());
                }
            }
            if (builder.build().getEncodedQuery() != null) {
                encodedParams = builder.build().getEncodedQuery();

            }

            if ("GET".equals(method)) {
                //url = url + "?" + encodedParams;
                urlObj = new URL(url);
                urlConnection = (HttpURLConnection) urlObj.openConnection();
                urlConnection.setRequestMethod(method);


            }
            else {
                urlObj = new URL(url);
                urlConnection = (HttpURLConnection) urlObj.openConnection();
                urlConnection.setRequestMethod(method);
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                urlConnection.setRequestProperty("Content-Length", String.valueOf(encodedParams.getBytes().length));
                urlConnection.getOutputStream().write(encodedParams.getBytes());
            }
            //Connect to the server
            urlConnection.connect();
            //Read the response
            inputStream = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";

            //Parse the response

            while (line != null){
                line = reader.readLine();
                //TODO: optimize with Stringbuilder
                data = data + line;
            }

            jsonArray = new JSONArray(data);

            inputStream.close();


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Log.e("Exception", "Error parsing data " + e.toString());
        }

        return jsonArray;
    }
}