package com.mikaela.hhtimer.Calendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mikaela.hhtimer.R;
import com.mikaela.hhtimer.Database.dbHandler;
import com.mikaela.hhtimer.Database.dbProduct;

import java.util.Date;
import java.util.Calendar;

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
        setViewItems();
        findSelectedDate();
        setDateText();
    }

    public void setViewItems(){
        titleOfAssignment = (EditText) findViewById(R.id.titleOfAss);
        amountTime = (EditText) findViewById(R.id.amountTime);
        amountHP = (EditText) findViewById(R.id.amountHP);
    }

    public void findSelectedDate(){
        Bundle selectedDate = getIntent().getExtras();
        assert selectedDate != null;
        SelectedTime = (Date) selectedDate.get("Time");
    }

    public void setDateText(){
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

    /*********** Pressed button "create deadline" ************/
    public void deadLine (View view){
        makeEvent();
        finish();
    }

    /****************** creates the event after the button clicked ******************/
    private void makeEvent(){
        if(!checkArgumentsOK())
            return;

        if (amountTime.getText().length() < 1){
            workload = turnHPintoTime(Integer.parseInt(amountHP.getText().toString()));
            parseDeadLine();
            return;
        }
        workload = Integer.parseInt(amountTime.getText().toString());
        parseDeadLine();
    }

    public boolean checkArgumentsOK(){
        if(titleOfAssignment.getText().toString() == "") {
            Toast.makeText(this, "Add a title for your assignment", Toast.LENGTH_LONG).show();
            return false;
        }

        else if (amountTime.getText().length() < 1 && amountHP.getText().length()<1) {
            Toast.makeText(this, "Add time or point for you assignment", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void parseDeadLine(){
        int hoursPerDay = calculateScheduel(workload);
        dbProduct newDeadLine = new dbProduct(titleOfAssignment.getText().toString(), SelectedTime.getTime(), workload, hoursPerDay);
        dbHandler db = new dbHandler(this, "", null, 0);
        db.addProduct(newDeadLine);
    }

    /*********** Calculate time-per-day for deadline **********/
    private int calculateScheduel(int timeOfAssignment){
        Long studyIntervall = getTimeToDeadline();
        int daysToStudy = getFreeWeekends(studyIntervall);
        if(timeOfAssignment/daysToStudy == 0)
            return 1;
        return timeOfAssignment/daysToStudy;
    }

    public Long getTimeToDeadline(){
        Long deadlineDate = SelectedTime.getTime();
        Calendar c = Calendar.getInstance();
        Long today = c.getTimeInMillis();
        Long studyIntervall = deadlineDate - today;
        return studyIntervall/(1000*60*60*24);
    }

    public int  getFreeWeekends(Long studyIntervall){
        if(studyIntervall>7)
            studyIntervall = 5 * ((studyIntervall) / 7);
        return studyIntervall.intValue() + 1;
    }

}

