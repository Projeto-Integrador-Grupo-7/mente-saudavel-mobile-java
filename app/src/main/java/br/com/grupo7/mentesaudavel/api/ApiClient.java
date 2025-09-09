package br.com.grupo7.mentesaudavel.api;

import android.util.Log;

import java.io.IOException;

import okhttp3.*;

public class ApiClient {
    private static final String BASE_URL = "http://10.0.2.2:5014/api/";
    private final OkHttpClient client;

    public ApiClient() {
        client = new OkHttpClient();
    }

    public void getUsuarios() {
        Request request = new Request.Builder()
                .url(BASE_URL + "usuarios")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("API", "Erro: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String resposta = response.body().string();
                    Log.d("API", "Resposta: " + resposta);
                } else {
                    Log.e("API", "Erro HTTP: " + response.code());
                }
            }
        });
    }
}