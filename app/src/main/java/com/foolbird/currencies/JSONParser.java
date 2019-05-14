package com.foolbird.currencies;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JSONParser {

    private static final String TAG = "JSONParser";
    static InputStream sInputStream = null;
    static JSONObject sReturnJsonObject = null;
    static String sRawJsonString = "";

    public JSONParser() {

    }

    public JSONObject getJSONFromUrl(String url) {
        try {
            Response response = get(url);
            if (response != null) {
                if (response.body() != null) {
                    sInputStream = response.body().byteStream();
                    sRawJsonString = response.body().string();
                    Log.d(TAG, "getJSONFromUrl: " + sRawJsonString);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 模拟网络耗时2s
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            sReturnJsonObject = new JSONObject(sRawJsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sReturnJsonObject;
    }

    private Response get(String url) throws IOException {
        OkHttpClient client = HttpHelper.getInstance().getOkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        // 上面两个语句可合为一句
//        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            Log.d(TAG, "get: " + "成功");
            return response;
        } else {
            Log.d(TAG, "get: " + "失败");
            return null;
        }
    }
}
