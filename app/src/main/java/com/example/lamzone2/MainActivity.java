    package com.example.lamzone2;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import android.view.MenuItem;

import android.widget.DatePicker;
import android.widget.Toast;

import com.example.lamzone2.di.Di;
import com.example.lamzone2.model.Reunion;
import com.example.lamzone2.reunion_liste.AddReunion;
import com.example.lamzone2.reunion_liste.ContactReunionAdpater;
import com.example.lamzone2.reunion_liste.DatePickerFragment;

import com.example.lamzone2.service.DummyReunionApiService;
import com.example.lamzone2.service.ReunionApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;

import java.util.Calendar;

import java.util.List;

public class MainActivity extends AppCompatActivity implements  DatePickerDialog.OnDateSetListener   {

    private RecyclerView myRecyclerView;
    private List<Reunion> rReunion;
    private ContactReunionAdpater adapter;
    private ReunionApiService mApiservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        myRecyclerView= findViewById(R.id.RecyclerView);
        FloatingActionButton add_button = findViewById(R.id.fab);
        mApiservice=Di.getService();
        initList();

        ActivityResultLauncher<Intent> someactivityResultLuncher= registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        assert result.getData() != null;
                        Reunion reunion = result.getData().getExtras().getParcelable("reunion");
                        //a corriger pour que ca soit différent pas null sinon ca ne fonctionne pas 
                        Reunion reunionTest =null;
                        //pour bloquer l
                        for(int i =0 ; i<rReunion.size();i++) {
                            if (reunion.getHeure().equals(rReunion.get(i).getHeure()) && reunion.getReunionName().equals(rReunion.get(i).getReunionName())
                                    && reunion.getDatetime().equals(rReunion.get(i).getDatetime())) {
                                Toast.makeText(getApplicationContext(), "impossible de reserver à cette heure veuillez décaller", Toast.LENGTH_LONG).show();
                                reunionTest=rReunion.get(i);
                                break;
                            }
                            else{
                                reunionTest=rReunion.get(i);
                            }
                        }
                        assert reunionTest != null;
                        if(!reunion.getHeure().equals(reunionTest.getHeure()) || !reunion.getReunionName().equals(reunionTest.getReunionName())) {
                            rReunion.add(reunion);
                        }
                    }
                });

        //open AddReunion
            add_button.setOnClickListener(v -> {
                Intent intent = new Intent(this, AddReunion.class);
                someactivityResultLuncher.launch(intent);
            });


        }

    private void initList() {
        DummyReunionApiService mApiservice = new DummyReunionApiService();
        rReunion= mApiservice.getReunion();
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem searchItem =  menu.findItem(R.id.sujet_Reunion);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                rReunion.clear();
                rReunion.addAll(mApiservice.getReunionFilter(newText));
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        return true;
    }



    //rajouter la méthode onOptionSelected

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.show_Date)
        {
            initDatePicker();
            return true;
        }
        if(item.getItemId()== R.id.menu_Home)
        {
            rReunion.clear();
            rReunion.addAll(mApiservice.getReunion());
            adapter.notifyDataSetChanged();
        }
        return super.onOptionsItemSelected(item);
    }



    private void initDatePicker()
    {
        DialogFragment dateReunion = new DatePickerFragment();
        dateReunion.show(getSupportFragmentManager(), "date picker");
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());
        for(int i=0;i< rReunion.size();i++)
        {
            if(currentDateString.equals(rReunion.get(i).getDatetime()))
            {
                rReunion.clear();
                rReunion.addAll(mApiservice.getReunionFilter(currentDateString));
                adapter.notifyDataSetChanged();
            }
        }

    }





}

