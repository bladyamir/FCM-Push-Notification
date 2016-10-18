package test.amir.pushnotification3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import helper.CheckConnection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CheckConnection check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sendBtn = (Button) findViewById(R.id.main_send);
        Button registerBtn = (Button) findViewById(R.id.reg);

        check=new CheckConnection(this);

        SharedPreferences preferences = getSharedPreferences("token_file", MODE_PRIVATE);
        String token = preferences.getString("token", "");

        if (token.matches("")) {

            registerBtn.setOnClickListener(this);

        } else {
            registerBtn.setVisibility(View.GONE);
        }

        sendBtn.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reg:
                runActivity(RegisterActivity.class);
                break;
            case R.id.main_send:
                runActivity(SendNotification.class);


        }


    }

    private void runActivity(Class<?> target) {
        if (check.checkConnection()) {
            Intent intent = new Intent(this, target);
            startActivity(intent);
        }

    }





}
