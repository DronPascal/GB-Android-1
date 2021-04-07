package com.pascal.hw2_java;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pascal.homeworks.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int MAX_NUMBER_LENGTH = 7;
    private static final String APP_PREF = "APP_SETTINGS";
    private static final String APP_THEME = "APP_THEME";
    private static final int AppLightTheme = 0;
    private static final int AppDarkTheme = 1;
    private int currentThemeCode;

    private TextView txtResult;
    private Calculator calculator;
    private List<View> buttons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getAppTheme(R.style.MyAppTheme));
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

    private int getAppTheme(int codeStyle) {
        return codeStyleToStyleId(getCodeStyle(codeStyle));
    }

    private int getCodeStyle(int codeStyle) {
        SharedPreferences sharedPreferences = getSharedPreferences(APP_PREF, MODE_PRIVATE);
        currentThemeCode = sharedPreferences.getInt(APP_THEME, codeStyle);
        return currentThemeCode;
    }

    private int codeStyleToStyleId(int codeStyle) {
        if (codeStyle == AppDarkTheme) {
            return R.style.MyAppThemeDark;
        }
        return R.style.MyAppTheme;
    }

    private void setAppTheme(int codeStyle) {
        SharedPreferences sharedPreferences = getSharedPreferences(APP_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(APP_THEME, codeStyle);
        editor.apply();
    }

    // Cоздание меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, this.getString(R.string.datk_theme_title));
        return super.onCreateOptionsMenu(menu);
    }

    // Обновление меню
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.getItem(0).setCheckable(true);
        if (currentThemeCode == AppDarkTheme) {
            menu.getItem(0).setChecked(true);
        } else {
            menu.getItem(0).setChecked(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    // Обработка нажатий
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 0) {
            if (item.isChecked())
                setAppTheme(AppLightTheme);
            else
                setAppTheme(AppDarkTheme);
            recreate();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NonConstantResourceId")
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