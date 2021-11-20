package com.example.ejericio22grupo5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.ejericio22grupo5.Interfaces.UsuariosApi;
import com.example.ejericio22grupo5.Models.Usuarios;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class ApiN1 extends AppCompatActivity {
    ListView listaPersonas;
    ArrayList<String> titulos=new ArrayList<>();
    ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_n1);
        obtenerListaPersona();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, titulos);
        listaPersonas=(ListView)  findViewById(R.id.listusers);
        listaPersonas.setAdapter(arrayAdapter);
    }
    private void obtenerListaPersona() {


        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UsuariosApi usuariosApi = retrofit.create(UsuariosApi.class);

        Call <List<Usuarios>> calllista =usuariosApi.getUsuarios();

        calllista.enqueue(new Callback<List<Usuarios>>() {
            @Override
            public void onResponse(Call<List<Usuarios>> call, Response<List<Usuarios>> response) {

                for (Usuarios usuarios : response.body()){
                    Log.i(usuarios.getTitle(), "onResponse");
                    titulos.add(usuarios.getTitle());
                    arrayAdapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onFailure(Call<List<Usuarios>> call, Throwable t) {

            }
        });



    }
}