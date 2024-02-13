package com.example.pizzaorderingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
        } else if (view.id == R.id.switch_spicy) {
            if (view is Switch && view.isChecked) {
                updateSpicy(true)
            } else {
                updateSpicy(false)
            }
        }

    }
    fun switchSpicy(view: View) {
        if (view is Switch && view.isChecked) {
            updateSpicy(true)
        } else {
            updateSpicy(false)
        }
    }
    private fun updateQuantity(value: Int) {
        val quantity = findViewById<TextView>(R.id.text_quantity)

        quantity.text = "$value"
    }

    private fun updateSpicy(isVisible: Boolean) {
        val spicyYesNo = findViewById<TextView>(R.id.text_spicy_yes_no)
        val spicyLevel = findViewById<TextView>(R.id.text_spiciness_level)
        val seekSpicy = findViewById<SeekBar>(R.id.seek_spicy)

        if (isVisible) {
            spicyYesNo.text = "Yes, $1.00"
        } else {
            spicyYesNo.text = "No, $0.00"
        }
        spicyLevel.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
        seekSpicy.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }
}