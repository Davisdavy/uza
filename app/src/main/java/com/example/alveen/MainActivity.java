package com.example.alveen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NoConnectionError;
import com.android.volley.RequestQueue;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private Button mLogoutBtn;
    private RecyclerView recyclerView;
    private List<Town>list_town;
    private TownAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mLogoutBtn = findViewById(R.id.logout_btn);
        recyclerView=findViewById(R.id.town_list);
        recyclerView.setHasFixedSize(true);
        progressDialog= new ProgressDialog(MainActivity.this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        list_town=new ArrayList<>();
        adapter=new TownAdapter(this, list_town);
        recyclerView.setAdapter(adapter);
        getTownData();

        mLogoutBtn.setOnClickListener(v -> {
            mAuth.signOut();
            sendUserToLogin();
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        if(mCurrentUser == null){
            sendUserToLogin();
        }
    }


    private void sendUserToLogin() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }

    private void getTownData() {
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Finding Towns...");
        progressDialog.show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ConfigT.TOWN_URL, response -> {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject jsonObject = response.getJSONObject(i);
                    Town listtown = new Town(jsonObject.getInt("town_no"),jsonObject.getString("town_name"), jsonObject.getString("mobile_no"));
                    list_town.add(listtown);
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
            progressDialog.dismiss();
            recyclerView.setAdapter(adapter);
        }, error -> {
            if (error instanceof TimeoutError ) {
                progressDialog.dismiss();
                error.toString();
                String newError;
                newError = "Network Busy";
                Toast.makeText(MainActivity.this, newError, Toast.LENGTH_SHORT).show();
            }else if(error instanceof NoConnectionError){
                progressDialog.dismiss();
                String newError = "Interner: "+error;
                Toast.makeText(MainActivity.this, newError, Toast.LENGTH_LONG).show();
                System.out.println("Error: "+error);
            }
            else{
                Toast.makeText(MainActivity.this, "Something wrong happened!", Toast.LENGTH_SHORT).show();
            }

        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
    @Override
    protected void onDestroy() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
        super.onDestroy();
    }
}

