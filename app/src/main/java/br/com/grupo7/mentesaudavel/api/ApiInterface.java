package br.com.grupo7.mentesaudavel.api;

import java.util.List;

import br.com.grupo7.mentesaudavel.model.DashboardRequest;
import br.com.grupo7.mentesaudavel.model.Questionario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("dashboard/historico")
    Call<List<Questionario>> getQuestionariosRespondidos(@Body DashboardRequest request);
}
