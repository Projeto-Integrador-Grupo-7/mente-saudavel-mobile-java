package br.com.grupo7.mentesaudavel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import br.com.grupo7.mentesaudavel.R;

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
        // Caso usuário não for autenticado corretamente, deve interromper o fluxo

        Bundle dados = new Bundle();
        dados.putString("email", email);
        dados.putString("senha", senha);

        redirectToHome(dados);
    }

    private void autenticarUsuario(String email, String senha) {
        // Chamar API para autenticação
    }

    private void redirectToHome(Bundle dados) {
        Intent telaHome = new Intent(LoginActivity.this, HomeActivity.class);
        telaHome.putExtras(dados);
        startActivity(telaHome);
    }
}
