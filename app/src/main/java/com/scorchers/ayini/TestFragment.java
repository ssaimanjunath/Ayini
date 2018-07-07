package com.scorchers.ayini;


import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.tooltip.Tooltip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class TestFragment extends Fragment {
    String ph,nit,phos,phot,cal,mg;
    SpinKitView spinKitView;
    public TestFragment() {
        // Required empty public constructor
    }

    public static TestFragment newInstance() {
        return new TestFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_test, container, false);

        TextView tv1 = rootView.findViewById(R.id.tv1);
        TextView tv2 = rootView.findViewById(R.id.tv2);
        TextView tv3 = rootView.findViewById(R.id.tv3);
        TextView tv4 = rootView.findViewById(R.id.tv4);
        TextView tv5 = rootView.findViewById(R.id.tv5);
        TextView tv6 = rootView.findViewById(R.id.tv6);

        final EditText et1 = rootView.findViewById(R.id.pH);
        final EditText et2 = rootView.findViewById(R.id.nitro);
        final EditText et3 = rootView.findViewById(R.id.phosp);
        final EditText et4 = rootView.findViewById(R.id.potash);
        final EditText et5 = rootView.findViewById(R.id.calc);
        final EditText et6 = rootView.findViewById(R.id.mg);

        spinKitView = rootView.findViewById(R.id.spin_pred);

        Button clear = rootView.findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et1.setText("");
                et2.setText("");
                et3.setText("");
                et4.setText("");
                et5.setText("");
                et6.setText("");
            }
        });

        final EditText phVal = rootView.findViewById(R.id.pH);
        final EditText nitVal = rootView.findViewById(R.id.nitro);
        final EditText phosVal = rootView.findViewById(R.id.phosp);
        final EditText photVal = rootView.findViewById(R.id.potash);
        final EditText calVal = rootView.findViewById(R.id.calc);
        final EditText mgVal = rootView.findViewById(R.id.mg);

        String ph,nit,phos,phot,cal,mg;

        Button predict = rootView.findViewById(R.id.pred);
        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncLogin().execute(phVal.getText().toString(),nitVal.getText().toString(),phosVal.getText().toString(),photVal.getText().toString(),
                        calVal.getText().toString(),mgVal.getText().toString());
            }
        });

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = getResources().getString(R.string.phImpt);
                showToolTip(v,Gravity.BOTTOM,val);
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = getResources().getString(R.string.NitImpt);
                showToolTip(v,Gravity.BOTTOM,val);
            }
        });
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = getResources().getString(R.string.PotImpt);
                showToolTip(v,Gravity.BOTTOM,val);
            }
        });
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = getResources().getString(R.string.PhoImpt);
                showToolTip(v,Gravity.BOTTOM,val);
            }
        });
        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = getResources().getString(R.string.CalImpt);
                showToolTip(v,Gravity.BOTTOM,val);
            }
        });
        tv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = getResources().getString(R.string.MagImpt);
                showToolTip(v,Gravity.BOTTOM,val);
            }
        });

        return rootView;
    }
    private void showToolTip(View v, int gravity, String textToDisplay)
    {
        TextView tv = (TextView)v;
        Tooltip tooltip = new Tooltip.Builder(tv)
                .setText(textToDisplay)
                .setTextColor(getResources().getColor(R.color.white_bg))
                .setGravity(gravity)
                .setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark))
                .setCornerRadius(8f)
                .setDismissOnClick(true)
                .show();
    }
    private class AsyncLogin extends AsyncTask<String, String, String>
    {
        HttpURLConnection conn;
        URL url = null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            spinKitView.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(String... params)
        {
            ph  = params[0];
            nit  = params[1];
            phos = params[2];
            phot  = params[3];
            cal  = params[4];
            mg  = params[5];

            try
            {
                url = new URL("https://ayini.herokuapp.com/data?&ph="+ph+"&n="+nit+"&p="+phos+"&k="+phot+"&ca="+cal+"&mg="+mg);
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
                return "exception";
            }
            try
            {
                conn = (HttpURLConnection)url.openConnection();
                conn.connect();

            } catch (IOException e1)
            {
                e1.printStackTrace();
                return "exception";
            }
            try {

                int response_code = conn.getResponseCode();
                if (response_code == HttpURLConnection.HTTP_OK)
                {
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line="";
                    while ((line = reader.readLine()) != null)
                    {
                        result.append(line);
                    }
                    return(result.toString());
                }
                else
                {
                    return("unsuccessful");
                }

            }
            catch (IOException e)
            {
                e.printStackTrace();
                return "exception";
            }
            finally
            {
                conn.disconnect();
            }
        }
        @Override
        protected void onPostExecute(String result)
        {
            spinKitView.setVisibility(View.INVISIBLE);
            ResultFragment fragment = ResultFragment.newInstance();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.content, fragment);
            ft.commit();
            Toast.makeText(getContext(),"Value Processed : "+result,Toast.LENGTH_LONG).show();
        }
    }
}
