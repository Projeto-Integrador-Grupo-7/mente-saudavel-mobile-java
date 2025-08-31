package br.com.grupo7.mentesaudavel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CadastroActivity extends AppCompatActivity {
    EditText txtNome, txtEmail, txtSenha, txtRepetirSenha, dtDataNascimento;
    Button btnJaPossuoConta, btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        txtNome = findViewById(R.id.txtNome);
        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);
        txtRepetirSenha = findViewById(R.id.txtRepetirSenha);
        dtDataNascimento = findViewById(R.id.dtDataNascimento);
        btnJaPossuoConta = findViewById(R.id.btnJaPossuoConta);
        btnCadastrar = findViewById(R.id.btnCadastrar);
    }

    // btnJaPossuoConta click
    public void redirectToLogin(View v) {
        this.finish();
    }

    // btnCadastrar click
    public void cadastrar(View v) {
        String nome = txtNome.getText().toString();
        String email = txtEmail.getText().toString();
        String senha = txtSenha.getText().toString();
        String dataNascimento = txtSenha.getText().toString();

        //validarDados(email, senha, dataNascimento);

        Bundle dados = new Bundle();
        dados.putString("nome", nome);
        dados.putString("email", email);
        dados.putString("senha", senha);
        dados.putString("dataNascimento", dataNascimento);

        salvarUsuario(dados);
    }

    private void salvarUsuario(Bundle dados) {
        this.finish();
    }

    private void validarDados(String email, String senha, String dataNascimento) {
        validarEmail(email);
        validarSenha(senha);
        validarDataNascimento(dataNascimento);
    }

    private void validarEmail(String email) throws IllegalArgumentException {
        if (email.isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }
    }

    private void validarSenha(String senha) throws IllegalArgumentException {
        if (senha.length() < 4) {
            throw new IllegalArgumentException("Senha deve ter no mínimo 4 dígitos");
        }
    }

    private void validarDataNascimento(String dataNascimento) throws IllegalArgumentException {
        if (dataNascimento.isEmpty()) {
            dtDataNascimento.setError("Campo obrigatório");
            throw new IllegalArgumentException("Data de nascimento deve ser preenchida");
        }

        //LocalDate data = LocalDate.parse(dataNascimento, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        //if ()
    }
}
