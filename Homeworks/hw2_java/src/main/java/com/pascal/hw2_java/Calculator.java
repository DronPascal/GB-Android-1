package com.pascal.hw2_java;

import android.util.Log;
import android.widget.Toast;

public class Calculator {
    private double result = 0;
    private String currentNum = "0";
    private String operation = "none";

    public String clearAll() {
        result = 0;
        currentNum = "0";
        operation = "none";
        return "0";
    }

    public String removeDigit() {
        if (currentNum.length() == 1)
            currentNum = "0";
        else
            currentNum = currentNum.substring(0, currentNum.length() - 1);

        return currentNum;
    }

    public String addDotSymbol() {
        if (!currentNum.contains(".") && currentNum.length() != 0) {
            currentNum += ".";
        }
        return currentNum;
    }

    public String calcResult() {
        if (!isParseble(currentNum)) return currentNum;
        if ("none".equals(operation)) return currentNum;
        double firstOperand = result;
        double secondOperand = Double.parseDouble(currentNum);
        double calculusResult = 0;
        try {
            switch (operation) {
                case "+": {
                    calculusResult = firstOperand + secondOperand;
                    break;
                }
                case "-": {
                    calculusResult = firstOperand - secondOperand;
                    break;
                }
                case "*": {
                    calculusResult = firstOperand * secondOperand;
                    break;
                }
                case "/": {
                    calculusResult = firstOperand / secondOperand;
                    break;
                }
                case "^": {
                    calculusResult = Math.pow(firstOperand, secondOperand);
                    break;
                }
            }
        } catch (ArithmeticException e) {
            e.printStackTrace();
            calculusResult = Double.NaN;
        } finally {
            Log.d("debug", "calculation finished");
        }
        Log.d("debug", "calculation is " + calculusResult);
        operation = "none";
        result = calculusResult;
        currentNum = calculusResult+"";
        return currentNum;
    }


    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public String getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(String currentNum) {
        this.currentNum = currentNum;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String oper) {
        if (!isParseble(currentNum)) return;

        if ("none".equals(operation)) {
            result = Double.parseDouble(currentNum);
            currentNum = "0";
            //        } else if (oper == "-") {
//            if (currentNum == "0")
//                currentNum = "-";
        }
        operation = oper;
    }

    public String addDigit(String digit) {
        if ("0".equals(currentNum))
            currentNum = "";
        currentNum += digit;
        return currentNum;
    }

    public boolean isParseble(String string) {
        if ("NaN".equals(currentNum)) return false;
        try {
            double num = Double.parseDouble(string);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
