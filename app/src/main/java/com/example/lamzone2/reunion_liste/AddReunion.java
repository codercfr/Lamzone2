    package com.example.lamzone2.reunion_liste;

import android.annotation.SuppressLint;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.lamzone2.R;
import com.example.lamzone2.di.Di;
import com.example.lamzone2.model.Reunion;
import com.example.lamzone2.service.ReunionApiService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import java.text.DateFormat;

import java.util.Calendar;
import java.util.Objects;

    public class AddReunion  extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {
    private TextInputLayout reunionL, sujetL, emailL;
    private EditText heureL,dateReuinon,salleR ;
    private ReunionApiService mApiService;

    @Nullable
    private Spinner spinnerReunion = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_reunion);
        reunionL = findViewById(R.id.reunionLyt);
        salleR=findViewById(R.id.reunion_add);
        heureL = findViewById(R.id.heureLyt);
        sujetL = findViewById(R.id.sujetLyt);
        emailL = findViewById(R.id.emailLyt);
        dateReuinon=findViewById(R.id.dateReunion);
        Button btnDate = findViewById(R.id.btn_Date);
        Button datePicker = findViewById(R.id.timeButton);
        MaterialButton addReunion = findViewById(R.id.create);
        this.spinnerReunion = findViewById(R.id.project_spinner);
        mApiService= Di.getService();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.salleReunion, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerReunion.setAdapter(adapter);
        spinnerReunion.setOnItemSelectedListener(this);

        datePicker.setOnClickListener(v -> {
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(),"time picker");
        });

        btnDate.setOnClickListener(v -> {
            DialogFragment dateReunion = new DatePickerFragment();
            dateReunion.show(getSupportFragmentManager(), "date picker");
        });

        addReunion.setOnClickListener(v -> {
            if(salleR.getText().toString().equals("") || sujetL.getEditText().getText().toString().equals("")|| emailL.getEditText().getText().toString().equals("")){
                Toast toast = Toast.makeText(getApplicationContext(),"veuillez rentrer un email/reunion/sujet",Toast.LENGTH_LONG);
                toast.show();
            }
            else {
                Reunion reunion = new Reunion(
                        Objects.requireNonNull(heureL.getText().toString()),
                        Objects.requireNonNull(salleR).getText().toString(),
                        Objects.requireNonNull(sujetL.getEditText()).getText().toString(),
                        Objects.requireNonNull(emailL.getEditText()).getText().toString(),
                        0xFFB4CDBA,
                        dateReuinon.getText().toString()
                );

                Intent intent = new Intent();
                intent.putExtra("reunion", reunion);
                setResult(RESULT_OK, intent);
                mApiService.getReunion();
                finish();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

        @SuppressLint({"SetTextI18n", "DefaultLocale"})
        @Override
        //recupére les valeurs du timerPicker
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            TextView textView = findViewById(R.id.heureLyt);
            textView.setText(String.format("%02dh%02d", hourOfDay,minute ));
        }

        @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String currentDateString = DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());
            TextView textView = findViewById(R.id.dateReunion);
            textView.setText(currentDateString);
        }


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String text = parent.getItemAtPosition(position).toString();
            EditText editTextReu= findViewById(R.id.reunion_add);
            editTextReu.setText(text);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }


