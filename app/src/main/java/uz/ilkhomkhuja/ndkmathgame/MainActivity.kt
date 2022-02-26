package uz.ilkhomkhuja.ndkmathgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import android.widget.Toast
import com.ankushgrover.hourglass.Hourglass
import uz.ilkhomkhuja.ndkmathgame.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var hourglass: Hourglass
    private var result: Double? = null
    private lateinit var random: Random
    private var accepted = 0
    var wrongAnswer = 0
    var isWrongAnswer = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        random = Random()

        hourglass = object : Hourglass(21 * 1000, 1000) {
            override fun onTimerTick(timeRemaining: Long) {
                binding.timeTv.text = (timeRemaining / 1000).toString()
                isWrongAnswer = timeRemaining != 0L
            }

            override fun onTimerFinish() {
                if (!isWrongAnswer) {
                    Toast.makeText(this@MainActivity, "Time Ended!!", Toast.LENGTH_SHORT).show()
                    isWrongAnswer = false
                    binding.timeTv.text = "0"
                    wrongAnswer += 1
                    binding.wrongAnswerTv.text = wrongAnswer.toString()
                    startTimer()
                    setNewAnswer()
                } else {
                    isWrongAnswer = false
                    binding.timeTv.text = "0"
                    wrongAnswer += 1
                    binding.wrongAnswerTv.text = wrongAnswer.toString()
                    startTimer()
                    setNewAnswer()
                }
            }
        }

        hourglass.startTimer()
        setNewAnswer()

        binding.nextBtn.setOnClickListener {
            val a = binding.resultEt.text.toString()
            if (a.trim().isNotEmpty()) {
                val myResult = a.toDouble()
                if (myResult == result) {
                    Toast.makeText(this, "Your answer is Correct", Toast.LENGTH_SHORT)
                        .show()
                    binding.resultEt.text.clear()
                    isWrongAnswer = false
                    accepted += 1
                    binding.correctAnswer.text = accepted.toString()
                    hourglass.startTimer()
                    setNewAnswer()
                } else {
                    Toast.makeText(this, "Your answer incorrect", Toast.LENGTH_SHORT).show()
                    wrongAnswer += 1
                    binding.resultEt.text.clear()
                    isWrongAnswer = true
                    hourglass.stopTimer()
                    hourglass.startTimer()
                    setNewAnswer()
                }
            }
        }
    }

    private fun setNewAnswer() {
        result = null
        val firstNumber = random.nextInt(100)
        val secondNumber = random.nextInt(100)
        binding.firstNumber.text = firstNumber.toString()
        binding.secondNumber.text = secondNumber.toString()
        when (random.nextInt(4)) {
            0 -> {
                binding.tv.text = "+"
                result = addition(firstNumber.toDouble(), secondNumber.toDouble())
            }
            1 -> {
                binding.tv.text = "-"
                result = subtraction(firstNumber.toDouble(), secondNumber.toDouble())
            }

            2 -> {
                binding.tv.text = "*"
                result = multiply(firstNumber.toDouble(), secondNumber.toDouble())
            }

            else -> {
                binding.tv.text = "÷"
                val div = division(firstNumber.toDouble(), secondNumber.toDouble())
                val length = div.toString().length
                result = if (length in 0..3) {
                    div
                } else {
                    div.toString().substring(0, 4).toDouble()
                }
            }
        }
    }

    private external fun addition(firstNumber: Double, secondNumber: Double): Double
    private external fun subtraction(firstNumber: Double, secondNumber: Double): Double
    private external fun multiply(firstNumber: Double, secondNumber: Double): Double
    private external fun division(firstNumber: Double, secondNumber: Double): Double

    companion object {
        init {
            System.loadLibrary("ndkmathgame")
        }
    }
}