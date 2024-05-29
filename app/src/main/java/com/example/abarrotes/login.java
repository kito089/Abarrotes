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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    EditText correoL, contrasenaL;
    Button btnSesion, btnIrRegistro;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        correoL = findViewById(R.id.correoL);
        contrasenaL = findViewById(R.id.contrasenaL);
        btnSesion = findViewById(R.id.btnSesion);
        btnIrRegistro = findViewById(R.id.btnIrRegistro);

        btnIrRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(login.this, registro.class);
                startActivity(i);
            }
        });

        btnSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = correoL.getText().toString().trim();
                String contrasena = contrasenaL.getText().toString().trim();
                
                if (correo.isEmpty() && contrasena.isEmpty()){
                    Toast.makeText(login.this, "Ingresar los datos", Toast.LENGTH_SHORT).show();
                }else{
                    InisiarSesion(correo, contrasena);
                }
            }
        });
    }

    private void InisiarSesion(String correo, String contrasena) {
        mAuth.signInWithEmailAndPassword(correo, contrasena).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    finish();
                    startActivity(new Intent(login.this, MenuActivity.class));
                    Toast.makeText(login.this, "Inisio Exitoso", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(login.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(login.this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
            }
        });
    }


}