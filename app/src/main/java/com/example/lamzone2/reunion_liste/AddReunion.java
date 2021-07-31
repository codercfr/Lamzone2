    package com.example.lamzone2.reunion_liste;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.lamzone2.R;
import com.example.lamzone2.di.Di;
import com.example.lamzone2.model.Reunion;
import com.example.lamzone2.service.ReunionApiService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

    public class AddReunion  extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private TextInputLayout reunionL, sujetL, emailL;
    private TextView heureL ;



        private ReunionApiService mApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_reunion);
        reunionL = findViewById(R.id.reunionLyt);
        heureL = findViewById(R.id.heureLyt);
        sujetL = findViewById(R.id.sujetLyt);
        emailL = findViewById(R.id.emailLyt);
        Button datePicker = findViewById(R.id.timeButton);
        MaterialButton addReunion = findViewById(R.id.create);
        mApiService= Di.getService();

        datePicker.setOnClickListener(v -> {
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(),"time picker");

        });

        addReunion.setOnClickListener(v -> {
            Reunion reunion = new Reunion(
                    Objects.requireNonNull(heureL.getText().toString()),
                    Objects.requireNonNull(reunionL.getEditText()).getText().toString(),
                    Objects.requireNonNull(sujetL.getEditText()).getText().toString(),
                    Objects.requireNonNull(emailL.getEditText()).getText().toString()
            );
            Intent intent = new Intent();
            intent.putExtra("reunion", reunion);
            setResult(RESULT_OK, intent);
            mApiService.getReunion();
            finish();
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
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            TextView textView = (TextView) findViewById(R.id.heureLyt);
            textView.setText("Hour: " + hourOfDay + " Minute: " + minute);
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

    }


