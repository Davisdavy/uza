package com.example.uzachap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SendSmsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CustomerAdapter adapter;
    private List<Town> customer_mobile_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(ConfigT.selectedTown.getTown_name());
        setSupportActionBar(toolbar);
        mRecyclerView = findViewById(R.id.customer_list);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        customer_mobile_list = new ArrayList<>();
        adapter=new CustomerAdapter(customer_mobile_list);

        getMobileNumbers();

    }

    private void getMobileNumbers() {
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, ConfigT.DATA_URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    JSONArray array = jsonObject.getJSONArray("result");
//                    for (int i = 0; i < array.length(); i++) {
//                        JSONObject ob = array.getJSONObject(i);
//                        Town listtown = new Town(ob.getString("town"),ob.getString(""));
//                        list_town.add(listtown);
//                    }
//                    recyclerView.setAdapter(adapter);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
    }
}
