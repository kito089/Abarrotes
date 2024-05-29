package com.example.abarrotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.example.abarrotes.adapter.VentasAdapter;
import com.example.abarrotes.model.Ventas;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MenuActivity extends AppCompatActivity {

    Button btnAgregar, btnCerrar;
    RecyclerView mRecycler;
    VentasAdapter mAdapter;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        searchView = findViewById(R.id.search);
        mRecycler = findViewById(R.id.recyclerViewSingle);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        Query query = mFirestore.collection("ventas");
        FirestoreRecyclerOptions<Ventas> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Ventas>().setQuery(query, Ventas.class).build();
        mAdapter = new VentasAdapter(firestoreRecyclerOptions, this, getSupportFragmentManager());
        mAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mAdapter);
        btnAgregar = findViewById(R.id.btnAgregar);
        btnCerrar = findViewById(R.id.btnCerrar);
    
        search_View();
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, VentasActivity.class));
            }
        });

        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finish();
                startActivity(new Intent(MenuActivity.this, login.class));
            }
        });

    }

    private void search_View() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                textSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                textSearch(s);
                return false;
            }
        });
    }

    private void textSearch(String s) {
        Query query = mFirestore.collection("ventas");
        FirestoreRecyclerOptions<Ventas> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Ventas>()
                        .setQuery(query.orderBy("nombreProducto")
                                .startAt(s).endAt(s+"~"), Ventas.class).build();
        mAdapter = new VentasAdapter(firestoreRecyclerOptions, this, getSupportFragmentManager());
        mAdapter.startListening();
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }
}