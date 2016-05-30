package com.mikaela.hhtimer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class makeDeadLine extends AppCompatActivity {
    public Date SelectedTime;
    public EditText deadLineTime;
    public EditText titleOfAssignment;
    public EditText amountTime;
    public EditText amountHP;
    public int workload = 0;

    /**************** creates interface ****************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_dead_line);

        titleOfAssignment = (EditText) findViewById(R.id.titleOfAss);
        amountTime = (EditText) findViewById(R.id.amountTime);
        amountHP = (EditText) findViewById(R.id.amountHP);

        Bundle selectedDate = getIntent().getExtras();
        if(selectedDate == null)
            return;
        SelectedTime = (Date) selectedDate.get("Time");

        TextView date = (TextView) findViewById(R.id.DateText);
        String deadDate = SelectedTime.getDate() + "-" + (SelectedTime.getMonth()+1) + "-20" + (SelectedTime.getYear()-100);
        assert date != null;
        date.setText(deadDate);

    }

    /**************** calculate points to time ***************/
    private int turnHPintoTime(int HP){
        CheckBox Lectures = (CheckBox) findViewById(R.id.Lecturebox);
        if(HP > 3 && Lectures.isChecked()){
            return HP * 27 * 6/10;
        }
        else
            return HP * 27;
    }

    /****************** creates the event after the button clicked ******************/
    private void makeEvent(){
        Context context = getApplicationContext();

        // Makes sure user filled in necessary fields
        if(titleOfAssignment.getText().toString() == ""){
            Toast.makeText(context, "Add a title for your assignment", Toast.LENGTH_LONG).show();
            return;
        }

        else if (amountTime.getText().length() < 1 && amountHP.getText().length()<1) {
            Toast.makeText(context, "Add time or point for you assignment", Toast.LENGTH_LONG).show();
            return;
        }

        // Calculating and creating the deadline
        if (amountTime.getText().length() < 1){
            workload = turnHPintoTime(Integer.parseInt(amountHP.getText().toString()));
            int hoursPerDay = calculateScheduel(workload);
            dbProduct newDeadLine = new dbProduct(titleOfAssignment.getText().toString(), SelectedTime.getTime(), workload, hoursPerDay);
            dbHandler db = new dbHandler(context, "", null, 0);
            db.addProduct(newDeadLine);
            return;
        }
        workload = Integer.parseInt(amountTime.getText().toString());
        int hoursPerDay = calculateScheduel(workload);
        dbProduct newDeadLine = new dbProduct(titleOfAssignment.getText().toString(), SelectedTime.getTime(), workload, hoursPerDay);
        dbHandler db = new dbHandler(context, "", null, 0);
        db.addProduct(newDeadLine);

    }

    /*********** Calculate time-per-day for deadline **********/
    private int calculateScheduel(int hours){
        long deadlineDateMilliS = SelectedTime.getTime();
        Calendar c = Calendar.getInstance();
        long today = c.getTimeInMillis();
        long studyIntervall = TimeUnit.MILLISECONDS.toDays(deadlineDateMilliS - today);
        studyIntervall = 5 * (studyIntervall / 7);
        return hours/(int) studyIntervall;
    }

    /*********** Pressed button "create deadline" ************/
    public void deadLine (View view){
        makeEvent();
        finish();
    }

}

