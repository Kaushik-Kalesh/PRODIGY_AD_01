package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvOutput: TextView

    private var previousOperandString: String = ""
    private var currentOperandString: String = ""
    private var currentOperator: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvOutput = findViewById(R.id.tvOuput)
    }

    private fun calculateValue(): String {
        val previousOperand: Double = previousOperandString.toDouble()
        val currentOperand: Double = currentOperandString.toDouble()
        var result: Double = 0.0

        when (currentOperator) {
            "+" -> result = previousOperand+currentOperand
            "-" -> result = previousOperand-currentOperand
            "*" -> result = previousOperand*currentOperand
            "/" -> result = previousOperand/currentOperand
        }

        return "%.2f".format(result)
    }

    fun onDigit(view: View) {
        val digitString: CharSequence = (view as Button).text
        tvOutput.append(digitString)
        currentOperandString += digitString
    }

    fun onOperator(view: View) {
        val operator = (view as Button).text.toString()

        if(currentOperandString.isEmpty() && currentOperator.isNotEmpty() && operator != "-") return

        tvOutput.append(operator)

        if(operator == "-" && currentOperandString.isEmpty()){
            currentOperandString += operator
        }
        else{
            if(currentOperator.isNotEmpty()) currentOperandString = calculateValue()

            currentOperator = operator

            previousOperandString = currentOperandString
            currentOperandString = ""
        }
    }

    fun onDot(view: View) {
        if(currentOperandString.isEmpty()){
            currentOperandString = "0"
            tvOutput.append(currentOperandString)
        }

        if(!currentOperandString.contains(".")) {
            currentOperandString += "."
            tvOutput.append(".")
        }
    }

    fun onEqual(view: View) {
        if(currentOperandString.isEmpty()) return
        if(previousOperandString.isNotEmpty()) currentOperandString = calculateValue()
        tvOutput.text = currentOperandString
        currentOperator = ""
        previousOperandString = ""
    }

    fun onCLR(view: View) {
        tvOutput.text = ""
        currentOperandString = ""
        previousOperandString = ""
    }
}