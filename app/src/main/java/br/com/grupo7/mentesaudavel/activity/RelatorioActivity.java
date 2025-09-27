package br.com.grupo7.mentesaudavel.activity;

import static java.lang.Integer.*;

import br.com.grupo7.mentesaudavel.R;
import br.com.grupo7.mentesaudavel.api.ApiClient;
import br.com.grupo7.mentesaudavel.api.ApiInterface;
import br.com.grupo7.mentesaudavel.model.Questionario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RelatorioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio);

        buscarRelatorio();
    }

    private void buscarRelatorio() {
        ApiInterface apiInterface = ApiClient.getApiInterface();

        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String usuarioId = prefs.getString("UsuarioId", null);

        Call<Questionario> call = apiInterface.getUltimoQuestionarioRespondido(usuarioId);

        Callback<Questionario> callback = callbackRelatorio();

        call.enqueue(callback);
    }

    private Callback<Questionario> callbackRelatorio() {
        return new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Questionario> call, @NonNull Response<Questionario> response) {
                if (response.isSuccessful()) {
                    popularRelatorio(response.body());
                } else {
                    ApiClient.tratarErroHttp(RelatorioActivity.this, response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Questionario> call, @NonNull Throwable error) {
                ApiClient.tratarFalhaConexao(RelatorioActivity.this, error);
            }
        };
    }

    private void popularRelatorio(Questionario questionario) {
        runOnUiThread(() -> {

            TextView textRelatorio = this.findViewById(R.id.textViewBodyRelatorio);

            String msgErro = validaDadosQuestionario(questionario);
            if (msgErro != null) {
                textRelatorio.setText(msgErro);
                return;
            }

            int pontos = parseInt(questionario.pontuacao);

            String mensagem;
            if (pontos <= 5) {
                mensagem = getString(R.string.relatorioBaixo);
            } else if (pontos <= 10) {
                mensagem = getString(R.string.relatorioLeve);
            } else if (pontos <= 15) {
                mensagem = getString(R.string.relatorioModerado);
            } else {
                mensagem = getString(R.string.relatorioGrave);
            }

            textRelatorio.setText(mensagem);
        });
    }

    private String validaDadosQuestionario(Questionario questionario) {
        String textRelatorio = null;

        if (questionario == null) {
            textRelatorio = this.getString(R.string.msgQuestionarioNaoEncontrado);
        }

        return textRelatorio;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void redirectToPaginaInicial(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
