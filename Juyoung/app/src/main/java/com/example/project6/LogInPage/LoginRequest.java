package com.example.project6.LogInPage;

import com.example.project6.User.UserDTO;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginRequest {
    public String requestLogin(String _url, UserDTO userDTO){
        int check=400;
        HttpURLConnection urlConn= null;
        String json="";
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.accumulate("email", userDTO.getEmail());
            jsonObject.accumulate("userPass", userDTO.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            URL url = new URL(_url);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("POST");
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.setRequestProperty("Accept-Charset", "application/json");
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);

            json=jsonObject.toString();
            OutputStream os=urlConn.getOutputStream();
            os.write(json.toString().getBytes("UTF-8"));
            os.flush();
            os.close();

            check=urlConn.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));
            if (urlConn.getResponseCode() != HttpURLConnection.HTTP_OK)
                return String.valueOf(check);
            String line;
            String page = "";
            while ((line = br.readLine()) != null) {
                page += line;
            }
            return page;
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            if(urlConn!=null)
                urlConn.disconnect();
        }
        return String.valueOf(check);
    }
}
