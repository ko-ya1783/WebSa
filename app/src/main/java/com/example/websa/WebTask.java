package com.example.websa;

import android.os.AsyncTask;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class WebTask extends AsyncTask<String, Void, String> {
    private TextView tv;

    // コンストラクター
    public WebTask(TextView tv){
        super();
        this.tv=tv;
    }

    @Override
    protected String doInBackground(String... params){
        String uri=params[0]; // 引数の取得
        String str=""; // 文字列定義

        // http通信用
        HttpGet request;
        request = new HttpGet(uri);

        HttpResponse httpResponse=null;
        HttpClient httpClient = new DefaultHttpClient();

        try{
            httpResponse=httpClient.execute(request);
        }catch(Exception e) {
            str = e.toString();
        }
        // HTTPの結果取得
        int status = httpResponse.getStatusLine().getStatusCode();
        if(HttpStatus.SC_OK==status){ // 結果がOKのとき
            try{
                str="HTTP通信OK";
            }catch(Exception e){
                str=e.toString();
            }
        }else{
            str="HTTP通信エラー";
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try{
            httpResponse.getEntity().writeTo(outputStream);
        }catch (IOException e){
            e.printStackTrace();
        }
        String data;
        data = outputStream.toString();
        str = data;


        return str;

    }
    // 終了時の処理
    protected void onPostExecute(String str){

        tv.setText(str); // textViewに読み取り結果を表示する
    }
}
