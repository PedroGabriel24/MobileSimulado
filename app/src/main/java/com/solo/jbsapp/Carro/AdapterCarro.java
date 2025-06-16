package com.solo.jbsapp.Carro;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.solo.jbsapp.R;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AdapterCarro extends RecyclerView.Adapter<AdapterCarro.MyViewHolder> {

    private static final double PRECO_SEGUNDO = 0.01;

    private List<Carro> listaCarro;

    private String userEmail;
    private Boolean role;

    private CarroRepository db = new CarroRepository();

    public AdapterCarro(List<Carro> lista, String email, Boolean role) {
        this.listaCarro = lista;
        this.userEmail = email;
        this.role = role;
    }

    @NonNull
    @Override
    public AdapterCarro.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carro, parent, false);
        return new MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCarro.MyViewHolder holder, int position) {
        final Carro currentCarro = listaCarro.get(position);

        holder.placa.setText(currentCarro.getPlaca());
        holder.dataEntrada.setText(holder.itemView.getContext().getString(R.string.label_entrada, formatarLocalDateTime(LocalDateTime.parse(currentCarro.getDtEntrada()))));

        if (currentCarro.getDtSaida() != null) {
            holder.dataSaida.setText(holder.itemView.getContext().getString(R.string.label_saida, formatarLocalDateTime(LocalDateTime.parse(currentCarro.getDtSaida()))));
            holder.preco.setVisibility(VISIBLE);
            holder.preco.setText(holder.itemView.getContext().getString(R.string.label_preco, String.format("%.2f", currentCarro.getPreco())));
        } else {
            holder.preco.setVisibility(INVISIBLE);
            holder.dataSaida.setText(holder.itemView.getContext().getString(R.string.label_saida, holder.itemView.getContext().getString(R.string.no_info)));
        }

        if (role) {
            holder.lixeira.setVisibility(VISIBLE);
        } else {
            holder.lixeira.setVisibility(INVISIBLE);
        }

        // adicionar evento em cada elemento do RecyclerView
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listaCarro.get(holder.getAdapterPosition()).getDtSaida() == null) {
                    // Car is still parked, show exit registration dialog
                    showRegisterExitDialog(v, listaCarro.get(holder.getAdapterPosition()), holder.getAdapterPosition());
                }
                return true;
            }
        });

        holder.lixeira.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Carro carroParaRemover = listaCarro.get(holder.getAdapterPosition());
                db.remover(carroParaRemover, v.getContext());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaCarro.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView placa, dataEntrada, dataSaida, preco;
        private ImageView lixeira;
        private ConstraintLayout fundo; // Declarado, mas não usado no código fornecido.

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            placa = itemView.findViewById(R.id.placa);
            dataEntrada = itemView.findViewById(R.id.entrada_dt);
            dataSaida = itemView.findViewById(R.id.saida_dt);
            preco = itemView.findViewById(R.id.valorPreco);
            lixeira = itemView.findViewById(R.id.lixeira);
        }
    }

    private Double calcularPreco(String dtEntrada, String dtSaida) {
        LocalDateTime entrada = LocalDateTime.parse(dtEntrada);
        LocalDateTime saida = LocalDateTime.parse(dtSaida);

        long duracao = saida.toEpochSecond(ZoneOffset.UTC) - entrada.toEpochSecond(ZoneOffset.UTC);
        if (duracao < 0) {
            duracao = 0;
        }

        return duracao * PRECO_SEGUNDO;
    }

    public static String formatarLocalDateTime(LocalDateTime data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return data.format(formatter);
    }

    private void showRegisterExitDialog(View v, Carro carToUpdate, int position) {
        AlertDialog.Builder alertNovo = new AlertDialog.Builder(v.getContext());
        TextView text = new TextView(v.getContext());
        LocalDateTime dtSaida = LocalDateTime.now();
        Double predictedPrice = calcularPreco(carToUpdate.getDtEntrada(), dtSaida.toString());

        text.setText(String.format(
                "Placa: %s\n" +
                        "Data de Entrada: %s\n" +
                        "Data de Saída: %s\n" +
                        "Preço: R$%.2f",
                carToUpdate.getPlaca(),
                formatarLocalDateTime(LocalDateTime.parse(carToUpdate.getDtEntrada())),
                formatarLocalDateTime(dtSaida),
                predictedPrice));

        LinearLayout layout = new LinearLayout(v.getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(text);
        layout.setPadding(30, 0, 30, 0);

        alertNovo.setView(layout);
        alertNovo.setTitle(v.getContext().getString(R.string.titulo_preco));

        alertNovo.setPositiveButton(R.string.registrar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                carToUpdate.setDtSaida(dtSaida.toString());
                carToUpdate.setPreco(predictedPrice);
                carToUpdate.setUserEmail(userEmail);
                db.salvar(carToUpdate, v.getContext());
                notifyItemChanged(position);
            }
        });
        alertNovo.setNegativeButton(R.string.cancelar, null); // Use string resource

        alertNovo.show();
    }
}
