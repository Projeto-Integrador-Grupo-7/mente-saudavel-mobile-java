package br.com.grupo7.mentesaudavel.model;

public class DashboardRequest {
    private String UsuarioId;
    private String DataInicio;
    private String DataFim;
    private char Genero;
    private int Idade;

    public DashboardRequest(String usuarioId) {
        this.UsuarioId = usuarioId;
    }

    public DashboardRequest(String usuarioId, String dataInicio, String dataFim, int idade, char genero) {
        this.UsuarioId = usuarioId;
        this.DataInicio = dataInicio;
        this.DataFim = dataFim;
        this.Idade = idade;
        this.Genero = genero;
    }
}
