package com.example.abarrotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class registro extends AppCompatActivity {
    EditText nombre, correo, telefono, contrasena, confimarcontra;
    Button btnRegistro;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        nombre=findViewById(R.id.nombre);
        correo=findViewById(R.id.correo);
        telefono=findViewById(R.id.telefono);
        contrasena=findViewById(R.id.contrasenaL);
        confimarcontra=findViewById(R.id.confimarcontra);
        btnRegistro=findViewById(R.id.btnRegistro);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Nombre = nombre.getText().toString();
                String Correo = correo.getText().toString().trim();
                String Telefono = telefono.getText().toString().trim();
                String Contrasena = contrasena.getText().toString();
                String Confimarcontra = confimarcontra.getText().toString();

                if (Nombre.isEmpty() && Correo.isEmpty() && Telefono.isEmpty() && Contrasena.isEmpty() && Confimarcontra.isEmpty()){
                    Toast.makeText(registro.this, "Completa los Datos", Toast.LENGTH_SHORT).show();
                }else{
                    if (Contrasena.equals(Confimarcontra)){
                        registrar(Nombre, Correo, Telefono, Contrasena);
                    }else{
                        Toast.makeText(registro.this, "Contraseñas no iguales", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void registrar(String nombre, String correo, String telefono, String contrasena) {

        mAuth.createUserWithEmailAndPassword(correo, contrasena).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String id = mAuth.getCurrentUser().getUid();
                Map<String, Object> map = new HashMap<>();
                map.put("id", id);
                map.put("nombre", nombre);
                map.put("correo", correo);
                map.put("telefono", telefono);
                map.put("contrasena", contrasena);

                mFirestore.collection("usuario").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        finish();
                        startActivity(new Intent(registro.this, MenuActivity.class));
                        Toast.makeText(registro.this, "Registro correcto", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(registro.this, "Error al Registrar", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(registro.this, "Error al registrar", Toast.LENGTH_SHORT).show();
            }
        });
    }


}