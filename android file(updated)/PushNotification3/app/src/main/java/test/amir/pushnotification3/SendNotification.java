package test.amir.pushnotification3;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import helper.CheckConnection;

public class SendNotification extends AppCompatActivity implements View.OnClickListener, Response.Listener<String>, Response.ErrorListener, DialogInterface.OnMultiChoiceClickListener, DialogInterface.OnClickListener {

    private EditText titleField, messageField;
    private StringBuilder sql;
    private CheckConnection check;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification);

        check=new CheckConnection(this);

        Button notiSend = (Button) findViewById(R.id.noti_send);
        Button chooseUser = (Button) findViewById(R.id.choose_user);
        titleField = (EditText) findViewById(R.id.title_field);
        messageField = (EditText) findViewById(R.id.message_field);


        notiSend.setOnClickListener(this);
        chooseUser.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.noti_send:
                if(check.checkConnection()){
                    sendToServer();
                }

                break;
            case R.id.choose_user:
                if(check.checkConnection()){
                    getFromServer();
                }

                break;


        }


    }




    private void sendToServer() {
        String notiServer = "http://pushnotification.net23.net/notification.php";

        final String title = titleField.getText().toString().trim();
        final String message = messageField.getText().toString().trim();
        StringRequest request = new StringRequest(Request.Method.POST, notiServer, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                params.put("message", message);

                /*when choose button was not clicked*/
                if(sql == null){
                    sql = new StringBuilder("select token from token_table");
                }
                params.put("sql", sql.toString());

                return params;
            }
        };

        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(request);
        Toast.makeText(this,"Notification Send",Toast.LENGTH_SHORT).show();
        finish();
    }


    private int[] idList;
    private boolean[] checkedItems;

    private boolean[] myItems = null;


    @Override
    public void onResponse(String response) {
        CharSequence[] nameList=getUserInfo(response);

        if(nameList !=null){
            createDialog(nameList);
        }



    }

    private CharSequence[] getUserInfo(String response) {
        try {
            JSONObject obj = new JSONObject(response);

            JSONArray jsonArray = obj.getJSONArray("data");
            JSONObject jsonObject;

            int size = jsonArray.length();

            CharSequence[] nameList = new CharSequence[size];

            idList = new int[size];

            /*checked ites are false by defult*/
            checkedItems = new boolean[size];

            /*restore my selected items*/
            if (myItems != null) {
                checkedItems = myItems;
            }


            for (int i = 0; i < size; i++) {
                jsonObject = jsonArray.getJSONObject(i);

                nameList[i] = jsonObject.getString("name");
                idList[i] = jsonObject.getInt("id");

            }


            return nameList;

        } catch (JSONException e) {


        }
        return null;
    }

    private void createDialog(CharSequence[] nameList) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMultiChoiceItems(nameList, checkedItems, this);
        dialog.setCancelable(true);
        dialog.setTitle("Registered Users");
        dialog.setPositiveButton("Ok", this);
        dialog.setNegativeButton("Cencel", this);
        dialog.show();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this,"Server Down", Toast.LENGTH_SHORT).show();
    }

    public void getFromServer() {
        String listServer = "http://pushnotification.net23.net/list.php";
        StringRequest request = new StringRequest(listServer, this, this);
        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(request);


    }




    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

        /*storeing ny choise*/
        myItems = checkedItems.clone();
    }


    private void analisisCheckedItems() {
        boolean custom = false;
        sql = new StringBuilder("select token from token_table");
        int i = 0;
        for (boolean click : checkedItems
                ) {
            if (click == true && custom == false) {
                custom = true;
                 /*add to index 1*/
                sql.append(" where id in ( ");

            }

            if (click == true && custom == true) {
                sql.append("" + idList[i]);
                sql.append(",");
            }
            i++;
        }

        if (custom) {
            sql.deleteCharAt(sql.length() - 1);
            sql.append(" )");
        }


//        Log.d("debuging point", "1");
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {


        switch (which) {
            /*ok button*/
            case -1:
                analisisCheckedItems();
                dialog.dismiss();
                break;
            /*cencel button*/
            case -2:
                dialog.dismiss();

                break;
        }


    }



}
