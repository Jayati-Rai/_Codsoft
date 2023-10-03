package com.example.calculator2o;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView resulttv,solutiontv;//creating the object of class TextView
    MaterialButton buttonC,buttonOpenBracket,buttonClosedBracket,buttonAC,buttone,buttondot;
    MaterialButton buttonDivision,buttonMultiply,buttonAdd,buttonSub,buttonEqualsTo;
    MaterialButton  button1,button2,button3,button4,button5,button6,button7,button8,button9,button0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resulttv=findViewById(R.id.result);
        solutiontv=findViewById(R.id.solution);
        assignId(buttonC,R.id.button_clear);
        assignId(button0,R.id.button_zero);
        assignId(button1,R.id.button_one);
        assignId(button2,R.id.button_two);
        assignId(button3,R.id.button_three);
        assignId(button4,R.id.button_four);
        assignId(button5,R.id.button_five);
        assignId(button6,R.id.button_six);
        assignId(button7,R.id.button_seven);
        assignId(button8,R.id.button_eight);
        assignId(button9,R.id.button_nine);
        assignId(buttonAdd,R.id.button_add);
        assignId(buttonMultiply,R.id.button_multiplication);
        assignId(buttonSub,R.id.button_minus);
        assignId(buttonDivision,R.id.button_division);
        assignId(buttondot,R.id.button_decimal);
        assignId(buttonOpenBracket,R.id.button_open_bracket);
        assignId(buttonClosedBracket,R.id.button_close_bracket);
        assignId(buttone,R.id.button_exponential);
        assignId(buttonAC,R.id.button_exponential);
        assignId(buttonEqualsTo,R.id.button_equalsto);



    }
    void assignId(MaterialButton btn, int id)
    {
        btn=findViewById(id);
        btn.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        MaterialButton button=(MaterialButton) view;
        String buttonText=button.getText().toString();//getting the text of the clicked button
        String dataTocalculate=solutiontv.getText().toString();
        if(buttonText.equals("C"))
        {
            solutiontv.setText("");
            resulttv.setText("0");
            return;
        }
        if(buttonText.equals("="))
        {
            solutiontv.setText(resulttv.getText());
            return;
        }
        else {
             dataTocalculate = dataTocalculate + buttonText;
        }
         //a string that can be concatenated later to extract data for calculation

        solutiontv.setText(dataTocalculate);//setting the solution text as dataTocalculate
        String finalResult=getResult(dataTocalculate);
        if(!finalResult.equals("err")){
            resulttv.setText(finalResult);
        }


    }
    String getResult(String data)//function
    {
        try {
            Context context= Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable= context.initStandardObjects();
            String finalResult=context.evaluateString(scriptable, data,"Javascript",1,null).toString();
            return finalResult;
        }catch(Exception e)
        {
            return "err";
        }
    }
}