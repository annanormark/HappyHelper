package com.mikaela.hhtimer.Calendar;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mikaela.hhtimer.R;
import com.mikaela.hhtimer.Database.dbHandler;


public class studyAssignmentAdapter extends ArrayAdapter {
    dbHandler datesOfDeadLines;
    private static final String COLUMN_WORK_PER_DAY = "work_per_day";
    private static final String COLUMN_PRODUCTNAME = "productname";
    Cursor cur;

    studyAssignmentAdapter(Context context, String[] s, Cursor db){
        super(context, R.layout.time_list, s);
        datesOfDeadLines = new dbHandler(context, null, null, 1);
        cur = db;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater Inflater = LayoutInflater.from(getContext());
        View customView = Inflater.inflate(R.layout.time_list, parent, false);

        TextView assTitle = (TextView) customView.findViewById(R.id.Titel);
        TextView assTime = (TextView) customView.findViewById(R.id.Time);

        Cursor cursor = datesOfDeadLines.getWorkRelatedTitles();
        cursor.move(position);

        assTime.setText("" + cursor.getInt(cursor.getColumnIndex(COLUMN_WORK_PER_DAY)) + " h");
        assTitle.setText(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCTNAME)));

        return customView;
    }
}
