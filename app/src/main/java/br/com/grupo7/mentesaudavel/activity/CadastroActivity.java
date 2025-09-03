package br.com.grupo7.mentesaudavel.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import br.com.grupo7.mentesaudavel.R;

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

        validarDados(email, senha, dataNascimento);

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

    private void validarEmail(String email) {
        // Aplicar regras básicas para validação de email
    }

    private void validarSenha(String senha) {
        // Aplicar regras básicas para validação de senha
    }

    private void validarDataNascimento(String dataNascimento) {
        // Aplicar regras básicas para validação de data de nascimento
    }
}
