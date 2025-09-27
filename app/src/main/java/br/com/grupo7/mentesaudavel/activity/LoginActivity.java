package br.com.grupo7.mentesaudavel.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import br.com.grupo7.mentesaudavel.R;

public class LoginActivity extends AppCompatActivity {
    EditText txtEmail, txtSenha;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);
    }

    public void redirectToCadastro(View v) {
        Intent telaCadastro = new Intent(this, CadastroActivity.class);
        startActivity(telaCadastro);
    }

    public void logar(View v) {
        String email = txtEmail.getText().toString().trim();
        String senha = txtSenha.getText().toString().trim();

        if (email.isEmpty() || senha.isEmpty()) {
            txtEmail.setError("Informe o email");
            txtSenha.setError("Informe a senha");
            return;
        }

        new Thread(() -> {
            SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();

            boolean autenticado = autenticarUsuario(editor, email, senha);

            runOnUiThread(() -> {
                if (autenticado) {
                    editor.apply();
                    Bundle dados = new Bundle();
                    dados.putString("email", email);
                    redirectToHome(dados);
                } else {
                    txtSenha.setError("Usuário ou senha inválidos");
                }
            });
        }).start();
    }

    private boolean autenticarUsuario(SharedPreferences.Editor editor, String email, String senha) {
        try {
            URL url = new URL("http://10.0.2.2:5014/api/usuarios/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);

            String jsonInputString = "{ \"email\": \"" + email + "\", \"senha\": \"" + senha + "\" }";

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int code = conn.getResponseCode();

            BufferedReader reader;
            if (code >= 200 && code < 300) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line.trim());
                }
                reader.close();
                conn.disconnect();

                String responseBody = response.toString();
                JSONObject json = new JSONObject(responseBody);

                String usuarioId = json.getString("usuarioId");

                editor.putString("UsuarioId", usuarioId);
            }

            return code == 200;

        } catch (Exception e) {
            Log.e("LoginActivity", "Erro ao autenticar usuário", e);
            return false;
        }
    }

    private void redirectToHome(Bundle dados) {
        Intent telaHome = new Intent(LoginActivity.this, HomeActivity.class);
        telaHome.putExtras(dados);
        startActivity(telaHome);
    }
}
