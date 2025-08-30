package br.com.grupo7.mentesaudavel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    EditText txtEmail, txtSenha;
    Button btnCadastro, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);
    }

    // btnCadastro click
    public void redirectToCadastro(View v) {
        Intent telaCadastro = new Intent(this, CadastroActivity.class);
        startActivity(telaCadastro);
    }

    // btnLogin click
    public void logar(View v) {
        String email = txtEmail.getText().toString();
        String senha = txtSenha.getText().toString();

        autenticarUsuario(email, senha);

        Bundle dados = new Bundle();
        dados.putString("email", email);
        dados.putString("senha", senha);

        redirectToHome(dados);
    }

    private void autenticarUsuario(String email, String senha) {
        if (!Objects.equals(email, "teste@email.com") ||
            !Objects.equals(senha, "teste")) {
            Toast.makeText(this, "Email ou senha inválidos!", Toast.LENGTH_LONG).show();
            return;
        }
    }

    private void redirectToHome(Bundle dados) {
        Intent telaHome = new Intent(LoginActivity.this, HomeActivity.class);
        telaHome.putExtras(dados);
        startActivity(telaHome);
    }
}
