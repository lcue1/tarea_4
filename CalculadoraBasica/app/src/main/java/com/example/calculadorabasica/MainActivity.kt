package com.example.calculadorabasica

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.marginEnd
import com.example.calculadorabasica.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //Atributes
    private lateinit var binding: ActivityMainBinding
    private lateinit var charactersNotNumeric:MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        initAtributes()//Initialize properties

        generateButtons()
    }

    private fun generateButtons() {
        //create an array with  buttons name
        val butonsName= arrayOf("7","8","9","/",
            "4","5","6","X",
            "1","2","3","-",
            "C","0","=","+",
        )
        butonsName.forEach { name ->
            val button = Button(this).apply {// create button
                this.text = name
                this.textSize = 32F
                this.setTextColor( ContextCompat.getColor(context, R.color.white))
                this.setBackgroundColor(ContextCompat.getColor(context, R.color.magenta))
            }

            // Set layout parameters for the button
            val layoutParams = GridLayout.LayoutParams().apply {
                width = 0 // Let the weight handle the width
                height = 0 // Let the weight handle the height
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f) // Equal column weight
                rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f) // Equal row weight
                setMargins(4, 4, 4, 4) // Optional: add margin for spacing
            }
            //Load in grid
            button.layoutParams = layoutParams
            binding.buttonsGrid.addView(button)
            //Set event
            button.setOnClickListener{writeInResultText(button.text.toString())  }
        }
    }

    private fun writeInResultText(butonText: String) {
        var actualText = binding.resultEditText.text.toString() // Get the current text in TextView

        // Default the string to write as the button text


        if(butonText=="C"){
            actualText = actualText.dropLast(1)
            binding.resultEditText.text = actualText
            return

        }

        if (actualText.isEmpty()) {
            // If the actual text is empty and the button is a non-numeric character, do nothing
            if (charactersNotNumeric.contains(butonText) || butonText=="0") return
        } else {
            if(butonText=="=" && !charactersNotNumeric.contains(actualText.last().toString())){
                if (actualText.contains("/0")) {
                    Toast.makeText(this,"No se puede dividir entre 0",Toast.LENGTH_SHORT).show()
                    return
                }
                var result = 0.0
                var currentOperator: String? = null
                var currentNumber = ""

                actualText.forEach { char ->
                    if (char.toString() in charactersNotNumeric) {
                        // Perform the operation when an operator is encountered
                        if (currentNumber.isNotEmpty()) {
                            result = performOperation(result, currentNumber.toDouble(), currentOperator)
                            currentNumber = "" // Reset currentNumber for the next part
                        }
                        currentOperator = char.toString() // Update the operator
                    } else {
                        // Accumulate digits for the current number
                        currentNumber += char
                    }
                }

                // Handle the last number
                if (currentNumber.isNotEmpty()) {
                    result = performOperation(result, currentNumber.toDouble(), currentOperator)
                }

                // Update the result in the text view
                actualText = result.toString()
                binding.resultEditText.text = actualText
                return
            }
            // Get the last character of the current text
            val lastValue = actualText.last().toString()

            // If the last character is non-numeric and the button is also non-numeric, prevent duplicate operators
            if (charactersNotNumeric.contains(lastValue) && charactersNotNumeric.contains(butonText)) {
                return
            }
        }

        // Update the result text with the new value
        binding.resultEditText.text = actualText + butonText
    }
    //Calculate operations
    private fun performOperation(accumulatedResult: Double, nextNumber: Double, operator: String?): Double {
        return when (operator) {
            "+" -> accumulatedResult + nextNumber
            "-" -> accumulatedResult - nextNumber
            "X" -> accumulatedResult * nextNumber
            "/" -> if (nextNumber != 0.0) accumulatedResult / nextNumber else throw ArithmeticException("Division by zero")
            else -> nextNumber // If no operator, return the current number (initial case)
        }
    }
    private fun initAtributes() {//Init atributes
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        charactersNotNumeric = mutableListOf("+","-","X","/","C","=")


    }
}