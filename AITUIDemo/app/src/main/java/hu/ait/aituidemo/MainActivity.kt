package hu.ait.aituidemo

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

            binding.tvData.text = "${Date(System.currentTimeMillis()).toString()}"

            Toast.makeText(this, "Number: ${binding.etNum.text.toString().toInt()}",
                Toast.LENGTH_LONG).show()
        }

    }
}