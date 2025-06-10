package com.solo.jbsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

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
        Button loginButton = findViewById(R.id.button); // Dê um ID ao seu botão no XML

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                // Agora emailField e senhaField já estão inicializados e não serão null
                String email = emailField.getText() != null ? emailField.getText().toString() : "";
                String senha = senhaField.getText() != null ? senhaField.getText().toString() : "";

                db.collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        boolean encontrado = false;

                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            String userEmail = document.getString("email");
                            String userSenha = document.getString("senha");

                            if (email.equals(userEmail) && senha.equals(userSenha)) {
                                encontrado = true;
                                Intent intent = new Intent(LoginActivity.this, ListaCarro.class); // Use SuaActivity.this para o contexto
                                startActivity(intent); // Chame startActivity diretamente
                                break;
                            }
                        }

                        if (!encontrado) {
                            Toast.makeText(LoginActivity.this, "Senha ou email incorretos", Toast.LENGTH_SHORT).show(); // Use SuaActivity.this para o contexto
                        }
                    }
                });
            }
        });
    }

}