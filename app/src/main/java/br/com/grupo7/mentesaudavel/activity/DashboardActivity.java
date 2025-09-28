package br.com.grupo7.mentesaudavel.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import br.com.grupo7.mentesaudavel.R;
import br.com.grupo7.mentesaudavel.adapter.HistoricoAdapter;
import br.com.grupo7.mentesaudavel.api.ApiClient;
import br.com.grupo7.mentesaudavel.api.ApiInterface;
import br.com.grupo7.mentesaudavel.model.DashboardRequest;
import br.com.grupo7.mentesaudavel.model.Questionario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {
    EditText filtroDataInicio, filtroDataFim, filtroIdade;
    Spinner filtroGenero;
    String usuarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        usuarioId = prefs.getString("UsuarioId", null);

        filtroDataInicio = findViewById(R.id.filtroDataInicio);
        filtroDataFim = findViewById(R.id.filtroDataFim);
        filtroIdade = findViewById(R.id.filtroIdade);
        filtroGenero = findViewById(R.id.filtroGenero);

        configurarFiltros();

        getQuestionariosRespondidos(new DashboardRequest(usuarioId));
        getQtdeUsuariosPorEstratificacao(new DashboardRequest(usuarioId));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // region FILTROS
    private void configurarFiltros() {
        LinearLayout painelFiltros = findViewById(R.id.painelFiltros);

        ImageButton btnFiltros = findViewById(R.id.btnFiltros);
        btnFiltros.setOnClickListener(v -> {
            if (painelFiltros.getVisibility() == View.GONE) {
                painelFiltros.setVisibility(View.VISIBLE);
            } else {
                painelFiltros.setVisibility(View.GONE);
            }
        });

        configurarSpinner();
    }

    private void configurarSpinner() {
        Spinner filtroGenero = findViewById(R.id.filtroGenero);
        String[] generos = {"Gênero", "Feminino", "Masculino" };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                generos
        ) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = view.findViewById(android.R.id.text1);
                text.setTextColor(Color.WHITE);
                return view;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filtroGenero.setAdapter(adapter);
        filtroGenero.setSelection(0);
    }

    public void onClickFiltrar(View v) {
        String dataInicio = filtroDataInicio.getText().toString();
        String dataFim = filtroDataFim.getText().toString();
        String stringIdade = filtroIdade.getText().toString();
        String stringGenero = filtroGenero.getSelectedItem().toString();

        dataInicio = formatarData(dataInicio);
        dataFim = formatarData(dataFim);
        int idade = stringIdade.isEmpty() ? 0 : Integer.parseInt(stringIdade);
        String genero = stringGenero.isEmpty() || stringGenero.equals("Gênero") ? null : stringGenero.substring(0, 1);

        DashboardRequest request = new DashboardRequest(usuarioId, dataInicio, dataFim, idade, genero);

        getQuestionariosRespondidos(request);
        getQtdeUsuariosPorEstratificacao(request);
    }

    private String formatarData(String data) {
        if (data.isEmpty()){
            return null;
        }

        String[] dataSplitada = data.split("/");

        data = dataSplitada[2] + "-" + dataSplitada[1] + "-" + dataSplitada[0];

        return data;
    }
    // endregion

    // region BOTOES
    public void redirectToQuestionario(View v) {
        Intent telaQuestionario = new Intent(DashboardActivity.this, QuestionarioActivity.class);
        startActivity(telaQuestionario);
    }

    public void redirectToRelatorio(View v) {
        Intent telaRelatorio = new Intent(DashboardActivity.this, RelatorioActivity.class);
        startActivity(telaRelatorio);
    }
    // endregion

    // region HISTORICO
    private void getQuestionariosRespondidos(DashboardRequest request) {
        ApiInterface apiInterface = ApiClient.getApiInterface();

        Call<List<Questionario>> call = apiInterface.getQuestionariosRespondidos(request);

        Callback<List<Questionario>> callback = callbackHistorico();

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

    private Callback<List<Questionario>> callbackHistorico() {
        return new Callback<>() {
            @Override
            public void onResponse(Call<List<Questionario>> call, Response<List<Questionario>> response) {
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
    // endregion

    // region GRAFICO PIZZA
    private void getQtdeUsuariosPorEstratificacao(DashboardRequest request) {
        ApiInterface apiInterface = ApiClient.getApiInterface();

        Call<Map<String, Integer>> call = apiInterface.getQtdeUsuariosPorEstratificacao(request);

        Callback<Map<String, Integer>> callback = callbackGraficoPizza();

        call.enqueue(callback);
    }

    private Callback<Map<String, Integer>> callbackGraficoPizza() {
        return new Callback<>() {
            @Override
            public void onResponse(Call<Map<String, Integer>> call, Response<Map<String, Integer>> response) {
                if (response.isSuccessful()) {
                    popularGrafico(response.body());
                } else {
                    ApiClient.tratarErroHttp(DashboardActivity.this, response.code());
                }
            }

            @Override
            public void onFailure(Call<Map<String, Integer>> call, Throwable error) {
                ApiClient.tratarFalhaConexao(DashboardActivity.this, error);
            }
        };
    }

    private void popularGrafico(Map<String, Integer> qtdeUsuariosPorEstratificacao) {
        runOnUiThread(() -> {
            PieChart graficoPizza = findViewById(R.id.graficoPizza);

            float total = 0f;
            ArrayList<PieEntry> entries = new ArrayList<>();

            for (Map.Entry<String, Integer> entry : qtdeUsuariosPorEstratificacao.entrySet()) {
                total += entry.getValue();

                String label = getLabelResumido(entry.getKey());
                entries.add(new PieEntry(entry.getValue(), label));
            }

            if (total <= 0f) {
                graficoPizza.clear();
                graficoPizza.invalidate();
                return;
            }

            PieDataSet dataSet = new PieDataSet(entries, "");
            dataSet.setColors(new int[]{
                    Color.parseColor("#0088FE"),
                    Color.parseColor("#00C49F"),
                    Color.parseColor("#FFBB28"),
                    Color.parseColor("#FF8042")
            });

            dataSet.setValueTextSize(14f);

            PieData data = new PieData(dataSet);

            data.setValueFormatter(new ValueFormatter() {
                @Override
                public String getPieLabel(float value, PieEntry pieEntry) {
                    return String.format(Locale.getDefault(), "%.0f%%", value);
                }
            });

            data.setValueTypeface(Typeface.DEFAULT_BOLD);

            graficoPizza.setData(data);
            graficoPizza.setUsePercentValues(true);
            graficoPizza.setEntryLabelColor(Color.BLACK);
            graficoPizza.setEntryLabelTextSize(12f);
            graficoPizza.setExtraBottomOffset(20f);

            configurarLegenda(graficoPizza);

            graficoPizza.getDescription().setEnabled(false);
            graficoPizza.animateY(1000, Easing.EaseInOutQuad);

            graficoPizza.invalidate();
        });
    }

    private String getLabelResumido(String label) {
        switch (label) {
            case "Sofrimento Não Identificado":
                return "Não Identificado";
            case "Sofrimento Leve":
                return "Leve";
            case "Sofrimento Moderado":
                return "Moderado";
            case "Sofrimento Grave":
                return "Grave";
            default:
                return label;
        }
    }

    private void configurarLegenda(PieChart graficoPizza) {
        Legend legend = graficoPizza.getLegend();
        legend.setWordWrapEnabled(true);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(true);
        legend.setTypeface(Typeface.DEFAULT_BOLD);
    }
    // endregion
}