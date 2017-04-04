package com.example.michaellitman.shuntingyard_csc300_spring2017;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity
{
    private EditText inputET;
    private ViewGroup inputQ;
    private ViewGroup opStack;
    private ViewGroup outputQ;
    private ViewGroup solutionStack;
    private ShuntingYard sy;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.inputET = (EditText)this.findViewById(R.id.inputET);
        this.inputQ = (ViewGroup)this.findViewById(R.id.inputQ);
        this.opStack = (ViewGroup)this.findViewById(R.id.opStack);
        this.outputQ = (ViewGroup)this.findViewById(R.id.outputQ);
        this.solutionStack = (ViewGroup)this.findViewById(R.id.solutionStack);
        this.sy = new ShuntingYard(inputET, inputQ, opStack, outputQ, solutionStack, this);
    }

    public void stepInputQButtonPressed(View v)
    {
        sy.processInputQ();
    }

    public void stepOutputQButtonPressed(View v)
    {
        sy.processOutputQ();
    }

    public void processButtonPressed(View v)
    {
        sy.processExpression();
    }
}
