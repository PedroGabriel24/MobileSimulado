package com.solo.jbsapp;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.util.List;

public class AdapterCarro extends RecyclerView.Adapter<AdapterCarro.MyViewHolder> {

    private List<Carro> listaCarro;

    private CarroRepository db = new CarroRepository();

    public AdapterCarro(List<Carro> lista) {
        this.listaCarro = lista;
    }

    @NonNull
    @Override
    public AdapterCarro.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Carregar o template de visualização
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carro, parent, false);
        return new MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCarro.MyViewHolder holder, int position) {
        holder.placa.setText(listaCarro.get(holder.getAdapterPosition()).getPlaca());
        holder.dataEntrada.setText("Data de Entrada: " + listaCarro.get(holder.getAdapterPosition()).getDtEntrada());
        if (listaCarro.get(holder.getAdapterPosition()).getDtSaida() != null){
            holder.dataSaida.setText("Data de Saída: " + listaCarro.get(holder.getAdapterPosition()).getDtSaida());
        }else{
            holder.dataSaida.setVisibility(INVISIBLE);
        }

        // adicionar evento em cada elemento do RecyclerView
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listaCarro.get(holder.getAdapterPosition()).getDtSaida() == null) {
                    AlertDialog.Builder alertNovo = new AlertDialog.Builder(v.getContext());
                    TextView text = new TextView(v.getContext());
                    text.setText("Deseja registrar a saída desse carro?");

                    // Layout do modal
                    LinearLayout layout = new LinearLayout(v.getContext());
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.addView(text);
                    layout.setPadding(30, 0, 30, 0);

                    // Atribuir layout ao modal
                    alertNovo.setView(layout);
                    alertNovo.setTitle("Registrar Saída");

                    // Definir os botões
                    alertNovo.setPositiveButton("Registrar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.salvar(new Carro(listaCarro.get(holder.getAdapterPosition()).getPlaca(), listaCarro.get(holder.getAdapterPosition()).getDtEntrada(), LocalDateTime.now().toString()), v.getContext());
                            holder.dataSaida.setVisibility(VISIBLE);
                            holder.dataSaida.setText("Data de Saída: " + listaCarro.get(holder.getAdapterPosition()).getDtSaida());
                        }
                    });
                    alertNovo.setNegativeButton("Cancelar", null);

                    alertNovo.show();
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaCarro.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView placa, dataEntrada, dataSaida;
        private ConstraintLayout fundo;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            placa = itemView.findViewById(R.id.placa);
            dataEntrada = itemView.findViewById(R.id.entrada_dt);
            dataSaida = itemView.findViewById(R.id.saida_dt);
        }
    }
}
