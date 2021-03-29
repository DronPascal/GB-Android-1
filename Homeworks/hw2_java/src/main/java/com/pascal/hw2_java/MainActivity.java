package com.pascal.hw2_java;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
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
    private TextView txtResult;
    private Calculator calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculator = new Calculator();
        txtResult = findViewById(R.id.resultNumArea);
        setCurrentNumber(calculator.getCurrentNum());

        for (View v : getAllChildren(findViewById(R.id.buttonsLayout))) {
            if (v instanceof Button) {
                v.setOnClickListener(this);
            }
        }
    }

    // создание меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        // добавляем пункты меню
        menu.add(0, 1, 0, "add");
        menu.add(0, 2, 0, "edit");
        menu.add(0, 3, 3, "delete");
        menu.add(1, 4, 1, "copy");
        menu.add(1, 5, 2, "paste");
        menu.add(1, 6, 4, "exit");

        return super.onCreateOptionsMenu(menu);
    }

    // обновление меню
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        // пункты меню с ID группы = 1 видны, если в CheckBox стоит галка
        menu.setGroupVisible(1, true);
        return super.onPrepareOptionsMenu(menu);
    }

    // обработка нажатий
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        StringBuilder sb = new StringBuilder();

        // Выведем в TextView информацию о нажатом пункте меню
        sb.append("Item Menu");
        sb.append("\r\n groupId: " + String.valueOf(item.getGroupId()));
        sb.append("\r\n itemId: " + String.valueOf(item.getItemId()));
        sb.append("\r\n order: " + String.valueOf(item.getOrder()));
        sb.append("\r\n title: " + item.getTitle());
        Toast.makeText(this,sb, Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v instanceof Button) {
            Button btn = (Button) v;
            Double curNum = (Double.parseDouble(((String) txtResult.getText())));
            if (Double.isNaN(curNum) || curNum.isInfinite(curNum)) {
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
        if (curNum.length() > 7) {
            size /= (float) curNum.length() / 7;
        }
        txtResult.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        txtResult.setText(curNum);
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