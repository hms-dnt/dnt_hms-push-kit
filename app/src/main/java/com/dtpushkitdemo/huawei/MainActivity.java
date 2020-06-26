package com.dtpushkitdemo.huawei;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.hms.aaid.HmsInstanceId;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "PushExample";
    private Button btnToken;
    private String pushtoken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnToken = findViewById(R.id.button2);
        btnToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getToken();
            }
        });
    }

    private void showLog(final String log) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                View tvView = findViewById(R.id.tv_log);
                if (tvView instanceof TextView) {
                    ((TextView) tvView).setText(log);
                    Toast.makeText(MainActivity.this, pushtoken, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Obtain a token.
     */
    private void getToken() {
        Log.i(TAG, "get token: begin");

        // get token
        new Thread() {
            @Override
            public void run() {
                try {
                    String appId = AGConnectServicesConfig.fromContext(MainActivity.this).getString("client/app_id");
                    pushtoken = HmsInstanceId.getInstance(MainActivity.this).getToken(appId, "HCM");
                    if(!TextUtils.isEmpty(pushtoken)) {
                        Log.i(TAG, "get token:" + pushtoken);
                        showLog(pushtoken);
                    }
                } catch (Exception e) {
                    Log.i(TAG,"getToken failed, " + e);

                }
            }
        }.start();
    }
}
