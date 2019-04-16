/*
Tyler Mendel 60286049
Michelle Nguyen 87407186

The StaticsActivity allows the users to get basic statistics for Expenses
for the specified date.
*/


package com.example.expenseslist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class StatisticsActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private ChildEventListener childEventListener;

    private ArrayList<Expense> expenseList;
    private ArrayList<Expense> searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Expenses");

        expenseList = new ArrayList<Expense>();
        searchResults = new ArrayList<Expense>();

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                expenseList.add(dataSnapshot.getValue(Expense.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };

        myRef.addChildEventListener(childEventListener);
    }

    public void calculate(View view)
    {
        boolean found = false;
        EditText text = (EditText)findViewById(R.id.editTextDate);
        ArrayList<Expense> items = new ArrayList<>();
        Calculate c = new Calculate();

        for( Expense e: expenseList)
        {
            String search = text.getText().toString();
            if(e.getDate().equalsIgnoreCase(search))
            {
                items.add(e);
                found = true;
            }
            double[] answers = c.getStacks(items);
            TextView textTotal = findViewById( R.id.textViewTint );
            textTotal.setText( "$" + Double.toString(answers[0]));
            TextView textAvg = findViewById( R.id.textViewAvgInt );
            textAvg.setText( "$" + Double.toString(answers[1]));
            TextView textHigh = findViewById( R.id.textViewHInt );
            textHigh.setText( "$" + Double.toString(answers[2]));
            TextView textLow = findViewById( R.id.textViewLInt);
            textLow.setText( "$" + Double.toString(answers[3]));
        }

        if(!found) {
            Toast.makeText(this, text.getText().toString() + " not found.", Toast.LENGTH_LONG).show();
        }

    }



    public void goHome( View view )
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity( intent);
    }



}

