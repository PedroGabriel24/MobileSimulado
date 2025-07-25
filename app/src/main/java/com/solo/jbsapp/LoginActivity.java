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
import com.solo.jbsapp.Carro.ListaCarro;

public class LoginActivity extends AppCompatActivity {

    private UserRepository db = new UserRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextInputEditText emailField = findViewById(R.id.email);
        TextInputEditText senhaField = findViewById(R.id.senha);
        TextView cadastroButton = findViewById(R.id.cadastro);
        Button loginButton = findViewById(R.id.button); // Dê um ID ao seu botão no XML

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.verificarLogin(
                        emailField.getText().toString(),
                        senhaField.getText().toString(),
                        getApplicationContext(),
                        new UserRepository.LoginCallback() {
                            @Override
                            public void onLoginConcluido(boolean sucesso, String email, Boolean role) {
                                if (sucesso){
                                    Bundle bundle = new Bundle();
                                    bundle.putString("email", email);
                                    bundle.putBoolean("role", role);

                                    Intent intent = new Intent(LoginActivity.this, ListaCarro.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            }
                        });
            }
        });

        cadastroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });
    }

}