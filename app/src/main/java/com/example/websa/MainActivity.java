package com.example.websa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button b1;
    private Button b2;
    private TextView tv;
    private EditText ed;
    WebTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button) findViewById(R.id.btn1);
        b2 = (Button) findViewById(R.id.btn2);
        tv = (TextView) findViewById(R.id.tv1);
        ed = (EditText) findViewById(R.id.et1);


        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // バーコードリーダーとして、zxingのバーコードリーダーを使用する
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                try {
                    startActivityForResult(intent, 1);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(MainActivity.this, "not found Barcode Scanner", Toast.LENGTH_SHORT).show();
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String bId = ed.getText().toString();
                    if (!bId.equals("")) {
                        String scheme = "http";
                        String authority = "shopping.yahooapis.jp";
                        String path = "/ShoppingWebService/V3/itemSearch";

                        Uri.Builder uriBuilder = new Uri.Builder();

                        uriBuilder.scheme(scheme);
                        uriBuilder.authority(authority);
                        uriBuilder.path(path);
                        uriBuilder.appendQueryParameter("appid", "dj0zaiZpPXBWM2RneUhxZXFXZyZzPWNvbnN1bWVyc2VjcmV0Jng9ZmU-");
                        uriBuilder.appendQueryParameter("jan_code", bId);
                        String uri = uriBuilder.toString();
                        task = new WebTask(tv);
                        task.execute(uri);
                    } else {
                        tv.setText("バーコードを読み取ってください。");
                    }
                } catch (Exception e) {
                    // エラー発生時
                    Toast.makeText(MainActivity.this, "取得エラー", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    String bId; // 読み取りバーコード値
                    bId = data.getStringExtra("SCAN_RESULT");
                    ed = (EditText) findViewById(R.id.et1);
                    ed.setText(bId);
                }
                break;
            default:
                break;
        }
    }
}