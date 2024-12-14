package com.example.calculadorabasica

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.GridLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculadorabasica.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //Atributes
    private lateinit var binding: ActivityMainBinding

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

            button.setOnClickListener{
                Log.d("btn",button.text.toString())
            }
        }
    }

    private fun initAtributes() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}