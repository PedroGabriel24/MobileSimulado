package com.solo.jbsapp;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class UserRepository {
    public UserRepository(){}

    public void verificarLogin(String email, String senha, Context c, LoginCallback callback){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                boolean encontrado = false;

                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    User user = document.toObject(User.class);

                    if (email.equals(user.getEmail()) && senha.equals(user.getSenha())) {
                        callback.onLoginConcluido(true, user.getEmail(), user.getUserRole());
                        encontrado = true;
                        break;
                    }
                }

                if (!encontrado) {
                    Toast.makeText(c, "Senha ou email incorretos", Toast.LENGTH_SHORT).show();
                    callback.onLoginConcluido(false, "", false);
                }
            }
        });
    }

    public void cadastrarUser(User user, Context c, CadastroCallback callback){
        FirebaseFirestore db =  FirebaseFirestore.getInstance();
        db.collection("users").document(user.getEmail()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                callback.onCadastroConcluido(true, user.getEmail(), user.getUserRole());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(c, "Email já cadastrado.", Toast.LENGTH_SHORT).show();
                callback.onCadastroConcluido(false, "", false);
            }
        });
    }

    public interface CadastroCallback {

        void onCadastroConcluido(boolean sucesso, String email, Boolean role);
    }

    public interface LoginCallback {

        void onLoginConcluido(boolean sucesso, String email, Boolean role);
    }

}
