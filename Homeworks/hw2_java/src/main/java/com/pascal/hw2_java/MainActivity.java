package com.pascal.hw2_java;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.pascal.homeworks.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView txtResult;
    private Calculator calculator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculator = new Calculator();
        txtResult = findViewById(R.id.resultNumArea);
        txtResult.setText(calculator.getCurrentNum());

        for (View v : getAllChildren(findViewById(R.id.buttonsLayout))) {
            if (v instanceof Button) {
                Log.d("d", "set listener to " + v.getId());
                v.setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v instanceof Button) {
            Log.d("d", "btn clicked");
            Button btn = (Button) v;
            if (Double.isNaN
                    (Double.parseDouble(
                            (String) txtResult.getText()))) {
                calculator.clearAll();
            }
            Log.d("d", btn.getId()+"");

            switch (btn.getId()) {
                case R.id.btnEquals:
                    txtResult.setText(calculator.calcResult());
                    break;
                case R.id.btnClear:
                    txtResult.setText(calculator.clearAll());
                    break;
                case R.id.btnDelete:
                    txtResult.setText(calculator.removeDigit());
                    break;
                case R.id.btnDot:
                    txtResult.setText(calculator.addDotSymbol());
                    break;
                case R.id.dtnPow:
                case R.id.btnMult:
                case R.id.btnDiv:
                case R.id.btnPlus:
                case R.id.btnMinus:
                    calculator.setOperation((String) btn.getText());
                    break;
                default:
                    Log.d("d", "btn clicked"+btn.getText());
                    calculator.setOperation((String) btn.getText());

            }
        }
    }

    private List<View> getAllChildren(View v) {
        List<View> visited = new ArrayList<View>();
        List<View> unvisited = new ArrayList<View>();
        unvisited.add(v);

        while (!unvisited.isEmpty()) {
            View child = unvisited.remove(0);
            visited.add(child);
            if (!(child instanceof ViewGroup)) continue;
            ViewGroup group = (ViewGroup) child;
            final int childCount = group.getChildCount();
            for (int i = 0; i < childCount; i++) unvisited.add(group.getChildAt(i));
        }
        return visited;
    }
}