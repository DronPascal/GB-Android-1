package com.pascal.hw2_kotlin

import kotlin.math.pow

class Calculator {
    var result = 0.0
    var currentNum = "0"
    private var operation = "none"

    fun clearAll(): String {
        result = 0.0
        currentNum = "0"
        operation = "none"
        return "0"
    }

    fun removeDigit(): String {
        currentNum = if (currentNum.length == 1) "0" else currentNum.substring(0, currentNum.length - 1)
        return currentNum
    }

    fun addDotSymbol(): String {
        if (!currentNum.contains(".") && currentNum.isNotEmpty()) {
            currentNum += "."
        }
        return currentNum
    }

    fun calcResult(): String {
        if (!isParseble(currentNum)) return currentNum
        if ("none" == operation) return currentNum
        val firstOperand = result
        val secondOperand = currentNum.toDouble()
        var calculusResult = 0.0
        try {
            when (operation) {
                "+" -> calculusResult = firstOperand + secondOperand
                "-" -> calculusResult = firstOperand - secondOperand
                "*" -> calculusResult = firstOperand * secondOperand
                "/" -> calculusResult = firstOperand / secondOperand
                "^" -> calculusResult = firstOperand.pow(secondOperand)
            }
        } catch (e: ArithmeticException) {
            e.printStackTrace()
            calculusResult = Double.NaN
        }
        operation = "none"
        result = calculusResult
        currentNum = calculusResult.toString() + ""
        return currentNum
    }

    fun getOperation(): String {
        return operation
    }

    fun setOperation(oper: String) {
        if (!isParseble(currentNum)) return
        if ("none" == operation) {
            result = currentNum.toDouble()
            currentNum = "0"
        }
        operation = oper
    }

    fun addDigit(digit: String): String {
        if ("0" == currentNum) currentNum = ""
        currentNum += digit
        return currentNum
    }

    private fun isParseble(string: String): Boolean {
        if ("NaN" == currentNum) return false
        try {
            val num = string.toDouble()
        } catch (e: Exception) {
            return false
        }
        return true
    }
}