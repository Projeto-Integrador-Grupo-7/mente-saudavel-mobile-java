package br.com.grupo7.mentesaudavel.model;

public class DashboardRequest {
    private String UsuarioId;
    private String DataInicio;
    private String DataFim;
    private char Genero;
    private int Idade;

    public DashboardRequest() {
        this.UsuarioId = "daba7458-bbac-4643-8b13-359e68440b5e";
    }

    public DashboardRequest(String dataInicio, String dataFim, int idade, char genero) {
        this.UsuarioId = "daba7458-bbac-4643-8b13-359e68440b5e";
        this.DataInicio = dataInicio;
        this.DataFim = dataFim;
        this.Idade = idade;
        this.Genero = genero;
    }
}
