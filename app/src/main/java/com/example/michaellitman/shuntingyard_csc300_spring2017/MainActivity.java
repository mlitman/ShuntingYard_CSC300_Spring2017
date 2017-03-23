package com.example.michaellitman.shuntingyard_csc300_spring2017;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity
{
    private EditText inputET;
    private TextView outputTV;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.inputET = (EditText)this.findViewById(R.id.inputET);
        this.outputTV = (TextView)this.findViewById(R.id.outputTV);
    }

    public void processButtonPressed(View v)
    {
        StringTokenizer st = new StringTokenizer(this.inputET.getText().toString(), "+-*/", true);
        Queue q = new Queue();
        while(st.hasMoreTokens())
        {
            q.enqueue(st.nextToken());
        }

        String answer = "";
        while(q.getCount() > 0)
        {
            answer = answer + q.dequeue().getPayload() + ",";
        }
        this.outputTV.setText(answer);
    }
}
