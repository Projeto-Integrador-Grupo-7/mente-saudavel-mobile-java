package br.com.grupo7.mentesaudavel.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.widget.Button;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Button btnQuestionario = findViewById(R.id.btnQuestionario);
        btnQuestionario.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, QuestionarioActivity.class);
            startActivity(intent);
        });

        getQuestionariosRespondidos();
        getQtdeUsuariosPorEstratificacao();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    // region HISTORICO
    private void getQuestionariosRespondidos() {
        ApiInterface apiInterface = ApiClient.getApiInterface();

        DashboardRequest request = new DashboardRequest("daba7458-bbac-4643-8b13-359e68440b5e");

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
    private void getQtdeUsuariosPorEstratificacao() {
        ApiInterface apiInterface = ApiClient.getApiInterface();

        DashboardRequest request = new DashboardRequest();

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