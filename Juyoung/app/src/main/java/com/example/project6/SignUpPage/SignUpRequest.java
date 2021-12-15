package com.example.project6.SignUpPage;

import com.example.project6.User.UserDTO;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SignUpRequest {
    public int requestSignUp(String _url, UserDTO userDTO){
        HttpURLConnection urlConn= null;
        String json="";
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.accumulate("email", userDTO.getEmail());
            jsonObject.accumulate("userPass", userDTO.getPassword());
            jsonObject.accumulate("userName",userDTO.getName());
            jsonObject.accumulate("phoneNum", userDTO.getPhone());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            URL url = new URL(_url);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("POST");
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.setDoInput(true);

            json=jsonObject.toString();
            OutputStream os=urlConn.getOutputStream();
            os.write(json.toString().getBytes("UTF-8"));
            os.flush();
            os.close();

            int check=urlConn.getResponseCode();
            return check;
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
        return 0;
    }
}
