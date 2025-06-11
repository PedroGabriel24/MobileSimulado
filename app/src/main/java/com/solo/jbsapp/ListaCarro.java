package com.solo.jbsapp;

import static android.view.View.INVISIBLE;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.service.controls.actions.FloatAction;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.solo.jbsapp.databinding.ActivityListaCarroBinding;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ListaCarro extends AppCompatActivity {
    private CarroRepository db = new CarroRepository();

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
        Bundle bundle = getIntent().getExtras();
        RecyclerView rv = findViewById(R.id.rv);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        FloatingActionButton rotina = findViewById(R.id.rotina);

        if (bundle == null){
            Intent intent = new Intent(ListaCarro.this, LoginActivity.class);
            startActivity(intent);
        }

        if (!bundle.getBoolean("role")){
            rotina.setVisibility(INVISIBLE);
        }

        rv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        // Configurar o Adapter do RecyclerView
        List<Carro> listaCarro = new ArrayList<>();
        AdapterCarro adapterCarro = new AdapterCarro(listaCarro, bundle.getString("email"));
        rv.setAdapter(adapterCarro);
        db.listar(listaCarro, adapterCarro, getApplicationContext());


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abrir modal para novo item
                AlertDialog.Builder alertNovo = new AlertDialog.Builder(ListaCarro.this);
                EditText editPlaca = new EditText(ListaCarro.this);
                editPlaca.setHint("Placa");

                // Layout do modal
                LinearLayout layout = new LinearLayout(ListaCarro.this);
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
                        Carro addCarro = new Carro(listaCarro.size() + 1, editPlaca.getText().toString(), LocalDateTime.now().toString(), bundle.getString("email"));
                        db.salvar(addCarro, ListaCarro.this);
                    }
                });
                alertNovo.setNegativeButton("Cancelar", null);

                alertNovo.show();
            }
        });


        rotina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertNovo = new AlertDialog.Builder(ListaCarro.this);
                TextView texto = new TextView(v.getContext());
                texto.setText("Deseja deletar registro das placas do mês anterior?");

                // Layout do modal
                LinearLayout layout = new LinearLayout(ListaCarro.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(texto);
                layout.setPadding(30, 0, 30, 0);

                // Atribuir layout ao modal
                alertNovo.setView(layout);
                alertNovo.setTitle("Rotina");

                // Definir os botões
                alertNovo.setPositiveButton("Rodar Rotina", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deletarMesAnterior(LocalDateTime.now(), v.getContext());
                    }
                });
                alertNovo.setNegativeButton("Cancelar", null);

                alertNovo.show();
            }
        });
    }
}