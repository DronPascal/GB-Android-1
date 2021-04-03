package com.pascal.hw2_java;

public class Calculator {
    public final static String NONE = "none";

    private double result = 0;
    private String currentNum = "0";
    private String operation = NONE;

    public String clearAll() {
        result = 0;
        currentNum = "0";
        operation = NONE;
        return "0";
    }

    public String removeDigit() {
        if (currentNum.length() == 1) {
            currentNum = "0";
        } else {
            currentNum = currentNum.substring(0, currentNum.length() - 1);
        }
        return currentNum;
    }

    public String addDotSymbol() {
        if (!currentNum.contains(".") && currentNum.length() != 0) {
            currentNum += ".";
        }
        return currentNum;
    }

    public String calcResult() {
        if (!isDouble(currentNum)) return currentNum;
        if (NONE.equals(operation)) return currentNum;
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
        }
        operation = NONE;
        result = calculusResult;
        currentNum = String.valueOf(calculusResult);
        return currentNum;
    }

    public String getCurrentNum() {
        return currentNum;
    }

    public void setOperation(String oper) {
        if (!isDouble(currentNum)) return;

        if (NONE.equals(operation)) {
            result = Double.parseDouble(currentNum);
            currentNum = "0";
        }
        operation = oper;
    }

    public String addDigit(String digit) {
        if ("0".equals(currentNum))
            currentNum = "";
        currentNum += digit;
        return currentNum;
    }

    public boolean isDouble(String string) {
        if ("NaN".equals(currentNum)) return false;
        try {
            double num = Double.parseDouble(string);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
