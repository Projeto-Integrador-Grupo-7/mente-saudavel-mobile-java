package br.com.grupo7.mentesaudavel.activity;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.grupo7.mentesaudavel.R;
import br.com.grupo7.mentesaudavel.adapter.HistoricoAdapter;
import br.com.grupo7.mentesaudavel.model.HistoricoItem;

public class DashboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        getHistorico();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void getHistorico() {
        RecyclerView rvHistorico = findViewById(R.id.rvHistorico);
        rvHistorico.setLayoutManager(new LinearLayoutManager(this));

        List<HistoricoItem> listaHistorico = new ArrayList<>();
        listaHistorico.add(new HistoricoItem("01/01/2020", "Sofrimento Leve", "5"));
        listaHistorico.add(new HistoricoItem("28/08/2023", "Sofrimento Moderado", "15"));
        listaHistorico.add(new HistoricoItem("25/12/2025", "Sofrimento Grave", "20"));

        HistoricoAdapter adapter = new HistoricoAdapter(listaHistorico);
        rvHistorico.setAdapter(adapter);
    }
}