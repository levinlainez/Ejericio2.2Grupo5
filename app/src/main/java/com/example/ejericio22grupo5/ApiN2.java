package com.example.ejericio22grupo5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.ejericio22grupo5.Interfaces.UsuariosApi;
import com.example.ejericio22grupo5.Models.Usuarios;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiN2 extends AppCompatActivity {
    ListView listaPersonas;
    ArrayList<String> titulos=new ArrayList<>();
    ArrayAdapter arrayAdapter;
    EditText idUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_n2);
        //obtenerListaPersona();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, titulos);
        listaPersonas=(ListView)  findViewById(R.id.listUsers1);
        idUsuario= (EditText) findViewById(R.id.idUsuario);
        listaPersonas.setAdapter(arrayAdapter);


        idUsuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                obtenerUsuario(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void obtenerListaPersona() {

        if (titulos.size()>0)
            titulos.remove(0);
        arrayAdapter.notifyDataSetChanged();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UsuariosApi usuariosApi=  retrofit.create(UsuariosApi.class);

        Call<List<Usuarios>> callLista=usuariosApi.getUsuarios();

        callLista.enqueue(new Callback<List<Usuarios>>() {
            @Override
            public void onResponse(Call<List<Usuarios>> call, Response<List<Usuarios>> response) {
                for (Usuarios usuarios : response.body()){
                    Log.i(usuarios.getTitle(),  "onResponse");
                    titulos.add(usuarios.getTitle());
                    arrayAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<Usuarios>> call, Throwable t) {
                t.getMessage();
            }
        });


    }

    private void obtenerUsuario( String texto){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UsuariosApi usuariosApi=retrofit.create(UsuariosApi.class);

        Call<Usuarios> callLista=  usuariosApi.getUsuarios(texto);

        callLista.enqueue(new Callback<Usuarios>() {
            @Override
            public void onResponse(Call<Usuarios> call, Response<Usuarios> response) {
                Log.i(response.body().getTitle(),  "onResponse");
                titulos.add(response.body().getTitle());
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<Usuarios> call, Throwable t) {

            }
        });

    }
}