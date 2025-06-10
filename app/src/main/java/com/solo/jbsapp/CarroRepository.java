package com.solo.jbsapp;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;


public class CarroRepository {

    public CarroRepository(){}

    public void salvar(Carro carro, Context c){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("cars").document(String.valueOf(carro.getId())).set(carro).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(c, "Registrada a entrada do carro de placa: " + carro.getPlaca(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(c, "Não foi possivel registrar a entrada do carro de placa: " + carro.getPlaca(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void remover(Carro carro, Context c){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("cars").document(String.valueOf(carro.getId())).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(c, "Registrada a saída do carro de placa: " + carro.getPlaca(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(c, "Não foi possivel registrar a saída do carro de placa: " + carro.getPlaca(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void listar(List<Carro> lista, AdapterCarro adapterCarro, Context c){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("cars").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    Toast.makeText(c, "Não foi possivel carregar os carros", Toast.LENGTH_SHORT).show();
                }else{
                    lista.clear();
                    for(DocumentSnapshot doc : value.getDocuments()){
                        Carro carro = doc.toObject(Carro.class);
                        lista.add(carro);
                    }
                    adapterCarro.notifyDataSetChanged();
                }
            }
        });
    }
}
