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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class VentasActivity extends AppCompatActivity {

    Button btnEnviar;
    EditText IdVenta, Fecha, IdEmpleado, IdProducto, NombreProducto, Descripcion, Marca, Precio, Cantidad, Subtotal, Total;
    private FirebaseFirestore mfirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventas);

        this.setTitle("Agregar Venta");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String id = getIntent().getStringExtra("idVentas");
        mfirestore = FirebaseFirestore.getInstance();

        IdVenta = findViewById(R.id.IdVenta);
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

        if (id == null || id == ""){
            btnEnviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String idVenta = IdVenta.getText().toString();
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

                    if (idVenta.isEmpty() && fecha.isEmpty() && idEmpleado.isEmpty() && idProducto.isEmpty() && nombreProducto.isEmpty() && descripcion.isEmpty() && marca.isEmpty() && precio.isEmpty() && cantidad.isEmpty() && subtotal.isEmpty() && total.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Ingresar los datos", Toast.LENGTH_SHORT).show();
                    }else {
                        enviarDatos(idVenta, fecha, idEmpleado, idProducto, nombreProducto, descripcion, marca, precio, cantidad, subtotal, total);
                    }
                }
            });
        }else{
            btnEnviar.setText("actualizar");
            getDatos(id);
            btnEnviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String idVenta = IdVenta.getText().toString();
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
                    if (idVenta.isEmpty() && fecha.isEmpty() && idEmpleado.isEmpty() && idProducto.isEmpty() && nombreProducto.isEmpty() && descripcion.isEmpty() && marca.isEmpty() && precio.isEmpty() && cantidad.isEmpty() && subtotal.isEmpty() && total.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Ingresar los datos", Toast.LENGTH_SHORT).show();
                    }else {
                        actualizarDatos(idVenta, fecha, idEmpleado, idProducto, nombreProducto, descripcion, marca, precio, cantidad, subtotal, total, id);
                    }

                }
            });
        }



    }

    private void actualizarDatos(String idVenta, String fecha, String idEmpleado, String idProducto, String nombreProducto, String descripcion, String marca, String precio, String cantidad, String subtotal, String total, String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("idVenta",idVenta);
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

        mfirestore.collection("ventas").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Actualizado exitosamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al Actualizar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void enviarDatos(String idVenta, String fecha, String idEmpleado, String idProducto, String nombreProducto, String descripcion, String marca, String precio, String cantidad, String subtotal, String total) {
        Map<String, Object> map = new HashMap<>();
        map.put("idVenta",idVenta);
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

    private void getDatos(String id){
        mfirestore.collection("ventas").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String idVenta = documentSnapshot.getString("idVenta");
                String fecha = documentSnapshot.getString("fecha");
                String idEmpleado = documentSnapshot.getString("idEmpleado");
                String idProducto = documentSnapshot.getString("idProducto");
                String nombreProducto = documentSnapshot.getString("nombreProducto");
                String descripcion = documentSnapshot.getString("descripcion");
                String marca = documentSnapshot.getString("marca");
                String precio = documentSnapshot.getString("precio");
                String cantidad = documentSnapshot.getString("cantidad");
                String subtotal = documentSnapshot.getString("subtotal");
                String total = documentSnapshot.getString("total");

                IdVenta.setText(idVenta);
                Fecha.setText(fecha);
                IdEmpleado.setText(idEmpleado);
                IdProducto.setText(idProducto);
                NombreProducto.setText(nombreProducto);
                Descripcion.setText(descripcion);
                Marca.setText(marca);
                Precio.setText(precio);
                Cantidad.setText(cantidad);
                Subtotal.setText(subtotal);
                Total.setText(total);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al Obtener los Datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}