package com.solo.jbsapp;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class UserRepository {
    public UserRepository(){}

    public void verificarLogin(String email, String senha, Context c, LoginCallback callback){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                boolean encontrado = false;

                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    String userEmail = document.getString("email");
                    String userSenha = document.getString("senha");

                    if (email.equals(userEmail) && senha.equals(userSenha)) {
                        callback.onLoginConcluido(true);
                        break;
                    }
                }

                if (!encontrado) {
                    Toast.makeText(c, "Senha ou email incorretos", Toast.LENGTH_SHORT).show();
                    callback.onLoginConcluido(false);
                }
            }
        });
    }

    public void cadastrarUser(User user, Context c, CadastroCallback callback){
        FirebaseFirestore db =  FirebaseFirestore.getInstance();

        db.collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                boolean encontrado = false;

                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    String userEmail = document.getString("email");
                    String userSenha = document.getString("senha");

                    if (user.getEmail().equals(userEmail) && user.getSenha().equals(userSenha)) {
                        encontrado = true;
                        break;
                    }
                }

                if (encontrado) {
                    Toast.makeText(c, "Senha ou email incorretos", Toast.LENGTH_SHORT).show();
                    callback.onCadastroConcluido(false);
                }else{
                    db.collection("users").document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            callback.onCadastroConcluido(true);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(c, "Erro ao cadastrar.", Toast.LENGTH_SHORT).show();
                            callback.onCadastroConcluido(false);
                        }
                    });
                }
            }
        });

    }

    public interface CadastroCallback {

        void onCadastroConcluido(boolean sucesso);
    }

    public interface LoginCallback {

        void onLoginConcluido(boolean sucesso);
    }

}
