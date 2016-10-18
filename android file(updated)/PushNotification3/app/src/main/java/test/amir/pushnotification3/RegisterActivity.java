package test.amir.pushnotification3;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import helper.CheckConnection;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, Response.Listener, Response.ErrorListener, Runnable {
    private static String appServer = "http://pushnotification.net23.net/token.php";
    private EditText nameField;
    private CheckConnection check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        Button doneBtn = (Button) findViewById(R.id.regi_done);
        doneBtn.setOnClickListener(this);

        nameField = (EditText) findViewById(R.id.name_field);

        check = new CheckConnection(this);


    }

    @Override
    public void onResponse(Object response) {


    }

    @Override
    public void onErrorResponse(VolleyError error) {


    }

    private String name;
    private  ProgressDialog progressDialog;


    public void saveToken() {
        Handler handler=new Handler();
        handler.postDelayed(this,5000);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.show();


    }


    @Override
    public void onClick(View v) {
        name = nameField.getText().toString().trim();

        if (name.matches("")) {
            Toast.makeText(this, "Name field is epmty", Toast.LENGTH_SHORT).show();
        } else {
            if (check.checkConnection()) {
                saveToken();
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        runActivity();
    }

    private void runActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void run() {
        SharedPreferences preferences = getSharedPreferences("token_file", MODE_PRIVATE);
        final String token = preferences.getString("token", null);

        StringRequest request = new StringRequest(Request.Method.POST, appServer, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("token", token);
                params.put("name", name);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

        progressDialog.dismiss();

        Toast.makeText(this, "Register sucessfully", Toast.LENGTH_SHORT).show();
        runActivity();


    }


}
