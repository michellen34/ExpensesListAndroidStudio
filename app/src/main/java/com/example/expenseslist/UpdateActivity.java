/*
Tyler Mendel 60286049
Michelle Nguyen 87407186

The EditActivity changes the information for an Expense. This
is needed to add the Price and Quantity.
*/


package com.example.expenseslist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class UpdateActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String uid, item, price, date, quantity;


    private EditText textDate;
    private EditText textItem;
    private EditText textPrice;
    private EditText textQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Expenses");

        textDate = (EditText) findViewById(R.id.editTextDate);
        textItem = (EditText) findViewById(R.id.editTextItem);
        textPrice = (EditText) findViewById(R.id.editTextPrice);
        textQuantity = (EditText) findViewById(R.id.editTextQuantity);
        TextView textUpdate = (TextView) findViewById(R.id.textViewUpdate);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        uid = b.getString("uid");
        item = b.getString("item");
        price = b.getString("price");
        date = b.getString("date");
        quantity = b.getString("quantity");


        if ( item.length() > 0 )
            textItem.setText(item);
        if ( quantity.length() > 0 )
            textQuantity.setText(quantity);
        if ( price.length() > 0 )
            textPrice.setText(price);
        if ( date.length() > 0 )
            textDate.setText(date);

        textUpdate.setText("Edit " + item);
    }


        public void update( View view )
        {
            boolean change = false;

            String updateDate = textDate.getText().toString();
            String updateItem = textItem.getText().toString();
            String updatePrice = textPrice.getText().toString();
            String updateQuantity = textQuantity.getText().toString();



            if ( updateDate.length() > 0 )
            {
                date = updateDate;
                change = true;
            }
            if ( updateItem.length() > 0 )
            {
                item = updateItem;
                change = true;
            }

            if ( ( updatePrice.length() > 0 || price.length() > 0 ) && ( updateQuantity.length() > 0 || quantity.length() > 0 ) )
            {
                if ( updatePrice.length() > 0 )
                    updatePrice = Double.toString( (double) Math.round( Double.parseDouble(updatePrice) * 100 ) / 100 );
                    price = updatePrice;
                if ( updateQuantity.length() > 0 )
                    quantity = updateQuantity;
                change = true;
            }

            else if ( updatePrice.length() > 0 && updateQuantity.length() == 0 )
                Toast.makeText(this, " Include a quantity.", Toast.LENGTH_LONG).show();
            else if ( updatePrice.length() == 0 && updateQuantity.length() > 0 )
                Toast.makeText(this, " Include a price.", Toast.LENGTH_LONG).show();

            if ( change )
            {
                Expense e = new Expense(item, date, uid, price, quantity);
                myRef.child(uid).setValue(e);

                goHome(view);
            }
        }


    public void goHome( View view )
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity( intent);
    }

}
