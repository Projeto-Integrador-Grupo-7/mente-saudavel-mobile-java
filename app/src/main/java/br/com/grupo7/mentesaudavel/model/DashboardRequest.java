package br.com.grupo7.mentesaudavel.model;

import java.util.Date;

public class DashboardRequest {
    private String UsuarioId;
    private Date DataInicio;
    private Date DataFim;
    private String Genero;
    private Integer Idade;

    public DashboardRequest(String usuarioId) {
        this.UsuarioId = usuarioId;
    }
}
