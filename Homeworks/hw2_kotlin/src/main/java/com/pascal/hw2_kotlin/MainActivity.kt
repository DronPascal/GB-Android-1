package com.pascal.hw2_kotlin

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var txtResult: TextView
    private lateinit var calculator: Calculator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        calculator = Calculator()
        txtResult = findViewById(R.id.resultNumArea)
        setCurrentNumber(calculator.currentNum)
        for (v in getAllChildren(findViewById(R.id.buttonsLayout))) {
            (v as? Button)?.setOnClickListener(this)
        }
    }

    override fun onClick(btn: View) {
        if (btn is Button) {
            val curNum = (txtResult.text as String).toDouble()
            if (java.lang.Double.isNaN(curNum) || java.lang.Double.isInfinite(curNum)) {
                setCurrentNumber(calculator.clearAll())
                Toast.makeText(this, "Calculator cleared", Toast.LENGTH_SHORT).show()
            }
            when (btn.id) {
                R.id.btnEquals -> setCurrentNumber(calculator.calcResult())
                R.id.btnClear -> setCurrentNumber(calculator.clearAll())
                R.id.btnDelete -> setCurrentNumber(calculator.removeDigit())
                R.id.btnDot -> setCurrentNumber(calculator.addDotSymbol())
                R.id.dtnPow, R.id.btnMult, R.id.btnDiv, R.id.btnPlus, R.id.btnMinus ->
                    calculator.setOperation(btn.text as String)
                else -> setCurrentNumber(calculator.addDigit(btn.text as String))
            }
        }
    }

    private fun setCurrentNumber(curNum: String) {
        var size = resources.getDimension(R.dimen.calc_text_size)
        if (curNum.length > 7) {
            size /= curNum.length.toFloat() / 7
        }
        txtResult.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
        txtResult.text = curNum
    }

    private fun getAllChildren(v: View): List<View> {
        val visited: MutableList<View> = ArrayList()
        val unvisited: MutableList<View> = ArrayList()
        unvisited.add(v)
        while (unvisited.isNotEmpty()) {
            val child = unvisited.removeAt(0)
            visited.add(child)
            if (child !is ViewGroup) continue
            val childCount = child.childCount
            for (i in 0 until childCount) unvisited.add(child.getChildAt(i))
        }
        return visited
    }
}