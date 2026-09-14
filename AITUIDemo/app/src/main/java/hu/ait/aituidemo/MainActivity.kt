package hu.ait.aituidemo

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import hu.ait.aituidemo.databinding.ActivityMainBinding
import java.util.Date

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // UI code here
        //val btnDemo = findViewById<Button>(R.id.btnDemo)
        //val tvData = findViewById<TextView>(R.id.tvData)

        binding.btnDemo.setOnClickListener {
            try {

                Log.d("TAG_DEBUG", "demo button clicked")

                if (binding.etNum.text.isNotEmpty()) {
                    val num = binding.etNum.text.toString().toInt()
                    val b = 6 / num

                    binding.tvData.text = "${Date(System.currentTimeMillis()).toString()} $b"
                    /*Toast.makeText(
                this,
                "Number: ${binding.etNum.text.toString().toInt()}",
                Toast.LENGTH_LONG).show()*/

                    Snackbar.make(
                        binding.root,
                        "Number: ${binding.etNum.text.toString().toInt()}",
                        Snackbar.LENGTH_LONG
                    ).setAction(
                        "Undo",
                        {

                        })
                        .show()

                    revealCard()
                } else {
                    binding.etNum.error = "Write a number here!"
                }
            } catch (e: Exception)
            {
                e.printStackTrace()
                binding.etNum.error = "Wrong input! ${e.message}"
            }
         }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun revealCard() {
        val x = binding.cardView.getRight()
        val y = binding.cardView.getBottom()

        val startRadius = 0f
        val endRadius = Math.hypot(binding.cardView.getWidth().toDouble(),
            binding.cardView.getHeight().toDouble())

        val anim = ViewAnimationUtils.createCircularReveal(
            binding.cardView,
            x,
            y,
            startRadius,
            endRadius.toFloat()
        )

        binding.cardView.setVisibility(View.VISIBLE)
        anim.duration = 5000
        anim.start()
    }

}