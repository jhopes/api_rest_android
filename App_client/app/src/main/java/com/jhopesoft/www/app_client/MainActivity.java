package com.jhopesoft.www.app_client;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Controller.ClsFacultad;
import Modell.Facultad;
import RestClient.VolleyS;

public class MainActivity extends AppCompatActivity {

    Facultad mf;
    ClsFacultad cf;
    private VolleyS volley;
    protected RequestQueue fRequestQueue;
    ListView listaFacultad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaFacultad = (ListView) findViewById(R.id.LvFacultad);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mf= new Facultad();
        cf = new ClsFacultad(MainActivity.this);
        volley = VolleyS.getInstance(getApplicationContext());
        fRequestQueue = volley.getRequestQueue();

    }

    public void syncfacultad(View v){
        cargarFacultad();
        String[] columnas = new String[]{"facultad","abv"};
        int[] id_views = new int[]{android.R.id.text1,android.R.id.text2};
        Cursor cursorf = cf.readFacultad();
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(), android.R.layout.simple_list_item_2, cursorf, columnas, id_views);
        listaFacultad.setAdapter(adapter);
    }
    public void cargarFacultad(){
        String url = "http://192.168.244.2/api%20rest/modulos/rest/rest1.php";
        JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                List<Facultad> MyList = new ArrayList<>();
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("facultad");
                    cf.insert_facultad(jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onConnectionFinished();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                onConnectionFailed(volleyError.toString());
            }
        });
        addToQueue(request);
    }

    public void addToQueue(Request request) {
        if (request != null) {
            request.setTag(this);
            if (fRequestQueue == null)
                fRequestQueue = volley.getRequestQueue();
            request.setRetryPolicy(new DefaultRetryPolicy(
                    60000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ));
            onPreStartConnection();
            fRequestQueue.add(request);
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        if (fRequestQueue != null) {
            fRequestQueue.cancelAll(this);
        }
    }
    public void  onPreStartConnection() {
        setProgressBarIndeterminateVisibility(true);
    }

    public void onConnectionFailed(String error) {
        setProgressBarIndeterminateVisibility(true);
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
    }

    public void onConnectionFinished() {
        setProgressBarIndeterminateVisibility(true);
    }




}
