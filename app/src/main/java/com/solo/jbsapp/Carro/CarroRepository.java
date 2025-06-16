package com.solo.jbsapp.Carro;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.time.LocalDateTime;
import java.util.List;


public class CarroRepository {

    private static final String COLLECTION = "cars";

    public CarroRepository(){}

    public void salvar(Carro carro, Context c) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(COLLECTION).document(carro.getId()).set(carro).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(c, "Registrada a entrada do carro de placa: " + carro.getPlaca(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(c, "Não foi possivel registrar a entrada do carro de placa: " + carro.getPlaca(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void verificarCarro(Carro addCarro, CarroCallback callback){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(COLLECTION)
                .whereEqualTo("placa", addCarro.getPlaca())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        boolean estacionado = false;

                        for (DocumentSnapshot doc : queryDocumentSnapshots) {
                            Carro carro = doc.toObject(Carro.class);

                            if (carro != null && carro.getDtSaida() == null) {
                                estacionado = true;
                                break;
                            }
                        }
                        callback.onCarroCallback(!estacionado);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FirebaseFirestore.getInstance().getApp().getApplicationContext(), "Erro ao verificar placa: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        callback.onCarroCallback(false);
                    }
                });
    }

    public void remover(Carro carro, Context c){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(COLLECTION).document(carro.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(c, "O registro da placa " + carro.getPlaca() + " foi deletado.", Toast.LENGTH_SHORT);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(c, "Não foi possivel deletar o registro da placa " + carro.getPlaca() + ".", Toast.LENGTH_SHORT);
            }
        });
    }

    public void listar(List<Carro> lista, AdapterCarro adapterCarro, Context c){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(COLLECTION).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    Toast.makeText(c, "Não foi possivel carregar os carros", Toast.LENGTH_SHORT).show();
                }else{
                    lista.clear();
                    for(DocumentSnapshot doc : value.getDocuments()){
                        Carro carro = doc.toObject(Carro.class);
                        lista.add(0, carro);
                    }
                    adapterCarro.notifyDataSetChanged();
                }
            }
        });
    }

    public void deletarMesAnterior(LocalDateTime atual, Context c){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(COLLECTION).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (!task.isSuccessful()){
                    Toast.makeText(c, "Não foi possivel se conectar com o banco.", Toast.LENGTH_LONG);
                }
                LocalDateTime atualMesAnterior = atual.minusMonths(1);

                for (DocumentSnapshot doc : task.getResult().getDocuments()){
                    Carro carro = doc.toObject(Carro.class);

                    if (carro.getDtSaida() != null){
                        LocalDateTime saida = LocalDateTime.parse(carro.getDtSaida());

                        if (saida.isBefore(atualMesAnterior) || saida.isEqual(atualMesAnterior)){
                            remover(carro, c);
                        }
                    }
                }

            }
        });
    }

    public interface CarroCallback{

        void onCarroCallback(boolean sucesso);
    }
}
