/*
Tyler Mendel 60286049
Michelle Nguyen 87407186

The AddActivity adds an Expense to the dictionary. Information
for the Price and Quantity are not provided because it is
expected the user will provide this later after a purchase,
similar to a check list.
*/

package com.example.expenseslist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddActivity extends AppCompatActivity
{
    private DatabaseReference myRef;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Expenses");
    }

    public void addExpense(View view)
    {
        EditText editItem = findViewById(R.id.editTextItem);
        String item = editItem.getText().toString();
        EditText editCat = findViewById(R.id.editTextDate);
        String date = (editCat.getText().toString()).toUpperCase();


        if( item.length() > 0 )
        {
            String key = myRef.push().getKey(); // Generates unique random key
            Expense e = new Expense(item, date, key);
            myRef.child(key).setValue(e);
            Toast.makeText(this, e.getItem() + " successfully added.", Toast.LENGTH_LONG).show();
        }

        editItem.setText("");
        //editCat.setText("");
    }


    public void goHome( View view )
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity( intent);
    }
}
