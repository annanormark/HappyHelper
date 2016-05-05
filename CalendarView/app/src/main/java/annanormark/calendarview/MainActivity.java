package annanormark.calendarview;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.CalendarDayEvent;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    dbHandler datesOfDeadLines;
   // public Calendar Cal;
    Date selectedDate;
   /* public static final String PREFS_NAME_title = "Deadlines_title";
    public static final String PREFS_NAME_work = "Deadlines_work";
    public static final String PREFS_NAME_date = "Deadlines_date";
    private Map<String, Booking> bookings = new HashMap<>();*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = getApplicationContext();

        datesOfDeadLines = new dbHandler(context, "", null, 0);
      /*  SharedPreferences titlePrefs = getSharedPreferences(PREFS_NAME_title, MODE_PRIVATE);
        SharedPreferences workPrefs = getSharedPreferences(PREFS_NAME_work, MODE_PRIVATE);
        SharedPreferences datePrefs = getSharedPreferences(PREFS_NAME_date, MODE_PRIVATE);
*/

        final TextView tex = (TextView) findViewById(R.id.annasText);
        //datesOfDeadLines = new dbHandler(this, null, null,1);
        final CompactCalendarView Cal = (CompactCalendarView) findViewById(R.id.Cal);
        Cal.drawSmallIndicatorForEvents(true);
        Cursor dates = datesOfDeadLines.getDates();
        dates.moveToFirst();
        int colIndex = dates.getColumnIndex("date");
        for (int i = 0; i <dates.getCount(); i++){
            Cal.addEvent(new CalendarDayEvent(dates.getLong(colIndex), Color.GREEN), false);
            dates.moveToNext();
        }

       // Cal.addEvent(new CalendarDayEvent(), false);

      //  Collection col = bookings.values();

        /*for (int i = 0; i < col.size(); i++){
            Booking temp = (Booking) col.toArray()[i];
            Cal.addEvent(new CalendarDayEvent(temp.getDate(),  Color.argb(255, 169, 68, 65)), false);
        }*/
        Cal.invalidate();

        Cal.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                selectedDate = dateClicked;


            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                //  actionBar.setTitle(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });


    }

    public void addDeadLine(View view){
        Context context = getApplicationContext();
        if (selectedDate==null) {
            Toast.makeText(context, "Select a date", Toast.LENGTH_LONG).show();
            return;
        }

        Intent i = new Intent(this, makeDeadLine.class);
        i.putExtra("Time", selectedDate);
        startActivity(i);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
