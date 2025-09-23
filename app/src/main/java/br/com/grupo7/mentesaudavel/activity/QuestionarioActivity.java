package br.com.grupo7.mentesaudavel.activity;

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
    private TextView txtResultado;

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
        txtResultado = findViewById(R.id.txtResultado);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularResultado();
            }
        });
    }

    private void calcularResultado() {
        int sim = 0, nao = 0;

        for (RadioGroup rg : perguntas) {
            int selectedId = rg.getCheckedRadioButtonId();
            if (selectedId != -1) {
                RadioButton resposta = findViewById(selectedId);
                if (resposta.getText().toString().equalsIgnoreCase("Sim")) {
                    sim++;
                } else {
                    nao++;
                }
            }
        }

        txtResultado.setText("Respostas SIM: " + sim + "\nRespostas NÃO: " + nao);
    }

}
