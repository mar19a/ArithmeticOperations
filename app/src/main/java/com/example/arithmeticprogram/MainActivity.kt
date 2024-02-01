package com.example.arithmeticprogram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    // Helper function to check if the input string is a valid numeric value.
    private fun isNumeric(input: String): Boolean {
        return input.toDoubleOrNull() != null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup the Spinner with arithmetic operations
        val operationSpinner: Spinner = findViewById(R.id.operationSpinner)
        val operations = arrayOf("+", "-", "x", "/", "%")
        operationSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            operations
        )

        // Get references to the EditTexts and TextView for the operands and result
        val operandOneEditText: EditText = findViewById(R.id.operandOneEditText)
        val operandTwoEditText: EditText = findViewById(R.id.operandTwoEditText)
        val resultTextView: TextView = findViewById(R.id.resultTextView)

        // Setup the calculate button with its click listener
        val calculateButton: Button = findViewById(R.id.calculateButton)
        calculateButton.setOnClickListener {
            // Get the selected operation from the Spinner
            val selectedOperation = operationSpinner.selectedItem.toString()

            // Get the operands from the input fields
            val operandOne = operandOneEditText.text.toString()
            val operandTwo = operandTwoEditText.text.toString()

            // Check if the operands are valid numbers
            if (!isNumeric(operandOne) || !isNumeric(operandTwo)) {
                Toast.makeText(this, "Please enter valid numeric values!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Parse operands to double
            val valueOne = operandOne.toDouble()
            val valueTwo = operandTwo.toDouble()

            // Perform calculation based on the selected operation
            val result = when (selectedOperation) {
                "+" -> valueOne + valueTwo
                "-" -> valueOne - valueTwo
                "x" -> valueOne * valueTwo
                "/" -> {
                    // Handle division by zero
                    if (valueTwo == 0.0) {
                        Toast.makeText(this, "Cannot divide by zero!", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    valueOne / valueTwo
                }
                "%" -> valueOne % valueTwo
                else -> throw IllegalArgumentException("Unsupported operation: $selectedOperation")
            }

            // Display the result in the result TextView
            resultTextView.text = result.toString()
        }
    }
}
