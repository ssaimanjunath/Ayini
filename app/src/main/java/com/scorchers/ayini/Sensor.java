package com.scorchers.ayini;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Sensor extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    View rootView;
    InputStream is = null;
    String line = null;
    String result = null;
    String moi="",ph="",fer="";
    ProgressBar moisture,pH,fer_lvl,micro_n,macro_n,cnc;
    TextView moisture_lvl,pH_level,fertility_level;
    public static String moist_lvl;
    public Sensor() {
        // Required empty public constructor
    }


    public static Sensor newInstance() {
        return new Sensor();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_sensor, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://helloagri.000webhostapp.com/getSenDetails.php?&id=A101");
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            is = entity.getContent();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
            is.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try
        {
            JSONArray jArray = new JSONArray(result);
            int count = jArray.length();
            for(int i=0;i<count;i++)
            {
                JSONObject json_data = jArray.getJSONObject(i);
                moi += json_data.getString("Moisture");
                ph += json_data.getString("pH");
                fer += json_data.getString("fertility");
            }
            moisture= rootView.findViewById(R.id.moisture);
            pH= rootView.findViewById(R.id.pH);
            fer_lvl= rootView.findViewById(R.id.fertility);
            moisture_lvl = rootView.findViewById(R.id.moisture_lvl);
            pH_level = rootView.findViewById(R.id.pH_level);
            fertility_level = rootView.findViewById(R.id.fertility_level);
            cnc = rootView.findViewById(R.id.cnc);
            micro_n = rootView.findViewById(R.id.micro_n);
            macro_n = rootView.findViewById(R.id.macro_n);
            moist_lvl=moi;
            moisture_lvl.setText(moi+"%");
            pH_level.setText(ph);
            fertility_level.setText(fer+"%");
            moisture.setProgress(Integer.parseInt(moi));
            pH.setProgress(Integer.parseInt(ph));
            fer_lvl.setProgress(Integer.parseInt(fer));
            if(Integer.parseInt(ph)>6)
            {
                int n = 65,m=20,c = 80;
                cnc.setProgress(n);
                micro_n.setProgress(m);
                macro_n.setProgress(c);
            }
            else
            {
                int n = 20,m=80,c = 40;
                cnc.setProgress(n);
                micro_n.setProgress(m);
                macro_n.setProgress(c);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return rootView;
    }
}
