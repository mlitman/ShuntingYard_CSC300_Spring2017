package com.example.michaellitman.shuntingyard_csc300_spring2017;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Created by michaellitman on 3/28/17.
 */

public class ShuntingYard
{
    private Queue inputQ;
    private Stack<String> opStack;
    private Queue outputQ;
    private Stack<String> solutionStack;
    private EditText inputET;
    private ViewGroup inputQView;
    private ViewGroup opStackView;
    private ViewGroup outputQView;
    private ViewGroup solutionStackView;
    private AppCompatActivity context;
    private String spacer = " | ";

    public ShuntingYard(EditText inputET, ViewGroup inputQView, ViewGroup opStackView, ViewGroup outputQView, ViewGroup solutionStackView, AppCompatActivity context)
    {
        this.inputQ = new Queue();
        this.opStack = new Stack<String>();
        this.outputQ = new Queue();
        this.solutionStack = new Stack<String>();
        this.inputET = inputET;
        this.inputQView = inputQView;
        this.opStackView = opStackView;
        this.outputQView = outputQView;
        this.solutionStackView = solutionStackView;
        this.context = context;
    }

    public void processExpression()
    {
        //clears all of our Qs and Stacks for re-use
        this.inputQ.removeAll();
        this.opStack.removeAllElements();
        this.outputQ.removeAll();
        this.solutionStack.removeAllElements();

        StringTokenizer st = new StringTokenizer(this.inputET.getText().toString(), "+-*/", true);
        String text;
        TextView tv;
        while(st.hasMoreTokens())
        {
            text = st.nextToken().trim();
            this.inputQ.enqueue(text);
            tv = new TextView(this.context);
            tv.setText(text + spacer);
            this.inputQView.addView(tv);
        }
    }

    public boolean processOutputQ()
    {
        if(this.outputQ.getCount() != 0)
        {
            String temp = this.outputQ.dequeue().getPayload();
            View tempView = this.outputQView.getChildAt(0);
            this.outputQView.removeViewAt(0);
            if("+-*/".indexOf(temp) == -1)
            {
                //we are looking at a number
                this.solutionStack.push(temp);
                this.solutionStackView.addView(tempView, 0);
                return true;
            }
            else
            {
                //we are looking at an op, time to do MATH!!!
                int num2 = Integer.parseInt(this.solutionStack.pop());
                int num1 = Integer.parseInt(this.solutionStack.pop());
                this.solutionStackView.removeViewAt(0);
                this.solutionStackView.removeViewAt(0);

                if(temp.equals("+"))
                {
                    TextView tv = new TextView(this.context);
                    String answer = "" + (num1 + num2);
                    tv.setText(answer + spacer);
                    this.solutionStack.push(answer);
                    this.solutionStackView.addView(tv, 0);
                    return true;
                }
                else if (temp.equals("-"))
                {
                    TextView tv = new TextView(this.context);
                    String answer = "" + (num1 - num2);
                    tv.setText(answer + spacer);
                    this.solutionStack.push(answer);
                    this.solutionStackView.addView(tv, 0);
                    return true;
                }
                else if (temp.equals("*"))
                {
                    TextView tv = new TextView(this.context);
                    String answer = "" + (num1 * num2);
                    tv.setText(answer + spacer);
                    this.solutionStack.push(answer);
                    this.solutionStackView.addView(tv, 0);
                    return true;
                }
                else if (temp.equals("/"))
                {
                    TextView tv = new TextView(this.context);
                    String answer = "" + (num1 / num2);
                    tv.setText(answer + spacer);
                    this.solutionStack.push(answer);
                    this.solutionStackView.addView(tv, 0);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean processInputQ()
    {
        if(this.inputQ.getCount() == 0)
        {
            if(!this.opStack.isEmpty())
            {
                this.outputQ.enqueue(this.opStack.pop());
                View tempView = this.opStackView.getChildAt(0);
                this.opStackView.removeViewAt(0);
                this.outputQView.addView(tempView);
                return true;
            }
            return false;
        }
        else
        {
            String temp = this.inputQ.dequeue().getPayload();
            if("+-*/".indexOf(temp) == -1)
            {
                //we have a non-operator (ie a number)
                this.outputQ.enqueue(temp);
                View tempView = this.inputQView.getChildAt(0);
                this.inputQView.removeViewAt(0);
                this.outputQView.addView(tempView);
            }
            else
            {
                //we are looking at an op
                if(this.opStack.isEmpty())
                {
                    this.opStack.push(temp);
                    View tempView = this.inputQView.getChildAt(0);
                    this.inputQView.removeViewAt(0);
                    this.opStackView.addView(tempView, 0);
                }
                else
                {
                    int valForOp = this.valueForOp(temp);
                    while(this.opStack.isEmpty() == false && this.valueForOp(this.opStack.peek()) >= valForOp)
                    {
                        this.outputQ.enqueue(this.opStack.pop());
                        View tempView = this.opStackView.getChildAt(0);
                        this.opStackView.removeViewAt(0);
                        this.outputQView.addView(tempView);
                    }
                    //we know that the opStack is ready to receive temp
                    this.opStack.push(temp);
                    View tempView = this.inputQView.getChildAt(0);
                    this.inputQView.removeViewAt(0);
                    this.opStackView.addView(tempView, 0);
                }
            }
            return true;
        }
    }

    private int valueForOp(String op)
    {
        if(op.equals("+") || op.equals("-"))
        {
            return 0;
        }
        else
        {
            return 1;
        }
    }
}
