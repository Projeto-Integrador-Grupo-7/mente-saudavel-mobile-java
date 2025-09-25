package br.com.grupo7.mentesaudavel.model;

import com.google.gson.annotations.SerializedName;

public class Usuario {

    private String usuarioId;

    private String nome;

    private String email;

    private String senha;

    @SerializedName("dataNascimento")
    private String dataNascimento;

    @SerializedName("sexo")
    private String genero;

    public Usuario(String nome, String email, String senha, String dataNascimento, String genero) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
    }

    public Usuario() {
    }

    // Getters e Setters
    public String getUsuarioId() { return usuarioId; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }
    public String getDataNascimento() { return dataNascimento; }
    public String getGenero() { return genero; }

    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
    public void setNome(String nome) { this.nome = nome; }
    public void setEmail(String email) { this.email = email; }
    public void setSenha(String senha) { this.senha = senha; }
    public void setDataNascimento(String dataNascimento) { this.dataNascimento = dataNascimento; }
    public void setGenero(String genero) { this.genero = genero; }
}