package com.example.pizzaorderingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // SeekBar Listener
        val spicy = findViewById<TextView>(R.id.text_spiciness_level)
        // Listen seekBar change events: There are three override methods that must be implemented
        // though you may not necessarily use the last two
        findViewById<SeekBar>(R.id.seek_spicy).setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, value: Int, fromUser: Boolean) {
                // As the seekbar moves, the progress value is obtained and displayed in our seekBar label
                spicy.text = "spiciness level: $value"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    fun radioButtonClick (view: View) {
        val image = findViewById<ImageView>(R.id.image_pizza)

        val id = when (view.id) {
            R.id.radio_pepperoni -> R.drawable.pepperoni
            R.id.radio_margherita -> R.drawable.margheritta
            R.id.radio_bbq_chicken -> R.drawable.bbq_chicken
            else -> R.drawable.hawaiian
        }
        image.setImageResource(id)
    }

    fun buttons (view: View) {
        var quantity = findViewById<TextView>(R.id.text_quantity).text.toString().toInt()

        if (view.id == R.id.button_plus) {
            quantity++
            updateQuantity(quantity)
        } else if (view.id == R.id.button_minus) {
            if(quantity > 1) {
                quantity--
                updateQuantity(quantity)
            }
        }
    }

    fun spicySwitch(view: View) {
        val spicyYesNo = findViewById<Switch>(R.id.switch_spicy)
        val spicyLevel = findViewById<TextView>(R.id.text_spiciness_level)
        val seekSpicy = findViewById<SeekBar>(R.id.seek_spicy)

        var isVisible = spicyYesNo.isChecked

        if (isVisible) {
            spicyYesNo.text = "Yes, $1.00"
        } else {
            spicyYesNo.text = "No, $0.00"
        }
        spicyLevel.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
        seekSpicy.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }

    private fun updateQuantity(value: Int) {
        val quantity = findViewById<TextView>(R.id.text_quantity)

        quantity.text = "$value"
    }
}
