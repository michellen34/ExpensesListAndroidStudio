// Tyler Mendel 60286049
//Michelle Nguyen 87407186

package com.example.expenseslist;


import java.util.ArrayList;

public class Calculate
{
    private double avgExpense;
    private double totalExpense;
    private double lowestExpense;
    private double highestExpense;


    public Calculate()
    {
        avgExpense = 0;
        totalExpense = 0;
        lowestExpense = 0;
        highestExpense = 0;
    }

    public double[] getStacks(ArrayList<Expense> elist)
    {
        double[] fail = {0,0,0,0};
        if ( elist.size() == 0 )
            return fail;
        calcTotal(elist);
        calcAverage(elist);
        calcLowest(elist);
        calcHighest(elist);
        double[] answers = new double[] {totalExpense, avgExpense, highestExpense, lowestExpense};
        return answers;
    }

    public void calcTotal(ArrayList<Expense> elist)
    {
        double t = 0;
        for (Expense s: elist)
        {
            if ( s.getPrice().equals("") )
                t += 0;
            else
            t += (Double.parseDouble(s.getPrice())  * Integer.parseInt(s.getQuantity()));
        }
        totalExpense = t;

    }

    public void calcAverage(ArrayList<Expense> elist)
    {
        int count = 0;
        double t = 0;
        for (Expense s: elist)
        {
            if ( s.getPrice().equals("") )
                t += 0;
            else
                {
                count++;
                t += Double.parseDouble(s.getPrice());
                }
        }
        avgExpense = t / count;
    }

    public void calcHighest(ArrayList<Expense> elist)
    {
        double high = 0;
        for (Expense s: elist)
        {
            if ( !s.getPrice().equals("") )
                if ( Double.parseDouble(s.getPrice()) > high )
                    high = Double.parseDouble(s.getPrice());
        }
        highestExpense = high;
    }

    public void calcLowest(ArrayList<Expense> elist)
    {
        double low = Double.parseDouble(elist.get(0).getPrice());

        for (Expense s: elist)
        {
            if ( !s.getPrice().equals("") )
                if ( Double.parseDouble(s.getPrice()) < low )
                    low = Double.parseDouble(s.getPrice());
        }
        lowestExpense = low;
    }


}
