package br.com.grupo7.mentesaudavel.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.grupo7.mentesaudavel.R;
import br.com.grupo7.mentesaudavel.model.HistoricoItem;

public class HistoricoAdapter extends RecyclerView.Adapter<HistoricoAdapter.ViewHolder> {
    private final List<HistoricoItem> historicoDados;

    public HistoricoAdapter(List<HistoricoItem> historicoDados) {
        this.historicoDados = historicoDados;
    }

    @NonNull
    @Override
    public HistoricoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.historico_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoricoAdapter.ViewHolder holder, int position) {
        HistoricoItem historicoItem = historicoDados.get(position);
        holder.txtHistoricoData.setText(historicoItem.data);
        holder.txtHistoricoResultado.setText(historicoItem.resultado);
        holder.txtHistoricoPontuacao.setText(historicoItem.pontuacao);
    }

    @Override
    public int getItemCount() {
        return historicoDados.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtHistoricoData, txtHistoricoResultado, txtHistoricoPontuacao;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtHistoricoData = itemView.findViewById(R.id.txtHistoricoData);
            txtHistoricoResultado = itemView.findViewById(R.id.txtHistoricoResultado);
            txtHistoricoPontuacao = itemView.findViewById(R.id.txtHistoricoPontuacao);
        }
    }
}
