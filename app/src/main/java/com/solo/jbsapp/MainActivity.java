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
import com.solo.jbsapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

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

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                TextInputEditText emailField = v.findViewById(R.id.email);
                TextInputEditText senhaField = v.findViewById(R.id.senha);

                // Verifica se os campos foram encontrados
                if (emailField == null || senhaField == null) {
                    Toast.makeText(v.getContext(), "Erro: Campos não encontrados", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Obtém os valores dos campos antes do callback
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
                                Intent intent = new Intent(v.getContext(), ListaCarro.class);
                                v.getContext().startActivity(intent);
                                break;
                            }
                        }

                        if (!encontrado) {
                            Toast.makeText(v.getContext(), "Senha ou email incorretos", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}