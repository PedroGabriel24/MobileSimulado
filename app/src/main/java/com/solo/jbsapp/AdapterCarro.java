package com.solo.jbsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.util.List;

public class AdapterCarro extends RecyclerView.Adapter<AdapterCarro.MyViewHolder> {

    private List<Carro> listaCarro;

//    private Database db = new Database();

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
        holder.placa.setText(listaCarro.get(position).getPlaca());

        // adicionar evento em cada elemento do RecyclerView
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listaCarro.get(holder.getAdapterPosition()).setDataSaida(LocalDateTime.now());
//                notifyItemRemoved(holder.getAdapterPosition());
//                db.remover(listaNota.get(holder.getAdapterPosition()), v.getContext());
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaCarro.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView placa, dataEntrada;
        private ConstraintLayout fundo;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            placa = itemView.findViewById(R.id.placa);
            dataEntrada = itemView.findViewById(R.id.entrada_dt);
        }
    }
}
