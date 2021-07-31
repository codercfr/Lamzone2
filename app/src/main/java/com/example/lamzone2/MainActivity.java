    package com.example.lamzone2;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.example.lamzone2.model.Reunion;
import com.example.lamzone2.reunion_liste.AddReunion;
import com.example.lamzone2.reunion_liste.ContactReunionAdpater;
import com.example.lamzone2.service.DummyReunionApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView myRecyclerView;
    private List<Reunion> rReunion;
    private ContactReunionAdpater adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        myRecyclerView= findViewById(R.id.RecyclerView);
        FloatingActionButton add_button = findViewById(R.id.fab);

        initList();

        ActivityResultLauncher<Intent> someactivityResultLuncher= registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        assert result.getData() != null;
                        Reunion reunion = result.getData().getExtras().getParcelable("reunion");
                       rReunion.add(reunion);
                    }
                });

        //open AddReunion
        add_button.setOnClickListener(v -> {
            Intent intent= new Intent(this, AddReunion.class);
            someactivityResultLuncher.launch(intent);
        });
    }
    
    private void initList() {
        DummyReunionApiService mApiservice = new DummyReunionApiService();
        rReunion= mApiservice.getReunion();
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myRecyclerView.setAdapter(new ContactReunionAdpater(rReunion));
        adapter=new ContactReunionAdpater(rReunion);
        myRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initList();
    }

    //connecter le filtre dans l'adapter et la vue
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //creation de notre menu
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        //creation de notre filtre
        MenuItem searchItem =  menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}

