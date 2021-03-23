package com.pascal.hw2_java;

import android.util.Log;

public class Calculator {
    private double result = 0;
    private String currentNum = "0";
    private String operation = "+";

    public String clearAll() {
        result = 0;
        currentNum = "0";
        operation = "+";
        return "0";
    }

    public String removeDigit() {
        currentNum = currentNum.substring(0, currentNum.length() - 1);
        return currentNum;
    }

    public String addDotSymbol() {
        if (!currentNum.contains(",") && currentNum.length()!=0) {
            currentNum+=",";
        }
        return currentNum;
    }

    public String calcResult() {
        double firstOperand = result;
        double secondOperand = Double.parseDouble(currentNum.replace(",", "."));
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
        Log.d("debug", "calculation is "+ calculusResult);
        return ""+calculusResult;
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

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
