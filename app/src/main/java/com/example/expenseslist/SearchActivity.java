/*
Tyler Mendel 60286049
Michelle Nguyen 87407186

The SearchActivity allows the user to search Expenses by date.

Click to edit an expense.
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

public class SearchActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private ChildEventListener childEventListener;

    private ArrayList<Expense> expenseList;
    private ArrayList<Expense> searchResults;


    private ExpenseAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Expenses");

        expenseList = new ArrayList<Expense>();
        searchResults = new ArrayList<Expense>();

        childEventListener = new ChildEventListener(){

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

        arrayAdapter = new ExpenseAdapter( this, searchResults);
        ListView results = findViewById(R.id.listViewResults);
        results.setAdapter(arrayAdapter);
        results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Expense selectedItem = (Expense) parent.getItemAtPosition(position);

                Bundle b = new Bundle();
                b.putString("uid", selectedItem.getUid());
                b.putString("item", selectedItem.getItem());
                b.putString("date", selectedItem.getDate());
                b.putString("price", selectedItem.getPrice());
                b.putString("quantity", selectedItem.getQuantity());

                Intent intent = new Intent(getApplicationContext(), UpdateActivity.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

    }

    public void search(View view)
    {
        arrayAdapter.clear();
        boolean found = false;
        EditText text = (EditText)findViewById(R.id.editTextDate);

        for( Expense c: expenseList)
        {
            String search = text.getText().toString();
            if(c.getDate().equalsIgnoreCase(search))
            {
                arrayAdapter.add(c);
                found = true;
            }
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
