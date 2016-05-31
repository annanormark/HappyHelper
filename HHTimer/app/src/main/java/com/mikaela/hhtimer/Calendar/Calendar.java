package com.mikaela.hhtimer.Calendar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.CalendarDayEvent;
import com.mikaela.hhtimer.R;
import com.mikaela.hhtimer.Database.dbHandler;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Calendar extends AppCompatActivity {
    dbHandler datesOfDeadLines;
    Date selectedDate;
    String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    String[] dayColNames ={"M", "T", "O", "T", "F", "S", "S"};
    CompactCalendarView Cal = null;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        final Context context = getApplicationContext();
        final TextView tex = (TextView) findViewById(R.id.annasText);
        Cal = (CompactCalendarView) findViewById(R.id.Cal);
        datesOfDeadLines = new dbHandler(this, null, null,1);
        final ListView eventList = (ListView) findViewById(R.id.eventList);

        // Set text on top of Calendar
        assert tex != null;
        tex.setText("");
        int thisMonth = java.util.Calendar.getInstance(Locale.getDefault()).getTime().getMonth();
        tex.setText(months[thisMonth]);

        //Makes the Calendar
        assert Cal != null;
        Cal.setCurrentDate(java.util.Calendar.getInstance(Locale.getDefault()).getTime());
        Cal.setDayColumnNames(dayColNames);
        refreshCalendar();
        Cal.invalidate();

        /*** Sets listener for what happens when a new date is clicked in the calendar ***/
        Cal.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                selectedDate = dateClicked;

                //Show events in this dates
                adapter = new titleOfAssignmentAdapter(getBaseContext(), datesOfDeadLines.getTitles(dateClicked.getTime()));
                assert eventList != null;
                eventList.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                /*** When event is clicked in list ***/
             /*   eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //Finds clicked item
                        Object o = eventList.getItemAtPosition(position);
                        String productname = (String) o;

                        //Start menu of choices
                        Intent i = new Intent(context, DeadlineClicked.class);
                        i.putExtra("event", productname);
                        startActivity(i);
                        refreshCalendar();
                    }
                });*/

                /*** When long-clicking event, delete it ***/
                eventList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        Object o = eventList.getItemAtPosition(position);
                        String productName = (String) o;
                        datesOfDeadLines.deleteProduct(productName);
                        Toast.makeText(Calendar.this, "Deleted " + productName, Toast.LENGTH_SHORT).show();
                        Cal.removeAllEvents();
                        refreshCalendar();
                        adapter = new titleOfAssignmentAdapter(getBaseContext(), datesOfDeadLines.getTitles(selectedDate.getTime()));
                        eventList.setAdapter(adapter);
                        return true;
                    }
                });
            }

            //Does nothing important here, must exist
            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                int thisMonth = firstDayOfNewMonth.getMonth();
                tex.setText(months[thisMonth]);
            }
        });
    }

    /*** clicked button "add event", starts activity "makeDeadLine" ***/
    public void addDeadLine(View view){
        Context context = getApplicationContext();
        if (selectedDate==null) {
            Toast.makeText(context, "Select a date", Toast.LENGTH_LONG).show();
            return;
        }

        Intent i = new Intent(this, makeDeadLine.class);
        i.putExtra("Time", selectedDate);
        startActivity(i);
        Cal.removeAllEvents();
        refreshCalendar();
    }

    /*** Refreshes the calendarview ***/
    private void refreshCalendar(){
        if(Cal != null) {
            Cal.drawSmallIndicatorForEvents(true);
            List<Long> dates = datesOfDeadLines.getDates();
            for (int i = 0; i < dates.size(); i++) {
                Cal.addEvent(new CalendarDayEvent(dates.get(i), Color.RED), false);
            }
            Cal.invalidate();
        }
    }

    /*** On button klicked "Planner" ***/
    public void showWork(View view){
        Intent i = new Intent(this, ShowTimeContatiner.class);
        startActivity(i);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshCalendar();
        if (selectedDate != null) {
            ListView eventList = (ListView) findViewById(R.id.eventList);
            adapter = new titleOfAssignmentAdapter(getBaseContext(), datesOfDeadLines.getTitles(selectedDate.getTime()));
            eventList.setAdapter(adapter);
        }
    }
}
