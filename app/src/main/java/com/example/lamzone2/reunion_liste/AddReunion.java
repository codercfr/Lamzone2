    package com.example.lamzone2.reunion_liste;

import android.annotation.SuppressLint;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.lamzone2.R;
import com.example.lamzone2.di.Di;
import com.example.lamzone2.model.Reunion;
import com.example.lamzone2.service.ReunionApiService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

    public class AddReunion  extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private TextInputLayout reunionL, sujetL, emailL;
    private EditText heureL,dateReuinon ;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat firstDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private ReunionApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_reunion);
        reunionL = findViewById(R.id.reunionLyt);
        heureL = findViewById(R.id.heureLyt);
        sujetL = findViewById(R.id.sujetLyt);
        emailL = findViewById(R.id.emailLyt);
        dateReuinon=findViewById(R.id.dateReunion);
        Button btnDate = findViewById(R.id.btn_Date);
        Button datePicker = findViewById(R.id.timeButton);
        MaterialButton addReunion = findViewById(R.id.create);
        mApiService= Di.getService();

        datePicker.setOnClickListener(v -> {
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(),"time picker");
        });

        btnDate.setOnClickListener(v -> {
            DialogFragment dateReunion = new DatePickerFragment();
            dateReunion.show(getSupportFragmentManager(), "date picker");
        });

        addReunion.setOnClickListener(v -> {
            if(reunionL.getEditText().getText().toString().equals("") || sujetL.getEditText().getText().toString().equals("")|| emailL.getEditText().getText().toString().equals("")){
                Toast toast = Toast.makeText(getApplicationContext(),"veuillez rentrer un email/reunion/sujet",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }
            else {
                Reunion reunion = new Reunion(
                        Objects.requireNonNull(heureL.getText().toString()),
                        Objects.requireNonNull(reunionL.getEditText()).getText().toString(),
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

        @SuppressLint("SetTextI18n")
        @Override
        //recupére les valeurs du timerPicker
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            TextView textView = (TextView) findViewById(R.id.heureLyt);
            textView.setText(  hourOfDay+"h"+minute+"" );
            //+minute si l'on veut rajouter les minutes
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




    }


