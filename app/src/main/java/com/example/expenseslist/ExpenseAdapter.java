// Tyler Mendel 60286049
//Michelle Nguyen 87407186

package com.example.expenseslist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapter extends ArrayAdapter<Expense>
{
    private Context mContext;
    private List<Expense> contactList = new ArrayList<Expense>();

    public ExpenseAdapter( Context context, ArrayList<Expense> list)
    {
        super( context, 0, list);
        mContext = context;
        contactList = list;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        String change;
        // Associates the list with the XML Layout file "contact_view"
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.expense_view,parent,false);

        // Individually handles each Contact in the contactList
        Expense currentExpense = contactList.get(position);

        // Extracts the item name of the Expense
        TextView item = (TextView) listItem.findViewById(R.id.textView_Item);
        item.setText(currentExpense.getItem());

        // Extracts the price of the Expense
        TextView price = (TextView) listItem.findViewById(R.id.textView_Price);

        if ( !currentExpense.getPrice().equals(""))
            change = "P:$" + currentExpense.getPrice();
        else
            change = currentExpense.getPrice();
        price.setText(change);

        // Extracts Date
        if ( !currentExpense.getDate().equals(""))
            change = "D:" + currentExpense.getDate();
        else
            change = currentExpense.getDate();
        TextView date = (TextView) listItem.findViewById(R.id.textView_Date);
        date.setText(change);

        // Extracts Quantity
        if ( !currentExpense.getQuantity().equals(""))
            change = "Q:" + currentExpense.getQuantity();
        else
            change = currentExpense.getQuantity();
        TextView quantity = (TextView) listItem.findViewById(R.id.textView_Quantity);
        quantity.setText(change);

        return listItem;
    }
}
