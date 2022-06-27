package com.example.uzachap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SendMessageActivity extends AppCompatActivity {

    Button btnSendMessage;
    private RecyclerView recyclerView;
    private List<Customer> listMobile;
    private CustomerAdapter customerAdapter;
    private RecyclerView.LayoutManager layoutManager;

    String townName;
    String townNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        townName = getIntent().getExtras().getString("TOWN_NAME"); //Display town name on the Toolbar
        townNo = String.valueOf(getIntent().getExtras().getInt("TOWN_NO"));
        //Toolbar code can be placed here
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Heading to "+townName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        //Add an up action button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        // Recylerview to display mobile phone numbers of customers of the town clicked
        recyclerView=findViewById(R.id.mobile_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        listMobile = new ArrayList<>();
        customerAdapter = new CustomerAdapter(this, listMobile);

        getCustomerMobileNo(townNo);

        btnSendMessage = findViewById(R.id.btn_send_message);

        //this method will remain
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(townName, townNo);
            }
        });
    }

    private void getCustomerMobileNo(final String townNumber) {
        final ProgressDialog progressDialog = new ProgressDialog(SendMessageActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Please wait a moment...");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, ConfigT.MOBILE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equals("mobile_none")){
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Customer customer = new Customer(jsonObject.getString("mobile_no"));
                            listMobile.add(customer);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                    progressDialog.dismiss();
                    recyclerView.setAdapter(customerAdapter);
                }else {
                    progressDialog.dismiss();
                    new MaterialStyledDialog.Builder(SendMessageActivity.this)
                            .setTitle("Opps!")
                            .setIcon(R.drawable.ic_sentiment_very_dissatisfied_black_24dp)
                            .setDescription("We don't have any customers in this town!")
                            .setPositiveText("OK")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                    Intent intent = new Intent(SendMessageActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    progressDialog.dismiss();
                    String newError = error.toString();
                    newError = "No Internet Connection!";
                    Toast.makeText(SendMessageActivity.this, newError, Toast.LENGTH_LONG).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("town_no",  townNumber);
                return param;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(SendMessageActivity.this).addToRequestQueue(request);
    }


    public void sendMessage(final String townName, final String townNo){
        final ProgressDialog progressDialog = new ProgressDialog(SendMessageActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Sending message...");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, ConfigT.MSG_URl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response == null || response.isEmpty()){
                    progressDialog.dismiss();
                    Toast.makeText(SendMessageActivity.this, "Not responding", Toast.LENGTH_LONG).show();
                    finish();
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(SendMessageActivity.this, "Message sent successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SendMessageActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, error -> {
            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                progressDialog.dismiss();
                String newError = error.toString();
                newError = "No Internet Connection!";
                Toast.makeText(SendMessageActivity.this, newError, Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(SendMessageActivity.this, "Failed to send message!", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("town_name", townName);
                param.put("town_no",  townNo);
                return param;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(SendMessageActivity.this).addToRequestQueue(request);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//Delete all activity
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }






}

