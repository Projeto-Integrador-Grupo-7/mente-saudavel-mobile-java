package br.com.grupo7.mentesaudavel.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.grupo7.mentesaudavel.R;
import br.com.grupo7.mentesaudavel.model.Questionario;

public class HistoricoAdapter extends RecyclerView.Adapter<HistoricoAdapter.ViewHolder> {
    private final List<Questionario> listaQuestionariosRespondidos;

    public HistoricoAdapter(List<Questionario> listaQuestionariosRespondidos) {
        this.listaQuestionariosRespondidos = listaQuestionariosRespondidos;
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
        Questionario questionario = listaQuestionariosRespondidos.get(position);
        holder.txtQuestionarioData.setText(questionario.dataEnvio);
        holder.txtQuestionarioResultado.setText(questionario.resultado);
        holder.txtQuestionarioPontuacao.setText(questionario.pontuacao);
    }

    @Override
    public int getItemCount() {
        return listaQuestionariosRespondidos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtQuestionarioData, txtQuestionarioResultado, txtQuestionarioPontuacao;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtQuestionarioData = itemView.findViewById(R.id.txtQuestionarioData);
            txtQuestionarioResultado = itemView.findViewById(R.id.txtQuestionarioResultado);
            txtQuestionarioPontuacao = itemView.findViewById(R.id.txtQuestionarioPontuacao);
        }
    }
}
