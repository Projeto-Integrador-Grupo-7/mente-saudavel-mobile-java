package br.com.grupo7.mentesaudavel.api;

import java.util.List;

import br.com.grupo7.mentesaudavel.model.DashboardRequest;
import br.com.grupo7.mentesaudavel.model.Questionario;
import br.com.grupo7.mentesaudavel.model.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import java.util.Map;

public interface ApiInterface {
    @POST("dashboard/historico")
    Call<List<Questionario>> getQuestionariosRespondidos(@Body DashboardRequest request);
    @POST("usuarios")
    Call<Void> cadastrarUsuario(@Body Usuario usuario);
    @POST("usuarios/login")
    Call<Usuario> logarUsuarioComMapa(@Body Map<String, String> dadosLogin);
}
