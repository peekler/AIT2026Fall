package hu.ait.dynamicuidemo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import hu.ait.dynamicuidemo.databinding.ActivityMainBinding
import hu.ait.dynamicuidemo.databinding.TodoRowBinding

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


        binding.btnSave.setOnClickListener {
            val bindingTodoRow = TodoRowBinding.inflate(layoutInflater)

            bindingTodoRow.todoTitle.text =
                binding.etTodo.text.toString()
            bindingTodoRow.btnDel.setOnClickListener {
                binding.layoutTodos.removeView(bindingTodoRow.root)
            }

            binding.layoutTodos.addView(bindingTodoRow.root)

            binding.etTodo.setText("")
        }
    }
}