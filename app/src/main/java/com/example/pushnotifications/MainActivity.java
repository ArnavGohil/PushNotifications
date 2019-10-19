package com.example.pushnotifications;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText edt ;
    String mess ;
    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "AAAATlh_q_s:APA91bFXP4MGfeaFvrZkRpmpqoKJZEcKGSsH-qZlqR7cmkU4-xCtfbJrtByxBrr5MBrNHB7I6cPOH-f7MrjIX0f7iGd_GPvplY6QzidCxnnXU5lGUitZna1-i0IhlSUiJIcvLpio2Aih";
    final private String contentType = "application/json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic("919667099953");

        edt = findViewById(R.id.editText);

    }
        public void sendClick(View view)
        {

                    String TOPIC = "fNUOhWucTAHS7YsC94hPFY:APA91bETp00K7WUgNbKS7Gy5RhH4qENcPayldouU4iKrzUHnk15PE-ZLnR2wbwIqGJG2kmLOXbhYF_nWgPc0Rsq3T7BeKYSw3Z1dXrH1xTwIBzG6KelJ-vlq_ysejv0zd7PK7BZxUliG" ; //topic must match with what the receiver subscribed to
                    Log.e("DBREF", TOPIC);
                    String NOTIFICATION_TITLE = "Notification Trial";
                    mess = String.valueOf(edt.getText());
                    String NOTIFICATION_MESSAGE = mess ;
                    Toast.makeText(MainActivity.this,"Notification Sent",Toast.LENGTH_LONG).show();
                    JSONObject notification = new JSONObject();
                    JSONObject notifcationBody = new JSONObject();
                    try {
                        notifcationBody.put("title", NOTIFICATION_TITLE);
                        notifcationBody.put("message", NOTIFICATION_MESSAGE);

                        notification.put("to", TOPIC);
                        notification.put("data", notifcationBody);
                    } catch (JSONException e) {
                        Log.e("FCM", "onCreate: " + e.getMessage() );
                    }
                    Log.e("Hello", notification.toString() );
                    sendNotification(notification);
                }


        private void sendNotification(JSONObject notification) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("FCM", "onResponse: " + response.toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, "Request error", Toast.LENGTH_LONG).show();
                            Log.i("FCM", "onErrorResponse: Didn't work");
                        }
                    }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Authorization", serverKey);
                    params.put("Content-Type", contentType);
                    return params;
                }
            };
            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
        }

}
