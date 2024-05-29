package com.example.abarrotes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abarrotes.R;
import com.example.abarrotes.model.Ventas;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class VentasAdapter extends FirestoreRecyclerAdapter<Ventas, VentasAdapter.ViewHolder>{

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public VentasAdapter(@NonNull FirestoreRecyclerOptions<Ventas> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int position, @NonNull Ventas Ventas) {
        viewHolder.fecha.setText(Ventas.getFecha());
        viewHolder.idEmpleado.setText(Ventas.getIdEmpleado());
        viewHolder.idProducto.setText(Ventas.getIdProducto());
        viewHolder.nombreProducto.setText(Ventas.getNombreProducto());
        viewHolder.descripcion.setText(Ventas.getDescripcion());
        viewHolder.marca.setText(Ventas.getMarca());
        viewHolder.precio.setText(Ventas.getPrecio());
        viewHolder.cantidad.setText(Ventas.getCantidad());
        viewHolder.subtotal.setText(Ventas.getSubtotal());
        viewHolder.total.setText(Ventas.getTotal());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_ventas_single, parent, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fecha, idEmpleado, idProducto, nombreProducto, descripcion, marca, precio, cantidad, subtotal, total;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fecha = itemView.findViewById(R.id.Fecha2);;
            idEmpleado = itemView.findViewById(R.id.IdEmpleado2);;
            idProducto = itemView.findViewById(R.id.IdProducto2);;
            nombreProducto = itemView.findViewById(R.id.NombreProducto2);;
            descripcion = itemView.findViewById(R.id.Descripcion2);;
            marca = itemView.findViewById(R.id.Marca2);;
            precio = itemView.findViewById(R.id.Precio2);;
            cantidad = itemView.findViewById(R.id.Cantidad2);;
            subtotal = itemView.findViewById(R.id.Subtotal2);;
            total = itemView.findViewById(R.id.Total2);
        }
    }
}
