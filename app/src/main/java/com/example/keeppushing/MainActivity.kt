package com.example.keeppushing

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.keeppushing.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var number = 1
    private var probability = 0.01

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textViewProbability.text = getString(R.string.probability) + probability.toString()
        binding.buttonStartAgain.visibility = View.GONE

        binding.buttonJackpot.setOnClickListener {
            number++
            binding.textViewNumber.text = number.toString()
            binding.textViewCategory.text = if (number % 2 == 0) getString(R.string.even) else getString(R.string.odd)

            if (number % 2 != 0 && number > 10 && isJackpot()) {
                binding.buttonJackpot.isEnabled = false
                binding.buttonStartAgain.visibility = View.VISIBLE

                showJackpotDialog(number)
            } else if (number % 2 != 0 && number > 10) {
                increaseJackpotProbability()
            }
        }

    binding.buttonStartAgain.setOnClickListener {
        resetGame()
    }
        }

    @SuppressLint("SetTextI18n")
    private fun resetGame() {
    number = 1
    probability = 0.01
    binding.textViewNumber.text = number.toString()
    binding.textViewCategory.text = getString(R.string.odd)
    binding.buttonJackpot.isEnabled = true
    binding.textViewProbability.text = getString(R.string.probability) + probability.toString()
    binding.buttonStartAgain.visibility = View.GONE
}

    private fun isJackpot(): Boolean {
        return Random.nextDouble() < probability
    }

    private fun increaseJackpotProbability() {
        probability = (probability + 0.01).coerceAtMost(0.05)
        binding.textViewProbability.text = getString(R.string.probability) + probability.toString()
    }

    private fun showJackpotDialog(jackpotValue: Int) {
        AlertDialog.Builder(this)
            .setTitle("Selamat!")
            .setMessage("Anda 'Jackpot' pada angka $jackpotValue.")
            .setPositiveButton("Oke") { _, _ -> }
            .show()
    }
}