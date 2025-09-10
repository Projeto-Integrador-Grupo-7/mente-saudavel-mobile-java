package br.com.grupo7.mentesaudavel.activity;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.grupo7.mentesaudavel.R;
import br.com.grupo7.mentesaudavel.adapter.HistoricoAdapter;
import br.com.grupo7.mentesaudavel.api.ApiClient;
import br.com.grupo7.mentesaudavel.api.ApiInterface;
import br.com.grupo7.mentesaudavel.model.DashboardRequest;
import br.com.grupo7.mentesaudavel.model.Questionario;
import retrofit2.Call;

public class DashboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        getQuestionariosRespondidos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

//    private void getHistorico() {
//        RecyclerView rvHistorico = findViewById(R.id.rvHistorico);
//        rvHistorico.setLayoutManager(new LinearLayoutManager(this));
//
//        List<Questionario> listaHistorico = new ArrayList<>();
//        listaHistorico.add(new Questionario("01/01/2020", "Sofrimento Leve", "5"));
//        listaHistorico.add(new Questionario("28/08/2023", "Sofrimento Moderado", "15"));
//        listaHistorico.add(new Questionario("25/12/2025", "Sofrimento Grave", "20"));
//
//        HistoricoAdapter adapter = new HistoricoAdapter(listaHistorico);
//        rvHistorico.setAdapter(adapter);
//    }

    private void getQuestionariosRespondidos() {
        ApiInterface apiInterface = ApiClient.getApiInterface();

        DashboardRequest request = new DashboardRequest("daba7458-bbac-4643-8b13-359e68440b5e");

        Call<List<Questionario>> call = apiInterface.getQuestionariosRespondidos(request);

        retrofit2.Callback<List<Questionario>> callback = getCallback();

        call.enqueue(callback);
    }

    private void popularHistorico(List<Questionario> listaQuestionariosRespondidos) {
        runOnUiThread(() -> {
            RecyclerView rvHistorico = findViewById(R.id.rvHistorico);
            rvHistorico.setLayoutManager(new LinearLayoutManager(this));

            HistoricoAdapter adapter = new HistoricoAdapter(listaQuestionariosRespondidos);
            rvHistorico.setAdapter(adapter);
        });
    }

    private retrofit2.Callback<List<Questionario>> getCallback() {
        return new retrofit2.Callback<List<Questionario>>() {
            @Override
            public void onResponse(Call<List<Questionario>> call, retrofit2.Response<List<Questionario>> response) {
                if (response.isSuccessful()) {
                    popularHistorico(response.body());
                } else {
                    ApiClient.tratarErroHttp(DashboardActivity.this, response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Questionario>> call, Throwable error) {
                ApiClient.tratarFalhaConexao(DashboardActivity.this, error);
            }
        };
    }
}