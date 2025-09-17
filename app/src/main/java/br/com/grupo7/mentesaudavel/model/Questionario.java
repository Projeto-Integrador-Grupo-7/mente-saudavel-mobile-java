package br.com.grupo7.mentesaudavel.model;

public class Questionario {
    private String id;
    public String pontuacao;
    public Estratificacao estratificacao;
    public String dataEnvio;

    public Questionario(String pontuacao, Estratificacao estratificacao, String dataEnvio) {
        this.pontuacao = pontuacao;
        this.estratificacao = estratificacao;
        this.dataEnvio = dataEnvio;
    }

    public String getId() { return id; }
}