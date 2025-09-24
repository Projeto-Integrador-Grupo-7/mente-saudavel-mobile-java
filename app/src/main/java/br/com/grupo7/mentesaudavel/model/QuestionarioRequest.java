package br.com.grupo7.mentesaudavel.model;

import java.util.Map;
import java.util.UUID;

public class QuestionarioRequest {
    private UUID usuarioId;
    private Map<String, Boolean> respostas;

    public QuestionarioRequest(UUID usuarioId, Map<String, Boolean> respostas) {
        this.usuarioId = usuarioId;
        this.respostas = respostas;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public Map<String, Boolean> getRespostas() {
        return respostas;
    }
}
