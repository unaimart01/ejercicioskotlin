package com.example.ejercicioskotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ejercicioskotlin.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.PI
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.calculateButton.setOnClickListener { calculateTip() }
    }
    fun calculateTip() {
        val stringInTextField = binding.costOfService.text.toString()
        val cost = stringInTextField.toDoubleOrNull()
        if (cost == null) {
            binding.tipResult.text = ""
            return
        }

        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        var tip = tipPercentage * cost
        if (binding.roundUpSwitch.isChecked) {
            tip = kotlin.math.ceil(tip)
        }

        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }
    }

    fun main() {
        val squareCabin = SquareCabin(6, 50.0)
        val roundHut = RoundHut(3, 10.0)
        val roundTower = RoundTower(4, 15.5)

        with(squareCabin) {
            println("\nSquare Cabin\n============")
            println("Capacity: ${capacity}")
            println("Material: ${buildingMaterial}")
            println("Floor area: ${floorArea()}")
        }

        with(roundHut) {
            println("\nRound Hut\n=========")
            println("Material: ${buildingMaterial}")
            println("Capacity: ${capacity}")
            println("Floor area: ${floorArea()}")
            println("Has room? ${hasRoom()}")
            getRoom()
            println("Has room? ${hasRoom()}")
            getRoom()
            println("Carpet size: ${calculateMaxCarpetSize()}")
        }

        with(roundTower) {
            println("\nRound Tower\n==========")
            println("Material: ${buildingMaterial}")
            println("Capacity: ${capacity}")
            println("Floor area: ${floorArea()}")
            println("Carpet size: ${calculateMaxCarpetSize()}")
        }
    }


    abstract class Dwelling(private var residents: Int) {
        abstract val buildingMaterial: String
        abstract val capacity: Int


        abstract fun floorArea(): Double


        fun hasRoom(): Boolean {
            return residents < capacity
        }


        fun getRoom() {
            if (capacity > residents) {
                residents++
                println("You got a room!")
            } else {
                println("Sorry, at capacity and no rooms left.")
            }
        }

    }


    class SquareCabin(residents: Int, val length: Double) : Dwelling(residents) {
        override val buildingMaterial = "Wood"
        override val capacity = 6


        override fun floorArea(): Double {
            return length * length
        }

    }


    open class RoundHut(
        val residents: Int, val radius: Double) : Dwelling(residents) {

        override val buildingMaterial = "Straw"
        override val capacity = 4

        override fun floorArea(): Double {
            return PI * radius * radius
        }


        fun calculateMaxCarpetSize(): Double {
            val diameter = 2 * radius
            return sqrt(diameter * diameter / 2)
        }

    }


    class RoundTower(
        residents: Int,
        radius: Double,
        val floors: Int = 2) : RoundHut(residents, radius) {

        override val buildingMaterial = "Stone"

        // Capacity depends on the number of floors.
        override val capacity = floors * 4


        override fun floorArea(): Double {
            return super.floorArea() * floors
        }
    }
