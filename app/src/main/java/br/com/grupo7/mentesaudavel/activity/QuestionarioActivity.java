package br.com.grupo7.mentesaudavel.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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
    private Button btnEnviar;
    private TextView txtResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionario);

        // Inicializar os RadioGroups (q1..q20)
        for (int i = 0; i < 20; i++) {
            String idName = "q" + (i + 1);
            int resID = getResources().getIdentifier(idName, "id", getPackageName());
            // resID pode ser 0 se id não existir no XML => melhor checar
            if (resID != 0) {
                perguntas[i] = findViewById(resID);
            } else {
                perguntas[i] = null; // evita NullPointer mais tarde
            }
        }

        btnEnviar = findViewById(R.id.btnEnviar);
        txtResultado = findViewById(R.id.txtResultado);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularResultadoEEnviar();
            }
        });
    }

    private void calcularResultadoEEnviar() {
        int sim = 0, nao = 0;
        Map<String, Boolean> respostas = new HashMap<>();

        for (int i = 0; i < perguntas.length; i++) {
            RadioGroup rg = perguntas[i];
            if (rg == null) continue; // se não existe no layout
            int selectedId = rg.getCheckedRadioButtonId();

            if (selectedId != -1) {
                RadioButton resposta = findViewById(selectedId);
                boolean valor = resposta.getText().toString().equalsIgnoreCase("Sim");

                respostas.put("q" + (i + 1), valor);

                if (valor) sim++;
                else nao++;
            } else {
                // Se quiser obrigar todas respondidas, trate aqui
                respostas.put("q" + (i + 1), false); // default false (ou não colocar)
            }
        }

        txtResultado.setText("Respostas SIM: " + sim + "\nRespostas NÃO: " + nao);
        txtResultado.setVisibility(View.VISIBLE);

        // Monta o objeto de requisição (troque pelo UUID do usuário real)
        QuestionarioRequest request = new QuestionarioRequest(
                UUID.fromString("daba7458-bbac-4643-8b13-359e68440b5e"),
                respostas
        );

        // Envia via Retrofit
        ApiInterface api = ApiClient.getApiInterface();
        Call<Void> call = api.enviarQuestionario(request);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    txtResultado.append("\n\nEnviado com sucesso!");
                } else {
                    txtResultado.append("\n\nErro ao enviar: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                txtResultado.append("\n\nFalha de conexão: " + t.getMessage());
            }
        });
    }
}
