package com.pascal.hw2_java;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
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
    private List<View> buttons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculator = new Calculator();
        txtResult = findViewById(R.id.resultNumArea);
        setCurrentNumber(calculator.getCurrentNum());

        buttons = findAllButtons();
        for (View v : buttons) {
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
                Toast.makeText(this, this.getString(R.string.calc_cleared), Toast.LENGTH_SHORT).show();
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

    private List<View> findAllButtons() {
        List<View> buttonsViews = new ArrayList<>();
        buttonsViews.add(findViewById(R.id.btn0));
        buttonsViews.add(findViewById(R.id.btn1));
        buttonsViews.add(findViewById(R.id.btn2));
        buttonsViews.add(findViewById(R.id.btn3));
        buttonsViews.add(findViewById(R.id.btn4));
        buttonsViews.add(findViewById(R.id.btn5));
        buttonsViews.add(findViewById(R.id.btn6));
        buttonsViews.add(findViewById(R.id.btn7));
        buttonsViews.add(findViewById(R.id.btn8));
        buttonsViews.add(findViewById(R.id.btn9));
        buttonsViews.add(findViewById(R.id.btnClear));
        buttonsViews.add(findViewById(R.id.btnDelete));
        buttonsViews.add(findViewById(R.id.btnDiv));
        buttonsViews.add(findViewById(R.id.btnDot));
        buttonsViews.add(findViewById(R.id.btnEquals));
        buttonsViews.add(findViewById(R.id.btnMinus));
        buttonsViews.add(findViewById(R.id.btnPlus));
        return buttonsViews;
    }
}