package com.example.abarrotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class VentasActivity extends AppCompatActivity {

    Button btnEnviar;
    EditText Fecha, IdEmpleado, IdProducto, NombreProducto, Descripcion, Marca, Precio, Cantidad, Subtotal, Total;
    private FirebaseFirestore mfirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventas);

        this.setTitle("Agregar Venta");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mfirestore = FirebaseFirestore.getInstance();

        Fecha = findViewById(R.id.Fecha);
        IdEmpleado = findViewById(R.id.IdEmpleado);
        IdProducto = findViewById(R.id.IdProducto);
        NombreProducto = findViewById(R.id.NombreProducto);
        Descripcion = findViewById(R.id.Descripcion);
        Marca = findViewById(R.id.Marca);
        Precio = findViewById(R.id.Precio);
        Cantidad = findViewById(R.id.Cantidad);
        Subtotal = findViewById(R.id.Subtotal);
        Total = findViewById(R.id.Total);

        btnEnviar = findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fecha = Fecha.getText().toString();
                String idEmpleado = IdEmpleado.getText().toString();
                String idProducto = IdProducto.getText().toString();
                String nombreProducto = NombreProducto.getText().toString();
                String descripcion = Descripcion.getText().toString();
                String marca = Marca.getText().toString();
                String precio = Precio.getText().toString();
                String cantidad = Cantidad.getText().toString();
                String subtotal = Subtotal.getText().toString();
                String total = Total.getText().toString();

                if (fecha.isEmpty() && idEmpleado.isEmpty() && idProducto.isEmpty() && nombreProducto.isEmpty() && descripcion.isEmpty() && marca.isEmpty() && precio.isEmpty() && cantidad.isEmpty() && subtotal.isEmpty() && total.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Ingresar los datos", Toast.LENGTH_SHORT).show();
                }else {
                    enviarDatos(fecha, idEmpleado, idProducto, nombreProducto, descripcion, marca, precio, cantidad, subtotal, total);
                }
            }
        });

    }

    private void enviarDatos(String fecha, String idEmpleado, String idProducto, String nombreProducto, String descripcion, String marca, String precio, String cantidad, String subtotal, String total) {
        Map<String, Object> map = new HashMap<>();
        map.put("fecha",fecha);
        map.put("idEmpleado",idEmpleado);
        map.put("idProducto",idProducto);
        map.put("nombreProducto",nombreProducto);
        map.put("descripcion",descripcion);
        map.put("marca",marca);
        map.put("precio",precio);
        map.put("cantidad",cantidad);
        map.put("subtotal",subtotal);
        map.put("total",total);
        mfirestore.collection("ventas").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(), "Creado exitosamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al Ingresar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}