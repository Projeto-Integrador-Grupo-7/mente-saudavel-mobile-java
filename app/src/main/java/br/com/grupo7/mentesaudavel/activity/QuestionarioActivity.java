package br.com.grupo7.mentesaudavel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import br.com.grupo7.mentesaudavel.R;

public class QuestionarioActivity extends AppCompatActivity {

    private RadioGroup[] perguntas = new RadioGroup[20];
    private Button btnEnviar;
    // private TextView txtResultado; // Removido, pois o resultado será exibido em ResultadoActivity

    public static final String EXTRA_RESULTADO_SIM = "VALOR_RESULTADO_SIM"; // Chave para o Intent extra

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

        btnEnviar = findViewById(R.id.btnEnviar);
        // txtResultado = findViewById(R.id.txtResultado); // Removido

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularResultadoEChamarResultadoActivity();
            }
        });
    }

    private void calcularResultadoEChamarResultadoActivity() {
        int sim = 0;
        // int nao = 0; // Variável 'nao' não é mais necessária para passar para a próxima activity

        for (RadioGroup rg : perguntas) {
            int selectedId = rg.getCheckedRadioButtonId();
            if (selectedId != -1) {
                RadioButton resposta = findViewById(selectedId);
                if (resposta.getText().toString().equalsIgnoreCase("Sim")) {
                    sim++;
                }
                // else { // Não precisamos mais contar 'nao' separadamente para este fluxo
                // nao++;
                // }
            }
        }

        // Remover a exibição do resultado nesta activity
        // txtResultado.setText("Respostas SIM: " + sim + "\nRespostas NÃO: " + nao);

        // Chamar a ResultadoActivity, passando a contagem de "Sim"
        Intent intent = new Intent(QuestionarioActivity.this, ResultadoActivity.class);
        intent.putExtra(EXTRA_RESULTADO_SIM, sim); // Passa a contagem de "Sim"
        startActivity(intent);
    }

}
