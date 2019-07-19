package com.gan.tbabsensi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.gan.tbabsensi.Adapters.BeritaAdapter;
import com.gan.tbabsensi.Models.Berita;
import com.gan.tbabsensi.Models.Parsing.GetAllBerita;
import com.gan.tbabsensi.REST.RetrofitClient;
import com.gan.tbabsensi.REST.RetrofitInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        final RecyclerView recyclerView = findViewById(R.id.recyclerBerita);
        final LinearLayout layoutProgress = findViewById(R.id.layoutProgress);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        RetrofitInterface retrofitInterface = RetrofitClient.getClient().create(RetrofitInterface.class);

        layoutProgress.setVisibility(View.VISIBLE);
        Call<GetAllBerita> getAllBerita = retrofitInterface.getAllBerita();
        getAllBerita.enqueue(new Callback<GetAllBerita>() {
            @Override
            public void onResponse(Call<GetAllBerita> call, Response<GetAllBerita> response) {
                List<Berita> beritaList = response.body().getBerita();
                RecyclerView.Adapter adapter = new BeritaAdapter(beritaList);
                recyclerView.setAdapter(adapter);

                layoutProgress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<GetAllBerita> call, Throwable t) {
                Log.d("failure", t.getMessage());
                layoutProgress.setVisibility(View.GONE);
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.spiMatkul);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.matkul, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }
}
