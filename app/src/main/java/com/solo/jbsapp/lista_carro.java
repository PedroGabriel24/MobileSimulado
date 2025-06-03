package com.solo.jbsapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.solo.jbsapp.databinding.ActivityListaCarroBinding;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class lista_carro extends AppCompatActivity {
    private ActivityListaCarroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_carro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding = ActivityListaCarroBinding.inflate(getLayoutInflater());

        binding.rv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        // Configurar o Adapter do RecyclerView
        List<Carro> listaCarro = new ArrayList<>();
        AdapterCarro adapterNota = new AdapterCarro(listaCarro);
        binding.rv.setAdapter(adapterNota);


        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abrir modal para novo item
                AlertDialog.Builder alertNovo = new AlertDialog.Builder(lista_carro.this);
                EditText editPlaca = new EditText(lista_carro.this);
                editPlaca.setHint("Placa");

                // Layout do modal
                LinearLayout layout = new LinearLayout(lista_carro.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(editPlaca);
                layout.setPadding(30, 0, 30, 0);

                // Atribuir layout ao modal
                alertNovo.setView(layout);
                alertNovo.setTitle("Registrar Entrada");

                // Definir os botões
                alertNovo.setPositiveButton("Registrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Carro addNota = new Carro(editPlaca.getText().toString(), LocalDateTime.now());
                        Toast.makeText(lista_carro.this, "Item adicionado", Toast.LENGTH_SHORT).show();
                    }
                });
                alertNovo.setNegativeButton("Cancelar", null);

                alertNovo.show();
            }
        });
    }
}