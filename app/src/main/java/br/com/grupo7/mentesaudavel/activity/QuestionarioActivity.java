package br.com.grupo7.mentesaudavel.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import br.com.grupo7.mentesaudavel.R;
import br.com.grupo7.mentesaudavel.api.ApiClient;
import br.com.grupo7.mentesaudavel.api.ApiInterface;
import br.com.grupo7.mentesaudavel.model.QuestionarioRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionarioActivity extends AppCompatActivity {

    private RadioGroup[] perguntas = new RadioGroup[20];

    // Chave para o Intent extra. É boa prática usar o caminho completo da classe para garantir unicidade.
    public static final String EXTRA_RESULTADO_SIM = "br.com.grupo7.mentesaudavel.activity.QuestionarioActivity.EXTRA_RESULTADO_SIM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionario);

        // Inicializar os RadioGroups (20 perguntas)
        for (int i = 0; i < 20; i++) {
            String idName = "q" + (i + 1);
            int resID = getResources().getIdentifier(idName, "id", getPackageName());
            perguntas[i] = findViewById(resID);
        }
    }

    public void onClickBtnEnviar(View v) {
        Map<String, Boolean> respostas = mapearRespostas();

        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String usuarioId = prefs.getString("UsuarioId", null);

        QuestionarioRequest request = new QuestionarioRequest(
                UUID.fromString(usuarioId),
                respostas
        );

        ApiInterface api = ApiClient.getApiInterface();
        Call<Void> call = api.enviarQuestionario(request);
        Callback<Void> callback = callbackQuestionario(respostas);
        call.enqueue(callback);
    }

    private Map<String, Boolean> mapearRespostas() {
        Map<String, Boolean> respostas = new HashMap<>();

        int count = 0;
        for (RadioGroup rg : perguntas) {
            int selectedId = rg.getCheckedRadioButtonId();
            if (selectedId != -1) {
                count++;
                RadioButton resposta = findViewById(selectedId);
                if (resposta.getText().toString().equalsIgnoreCase("Sim")) {
                    respostas.put("q" + count, true);
                }
                else{
                    respostas.put("q" + count, false);
                }
            }
        }

        return respostas;
    }

    private void redirectToResultado(int quantidadeSim) {
        Intent intent = new Intent(QuestionarioActivity.this, ResultadoActivity.class);
        intent.putExtra(EXTRA_RESULTADO_SIM, quantidadeSim);
        startActivity(intent);
    }

    private Callback<Void> callbackQuestionario(Map<String, Boolean> respostas) {
        return new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    long quantidadeSim = respostas.values()
                            .stream()
                            .filter(Boolean::booleanValue)
                            .count();
                    redirectToResultado((int)quantidadeSim);
                } else {
                    Toast.makeText(QuestionarioActivity.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(QuestionarioActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
    }
}