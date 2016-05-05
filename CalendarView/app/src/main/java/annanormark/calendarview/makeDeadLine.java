package annanormark.calendarview;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class makeDeadLine extends AppCompatActivity {

    public Date SelectedTime;
    public EditText deadLineTime;
    public EditText titleOfAssignment;
    public EditText amountTime;
    public EditText amountHP;
    public static final String PREFS_NAME_title = "Deadlines_title";
    public static final String PREFS_NAME_work = "Deadlines_work";
    public static final String PREFS_NAME_date = "Deadlines_date";
    public int workload = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_dead_line);

        deadLineTime = (EditText) findViewById(R.id.timeOfDead);
        titleOfAssignment = (EditText) findViewById(R.id.titleOfAss);
        amountTime = (EditText) findViewById(R.id.amountTime);
        amountHP = (EditText) findViewById(R.id.amountHP);

        Bundle selectedDate = getIntent().getExtras();
        if(selectedDate == null)
            return;
        SelectedTime = (Date) selectedDate.get("Time");

        TextView date = (TextView) findViewById(R.id.DateText);
        date.setText("" + SelectedTime.getMonth() + "/" + SelectedTime.getDate() + " ");

    }

    private int turnHPintoTime(int HP){
        CheckBox Lectures = (CheckBox) findViewById(R.id.Lecturebox);
        if(HP > 3 && Lectures.isChecked()){
            return HP * 27 * 4/10;
        }
        else
            return HP * 27;
    }


    private void makeEvent(){
        Context context = getApplicationContext();

       /* SharedPreferences titlePrefs = getSharedPreferences(PREFS_NAME_title, MODE_PRIVATE);
        SharedPreferences workPrefs = getSharedPreferences(PREFS_NAME_work, MODE_PRIVATE);

        SharedPreferences datePrefs = getSharedPreferences(PREFS_NAME_date, MODE_PRIVATE);

        SharedPreferences.Editor titleEditor = titlePrefs.edit();
        SharedPreferences.Editor workEditor = workPrefs.edit();*/

        if(/*titleOfAssignment.getText().toString() == "" ||*/ titleOfAssignment.getText().toString() == ""){
            Toast.makeText(context, "Add a title for your assignment", Toast.LENGTH_LONG).show();
            return;
        }/*
        else if (amountTime.getText() == null && amountHP == null){*/
        //Toast.makeText(context, "Add amount of work for your assignment", Toast.LENGTH_LONG).show();
        // return;
        //}

        if (amountTime.getText().length() < 1 && amountHP.getText().length()<1) {
            Toast.makeText(context, "Add time or point for you assignment", Toast.LENGTH_LONG).show();
            return;
        }
        if (amountTime.getText().length() < 1){
            workload = turnHPintoTime(Integer.parseInt(amountHP.getText().toString()));
           /* titleEditor.putString(SelectedTime.toString(), titleOfAssignment.toString());
            workEditor.putInt(SelectedTime.toString() , workload);

            titleEditor.putLong(SelectedTime.toString() , SelectedTime.getTime());
            titleEditor.commit();
            workEditor.commit();*/
            return;
        }
        workload = Integer.parseInt(amountTime.getText().toString());
        dbProduct newDeadLine = new dbProduct(titleOfAssignment.getText().toString(), SelectedTime.getTime());
        dbHandler db = new dbHandler(context, "", null, 0);
        db.addProduct(newDeadLine);
        /*titleEditor.putString(SelectedTime.toString(), titleOfAssignment.toString());
        workEditor.putInt(SelectedTime.toString(), workload);
        titleEditor.commit();
        workEditor.commit();*/

    }

    public void deadLine (View view){

        makeEvent();
        Intent i = new Intent(this, MainActivity.class);
        //i.putExtra("Time", selectedDate);
        startActivity(i);
    }

}
