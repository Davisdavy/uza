package com.example.uzachap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class SendMessageActivity extends AppCompatActivity {

    Button btnSendMessage;
    TextView town_name;
    TextView town_no;

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

        getCustomerMobile();

        town_name = findViewById(R.id.txt_town_name);
        town_no = findViewById(R.id.txt_town_no);

        town_name.setText(townName);
        town_no.setText(townNo);
        btnSendMessage = findViewById(R.id.btn_send_message);
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(townName, townNo);
            }
        });
    }

    private void getCustomerMobile() {


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
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(SendMessageActivity.this, "Failed to send message", Toast.LENGTH_LONG).show();
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



//    new MaterialStyledDialog.Builder(this)
//            .setTitle("Opps!")
//                        .setIcon(R.drawable.ic_sentiment_very_dissatisfied_black_24dp)
//                        .setDescription("We don't have any question in this subject")
//                        .setPositiveText("OK")
//                        .onPositive(new MaterialDialog.SingleButtonCallback() {
//        @Override
//        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//            dialog.dismiss();
//            finish();
//        }
//    }).show();

}
