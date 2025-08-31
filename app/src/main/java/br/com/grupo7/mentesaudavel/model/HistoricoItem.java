package br.com.grupo7.mentesaudavel.model;

public class HistoricoItem {
    public String data;
    public String resultado;
    public String pontuacao;

    public HistoricoItem(String data, String resultado, String pontuacao) {
        this.data = data;
        this.resultado = resultado;
        this.pontuacao = pontuacao;
    }
}