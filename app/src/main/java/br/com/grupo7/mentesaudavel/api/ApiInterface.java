package br.com.grupo7.mentesaudavel.api;

import java.util.List;
import java.util.Map;

import br.com.grupo7.mentesaudavel.model.DashboardRequest;
import br.com.grupo7.mentesaudavel.model.Questionario;
import br.com.grupo7.mentesaudavel.model.QuestionarioRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("dashboard/historico")
    Call<List<Questionario>> getQuestionariosRespondidos(@Body DashboardRequest request);

    @POST("dashboard/graficoPizza")
    Call<Map<String, Integer>> getQtdeUsuariosPorEstratificacao(@Body DashboardRequest request);

    @POST("questionarios")
    Call<Void> enviarQuestionario(@Body QuestionarioRequest request);
}
