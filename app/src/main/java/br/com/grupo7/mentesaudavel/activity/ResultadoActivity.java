package br.com.grupo7.mentesaudavel.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import br.com.grupo7.mentesaudavel.R;
import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Button;
import android.widget.TextView;
public class ResultadoActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private Button btnHome;
    private Button btnDashboard;
    private TextView textViewResultadoDescricao;
    private TextView textViewResultadoTitulo;

    // TextViews para os Emojis
    private TextView emojiFeliz; // 😁 textView_emoji_inicio
    private TextView emojiSatisfeito; // 😊 textView_emoji_inicio3
    private TextView emojiNeutro; // 😐 textView_emoji_inicio2
    private TextView emojiDoente; // 🤒 textView_emoji_inicio5
    private TextView emojiTriste; // 😣 textView_emoji_fim


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        // Associa as variáveis aos IDs dos elementos no layout XML
        progressBar = findViewById(R.id.progressBar);
        btnHome = findViewById(R.id.btnHome);
        btnDashboard = findViewById(R.id.btnDashboard);
        textViewResultadoDescricao = findViewById(R.id.textView_resultado_descricao);
        textViewResultadoTitulo = findViewById(R.id.textView_resultado_titulo);

        // Associa os TextViews dos Emojis
        emojiFeliz = findViewById(R.id.textView_emoji_inicio);
        emojiSatisfeito = findViewById(R.id.textView_emoji_inicio3);
        emojiNeutro = findViewById(R.id.textView_emoji_inicio2);
        emojiDoente = findViewById(R.id.textView_emoji_inicio5);
        emojiTriste = findViewById(R.id.textView_emoji_fim);

        // Recupera a contagem de respostas "Sim" do Intent
        int contagemSim = getIntent().getIntExtra(QuestionarioActivity.EXTRA_RESULTADO_SIM, 0); // Padrão 0 se não encontrar

        // Mapeia a contagem de "Sim" (0-20) para valorDoResultado (1-5)
        int valorDoResultado;
        if (contagemSim >= 15) { // 15-20 SIM
            valorDoResultado = 5;
        } else if (contagemSim >= 11) { // 11-14 SIM
            valorDoResultado = 4;
        } else if (contagemSim >= 8) { // 8-12 SIM
            valorDoResultado = 3;
        } else if (contagemSim >= 1) { // 1-7 SIM
            valorDoResultado = 2;
        } else { // 0 SIM
            valorDoResultado = 1;
        }

        // --- LÓGICA DA PROGRESSBAR E TEXTOS ---
        int progresso = (valorDoResultado * 100) / 5;
        progressBar.setProgress(progresso);

        // Atualiza o destaque do emoji
        atualizarDestaqueEmoji(valorDoResultado);

        // Define a lógica condicional para exibir textos diferentes com base no resultado.
        if (valorDoResultado == 1) {
            textViewResultadoTitulo.setText("Sofrimento Não Identificado");
            textViewResultadoDescricao.setText("Felicitações! Seu resultado reflete uma saúde mental robusta e em equilíbrio. Este é um excelente momento para reforçar práticas que nutrem a mente e o corpo. Continue cultivando sua resiliência e a alegria nas pequenas coisas. Lembre-se, o autocuidado é um investimento contínuo.");
        } else if (valorDoResultado == 2) {
            textViewResultadoTitulo.setText("Sofrimento Leve");
            textViewResultadoDescricao.setText("Seu resultado aponta para uma saúde mental positiva, mas com oportunidades para fortalecimento. A vida tem seus desafios, e focar em estratégias como a meditação, exercícios de respiração e a conexão com a natureza pode ajudar a manter a serenidade. Você está em um ótimo caminho, e pequenos passos farão toda a diferença.");
        } else if (valorDoResultado == 3) {
            textViewResultadoTitulo.setText("Sofrimento Moderado");
            textViewResultadoDescricao.setText("Seu bem-estar emocional se encontra em um ponto de atenção. É normal sentir-se sobrecarregado(a) em alguns momentos. Considere este um convite para desacelerar e focar no que realmente importa. Que tal um hobby novo, um encontro com amigos ou um tempo de qualidade consigo mesmo(a)? O autocuidado é a chave para o seu retorno ao eixo.");
        } else if (valorDoResultado == 4) {
            textViewResultadoTitulo.setText("Sofrimento Moderado");
            textViewResultadoDescricao.setText("O nível de estresse e desconforto emocional merece sua atenção imediata. Não hesite em buscar suporte. Conversar abertamente com um amigo, familiar ou, idealmente, um profissional de saúde mental, pode aliviar um peso enorme. Você não está sozinho(a), e existem recursos disponíveis para te ajudar.");
        } else if (valorDoResultado == 5) {
            textViewResultadoTitulo.setText("Sofrimento Grave");
            textViewResultadoDescricao.setText("Seu resultado indica um nível elevado de sofrimento emocional que requer suporte urgente. A sua saúde mental é a sua maior prioridade. Por favor, procure um profissional qualificado ou um serviço de emergência. A jornada pode ser desafiadora, mas com a ajuda certa, a recuperação é possível. Você merece todo o suporte necessário.");
        }

        // --- LÓGICA DOS BOTÕES ---
        btnDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultadoActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultadoActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void atualizarDestaqueEmoji(int valorDoResultado) {
        // Primeiro, remove o background de todos os emojis
        emojiFeliz.setBackground(null);
        emojiSatisfeito.setBackground(null);
        emojiNeutro.setBackground(null);
        emojiDoente.setBackground(null);
        emojiTriste.setBackground(null);

        // Aplica o background ao emoji correspondente
        switch (valorDoResultado) {
            case 1:
                emojiFeliz.setBackground(ContextCompat.getDrawable(this, R.drawable.emoji_highlight_border));
                break;
            case 2:
                emojiSatisfeito.setBackground(ContextCompat.getDrawable(this, R.drawable.emoji_highlight_border));
                break;
            case 3:
                emojiNeutro.setBackground(ContextCompat.getDrawable(this, R.drawable.emoji_highlight_border));
                break;
            case 4:
                emojiDoente.setBackground(ContextCompat.getDrawable(this, R.drawable.emoji_highlight_border));
                break;
            case 5:
                emojiTriste.setBackground(ContextCompat.getDrawable(this, R.drawable.emoji_highlight_border));
                break;
        }
    }
}
