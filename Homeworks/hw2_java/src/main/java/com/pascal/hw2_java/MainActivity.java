package com.pascal.hw2_java;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pascal.homeworks.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int MAX_NUMBER_LENGTH = 7;

    private TextView txtResult;
    private Calculator calculator;
    private List<View> tableViewsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculator = new Calculator();
        txtResult = findViewById(R.id.resultNumArea);
        setCurrentNumber(calculator.getCurrentNum());

        tableViewsList = getAllChildren(findViewById(R.id.buttonsLayout));
        for (View v : tableViewsList) {
            if (v instanceof Button) {
                v.setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v instanceof Button) {
            Button btn = (Button) v;
            Double curNum = (Double.parseDouble(((String) txtResult.getText())));
            if (Double.isNaN(curNum) || Double.isInfinite(curNum)) {
                setCurrentNumber(calculator.clearAll());
                Toast.makeText(this, "Calculator cleared", Toast.LENGTH_SHORT).show();
            }
            switch (btn.getId()) {
                case R.id.btnEquals:
                    setCurrentNumber(calculator.calcResult());
                    break;
                case R.id.btnClear:
                    setCurrentNumber(calculator.clearAll());
                    break;
                case R.id.btnDelete:
                    setCurrentNumber(calculator.removeDigit());
                    break;
                case R.id.btnDot:
                    setCurrentNumber(calculator.addDotSymbol());
                    break;
                case R.id.dtnPow:
                case R.id.btnMult:
                case R.id.btnDiv:
                case R.id.btnPlus:
                case R.id.btnMinus:
                    calculator.setOperation((String) btn.getText());
                    break;
                default:
                    setCurrentNumber(calculator.addDigit((String) btn.getText()));
            }
        }
    }

    private void setCurrentNumber(String curNum) {
        float size = getResources().getDimension(R.dimen.calc_text_size);
        if (curNum.length() > MAX_NUMBER_LENGTH) {
            size /= (float) curNum.length() / MAX_NUMBER_LENGTH;
        }
        txtResult.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        txtResult.setText(curNum);
    }

    private List<View> getAllChildren(View v) {
        List<View> visited = new ArrayList<>();
        List<View> unvisited = new ArrayList<>();
        unvisited.add(v);

        while (!unvisited.isEmpty()) {
            View child = unvisited.remove(0);
            visited.add(child);
            if (!(child instanceof ViewGroup)) continue;
            ViewGroup group = (ViewGroup) child;
            final int childCount = group.getChildCount();
            for (int i = 0; i < childCount; i++) {
                unvisited.add(group.getChildAt(i));
            }
        }
        return visited;
    }
}