package br.com.grupo7.mentesaudavel.api;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://10.0.2.2:5014/api/";
    private static ApiInterface apiInterface;

    public static ApiInterface getApiInterface() {
        if (apiInterface == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiInterface = retrofit.create(ApiInterface.class);
        }
        return apiInterface;
    }

    public static void tratarErroHttp(Activity activity, int codigo) {
        activity.runOnUiThread(() -> {
            Toast.makeText(activity, "Erro no servidor: " + codigo, Toast.LENGTH_LONG).show();
        });
    }

    public static void tratarFalhaConexao(Activity activity, Throwable error) {
        activity.runOnUiThread(() -> {
            Log.e("API", "Falha na requisição: " + error.getMessage());
            Toast.makeText(activity, "Não foi possível estabelecer uma conexão com o servidor. Por favor, tente novamente mais tarde", Toast.LENGTH_LONG).show();
        });
    }
}