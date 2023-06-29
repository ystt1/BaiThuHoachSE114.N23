package com.example.hwweatherapp;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;



public class MainActivity extends AppCompatActivity {
    private TextView NhietDo,DoAm,SucGio;
    private ImageView backgroundIV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_main);
        NhietDo=findViewById(R.id.idTVND);
        DoAm=findViewById(R.id.idTVDA);
        SucGio=findViewById(R.id.idTVSG);
        backgroundIV=findViewById(R.id.idIVBG);
        getApi();

    }


    private void getApi()
    {

        String url= "https://3bro.hoanghy.tech/api/weather?q=ho%20chi%20minh%20city";
        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    NhietDo.setText("Nhiệt Độ: "+response.getString("temperature"));
                    String conditionIcon=response.getString("photo");

                    Picasso.get().load(conditionIcon).into(backgroundIV);
                    DoAm.setText("Độ ẩm: "+response.getString("humidity"));
                    SucGio.setText("Sức gió: "+response.getString("wind"));
                } catch (JSONException e) {

                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", String.valueOf(error));
            }     });
        requestQueue.add(jsonObjectRequest);
    }


}