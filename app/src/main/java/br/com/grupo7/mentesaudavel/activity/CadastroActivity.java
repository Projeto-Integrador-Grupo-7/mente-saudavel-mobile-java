package br.com.grupo7.mentesaudavel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.com.grupo7.mentesaudavel.R;
import br.com.grupo7.mentesaudavel.api.ApiClient;
import br.com.grupo7.mentesaudavel.model.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroActivity extends AppCompatActivity {
    EditText txtNome, txtEmail, txtSenha, txtRepetirSenha, dtDataNascimento;
    Button btnJaPossuoConta, btnCadastrar;

    RadioGroup rgGenero;
    RadioButton rbFeminino, rbMasculino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        txtNome = findViewById(R.id.txtNome);
        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);
        txtRepetirSenha = findViewById(R.id.txtRepetirSenha);
        dtDataNascimento = findViewById(R.id.dtDataNascimento);
        rgGenero = findViewById(R.id.rgGenero);
        rbFeminino = findViewById(R.id.rbFeminino);
        rbMasculino = findViewById(R.id.rbMasculino);

        btnJaPossuoConta = findViewById(R.id.btnJaPossuoConta);
        btnCadastrar = findViewById(R.id.btnCadastrar);
    }

    // btnJaPossuoConta click
    public void redirectToLogin(View v) {
        this.finish();
    }

    // btnCadastrar click
    public void cadastrar(View v) {
        String nome = txtNome.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();
        String senha = txtSenha.getText().toString().trim();
        String repetirSenha = txtRepetirSenha.getText().toString().trim();
        String dataNascimento = dtDataNascimento.getText().toString().trim();

        String genero;

        if (!validarDados(nome, email, senha, repetirSenha, dataNascimento)) {
            return;
        }

        int generoIdSelecionado = rgGenero.getCheckedRadioButtonId();

        if (generoIdSelecionado == -1) {
            Toast.makeText(this, "Selecione o gênero.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (generoIdSelecionado == R.id.rbFeminino) {
            genero = "F";
        } else {
            genero = "M";
        }

        SimpleDateFormat sdfEntrada = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat sdfSaida = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dataFormatada;
        try {
            Date data = sdfEntrada.parse(dataNascimento);
            dataFormatada = sdfSaida.format(data);
        } catch (ParseException e) {
            Toast.makeText(this, "Erro ao formatar data.", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario usuario = new Usuario(nome, email, senha, dataFormatada, genero);

        cadastrarUsuario(usuario);
    }

    private boolean validarDados(String nome, String email, String senha, String repetirSenha, String dataNascimento) {
        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || dataNascimento.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Insira um e-mail válido.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (senha.length() < 8) {
            Toast.makeText(this, "A senha deve ter no mínimo 8 caracteres.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!senha.equals(repetirSenha)) {
            Toast.makeText(this, "As senhas não coincidem.", Toast.LENGTH_SHORT).show();
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        sdf.setLenient(false);
        try {
            sdf.parse(dataNascimento);
        } catch (ParseException e) {
            Toast.makeText(this, "Formato de data inválido. Use dd/MM/yyyy.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void cadastrarUsuario(Usuario usuario) {
        ApiClient.getApiInterface().cadastrarUsuario(usuario).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(CadastroActivity.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_LONG).show();
                redirectToLogin(null);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                ApiClient.tratarFalhaConexao(CadastroActivity.this, t);
            }
        });
    }
}