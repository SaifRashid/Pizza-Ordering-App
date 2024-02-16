package com.example.pizzaorderingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var image: ImageView
    private lateinit var radioGroup: RadioGroup
    private lateinit var spinner: Spinner
    private lateinit var spinnerText: String
    private lateinit var tomatoes: CheckBox
    private lateinit var mushrooms: CheckBox
    private lateinit var olives: CheckBox
    private lateinit var onions: CheckBox
    private lateinit var broccoli: CheckBox
    private lateinit var spinach: CheckBox
    private lateinit var spicySwitch: Switch
    private lateinit var text_spiciness_level: TextView
    private lateinit var seekBar: SeekBar
    private lateinit var quantity: TextView
    private lateinit var deliverySwitch: Switch
    private lateinit var subtotal: TextView
    private lateinit var tax: TextView
    private lateinit var total: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        image = findViewById(R.id.image_pizza)
        radioGroup = findViewById(R.id.radioGroup)
        spinner = findViewById(R.id.spinner)
        tomatoes = findViewById(R.id.check_tomatoes)
        mushrooms = findViewById(R.id.check_mushrooms)
        olives = findViewById(R.id.check_olives)
        onions = findViewById(R.id.check_onions)
        broccoli = findViewById(R.id.check_broccoli)
        spinach = findViewById(R.id.check_spinach)
        spicySwitch = findViewById(R.id.switch_spicy)
        text_spiciness_level = findViewById(R.id.text_spiciness_level)
        seekBar = findViewById(R.id.seek_spicy)
        quantity = findViewById(R.id.text_quantity)
        deliverySwitch = findViewById(R.id.switch_delivery)
        subtotal = findViewById(R.id.text_subtotal)
        tax = findViewById(R.id.text_tax)
        total = findViewById(R.id.text_total_price)

        val sizeList = listOf("Choose a pizza type", "Medium (\$9.99)", "Large (\$13.99)", "Extra Large (\$15.99)", "Party Size (\$25.99)")

        val sizeAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sizeList)
        spinner.adapter = sizeAdapter
        spinner.onItemSelectedListener = this

        // Listen seekBar change events: There are three override methods that must be implemented
        // though you may not necessarily use the last two
        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, value: Int, fromUser: Boolean) {
                // As the seekbar moves, the progress value is obtained and displayed in our seekBar label
                text_spiciness_level.text = "spiciness level: $value"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    fun radioButtonPizzas(view: View) {
        val id = when (view.id) {
            R.id.radio_pepperoni -> R.drawable.pepperoni
            R.id.radio_bbq_chicken -> R.drawable.bbq_chicken
            R.id.radio_margherita -> R.drawable.margheritta
            else -> R.drawable.hawaiian
        }
        image.setImageResource(id)
        calculatePrice()
    }

    fun checkBoxes(view: View) {
        calculatePrice()
    }

    fun buttons(view: View) {
        var quantityInt = quantity.text.toString().toInt()

        if (view.id == R.id.button_plus) {
            quantityInt++
            quantity.text = "$quantityInt"
        } else if (view.id == R.id.button_minus) {
            if(quantityInt > 1) {
                quantityInt--
                quantity.text = "$quantityInt"
            }
        }
        calculatePrice()
    }

    fun switchSpicy(view: View) {

        val visible = spicySwitch.isChecked

        if (visible) {
            spicySwitch.text = "Yes, $1.00"
        } else {
            spicySwitch.text = "No, $0.00"
        }
        text_spiciness_level.visibility = if (visible) View.VISIBLE else View.INVISIBLE
        seekBar.visibility = if (visible) View.VISIBLE else View.INVISIBLE
        calculatePrice()
    }

    fun switchDelivery(view: View) {
        if (deliverySwitch.isChecked) {
            deliverySwitch.text = "Yes, $2.00"
        } else {
            deliverySwitch.text = "No, $0.00"
        }
        calculatePrice()
    }

    private fun calculatePrice() {
        val quantityInt = quantity.text.toString().toInt()
        var subtotalDouble = 0.0
        var taxDouble = 0.0
        var totalDouble = 0.0

        // Return if no pizza selected
        val selectedRadioId = radioGroup.checkedRadioButtonId
        if (selectedRadioId == -1) {
            return
        }

        subtotalDouble += when (spinnerText) {
            "Medium (\$9.99)" -> 9.99
            "Large (\$13.99)" -> 13.99
            "Extra Large (\$15.99)" -> 15.99
            "Party Size (\$25.99)" -> 25.99
            else -> 0.0
        }

        if (tomatoes.isChecked)
            subtotalDouble += 1
        if (mushrooms.isChecked)
            subtotalDouble += 2.3
        if (olives.isChecked)
            subtotalDouble += 1.7
        if (onions.isChecked)
            subtotalDouble += 1.25
        if (broccoli.isChecked)
            subtotalDouble += 1.8
        if (spinach.isChecked)
            subtotalDouble += 2
        if (spicySwitch.isChecked)
            subtotalDouble += 1

        subtotalDouble *= quantityInt
        taxDouble = subtotalDouble * 0.0635
        totalDouble = subtotalDouble + taxDouble
        if (deliverySwitch.isChecked)
            totalDouble += 2

        subtotal.text = "$" + String.format("%.2f", subtotalDouble)
        tax.text = "$" + String.format("%.2f", taxDouble)
        total.text = "$" + String.format("%.2f", totalDouble)
    }

    fun reset(view: View) {
        image.setImageResource(R.drawable.pizza_crust)
        radioGroup.clearCheck()
        spinner.setSelection(0)

        tomatoes.isChecked = false
        mushrooms.isChecked = false
        olives.isChecked = false
        onions.isChecked = false
        broccoli.isChecked = false
        spinach.isChecked = false

        seekBar.progress = 1
        spicySwitch.isChecked = false
        switchSpicy(spicySwitch)

        quantity.text = "1"
        subtotal.text = "$0.00"
        tax.text = "$0.00"
        deliverySwitch.isChecked = false
        switchDelivery(deliverySwitch)
        total.text = "$0.00"
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        spinnerText = parent?.getItemAtPosition(position) as String
        calculatePrice()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}