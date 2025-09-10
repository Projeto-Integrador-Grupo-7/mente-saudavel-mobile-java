package br.com.grupo7.mentesaudavel.model;

public class Questionario {
    private String id;
    public String pontuacao;
    public String resultado;
    public String dataEnvio;

    public Questionario(String pontuacao, String resultado, String dataEnvio) {
        this.pontuacao = pontuacao;
        this.resultado = resultado;
        this.dataEnvio = dataEnvio;
    }

    public String getId() { return id; }
}