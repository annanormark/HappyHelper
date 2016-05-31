package com.mikaela.hhtimer.Calendar;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mikaela.hhtimer.R;


class titleOfAssignmentAdapter extends ArrayAdapter {

    titleOfAssignmentAdapter(Context context, String[] foods) {
        super(context, R.layout.custom_row, foods);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater annasInflater = LayoutInflater.from(getContext());
        View customView = annasInflater.inflate(R.layout.custom_row, parent, false);

        String singleFoodItem = (String) getItem(position);
        TextView annasText = (TextView) customView.findViewById(R.id.Titel);

        annasText.setText(singleFoodItem);
        return customView;
    }
}
