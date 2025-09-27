package br.com.grupo7.mentesaudavel.activity;
import androidx.appcompat.app.AppCompatActivity;
import br.com.grupo7.mentesaudavel.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void redirectToQuestionario(View view) {
        Intent intent = new Intent(this, QuestionarioActivity.class);
        startActivity(intent);
    }

    public void redirectToDashboard(View view) {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }
}
