package com.example.jacobapplication2

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.*

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var expressionBuilder = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.calculator_display)

        val button0: Button = findViewById(R.id.button_0)
        val button1: Button = findViewById(R.id.button_1)
        val button2: Button = findViewById(R.id.button_2)
        val button3: Button = findViewById(R.id.button_3)
        val button4: Button = findViewById(R.id.button_4)
        val button5: Button = findViewById(R.id.button_5)
        val button6: Button = findViewById(R.id.button_6)
        val button7: Button = findViewById(R.id.button_7)
        val button8: Button = findViewById(R.id.button_8)
        val button9: Button = findViewById(R.id.button_9)
        val buttonDot: Button = findViewById(R.id.button_dot)

        val buttonAdd: Button = findViewById(R.id.button_add)
        val buttonSubtract: Button = findViewById(R.id.button_subtract)
        val buttonMultiply: Button = findViewById(R.id.button_multiply)
        val buttonDivide: Button = findViewById(R.id.button_divide)
        val buttonEquals: Button = findViewById(R.id.button_equals)

        val buttonClear: Button = findViewById(R.id.button_clear)
        val buttonBackspace: Button = findViewById(R.id.button_backspace)

        val buttonSin: Button = findViewById(R.id.button_sin)
        val buttonCos: Button = findViewById(R.id.button_cos)
        val buttonLn: Button = findViewById(R.id.button_ln)
        val buttonPi: Button = findViewById(R.id.button_pi)
        val buttonArcsin: Button = findViewById(R.id.button_arcsin)
        val buttonE: Button = findViewById(R.id.button_e)
        val buttonQuadratic: Button = findViewById(R.id.button_quad)
        val buttonSqrt: Button = findViewById(R.id.button_sqrt)
        val buttonExponent: Button = findViewById(R.id.button_exponent)


        val numberClickListener = { v: android.view.View ->
            val button = v as Button
            expressionBuilder.append(button.text)
            display.text = expressionBuilder.toString()
        }

        button0.setOnClickListener(numberClickListener)
        button1.setOnClickListener(numberClickListener)
        button2.setOnClickListener(numberClickListener)
        button3.setOnClickListener(numberClickListener)
        button4.setOnClickListener(numberClickListener)
        button5.setOnClickListener(numberClickListener)
        button6.setOnClickListener(numberClickListener)
        button7.setOnClickListener(numberClickListener)
        button8.setOnClickListener(numberClickListener)
        button9.setOnClickListener(numberClickListener)
        buttonDot.setOnClickListener {
            // Avoid multiple dots in a number
            val expression = expressionBuilder.toString()
            val lastOperatorIndex = expression.lastIndexOfAny(charArrayOf('+','-','*','/','^'))
            val currentNumber = if(lastOperatorIndex == -1) expression else expression.substring(lastOperatorIndex + 1)

            if (!currentNumber.contains('.')) {
                expressionBuilder.append(".")
                display.text = expressionBuilder.toString()
            }
        }

        val operatorClickListener = { v: android.view.View ->
            val button = v as Button
            if (expressionBuilder.isNotEmpty()) {
                expressionBuilder.append(button.text)
                display.text = expressionBuilder.toString()
            }
        }

        buttonAdd.setOnClickListener(operatorClickListener)
        buttonSubtract.setOnClickListener(operatorClickListener)
        buttonMultiply.setOnClickListener(operatorClickListener)
        buttonDivide.setOnClickListener(operatorClickListener)
        buttonExponent.setOnClickListener(operatorClickListener)

        buttonEquals.setOnClickListener {
            try {
                val expression = expressionBuilder.toString()
                val operatorChars = charArrayOf('+', '-', '*', '/', '^')
                val operatorIndex = expression.indexOfAny(operatorChars, startIndex = 1)

                if (operatorIndex != -1) {
                    val operator = expression[operatorIndex].toString()
                    val operand1 = expression.substring(0, operatorIndex).toDouble()
                    val operand2 = expression.substring(operatorIndex + 1).toDouble()

                    val result = when (operator) {
                        "+" -> operand1 + operand2
                        "-" -> operand1 - operand2
                        "*" -> operand1 * operand2
                        "/" -> operand1 / operand2
                        "^" -> operand1.pow(operand2)
                        else -> 0.0
                    }
                    display.text = result.toString()
                    expressionBuilder.clear()
                    expressionBuilder.append(result.toString())
                }
            } catch (e: Exception) {
                display.text = "Error"
                expressionBuilder.clear()
            }
        }

        buttonClear.setOnClickListener {
            expressionBuilder.clear()
            display.text = ""
        }

        buttonBackspace.setOnClickListener {
            if (expressionBuilder.isNotEmpty()) {
                expressionBuilder.deleteCharAt(expressionBuilder.length - 1)
                display.text = expressionBuilder.toString()
            }
        }

        val scientificFunctionListener = { func: (Double) -> Double ->
            if (expressionBuilder.isNotEmpty()) {
                try {
                    val value = expressionBuilder.toString().toDouble()
                    val result = func(value)
                    display.text = result.toString()
                    expressionBuilder.clear()
                    expressionBuilder.append(result.toString())
                } catch (e: NumberFormatException) {
                    // Not a single number, do nothing
                }
            }
        }

        buttonSin.setOnClickListener {
            scientificFunctionListener { sin(Math.toRadians(it)) }
        }

        buttonCos.setOnClickListener {
            scientificFunctionListener { cos(Math.toRadians(it)) }
        }

        buttonLn.setOnClickListener {
            if (expressionBuilder.isNotEmpty()) {
                 try {
                    val value = expressionBuilder.toString().toDouble()
                    if (value > 0) {
                        val result = ln(value)
                        display.text = result.toString()
                        expressionBuilder.clear()
                        expressionBuilder.append(result.toString())
                    } else {
                        display.text = "Error"
                        expressionBuilder.clear()
                    }
                } catch (e: NumberFormatException) {
                    // Not a single number, do nothing
                }
            }
        }

        buttonPi.setOnClickListener {
            expressionBuilder.append(PI)
            display.text = expressionBuilder.toString()
        }

        buttonArcsin.setOnClickListener {
            if (expressionBuilder.isNotEmpty()) {
                try {
                    val value = expressionBuilder.toString().toDouble()
                     if (value >= -1 && value <= 1) {
                        val result = asin(value)
                        val resultDegrees = Math.toDegrees(result)
                        display.text = resultDegrees.toString()
                        expressionBuilder.clear()
                        expressionBuilder.append(resultDegrees.toString())
                    } else {
                        display.text = "Error"
                        expressionBuilder.clear()
                    }
                } catch (e: NumberFormatException) {
                    // Not a single number, do nothing
                }
            }
        }

        buttonE.setOnClickListener {
            expressionBuilder.append(E)
            display.text = expressionBuilder.toString()
        }

        buttonSqrt.setOnClickListener {
             if (expressionBuilder.isNotEmpty()) {
                try {
                    val value = expressionBuilder.toString().toDouble()
                    if (value >= 0) {
                        val result = sqrt(value)
                        display.text = result.toString()
                        expressionBuilder.clear()
                        expressionBuilder.append(result.toString())
                    } else {
                        display.text = "Error"
                        expressionBuilder.clear()
                    }
                } catch (e: NumberFormatException) {
                    // Not a single number, do nothing
                }
            }
        }

        buttonQuadratic.setOnClickListener {
            // The quadratic formula button is not fully implemented
            // as it would require a more complex UI to input a, b, and c.
            display.text = "a, b, c?"
        }
    }
}