package com.foxconn.beacon.salary;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * @author: F1331886
 * @date: 2017/10/24 0024.
 * @describe: 主界面
 */

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE = 1;
    private NotificationManager mManager;
    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = (WebView) findViewById(R.id.webview);

        initWebView();
        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        showNotice();

//        int checkSelfPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
//        if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{}, REQUEST_CODE);
//            Log.i(TAG, "onCreate: " + checkSelfPermission);
//        } else {
//            Log.i(TAG, "onCreate: " + checkSelfPermission);
//            showNotice();
//        }
    }

    private void initWebView() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl("http://www.baidu.com");
    }

    private void showNotice() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:888888"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_ONE_SHOT);
        Notification notify = new NotificationCompat.Builder(this)
                .setContentTitle("这是标题")
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("this is a large text,this is a large text," +
                        "this is a large text,this is a large text,this is a large text"))
                .setLights(Color.GREEN,2000,2000)
                .build();
        mManager.notify(1, notify);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showNotice();
                } else {
                    Toast.makeText(this, "拒绝了", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
