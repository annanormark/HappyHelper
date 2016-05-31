package com.mikaela.hhtimer.Calendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.mikaela.hhtimer.R;
import com.mikaela.hhtimer.Database.dbHandler;

public class DeadlineClicked extends AppCompatActivity {
    String productName;
    dbHandler DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deadline_clicked);
        DB = new dbHandler(this, null, null,1);

        Bundle selectedDate = getIntent().getExtras();
        productName = selectedDate.getString("event");

    }

    public void changeDeadline(View view){

    }

    public void deleteDeadline(View view){
        DB.deleteProduct(productName);
        Toast.makeText(DeadlineClicked.this, "Deleted deadline", Toast.LENGTH_SHORT).show();
        finish();
    }

}
