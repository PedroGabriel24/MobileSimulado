package com.solo.jbsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class CadastroActivity extends AppCompatActivity {

    private UserRepository db = new UserRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextInputEditText emailField = findViewById(R.id.email);
        TextInputEditText senhaField = findViewById(R.id.senha);
        TextView loginButton = findViewById(R.id.login);
        Button cadastroButton = findViewById(R.id.button); // Dê um ID ao seu botão no XML

        cadastroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.cadastrarUser(
                        new User(emailField.getText().toString(), senhaField.getText().toString(), true),
                        getApplicationContext(),
                        new UserRepository.CadastroCallback() {
                            @Override
                            public void onCadastroConcluido(boolean sucesso, String email, Boolean role) {
                                if (sucesso){
                                    Bundle bundle = new Bundle();
                                    bundle.putString("email", email);
                                    bundle.putBoolean("role", role);

                                    Intent intent = new Intent(CadastroActivity.this, ListaCarro.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            }
                        });
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}